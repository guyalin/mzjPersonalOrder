package com.woniu.mzjOrder.service;

import com.woniu.mzjOrder.entity.ArticleRecord;
import org.jsoup.nodes.Document;

import java.util.List;

public interface DocumentProcessor {
    List<ArticleRecord> findAndExplain(Document document);
}
