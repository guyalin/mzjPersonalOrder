package com.woniu.mzjOrder.service.impl;

import com.woniu.mzjOrder.Bo.LocalFileWriterBean;
import com.woniu.mzjOrder.dao.NetInformationDao;
import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.service.NetInformationService;
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

    /**
     * 监控特定网页固定栏目的新闻动态。定时持久化到数据库
     * 用Jsoup实现
     */
    @Override
    public void loadNetNewsArticleToDB() throws IOException {
        List<ArticleRecord> articleList = new ArrayList<>();
        List<UrlMonitorEntity> UrlEntities =  informationDao.queryNetUrlEntity();
        Map<String, DocumentProcessor> allProcessorMap = infoRuleMapBean.getNetInfoRules();

        for (UrlMonitorEntity urlEntity : UrlEntities) {
            try{
                DocumentProcessor processor = allProcessorMap.get(urlEntity.getName());
                Document document = Jsoup.connect(urlEntity.getConnectUrl()).maxBodySize(0).timeout(3000).get();
                if (processor != null) {
                    List<ArticleRecord> records = processor.findAndExplain(document, urlEntity);
                    articleList.addAll(records);
                }
            }catch (Exception e){
                log.error("网址解析异常:{}",e.toString());
                continue;
            }
        }
        saveToDB(articleList);
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
