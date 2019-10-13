package com.woniu.mzjOrder.service.processor;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.vo.DateRule;
import com.woniu.mzjOrder.vo.NodeRule;
import com.woniu.mzjOrder.vo.TextLocationEnum;
import com.woniu.mzjOrder.vo.TitleRule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class ProcessorForGov_zgkjb implements DocumentProcessor {
    @Override
    public List<ArticleRecord> findAndExplainToArticleRecord(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords = null;
        Element eRoot = document.select("div.rowInRecord").first();
        Element tBody = eRoot.select("tbody").first();
        Elements trs = tBody.select("tr:gt(0)");

        TitleRule titleRule = new TitleRule("div.Tip tr:eq(1) td td",0,true,false,"");
        DateRule dateRule = new DateRule("tr",0, TextLocationEnum.DATA,"[0-9]{4}-[0-1]{0,1}[0-9]-[0-3]{0,1}[0-9]");
        NodeRule nodeRule = new NodeRule("a[href]",titleRule,dateRule);
        articleRecords = elementsAnalysisType2(trs, document, urlMonitorEntity, nodeRule);

        return articleRecords;


    }
}
