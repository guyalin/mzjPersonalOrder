package com.woniu.mzjOrder.entity;

import lombok.Data;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/6
 */
@Data
public class NetLabelEntity {

    private String labelId;
    private String labelName;
    private String netList;
    private String createTime;
    private String description;
    private String createUserId;
}
