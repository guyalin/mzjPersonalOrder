package com.woniu.mzjOrder.entity;

import lombok.Data;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/3
 */
@Data
public class AuthUser {

    private String userId;

    private String userName;

    private String userPassWd;

    private Integer isLocked;

    private String lastLoginTime;
}
