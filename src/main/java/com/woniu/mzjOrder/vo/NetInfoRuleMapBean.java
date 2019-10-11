package com.woniu.mzjOrder.vo;

import com.woniu.mzjOrder.service.DocumentProcessor;
import lombok.Data;

import java.util.Map;

@Data
public class NetInfoRuleMapBean {

    private Map<String, DocumentProcessor> netInfoRules;
    private Map<String, Integer> netIsActiveNodeMap;
}
