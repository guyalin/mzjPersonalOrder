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
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.service.NetInformationService;
import com.woniu.mzjOrder.service.processor.ProcessorForGov_Common;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import com.woniu.mzjOrder.vo.NetInfoRuleMapBean;
import com.woniu.mzjOrder.vo.NetUrlVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
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
        List<UrlMonitorEntity> urlEntities =  informationDao.queryNetUrlEntity();
        recordListForOneQueryFailed = new ArrayList<>();
        /*Map<String, DocumentProcessor> allProcessorMap = infoRuleMapBean.getNetInfoRules();
        Map<String, Integer> netIsActiveNodeMap = infoRuleMapBean.getNetIsActiveNodeMap();
        Map<String, Integer> hasChildNetSiteMap = infoRuleMapBean.getHasChildNetSiteMap();
        Map<String, ChildDocumentRule> childDocumentRuleMap = infoRuleMapBean.getChildDocumentRuleMap();*/

        //articleList = getNewsArticle(urlEntities, netIsActiveNodeMap, hasChildNetSiteMap, childDocumentRuleMap);
        articleList = getNewsArticle(urlEntities, new ProcessorForGov_Common());
        saveToDB(articleList);
        //推送更新异常的网站信息
        if (recordListForOneQueryFailed.size() >0 ){
            //String message = recordListForOneQueryFailed.toString();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("failedUrls", recordListForOneQueryFailed);
            for (WebSocketServer server : WebSocketServer.webSocketSet.values()) {
                sendWebSocketMessage(server, jsonObject);
            }
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
                    document = Jsoup.connect(urlEntity.getConnectUrl()).maxBodySize(0).timeout(5000).get();
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
        try{
            informationDao.saveArticleRecords(records);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * 根据参数读取数据库中已经持久化的记录
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
    public List<UrlMonitorEntity> queryUrlEntities(){
        List<UrlMonitorEntity> urlEntities =  informationDao.queryNetUrlEntity();
        return urlEntities;
    }

    @Override
    public void sendWebSocketMessage(WebSocketServer socketServer, Object message){
        socketServer.sendMessage(message);
    }

    @Override
    @Transactional
    public void saveNetUrl(NetUrlVo netUrlVo) {
        UrlMonitorEntity urlMonitorEntity = netUrlVo.getUrlMonitorEntity();
        NetChildFilter childFilter = netUrlVo.getNetChildFilter();
        ArticleRecordFilter recordFilter = netUrlVo.getArticleRecordFilter();
        Assert.notNull(urlMonitorEntity, "网页实体不能为空！");
        Assert.notNull(recordFilter, "列表过滤规则不能为空！");
        //保存添加的网站，子页面过滤规则以及列表记录筛选规则
        informationDao.saveUrlEntity(urlMonitorEntity);
        informationDao.saveNetChildFilter(childFilter);
        informationDao.saveRecordFilter(recordFilter);
    }

    @Override
    public List<ArticleRecord> testUrlEntity(UrlMonitorEntity urlMonitorEntity){
        List<ArticleRecord> records;
        List<UrlMonitorEntity> monitorEntities = new ArrayList<>();
        monitorEntities.add(urlMonitorEntity);
        records = getNewsArticle(monitorEntities, new ProcessorForGov_Common());
        return records;
    }

}
