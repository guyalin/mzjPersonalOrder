package com.woniu.mzjOrder.exception;

import com.woniu.mzjOrder.vo.ResultStatusEnum;
import lombok.Getter;
import net.sf.json.JSONObject;

@Getter
public class CustomException extends RuntimeException {
    private int code;
    private String message;
    private JSONObject jsonObject;

    public CustomException(int code, String message, JSONObject jsonObject) {
        this.code = code;
        this.message = message;
        this.jsonObject = jsonObject;
    }

    public CustomException(ResultStatusEnum resultStatusEnum, JSONObject jsonObject) {
        this.code = resultStatusEnum.getCode();
        this.message = resultStatusEnum.getMsg();
        this.jsonObject = jsonObject;
    }

    public CustomException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomException(ResultStatusEnum resultStatusEnum) {
        this.code = resultStatusEnum.getCode();
        this.message = resultStatusEnum.getMsg();
    }
}
