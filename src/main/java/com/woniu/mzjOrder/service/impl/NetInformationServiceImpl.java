package com.woniu.mzjOrder.service.impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.woniu.mzjOrder.Bo.LocalFileWriterBean;
import com.woniu.mzjOrder.dao.NetInformationDao;
import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.service.NetInformationService;
import com.woniu.mzjOrder.vo.ChildDocumentRule;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import com.woniu.mzjOrder.vo.NetInfoRuleMapBean;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 监控特定网页固定栏目的新闻动态。定时持久化到数据库
     * 用Jsoup实现
     */
    @Override
    public void loadNetNewsArticleToDB() {
        List<ArticleRecord> articleList;
        List<UrlMonitorEntity> UrlEntities =  informationDao.queryNetUrlEntity();
        Map<String, DocumentProcessor> allProcessorMap = infoRuleMapBean.getNetInfoRules();
        Map<String, Integer> netIsActiveNodeMap = infoRuleMapBean.getNetIsActiveNodeMap();
        Map<String, Integer> hasChildNetSiteMap = infoRuleMapBean.getHasChildNetSiteMap();
        Map<String, ChildDocumentRule> childDocumentRuleMap = infoRuleMapBean.getChildDocumentRuleMap();

        articleList = getNewsArticle(UrlEntities, allProcessorMap, netIsActiveNodeMap, hasChildNetSiteMap, childDocumentRuleMap);

        saveToDB(articleList);
    }

    private List<ArticleRecord> getNewsArticle(List<UrlMonitorEntity> UrlEntities,
                                               Map<String, DocumentProcessor> allProcessorMap,
                                               Map<String, Integer> netIsActiveNodeMap,
                                               Map<String, Integer> hasChildNetSiteMap,
                                               Map<String, ChildDocumentRule> childDocumentRuleMap) {
        List<ArticleRecord> articleList = new ArrayList<>();
        Map<String, DocumentProcessor> processorMap = new HashMap<>();
        Map<String, Integer> netIsActiveNode = new HashMap<>();
        Map<String, Integer> hasChildNetSite = new HashMap<>();
        Map<String, ChildDocumentRule> childDocumentRule = new HashMap<>();
        for (UrlMonitorEntity urlEntity : UrlEntities) {
            try {
                Integer isActiveNode = netIsActiveNodeMap.get(urlEntity.getName());
                DocumentProcessor processor = allProcessorMap.get(urlEntity.getName());
                Integer hasChildSite = hasChildNetSiteMap.get(urlEntity.getName());

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
                if (hasChildSite!= null && hasChildSite == 1) {
                    //List<ArticleRecord> recordList = new ArrayList<>();
                    List<UrlMonitorEntity> monitorEntities = getChildMonitorEntity(document, childDocumentRuleMap.get(urlEntity.getName()), urlEntity.getName(), urlEntity.getArea());
                    for (UrlMonitorEntity monitorEntity : monitorEntities) {
                        processorMap.put(monitorEntity.getName(),processor);
                    }
                    List<ArticleRecord> childList = getNewsArticle(monitorEntities, processorMap, netIsActiveNode, hasChildNetSite, childDocumentRule);
                    articleList.addAll(childList);
                    continue;
                }
                if (processor != null) {
                    List<ArticleRecord> records = processor.findAndExplainToArticleRecord(document, urlEntity);
                    articleList.addAll(records);
                }
            } catch (Exception e) {
                log.error("{}网址解析异常:{}", urlEntity.getName(), e.toString());
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
    public void queryNetNewsArticleToFile(NetInfoQueryParamVo queryParamVo) throws FileNotFoundException {
        List<ArticleRecord> articleList;
        articleList = informationDao.queryArticleRecordsByParams(queryParamVo);
        localFileWriterBean.saveToFile(articleList);
    }

}
