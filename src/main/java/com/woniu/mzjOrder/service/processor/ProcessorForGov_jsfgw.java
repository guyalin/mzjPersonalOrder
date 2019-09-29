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

public class ProcessorForGov_jsfgw implements DocumentProcessor {
    @Override
    public List<ArticleRecord> findAndExplain(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords = new ArrayList<>();
        Elements es = baseCDataTypeAnalysis(document, "3227");

        for (Element e : es)
        {
            ArticleRecord record = new ArticleRecord();
            Element link = e.select("a[href]").first();
            String linkStr = link.attr("href");
            if (!linkStr.startsWith("http:") && !linkStr.startsWith("https:")){
                linkStr = urlMonitorEntity.getRootUrl().concat(linkStr);
            }
            record.setArea("江苏");
            record.setArticleName("江苏发改委");
            record.setRootUrl(urlMonitorEntity.getRootUrl());
            record.setConnectUrl(document.baseUri());
            record.setUrlTitle(document.title());
            String date = e.select("li").first().ownText();
            Date dateTime = DateUtil.StringToDate(date);
            record.setDateTime(dateTime);
            record.setTargetUrl(linkStr);
            record.setArticleTitle(link.attr("title"));
            articleRecords.add(record);
        }
        return articleRecords;

    }
}
