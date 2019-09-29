package com.woniu.mzjOrder.service;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.util.DateUtil;
import com.woniu.mzjOrder.vo.NodeRule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface DocumentProcessor {

    default Elements baseCDataTypeAnalysis(Document document, String classId){
        String dataStr = document.getElementById(classId).data();
        dataStr = dataStr.replaceAll("<!\\[CDATA\\[", "").
                replaceAll("\\]\\]>","");
        Document docPart = Jsoup.parseBodyFragment(dataStr);
        Elements es = docPart.select("record");
        return es;
    }

    default List<ArticleRecord> elementsAnalysisType1(Elements es, Document document,
                                                      UrlMonitorEntity urlMonitorEntity, NodeRule nodeRule){
        List<ArticleRecord> articleRecords = new ArrayList<>();

        for (Element e : es)
        {
            ArticleRecord record = new ArticleRecord();
            Element link = e.select(nodeRule.getUrlTag()).first();
            String linkStr = link.attr("href").replaceFirst("\\.\\./","/");
            linkStr= linkStr.replaceAll("\\.\\./","");
            if (!linkStr.startsWith("http:") && !linkStr.startsWith("https:")){
                linkStr = urlMonitorEntity.getRootUrl().concat(linkStr);
            }
            record.setArea(urlMonitorEntity.getArea());
            record.setArticleName(urlMonitorEntity.getName());
            record.setRootUrl(urlMonitorEntity.getRootUrl());
            record.setConnectUrl(document.baseUri());
            record.setUrlTitle(document.title());
            String date;
            if (nodeRule.isOwnDataText()){
                if (nodeRule.getRecordTag().equals(""))
                    date = e.ownText();
                else
                    date = e.select(nodeRule.getRecordTag()).first().ownText();
            }else {
                date = e.select(nodeRule.getDateTag()).first().text();
            }
            Date dateTime = DateUtil.StringToDate(date);
            record.setDateTime(dateTime);
            record.setTargetUrl(linkStr);
            if (nodeRule.getTitleFlag() == 1){
                record.setArticleTitle(link.attr("title"));
            }else if(nodeRule.getTitleFlag() == 2) {
                record.setArticleTitle(link.ownText());
            }
            articleRecords.add(record);
        }
        return articleRecords;

    }

    List<ArticleRecord> findAndExplain(Document document, UrlMonitorEntity urlMonitorEntity);
}
