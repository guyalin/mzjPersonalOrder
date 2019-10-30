package com.woniu.mzjOrder.entity;

import lombok.Data;

@Data
public class NetChildFilter {
    private String childFilterId;
    private String rootTag;
    private String recordTag;
    private String urlTag;
    private Integer urlTagIndex;
    private String urlLocation;
    private String urlAttrName;
    private String nameTag;
    private Integer nameTagIndex;
    private String nameLocation;
    private String nameAttrName;

}
