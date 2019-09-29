package com.woniu.mzjOrder.entity;

import lombok.Data;

@Data
public class UrlMonitorEntity {

    private String area;
    private String name;
    private String rootUrl;
    private String connectUrl;
    private String targetUrlPattern;

    public UrlMonitorEntity(String rootUrl, String connectUrl, String targetUrlPattern){
        this.rootUrl = rootUrl;
        this.connectUrl = connectUrl;
        this.targetUrlPattern = targetUrlPattern;
    }
}
