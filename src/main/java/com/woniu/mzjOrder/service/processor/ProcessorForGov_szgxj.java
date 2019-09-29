package com.woniu.mzjOrder.service.processor;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.vo.NodeRule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class ProcessorForGov_szgxj implements DocumentProcessor {
    @Override
    public List<ArticleRecord> findAndExplain(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords;
        Element e = document.select("div.conter_listCont").first();
        Element el = e.select("ul").first();
        Elements es = el.select("li");
        NodeRule nodeRule = new NodeRule("","a[href]","span", false, 2);
        articleRecords = elementsAnalysisType1(es, document, urlMonitorEntity, nodeRule);
        return articleRecords;
    }
}
