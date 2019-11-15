package com.woniu.mzjOrder.service.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.woniu.mzjOrder.Bo.LocalFileWriterBean;
import com.woniu.mzjOrder.controller.WebSocketServer;
import com.woniu.mzjOrder.dao.NetInformationDao;
import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.ArticleRecordFilter;
import com.woniu.mzjOrder.entity.NetChildFilter;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.exception.CustomException;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.service.NetInformationService;
import com.woniu.mzjOrder.service.processor.ProcessorForGov_Common;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import com.woniu.mzjOrder.vo.NetInfoRuleMapBean;
import com.woniu.mzjOrder.vo.NetUrlVo;
import com.woniu.mzjOrder.vo.ResultStatusEnum;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.eclipse.jetty.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.*;
import java.util.*;

@Slf4j
@Service
public class NetInformationServiceImpl implements NetInformationService {

    @Autowired
    private NetInfoRuleMapBean infoRuleMapBean;
    // 本地下载类
    @Autowired
    private LocalFileWriterBean localFileWriterBean;
    @Autowired
    private NetInformationDao informationDao;
    @Autowired
    private WebClient webClient;
    /*@Autowired
    private WebSocketServer webSocketServer; //为什么是新的实例。*/

    private List<ArticleRecord> recordListForOneQuery = new ArrayList<>();

    private List<UrlMonitorEntity> recordListForOneQueryFailed;

    /**
     * 监控特定网页固定栏目的新闻动态。定时持久化到数据库
     * 用Jsoup实现
     */
    @Override
    public void loadNetNewsArticleToDB() {
        List<ArticleRecord> articleList;
        List<UrlMonitorEntity> urlEntities = informationDao.queryNetUrlEntity();
        recordListForOneQueryFailed = new ArrayList<>();
        /*Map<String, DocumentProcessor> allProcessorMap = infoRuleMapBean.getNetInfoRules();
        Map<String, Integer> netIsActiveNodeMap = infoRuleMapBean.getNetIsActiveNodeMap();
        Map<String, Integer> hasChildNetSiteMap = infoRuleMapBean.getHasChildNetSiteMap();
        Map<String, ChildDocumentRule> childDocumentRuleMap = infoRuleMapBean.getChildDocumentRuleMap();*/

        //articleList = getNewsArticle(urlEntities, netIsActiveNodeMap, hasChildNetSiteMap, childDocumentRuleMap);
        articleList = getNewsArticle(urlEntities, new ProcessorForGov_Common());
        saveToDB(articleList);
        //推送更新异常的网站信息
        if (recordListForOneQueryFailed.size() > 0) {
            //String message = recordListForOneQueryFailed.toString();
            /**
             * 考虑做成自定义异常。通过自定义异常处理，统一推送到客户端
             */
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("failedUrls", recordListForOneQueryFailed);
            throw new CustomException(ResultStatusEnum.URL_ANALYSIS_ERROR, jsonObject);
        }
    }

    private List<ArticleRecord> getNewsArticle(List<UrlMonitorEntity> UrlEntities,
                                               //Map<String, DocumentProcessor> allProcessorMap,
                                               DocumentProcessor documentProcessor
                                               //Map<String, Integer> netIsActiveNodeMap,
                                               //Map<String, Integer> hasChildNetSiteMap,
                                               //Map<String, ChildDocumentRule> childDocumentRuleMap
    ) {
        List<ArticleRecord> articleList = new ArrayList<>();
        //Map<String, DocumentProcessor> processorMap = new HashMap<>();

        for (UrlMonitorEntity urlEntity : UrlEntities) {
            try {
                ArticleRecordFilter recordFilter = urlEntity.getArticleRecordFilter();
                NetChildFilter childFilter = urlEntity.getNetChildFilter();
                Integer isActiveNode = recordFilter.getIfActiveAsync();
                DocumentProcessor processor = documentProcessor;
                Integer hasChildSite = (childFilter == null ? 0 : 1);

                Document document;
                if (isActiveNode != null && isActiveNode == 1) {
                    HtmlPage page = null;
                    page = webClient.getPage(urlEntity.getConnectUrl());
                    webClient.waitForBackgroundJavaScript(3000);//异步JS执行需要耗时,所以这里线程要阻塞3秒,等待异步JS执行结束
                    String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串
                    document = Jsoup.parse(pageXml);//获取html文档
                } else {
                    document = Jsoup.connect(urlEntity.getConnectUrl()).maxBodySize(0).timeout(10000).get();
                }
                //深度优先加载每个子页面
                if (hasChildSite != null && hasChildSite == 1) {
                    //List<ArticleRecord> recordList = new ArrayList<>();
                    List<UrlMonitorEntity> monitorEntities = getChildMonitorEntity(document, urlEntity);
                    /*for (UrlMonitorEntity monitorEntity : monitorEntities) {
                        processorMap.put(monitorEntity.getName(),processor);
                    }*/
                    List<ArticleRecord> childList = getNewsArticle(monitorEntities, processor);
                    articleList.addAll(childList);
                    continue;
                }
                if (processor != null) {
                    List<ArticleRecord> records = processor.findAndExplainToArticleRecord(document, urlEntity);
                    articleList.addAll(records);
                }
            } catch (Exception e) {
                log.error("{}网址解析异常:{}", urlEntity.getName(), e.toString());
                recordListForOneQueryFailed.add(urlEntity);
                continue;
            }
        }
        return articleList;
    }

    private int saveToDB(List<ArticleRecord> records) {
        try {
            informationDao.saveArticleRecords(records);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 根据参数读取数据库中已经持久化的记录
     *
     * @param queryParamVo
     * @return
     */
    @Override
    public List<ArticleRecord> queryNetNewsArticle(NetInfoQueryParamVo queryParamVo) {
        recordListForOneQuery = informationDao.queryArticleRecordsByParams(queryParamVo);
        return recordListForOneQuery;
    }

    @Override
    public void saveToLocalFile() throws FileNotFoundException {
        localFileWriterBean.saveToFile(recordListForOneQuery);
    }

    @Override
    public List<UrlMonitorEntity> queryUrlEntities() {
        List<UrlMonitorEntity> urlEntities = informationDao.queryNetUrlEntity();
        return urlEntities;
    }

    @Override
    public void sendWebSocketMessage(WebSocketServer socketServer, Object message) {
        socketServer.sendMessage(message);
    }

    @Override
    @Transactional
    public void saveNetUrl(UrlMonitorEntity urlMonitorEntity) {
        //UrlMonitorEntity urlMonitorEntity = netUrlVo.getUrlMonitorEntity();
        NetChildFilter childFilter = urlMonitorEntity.getNetChildFilter();
        ArticleRecordFilter recordFilter = urlMonitorEntity.getArticleRecordFilter();
        //Assert.notNull(urlMonitorEntity, "网页实体不能为空！");
        if (StringUtil.isNotBlank(urlMonitorEntity.getName())){
            throw new CustomException(3, "网页实体不能为空");
        }
        //Assert.notNull(recordFilter, "列表过滤规则不能为空！");
        if (recordFilter == null){
            throw new CustomException(3, "列表过滤规则不能为空");
        }
        //保存添加的网站，子页面过滤规则以及列表记录筛选规则
        Integer urlCnt = informationDao.queryUrlEntity(urlMonitorEntity.getName());
        Integer childFilterCnt = informationDao.queryChildFilter(childFilter.getChildFilterId());
        Integer recordFilterCnt = informationDao.queryArticleRecordFilter(recordFilter.getFilterId());
        Integer urlRecordRelationCnt = informationDao.queryUrlRecordRelation(urlMonitorEntity.getName());
        String res = "";
        if (urlCnt > 0) {
            res = "网址已存在！";
            throw new CustomException(3, res);
        } else if (childFilterCnt > 0) {
            res = "子页过滤条件名已存在";
            throw new CustomException(3, res);
        } else if (recordFilterCnt > 0) {
            res = "列表记录筛选规则名已存在";
            throw new CustomException(3, res);
        } else if (urlRecordRelationCnt > 0) {
            res = "已存在相同筛选RULE";
            throw new CustomException(3, res);
        }
        informationDao.saveUrlEntity(urlMonitorEntity);
        informationDao.saveNetChildFilter(childFilter);
        informationDao.saveRecordFilter(recordFilter);
        Map<String, String> relationParam = new HashMap<>();
        relationParam.put("urlName", urlMonitorEntity.getName());
        relationParam.put("childFilterId", childFilter.getChildFilterId());
        relationParam.put("recordFilterId", recordFilter.getFilterId());
        informationDao.saveUrlRecordRelation(relationParam);

    }

    @Override
    public List<ArticleRecord> testUrlEntity(UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> records;
        List<UrlMonitorEntity> monitorEntities = new ArrayList<>();
        monitorEntities.add(urlMonitorEntity);
        records = getNewsArticle(monitorEntities, new ProcessorForGov_Common());
        return records;
    }

}
