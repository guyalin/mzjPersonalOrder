package com.woniu.mzjOrder.advice;

import com.woniu.mzjOrder.controller.WebSocketServer;
import com.woniu.mzjOrder.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {
    //声明要捕获的异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public void defaultExceptionHandler(CustomException e) {
        //e.printStackTrace();
        log.error("{}--业务异常,错误码：{}, {}", this.getClass(),e.getCode(), e.getMessage());
        if (e.getCode() == 2){
            WebSocketServer.sendWebSocketMessage(e.getJsonObject(), null);
        }
        if (e.getCode() == 3) {
            WebSocketServer.sendWebSocketMessage(e.getJsonObject(),null);
        }
    }
}
