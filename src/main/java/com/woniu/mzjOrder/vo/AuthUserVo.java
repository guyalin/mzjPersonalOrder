package com.woniu.mzjOrder.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/11/30
 */
@Data
public class AuthUserVo implements Serializable {
    private String userId;
    private String passWd;
}
