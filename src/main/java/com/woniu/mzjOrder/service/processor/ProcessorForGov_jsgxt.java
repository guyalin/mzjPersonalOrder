package com.woniu.mzjOrder.service.processor;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.util.DateUtil;
import com.woniu.mzjOrder.vo.NodeRule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessorForGov_jsgxt implements DocumentProcessor {
    @Override
    public List<ArticleRecord> findAndExplain(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords;
        Elements es = baseCDataTypeAnalysis(document, "17739");
        NodeRule nodeRule = new NodeRule("li","a[href]","span", true, 1);
        articleRecords = elementsAnalysisType1(es, document, urlMonitorEntity, nodeRule);
        return articleRecords;
    }
}
