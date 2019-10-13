package com.woniu.mzjOrder.service.processor;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import org.jsoup.nodes.Document;

import java.util.List;

public class ProcessorForGovCommon implements DocumentProcessor {
    @Override
    public List<ArticleRecord> findAndExplainToArticleRecord(Document document, UrlMonitorEntity urlMonitorEntity) {
        return null;
    }
}
