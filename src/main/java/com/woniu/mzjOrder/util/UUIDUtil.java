package com.woniu.mzjOrder.util;

import java.util.UUID;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/17
 */
public class UUIDUtil {
    /**
     * 生成32位UUID
     * @return
     */
    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
