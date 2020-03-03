package com.woniu.mzjOrder.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * @description:
 * @author: guyalin
 * @date: 2020/2/27
 */
@Slf4j
public class StopLoadPage extends Thread {
    WebDriver driver = null;
    int sec = 0;
    public StopLoadPage(WebDriver driver,int sec){
        this.driver = driver;
        this.sec = sec;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("子线程的睡眠被终止，js页面加载成功！");
            return;
        }
        ((JavascriptExecutor) driver).executeScript("window.stop();");
        log.info("子线程终止加载js页面");
    }

}
