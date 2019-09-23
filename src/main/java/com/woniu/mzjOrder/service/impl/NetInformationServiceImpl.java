package com.woniu.mzjOrder.service.impl;

import com.woniu.mzjOrder.Bo.LocalFileWriterBean;
import com.woniu.mzjOrder.dao.NetInformationDao;
import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.service.NetInformationService;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import com.woniu.mzjOrder.vo.NetInfoRule;
import com.woniu.mzjOrder.vo.NetInfoRuleListBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class NetInformationServiceImpl implements NetInformationService {

    @Autowired
    private NetInfoRuleListBean infoRuleListBean;
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
        List<NetInfoRule> infoRules =  infoRuleListBean.getNetInfoRules();

        for (NetInfoRule rule : infoRules) {
            Document document = Jsoup.connect(rule.getRootUrl()).timeout(3000).get();
            List<ArticleRecord> records = rule.getProcessor().findAndExplain(document);
            articleList.addAll(records);
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
