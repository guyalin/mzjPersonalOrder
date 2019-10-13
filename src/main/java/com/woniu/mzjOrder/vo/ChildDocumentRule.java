package com.woniu.mzjOrder.vo;

import lombok.Data;

@Data
public class ChildDocumentRule {

    private String rootTag;
    private String rootAreaTag;
    private Integer rootAreaTagIndex;
    private TextLocationEnum areaLocation;
    private String areaAttrName;
    private String recordTag;
    private String childRootUrlTag;
    private Integer childRootUrlTagIndex;
    private TextLocationEnum childRootUrlLocation;
    private String urlAttrName;
    private String childNameTag;
    private Integer childNameTagIndex;
    private TextLocationEnum nameLocation;
    private String nameAttr;

    public ChildDocumentRule(String rootTag, String rootAreaTag, Integer rootAreaTagIndex, TextLocationEnum areaLocation, String areaAttrName,
                             String recordTag, String childRootUrlTag, Integer childRootUrlTagIndex, TextLocationEnum childRootUrlLocation, String urlAttrName,
                             String childNameTag, Integer childNameTagIndex, TextLocationEnum nameLocation, String nameAttr){
        this.rootTag = rootTag;
        this.rootAreaTag = rootAreaTag;
        this.rootAreaTagIndex = rootAreaTagIndex;
        this.areaLocation = areaLocation;
        this.areaAttrName = areaAttrName;
        this.recordTag = recordTag;
        this.childRootUrlTag = childRootUrlTag;
        this.childRootUrlTagIndex = childRootUrlTagIndex;
        this.childRootUrlLocation = childRootUrlLocation;
        this.urlAttrName = urlAttrName;
        this.childNameTag = childNameTag;
        this.childNameTagIndex = childNameTagIndex;
        this.nameLocation = nameLocation;
        this.nameAttr = nameAttr;

    }
}
