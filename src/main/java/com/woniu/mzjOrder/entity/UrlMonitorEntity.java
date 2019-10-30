package com.woniu.mzjOrder.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UrlMonitorEntity implements Serializable {

    private static final long serialVersionUID = 6977058622880828314L;
    private String area;
    private String name;
    private String rootUrl;
    private String connectUrl;
    private String targetUrlPattern;  //暂时无用处

    private ArticleRecordFilter articleRecordFilter;
    private NetChildFilter netChildFilter;

    public UrlMonitorEntity(){

    }

    public UrlMonitorEntity(String area,String name, String rootUrl, String connectUrl, String targetUrlPattern){
        this.area = area;
        this.name = name;
        this.rootUrl = rootUrl;
        this.connectUrl = connectUrl;
        this.targetUrlPattern = targetUrlPattern;
    }
}
