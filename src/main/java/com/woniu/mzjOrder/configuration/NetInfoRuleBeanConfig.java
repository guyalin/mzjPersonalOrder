package com.woniu.mzjOrder.configuration;

import com.woniu.mzjOrder.Bo.LocalFileWriterBean;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.service.processor.*;
import com.woniu.mzjOrder.vo.NetInfoRuleMapBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class NetInfoRuleBeanConfig{

    /**
     * 初始化所有网页及规则
     * @return
     */
    @Bean
    public NetInfoRuleMapBean netInfoRuleListBean(){
        NetInfoRuleMapBean infoRuleMapBean = new NetInfoRuleMapBean();
        Map<String, DocumentProcessor> documentProcessorMap = new HashMap<>();
        documentProcessorMap.put("浙江经信委", new ProcessorForGov_zjjxw());
        documentProcessorMap.put("江苏人民政府", new ProcessorForGov_jsrmzf());
        documentProcessorMap.put("江苏工信厅", new ProcessorForGov_jsgxt());
        documentProcessorMap.put("江苏发改委", new ProcessorForGov_jsfgw());
        documentProcessorMap.put("上海市政府", new ProcessorForGov_shszf());
        documentProcessorMap.put("上海经信委", new ProcessorForGov_shjxw());
        documentProcessorMap.put("上海发改委", new ProcessorForGov_shfgw());
        documentProcessorMap.put("中国工信部", new ProcessorForGov_zggxb());
        documentProcessorMap.put("深圳工信局", new ProcessorForGov_szgxj());
        documentProcessorMap.put("广东经信委", new ProcessorForGov_gdjxw());
        documentProcessorMap.put("全球技术地图-信息", new ProcessorForGov_qqjsdt());
        documentProcessorMap.put("全球技术地图-航空", new ProcessorForGov_qqjsdt());
        documentProcessorMap.put("全球技术地图-航天", new ProcessorForGov_qqjsdt());
        infoRuleMapBean.setNetInfoRules(documentProcessorMap);
        return infoRuleMapBean;
    }

    /**
     *  写入本地文件器
     */
    @Bean(destroyMethod="destroy")
    public LocalFileWriterBean localFileWriterBean(@Value("${local.net-file.root-path}") String path){
        LocalFileWriterBean localFileWriterBean = new LocalFileWriterBean();
        try {
            localFileWriterBean.setLocalFilePath(path);
            localFileWriterBean.init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return localFileWriterBean;
    }
}
