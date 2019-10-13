package com.woniu.mzjOrder.service;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.util.DateUtil;
import com.woniu.mzjOrder.util.DocumentUtil;
import com.woniu.mzjOrder.vo.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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


    default List<ArticleRecord> elementsAnalysisType1(Elements es, Document document,
                                                      UrlMonitorEntity urlMonitorEntity, NodeRule nodeRule) {
        List<ArticleRecord> articleRecords = new ArrayList<>();

        for (Element e : es) {
            ArticleRecord record = new ArticleRecord();
            Element link = e.select(nodeRule.getUrlTag()).first();
            String linkStr = link.attr("href").replaceFirst("\\.\\./", "/");
            linkStr = linkStr.replaceAll("\\.\\./", "");
            linkStr = linkStr.replaceAll("\\./", "/");
            if (!linkStr.startsWith("http:") && !linkStr.startsWith("https:")) {
                linkStr = urlMonitorEntity.getRootUrl().concat(linkStr);
            }
            record.setArea(urlMonitorEntity.getArea());
            record.setArticleName(urlMonitorEntity.getName());
            record.setRootUrl(urlMonitorEntity.getRootUrl());
            record.setConnectUrl(document.baseUri());
            record.setUrlTitle(document.title());
            String date;
            if (nodeRule.isOwnDateText()) {
                if (nodeRule.getRecordTag().equals(""))
                    date = e.ownText();
                else
                    date = e.select(nodeRule.getRecordTag()).get(nodeRule.getDateTagIndex()).ownText();
            } else {
                //date = e.select(nodeRule.getDateTag()).first().text();
                Elements ee = e.select(nodeRule.getDateTag());
                date = ee.get(nodeRule.getDateTagIndex()).text();
            }
            Date dateTime = DateUtil.StringToDate(date);
            record.setDateTime(dateTime);
            record.setTargetUrl(linkStr);
            if (nodeRule.getTitleFlag() == 1) {
                record.setArticleTitle(link.attr("title"));
            } else if (nodeRule.getTitleFlag() == 2) {
                record.setArticleTitle(link.ownText());
            }
            articleRecords.add(record);
        }
        return articleRecords;

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
                String linkStr = link.attr("href").replaceFirst("\\.\\./", "/");
                linkStr = linkStr.replaceAll("\\.\\./", "");
                linkStr = linkStr.replaceAll("\\./", "/");
                if (!linkStr.startsWith("http:") && !linkStr.startsWith("https:")) {
                    linkStr = urlMonitorEntity.getRootUrl().concat(linkStr);
                }
                record.setArea(urlMonitorEntity.getArea());
                record.setArticleName(urlMonitorEntity.getName());
                record.setRootUrl(urlMonitorEntity.getRootUrl());
                record.setConnectUrl(urlMonitorEntity.getConnectUrl());
                record.setUrlTitle(document.title());

                DateRule dateRule = nodeRule.getDateRule();
                String date = DocumentUtil.getLocationText(sourceRecord, dateRule.getLocation(), dateRule.getDateTag(), dateRule.getDateTagIndex(), dateRule.getFilterStr());
                /*switch (dateRule.getLocation()) {
                    case OWNER: date = sourceRecord.select(dateRule.getDateTag()).get(dateRule.getDateTagIndex()).ownText();
                        break;
                    case ATTR: date = sourceRecord.select(dateRule.getDateTag()).
                            get(dateRule.getDateTagIndex()).
                            attr(dateRule.getFilterStr());
                        break;
                    case UPPER: date = sourceRecord.ownText();
                        break;
                    case DATA:
                        String sourceDateText = sourceRecord.select(dateRule.getDateTag()).
                                get(dateRule.getDateTagIndex()).data();
                        date = DateUtil.extractDataStr(sourceDateText, dateRule.getFilterStr());
                        break;
                    default: date = null;
                        break;
                }*/
                Date dateTime = DateUtil.StringToDate(date);
                record.setDateTime(dateTime);
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

}
