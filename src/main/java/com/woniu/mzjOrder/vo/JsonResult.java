package com.woniu.mzjOrder.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult implements Serializable {
    private static final long serialVersionUID = -7154223299840034675L;
    public static final String SUCC="0";
    public static final String FAIL="1";


    private String returnCode=SUCC;
    private String returnMsg;
    private Object data;

}