package com.woniu.mzjOrder.controller;

import com.woniu.mzjOrder.entity.AuthUser;
import com.woniu.mzjOrder.service.AuthUserService;
import com.woniu.mzjOrder.vo.AuthUserVo;
import com.woniu.mzjOrder.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/11/30
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthUserService loginService;

    @Autowired
    private AuthUserService authUserService;

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

    @PostMapping("/newUser")
    public JsonResult createUser(@RequestBody AuthUser authUser){
        JsonResult jsonResult = new JsonResult();
        try {
            Integer cnt = authUserService.saveOrUpdUser(authUser);
            jsonResult.setReturnMsg("Succeed to insert or update "+ cnt + " records");
            jsonResult.setReturnCode("200");
            jsonResult.setData(null);
            return jsonResult;
        }catch (Exception e) {
            jsonResult.setReturnCode("-1");
            jsonResult.setReturnMsg(e.toString());
            return jsonResult;
        }
    }
}
