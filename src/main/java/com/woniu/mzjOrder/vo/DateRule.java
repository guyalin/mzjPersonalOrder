package com.woniu.mzjOrder.vo;

import lombok.Data;

@Data
public class DateRule {
    private String dateTag;
    private Integer dateTagIndex;
    private TextLocationEnum location;
    private String filterStr; //当location为ATTR时，传入属性名,当为DATA时，传入过滤字符串

    public DateRule(String dateTag, Integer dateTagIndex, TextLocationEnum location, String filterStr){
        this.dateTag = dateTag;
        this.dateTagIndex = dateTagIndex;
        this.location = location;
        this.filterStr = filterStr;
    }
}
