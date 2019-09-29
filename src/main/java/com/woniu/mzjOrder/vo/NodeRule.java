package com.woniu.mzjOrder.vo;

import lombok.Data;

@Data
public class NodeRule {
    private String recordTag;
    private String urlTag;
    private String dateTag;
    private boolean ownDataText;
    private Integer titleFlag;   //1，表示查找属性title  2，表示a[href]的text属性

    public NodeRule(String recordTag, String urlTag, String dataTag, boolean ownDataText, Integer titleFlag){
        this.recordTag = recordTag;
        this.urlTag = urlTag;
        this.dateTag = dataTag;
        this.ownDataText = ownDataText;
        this.titleFlag = titleFlag;
    }
}
