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

public class ProcessorForGov_shfgw implements DocumentProcessor {
    @Override
    public List<ArticleRecord> findAndExplain(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords;
        Element e = document.select("div.xwzx_list").first();
        Elements es = e.select("li");
        TitleRule titleRule = new TitleRule("a[href]",0,false,true,"title");
        DateRule dateRule = new DateRule("span",0, TextLocationEnum.OWNER, "");
        NodeRule nodeRule = new NodeRule("a[href]",titleRule,dateRule);
        articleRecords = elementsAnalysisType2(es, document, urlMonitorEntity, nodeRule);
        return articleRecords;
    }
}
