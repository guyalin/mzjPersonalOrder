package com.woniu.mzjOrder.vo;

import com.woniu.mzjOrder.service.DocumentProcessor;
import lombok.Data;

import java.util.Map;

@Data
public class NetInfoRuleMapBean {

    private Map<String, DocumentProcessor> netInfoRules;
    private Map<String, Integer> netIsActiveNodeMap; //是否要动态预加载js
    private Map<String, Integer> hasChildNetSiteMap; //是否需要解析子页面
    private Map<String, ChildDocumentRule> childDocumentRuleMap; //需要解析子页面的页面规则
}
