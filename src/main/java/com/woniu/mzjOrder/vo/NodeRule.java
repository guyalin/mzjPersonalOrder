package com.woniu.mzjOrder.vo;

import lombok.Data;

@Data
public class NodeRule {
    private String recordTag;
    private String urlTag;
    private TitleRule titleRule;
    private DateRule dateRule;

    private String dateTag;
    private Integer dateTagIndex;
    private boolean ownDateText;
    private Integer titleFlag;   //1，表示查找属性title  2，表示a[href]的text属性

    public NodeRule(String recordTag, String urlTag, String dataTag, Integer dateTagIndex, boolean ownDateText, Integer titleFlag){
        this.recordTag = recordTag;
        this.urlTag = urlTag;
        this.dateTag = dataTag;
        this.dateTagIndex = dateTagIndex;
        this.ownDateText = ownDateText;
        this.titleFlag = titleFlag;
    }

    public NodeRule(String urlTag, TitleRule titleRule, DateRule dateRule){
        this.urlTag = urlTag;
        this.titleRule = titleRule;
        this.dateRule = dateRule;
    }
}
