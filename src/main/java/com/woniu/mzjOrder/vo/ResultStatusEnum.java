package com.woniu.mzjOrder.vo;

import lombok.Getter;

@Getter
public enum ResultStatusEnum {
    UNKONW_ERROR(-1,"未知错误"),
    SUCCESS(0,"成功"),
    ERROR(1,"失败"),
    URL_ANALYSIS_ERROR(2,"网址解析异常"),
    NOT_NULL(3,"不能为空")
    ;

    private Integer code;
    private String msg;

    ResultStatusEnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }


}
