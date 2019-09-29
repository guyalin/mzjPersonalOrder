package com.woniu.mzjOrder.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ArticleRecord implements Serializable {

    private static final long serialVersionUID = 3450829660309603886L;

    private String rootUrl;
    private String connectUrl;
    private String urlTitle;
    private String area;
    private String targetUrl;
    private String articleName;
    private String articleTitle;
    private Date dateTime;
}
