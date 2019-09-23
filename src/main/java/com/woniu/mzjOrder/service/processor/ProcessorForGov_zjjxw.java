package com.woniu.mzjOrder.service.processor;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.util.DateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class ProcessorForGov_zjjxw implements DocumentProcessor {

    private Pattern pk = Pattern.compile("《.*?》");
    @Override
    public List<ArticleRecord> findAndExplain(Document document) {
        List<ArticleRecord> articleRecords = new ArrayList<>();
        String dataStr = document.getElementById("4809328").data();
        dataStr = dataStr.replaceAll("<!\\[CDATA\\[", "").
                replaceAll("\\]\\]>","");
        Document docPart = Jsoup.parseBodyFragment(dataStr);
        Elements es = docPart.select("record");

        for (Element e : es)
        {
            ArticleRecord record = new ArticleRecord();
            Element link = e.select("a[href]").first();
            String linkStr = link.attr("href");

            record.setArea("浙江");
            record.setArticleName("浙江信息网");
            record.setRootUrl(document.baseUri());
            record.setUrlTitle(document.title());
            String date = e.select("span").text();
            Date dateTime = DateUtil.StringToDate(date);
            record.setDateTime(dateTime);
            record.setTargetUrl(linkStr);
            record.setArticleTitle(link.attr("title"));
            articleRecords.add(record);
        }
        return articleRecords;
    }
}
