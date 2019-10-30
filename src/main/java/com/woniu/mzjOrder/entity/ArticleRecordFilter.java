package com.woniu.mzjOrder.entity;

import lombok.Data;

@Data
public class ArticleRecordFilter {
    private String filterId;
    private Integer ifActiveAsync;
    private Integer ifExcludeCdata;
    private String rootTag;
    private String recordBodyTag;
    private String titleTag;
    private Integer titleTagIndex;
    private Integer isOwnerText;
    private Integer isAttr;
    private String attrName;
    private String dateTag;
    private Integer dateTagIndex;
    private String dateTagLocation;
    private String dateFilterStr;
    private String urlTag;
}
