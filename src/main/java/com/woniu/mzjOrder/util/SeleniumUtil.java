package com.woniu.mzjOrder.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: guyalin
 * @date: 2020/2/24
 */
@AllArgsConstructor
@Data
public class SeleniumUtil {

    public static WebDriver getSeleniumDriver(String toolPath){
        //可使用的浏览器有：IE浏览器（webdriver.ie.driver）
        //火狐浏览器  (webdriver.gecko.driver)
        //谷歌浏览器 (webdriver.chrome.driver)
        //                  是使用那个浏览器                                   chromedriver所在的位置
        System.setProperty("webdriver.chrome.driver", toolPath);
        // InternetExplorerDriver()   浏览器
        // FirefoxDriver()            火狐浏览器
        //谷歌浏览器
        WebDriver driver = null;

        //创建chrome参数对象
        ChromeOptions options = new ChromeOptions();

        //浏览器后台运行
//        options.addArguments("headless");
//        options.addArguments("no-sandbox");
        options.setHeadless(Boolean.TRUE);
        options.addArguments("--no-sandbox","--disable-dev-shm-usage","start-maximized","--disable-gpu","--hide-scrollbars","blink-settings=imagesEnabled=false","--headless");

        //options.addArguments("disable-gpu");
        //options.addArguments("disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        //隐式等待等待5秒
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        /*
        //设置必要参数
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl证书支持
        dcaps.setCapability("acceptSslCerts", true);
        //截屏支持
        dcaps.setCapability("takesScreenshot", true);
        //css搜索支持
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js支持
        dcaps.setJavascriptEnabled(true);
        //驱动支持（第二参数表明的是你的phantomjs引擎所在的路径，which/whereis phantomjs可以查看）
        //fixme 这里写了执行， 可以考虑判断系统是否有安装，并获取对应的路径 or 开放出来指定路径
        // "usr/local/bin/phantomjs"
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, toolPath);
        //创建无界面浏览器对象
        PhantomJSDriver driver = new PhantomJSDriver(dcaps);
        //设置隐性等待（作用于全局）
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
*/

        return driver;
    }

}
