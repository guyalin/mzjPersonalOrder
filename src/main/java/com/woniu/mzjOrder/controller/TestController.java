package com.woniu.mzjOrder.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mg")
public class TestController {

    @GetMapping(value = "/gu/mu")
    public String mgTEST(){
        String mg = "顾亚林 love 穆振娟";
        return mg;
    }
}
