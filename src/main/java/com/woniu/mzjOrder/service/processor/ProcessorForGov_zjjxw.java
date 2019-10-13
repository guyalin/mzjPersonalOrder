package com.woniu.mzjOrder.service.processor;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.util.DateUtil;
import com.woniu.mzjOrder.vo.DateRule;
import com.woniu.mzjOrder.vo.NodeRule;
import com.woniu.mzjOrder.vo.TextLocationEnum;
import com.woniu.mzjOrder.vo.TitleRule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class ProcessorForGov_zjjxw implements DocumentProcessor {

    @Override
    public List<ArticleRecord> findAndExplainToArticleRecord(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords = new ArrayList<>();
        Elements es = baseCDataTypeAnalysis(document, "4809328","record");
        TitleRule titleRule = new TitleRule("a[href]",0,false,true,"title");
        DateRule dateRule = new DateRule("span",0, TextLocationEnum.OWNER, "");
        NodeRule nodeRule = new NodeRule("a[href]",titleRule,dateRule);
        articleRecords = elementsAnalysisType2(es, document, urlMonitorEntity, nodeRule);
        return articleRecords;
    }
}
