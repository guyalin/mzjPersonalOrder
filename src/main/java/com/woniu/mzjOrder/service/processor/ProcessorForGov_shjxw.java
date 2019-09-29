package com.woniu.mzjOrder.service.processor;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.vo.NodeRule;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

@Slf4j
public class ProcessorForGov_shjxw implements DocumentProcessor {
    @Override
    public List<ArticleRecord> findAndExplain(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords;
        Element e = document.select("ul.list").first();
        Elements es = e.select("div.li");
        NodeRule nodeRule = new NodeRule("h1","a[href]","span", false, 2);
        articleRecords = elementsAnalysisType1(es, document, urlMonitorEntity, nodeRule);
        return articleRecords;
    }
}
