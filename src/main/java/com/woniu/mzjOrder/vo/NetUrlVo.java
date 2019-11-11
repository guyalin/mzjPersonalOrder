package com.woniu.mzjOrder.vo;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.ArticleRecordFilter;
import com.woniu.mzjOrder.entity.NetChildFilter;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class NetUrlVo implements Serializable {
    private static final long serialVersionUID = -8178017285151921395L;

    private UrlMonitorEntity urlMonitorEntity;
    private NetChildFilter netChildFilter;
    private ArticleRecordFilter articleRecordFilter;


}
