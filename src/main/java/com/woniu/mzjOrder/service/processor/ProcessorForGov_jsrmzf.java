package com.woniu.mzjOrder.service.processor;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.util.DateUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessorForGov_jsrmzf implements DocumentProcessor {
    @Override
    public List<ArticleRecord> findAndExplain(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords = new ArrayList<>();
        Elements es_odd = document.select("tr.tr_main_value_odd");
        Elements es_even = document.select("tr.tr_main_value_even");
        List<ArticleRecord> articleRecordOdd = analysisArticleNode(es_odd, document, urlMonitorEntity.getRootUrl());
        List<ArticleRecord> articleRecordEven = analysisArticleNode(es_even, document, urlMonitorEntity.getRootUrl());
        articleRecords.addAll(articleRecordOdd);
        articleRecords.addAll(articleRecordEven);
        return articleRecords;
    }

    private List<ArticleRecord> analysisArticleNode(Elements elements, Document document, String rootUrl){
        List<ArticleRecord> records = new ArrayList<>();
        for (Element element : elements) {
            ArticleRecord record = new ArticleRecord();
            record.setArea("江苏");
            record.setArticleName("江苏人民政府网");
            record.setRootUrl(rootUrl);
            record.setConnectUrl(document.baseUri());
            record.setUrlTitle("政府信息公开目录");
            record.setArticleTitle(element.select("a[href]").first().attr("title"));
            record.setTargetUrl(element.select("a[href]").first().attr("href"));
            String date = element.select("td").get(2).text();
            Date dateTime = DateUtil.StringToDate(date);
            record.setDateTime(dateTime);
            records.add(record);
        }

        return records;
    }

}
