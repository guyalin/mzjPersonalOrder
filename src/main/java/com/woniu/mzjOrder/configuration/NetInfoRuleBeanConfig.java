package com.woniu.mzjOrder.configuration;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.woniu.mzjOrder.Bo.LocalFileWriterBean;
import com.woniu.mzjOrder.service.DocumentProcessor;
import com.woniu.mzjOrder.service.processor.*;
import com.woniu.mzjOrder.util.SeleniumUtil;
import com.woniu.mzjOrder.vo.ChildDocumentRule;
import com.woniu.mzjOrder.vo.NetInfoRuleMapBean;
import com.woniu.mzjOrder.vo.TextLocationEnum;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class NetInfoRuleBeanConfig {

    /**
     * 初始化所有网页及规则
     *
     * @return
     */
    @Bean
    public NetInfoRuleMapBean netInfoRuleListBean() {
        NetInfoRuleMapBean infoRuleMapBean = new NetInfoRuleMapBean();
        Map<String, DocumentProcessor> documentProcessorMap = new HashMap<>();
        Map<String, Integer> netIsActiveNodeMap = new HashMap<>();
        Map<String, Integer> hasChildNetSiteMap = new HashMap<>();
        Map<String, ChildDocumentRule> childDocumentRuleMap = new HashMap<>();
        documentProcessorMap.put("安徽经信厅", new ProcessorForGov_ahjxt());
        netIsActiveNodeMap.put("安徽经信厅", 0);
        documentProcessorMap.put("湖南工信厅", new ProcessorForGov_hngxt());
        netIsActiveNodeMap.put("湖南工信厅", 0);
        documentProcessorMap.put("山东工信厅", new ProcessorForGov_sdgxt());
        netIsActiveNodeMap.put("山东工信厅", 0);
        documentProcessorMap.put("广东人民政府", new ProcessorForGov_gdrmzf());
        netIsActiveNodeMap.put("广东人民政府", 0);
        documentProcessorMap.put("浙江经信委", new ProcessorForGov_zjjxw());
        netIsActiveNodeMap.put("浙江经信委", 0);
        documentProcessorMap.put("江苏人民政府", new ProcessorForGov_jsrmzf());
        netIsActiveNodeMap.put("江苏人民政府", 0);
        documentProcessorMap.put("江苏工信厅", new ProcessorForGov_jsgxt());
        netIsActiveNodeMap.put("江苏工信厅", 0);
        documentProcessorMap.put("江苏发改委", new ProcessorForGov_jsfgw());
        netIsActiveNodeMap.put("江苏发改委", 0);
        documentProcessorMap.put("上海市政府", new ProcessorForGov_shszf());
        netIsActiveNodeMap.put("上海市政府", 0);
        documentProcessorMap.put("上海经信委", new ProcessorForGov_shjxw());
        netIsActiveNodeMap.put("上海经信委", 0);
        documentProcessorMap.put("上海发改委", new ProcessorForGov_shfgw());
        netIsActiveNodeMap.put("上海发改委", 0);
        documentProcessorMap.put("中国工信部", new ProcessorForGov_zggxb());
        netIsActiveNodeMap.put("中国工信部", 0);
        documentProcessorMap.put("中国科技部", new ProcessorForGov_zgkjb());
        netIsActiveNodeMap.put("中国科技部", 0);
        documentProcessorMap.put("国务院", new ProcessorForGov_zggwy());
        netIsActiveNodeMap.put("国务院", 0);
        documentProcessorMap.put("中国发改部", new ProcessorForGov_zgfgb());
        netIsActiveNodeMap.put("中国发改部", 0);
        documentProcessorMap.put("中国商务部", new ProcessorForGov_zgswb());
        netIsActiveNodeMap.put("中国商务部", 0);
        hasChildNetSiteMap.put("中国商务部", 1);
        childDocumentRuleMap.put("中国商务部",
                new ChildDocumentRule("section.f-mt20", "", 0, TextLocationEnum.OWNER, "",
                        "ul.u-newsList02 li", "a[href]", 0, TextLocationEnum.ATTR, "href",
                        "a[href]", 0, TextLocationEnum.OWNER, "")
        );
        documentProcessorMap.put("深圳工信局", new ProcessorForGov_szgxj());
        netIsActiveNodeMap.put("深圳工信局", 0);
        documentProcessorMap.put("深圳市政府", new ProcessorForGov_szszf());
        netIsActiveNodeMap.put("深圳市政府", 0);
        documentProcessorMap.put("深圳发改委", new ProcessorForGov_szfgw());
        netIsActiveNodeMap.put("深圳发改委", 0);
        documentProcessorMap.put("广东经信委", new ProcessorForGov_gdjxw());
        netIsActiveNodeMap.put("广东经信委", 0);
        documentProcessorMap.put("江西工信厅", new ProcessorForGov_jxgxt());
        netIsActiveNodeMap.put("江西工信厅", 1);
        documentProcessorMap.put("全球技术地图", new ProcessorForGov_qqjsdt());
        netIsActiveNodeMap.put("全球技术地图", 0);
        hasChildNetSiteMap.put("全球技术地图", 1);
        childDocumentRuleMap.put("全球技术地图",
                new ChildDocumentRule("div.wrap", "", 0, TextLocationEnum.OWNER, "",
                        "ul li", "a[href]", 0, TextLocationEnum.ATTR, "href",
                        "a[href]", 0, TextLocationEnum.OWNER, "")
        );
        /*documentProcessorMap.put("全球技术地图-航空", new ProcessorForGov_qqjsdt());
        netIsActiveNodeMap.put("全球技术地图-航空", 0);
        documentProcessorMap.put("全球技术地图-航天", new ProcessorForGov_qqjsdt());
        netIsActiveNodeMap.put("全球技术地图-航天", 0);*/
        documentProcessorMap.put("福建工信厅", new ProcessorForGov_fjgxt());
        netIsActiveNodeMap.put("福建工信厅", 0);
        documentProcessorMap.put("河北经信厅", new ProcessorForGov_hbjxt());
        netIsActiveNodeMap.put("河北经信厅", 0);
        documentProcessorMap.put("山西经信厅", new ProcessorForGov_sxjxt());
        netIsActiveNodeMap.put("山西经信厅", 0);
        infoRuleMapBean.setNetInfoRules(documentProcessorMap);
        infoRuleMapBean.setNetIsActiveNodeMap(netIsActiveNodeMap);
        infoRuleMapBean.setHasChildNetSiteMap(hasChildNetSiteMap);
        infoRuleMapBean.setChildDocumentRuleMap(childDocumentRuleMap);
        return infoRuleMapBean;
    }

    /**
     * 写入本地文件器
     */
    @Bean(destroyMethod = "destroy")
    public LocalFileWriterBean localFileWriterBean() {
        LocalFileWriterBean localFileWriterBean = new LocalFileWriterBean();
        localFileWriterBean.setLocalFilePath("");
        return localFileWriterBean;
    }

    @Bean(name = "webClient")
    public WebClient getWebClient() {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        return webClient;
    }

    @Bean(name = "phantomJsDriver")
    public WebDriver phantomJS(@Value("${phantomjs.server-path}") String toolPath){
        WebDriver webDriver = SeleniumUtil.getSeleniumDriver(toolPath);
        return webDriver;
    }
}
