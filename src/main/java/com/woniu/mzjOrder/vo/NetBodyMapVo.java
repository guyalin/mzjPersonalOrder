package com.woniu.mzjOrder.vo;

import com.woniu.mzjOrder.entity.ArticleRecord;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/20
 */
@Data
public class NetBodyMapVo {
    private String netId;
    private List<ArticleRecord> articleRecords;
}
