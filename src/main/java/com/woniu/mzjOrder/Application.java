package com.woniu.mzjOrder;

import com.woniu.mzjOrder.visualClient.NetArticleMonitor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.woniu.mzjOrder.dao")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(Application.class, args);

    }

}
