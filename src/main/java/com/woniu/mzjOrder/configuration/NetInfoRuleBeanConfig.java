package com.woniu.mzjOrder.configuration;

import com.woniu.mzjOrder.Bo.LocalFileWriterBean;
import com.woniu.mzjOrder.service.processor.ProcessorForGov_zjjxw;
import com.woniu.mzjOrder.vo.NetInfoRule;
import com.woniu.mzjOrder.vo.NetInfoRuleListBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class NetInfoRuleBeanConfig{

    /**
     * 初始化所有网页及规则
     * @return
     */
    @Bean
    public NetInfoRuleListBean netInfoRuleListBean(){
        NetInfoRuleListBean infoRuleListBean = new NetInfoRuleListBean();
        List<NetInfoRule> infoRules = new ArrayList<>();
        //浙江省经济和信息化厅
        NetInfoRule infoRule1 = new NetInfoRule();
        infoRule1.setRootUrl("http://www.zjjxw.gov.cn/col/col1582899/index.html");
        infoRule1.setTargetUrlPattern("http://www.zjjxw.gov.cn/art/\\d{4}/\\d{1,2}/\\d{1,2}/art_1582899_.*?.html");  //http://www.zjjxw.gov.cn/art/2019/9/19/art_1582899_20439.html
        infoRule1.setProcessor(new ProcessorForGov_zjjxw());
        infoRules.add(infoRule1);

        infoRuleListBean.setNetInfoRules(infoRules);
        return infoRuleListBean;
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
