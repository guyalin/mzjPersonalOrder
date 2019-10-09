package com.woniu.mzjOrder.service.processor;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.vo.NodeRule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class ProcessorForGov_jsfgw implements DocumentProcessor {
    @Override
    public List<ArticleRecord> findAndExplain(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords;
        Elements es = baseCDataTypeAnalysis(document, "3227");

        NodeRule nodeRule = new NodeRule("li","a[href]","", true, 1);
        articleRecords = elementsAnalysisType1(es, document, urlMonitorEntity, nodeRule);

        return articleRecords;

    }
}
