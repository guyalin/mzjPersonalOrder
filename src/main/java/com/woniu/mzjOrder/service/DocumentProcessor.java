package com.woniu.mzjOrder.service;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.ArticleRecordFilter;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.util.DateUtil;
import com.woniu.mzjOrder.util.DocumentUtil;
import com.woniu.mzjOrder.vo.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface DocumentProcessor {

    default Elements baseCDataTypeAnalysis(Document document, String classId, String recordTag) {
        String dataStr = document.getElementById(classId).data();
        dataStr = dataStr.replaceAll("<!\\[CDATA\\[", "").
                replaceAll("\\]\\]>", "");
        Document docPart = Jsoup.parseBodyFragment(dataStr);
        Elements es = docPart.select(recordTag);
        return es;
    }

    default List<ArticleRecord> elementsAnalysisType2(Elements es, Document document,
                                                      UrlMonitorEntity urlMonitorEntity, NodeRule nodeRule) {
        List<ArticleRecord> articleRecords = new ArrayList<>();

        for (Element sourceRecord : es) {
            try {
                ArticleRecord record = new ArticleRecord();
                Element link = sourceRecord.select(nodeRule.getUrlTag()).first();
                if (link == null) {
                    continue;
                }

                /*String linkStr = link.attr("href").replaceFirst("\\.\\./", "/");
                linkStr = linkStr.replaceAll("\\.\\./", "");
                linkStr = linkStr.replaceAll("\\./", "/");
                if (!linkStr.startsWith("http:") && !linkStr.startsWith("https:")) {
                    linkStr = urlMonitorEntity.getRootUrl().concat(linkStr);
                }*/
                String linkStrUrl = link.attr("href");
                URI baseUrl = new URI(document.baseUri());
                URI relative = new URI(linkStrUrl);
                URI linkStrURI = baseUrl.resolve(relative);
                String linkStr = linkStrURI.toString();
                record.setArea(urlMonitorEntity.getArea());
                record.setArticleName(urlMonitorEntity.getName());
                record.setRootUrl(urlMonitorEntity.getRootUrl());
                record.setConnectUrl(urlMonitorEntity.getConnectUrl());
                record.setUrlTitle(document.title());
                DateRule dateRule = nodeRule.getDateRule();
                String date = DocumentUtil.getLocationText(sourceRecord, dateRule.getLocation(), dateRule.getDateTag(), dateRule.getDateTagIndex(), dateRule.getFilterStr());
                Date dateTime = DateUtil.StringToDate(date);
                String dateStr = DateUtil.DateToString(dateTime);
                record.setDateTime(dateStr);
                record.setTargetUrl(linkStr);
                TitleRule titleRule = nodeRule.getTitleRule();
                String title;
                if (titleRule.isAttr()) {
                    title = sourceRecord.select(titleRule.getTitleTag()).
                            get(titleRule.getTitleTagIndex()).
                            attr(titleRule.getAttrName());

                } else if (titleRule.isOwnerText()) {
                    title = sourceRecord.select(titleRule.getTitleTag()).
                            get(titleRule.getTitleTagIndex()).ownText();
                } else {
                    title = sourceRecord.ownText();
                }
                record.setArticleTitle(title);
                articleRecords.add(record);
            } catch (Exception e) {
                continue;
            }
        }
        return articleRecords;

    }

    List<ArticleRecord> findAndExplainToArticleRecord(Document document, UrlMonitorEntity urlMonitorEntity);

    default List<ArticleRecord> explainToArticleRecord(Document document, UrlMonitorEntity urlMonitorEntity) {
        List<ArticleRecord> articleRecords;
        ArticleRecordFilter filter = urlMonitorEntity.getArticleRecordFilter();
        Integer ifExcludeCdata = filter.getIfExcludeCdata();
        Elements es;
        if (ifExcludeCdata == 1) {
            es = baseCDataTypeAnalysis(document, filter.getRootTag(), filter.getRecordBodyTag());
        } else{
            Element root = document.select(filter.getRootTag()).first();
            es = root.select(filter.getRecordBodyTag());
        }

        String titleTag = filter.getTitleTag();
        Integer titleTagIndex = filter.getTitleTagIndex();
        Boolean isOwnerText = filter.getIsOwnerText() == 1;
        Boolean isAttr = filter.getIsAttr() == 1;
        String attrName = filter.getAttrName();

        String dateTag = filter.getDateTag();
        Integer dateTagIndex = filter.getDateTagIndex();
        TextLocationEnum locationEnum = DocumentUtil.strToEnum(filter.getDateTagLocation());

        String filterStr = filter.getDateFilterStr();

        String urlTag = filter.getUrlTag();

        TitleRule titleRule = new TitleRule(titleTag, titleTagIndex, isOwnerText, isAttr, attrName);
        DateRule dateRule = new DateRule(dateTag, dateTagIndex, locationEnum, filterStr);
        NodeRule nodeRule = new NodeRule(urlTag, titleRule, dateRule);
        articleRecords = elementsAnalysisType2(es, document, urlMonitorEntity, nodeRule);

        return articleRecords;
    }

}
