package com.woniu.mzjOrder.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NetInfoQueryParamVo implements Serializable {
    private static final long serialVersionUID = 6498135726704429527L;

    private String area;
    private String netName;
    private String rootUrl;
    private Integer latestDays;
    private Integer descType;  //0时间优先， 1网址类型优先排序
    private String netLabelId; //标签id
    private List<String> articleTitleRegex;

}
