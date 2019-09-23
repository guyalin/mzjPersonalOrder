package com.woniu.mzjOrder.vo;

import com.woniu.mzjOrder.service.DocumentProcessor;
import lombok.Data;

@Data
public class NetInfoRule {
    private String rootUrl;
    private String targetUrlPattern;
    private DocumentProcessor processor;
}
