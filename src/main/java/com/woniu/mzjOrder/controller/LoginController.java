package com.woniu.mzjOrder.controller;

import com.woniu.mzjOrder.service.AuthUserService;
import com.woniu.mzjOrder.vo.AuthUserVo;
import com.woniu.mzjOrder.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/11/30
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthUserService loginService;

    @PostMapping("/login")
    public JsonResult loginOperation(@RequestBody AuthUserVo authUser){
        JsonResult jsonResult = new JsonResult();
        try {
            jsonResult = loginService.loginChecking(authUser);
            return jsonResult;
        }catch (Exception e) {
            jsonResult.setReturnCode("-1");
            jsonResult.setReturnMsg(e.toString());
            return jsonResult;
        }
    }
}
