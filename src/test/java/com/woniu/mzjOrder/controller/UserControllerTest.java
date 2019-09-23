package com.woniu.mzjOrder.controller;

import com.woniu.mzjOrder.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class UserControllerTest {


    @Test
    public void testConnection() throws Exception {
        System.out.println("CEshi**********************************************************");
        System.out.println("CEshi**********************************************************");
    }

    @Test
    public void testPattern() throws Exception {
        Pattern pk = Pattern.compile("《.*?》");
        String longStr = "浙江省经济和信息化厅 浙江省人力资源和社会保障厅关于印发《浙江省机电制造专业正高级工程师职务任职资格评价条件（试行）》《浙江省信息技术专业正高级工程师职务任职资格评价条件(试行)》的通知";
        Matcher m = pk.matcher(longStr);
        while (m.find()){
            String href = m.group();
            System.out.println("++++++++++++++++++++++++++:"+href);
        }
    }
}