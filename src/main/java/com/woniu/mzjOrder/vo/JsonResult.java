package com.woniu.mzjOrder.vo;

import lombok.Data;

@Data
public class JsonResult {
    public static final String SUCC="0";
    public static final String FAIL="1";

    private String returnCode=SUCC;
    private String returnMsg;
    private Object data;

}