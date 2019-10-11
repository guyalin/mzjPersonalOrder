package com.woniu.mzjOrder.service.processor;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.vo.DateRule;
import com.woniu.mzjOrder.vo.NodeRule;
import com.woniu.mzjOrder.vo.TextLocationEnum;
import com.woniu.mzjOrder.vo.TitleRule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ProcessorForGov_jsrmzf implements DocumentProcessor {
    @Override
    public List<ArticleRecord> findAndExplain(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords = new ArrayList<>();
        Elements es = document.select("tr.tr_main_value_odd, tr.tr_main_value_even");
        TitleRule titleRule = new TitleRule("a[href]",0,false,true,"title");
        DateRule dateRule = new DateRule("td",2, TextLocationEnum.OWNER, "");
        NodeRule nodeRule = new NodeRule("a[href]",titleRule,dateRule);
        List<ArticleRecord> articleRecordOdd = elementsAnalysisType2(es, document, urlMonitorEntity, nodeRule);
        articleRecords.addAll(articleRecordOdd);
        return articleRecords;
    }

}
