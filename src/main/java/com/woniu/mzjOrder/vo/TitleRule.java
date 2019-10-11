package com.woniu.mzjOrder.vo;

import lombok.Data;

@Data
public class TitleRule {
    private String titleTag;
    private Integer titleTagIndex;
    private boolean isOwnerText;
    private boolean isAttr;
    private String attrName;

    public TitleRule(String titleTag, Integer titleTagIndex, boolean isOwnerText, boolean isAttr, String attrName){
        this.titleTag = titleTag;
        this.titleTagIndex = titleTagIndex;
        this.isOwnerText = isOwnerText;
        this.isAttr = isAttr;
        this.attrName = attrName;
    }
}
