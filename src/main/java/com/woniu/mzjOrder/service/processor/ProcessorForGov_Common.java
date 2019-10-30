package com.woniu.mzjOrder.service.processor;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class ProcessorForGov_Common implements DocumentProcessor {
    @Override
    public List<ArticleRecord> findAndExplainToArticleRecord(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords = new ArrayList<>();
        articleRecords = explainToArticleRecord(document, urlMonitorEntity);
        return articleRecords;
    }
}
