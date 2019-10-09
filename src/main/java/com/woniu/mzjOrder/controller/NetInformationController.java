package com.woniu.mzjOrder.controller;

import com.woniu.mzjOrder.service.NetInformationService;
import com.woniu.mzjOrder.visualClient.NetArticleMonitor;
import com.woniu.mzjOrder.vo.JsonResult;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/net")
public class NetInformationController {

    @Autowired
    private NetInformationService netInformationService;

    @GetMapping(value = "/important/article/persistence")
    public JsonResult loadNetNewsArticleToDB(){
        JsonResult jsonResult = new JsonResult();
        try {
            netInformationService.loadNetNewsArticleToDB();
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            return jsonResult;
        }catch (Exception e){
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败");
            jsonResult.setData("message:"+ e.toString());
            return jsonResult;
        }
    }

    @PostMapping(value = "/important/article/download")
    public JsonResult queryNetInfoArticleToLocalFile(@RequestBody NetInfoQueryParamVo queryParamVo){
        JsonResult jsonResult = new JsonResult();
        try {
            netInformationService.queryNetNewsArticleToFile(queryParamVo);
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            return jsonResult;
        }catch (Exception e){
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败");
            jsonResult.setData("message:"+ e.toString());
            return jsonResult;
        }
    }

    @GetMapping(value = "/important/article/monitor")
    public JsonResult activeMonitorClient(){
        JsonResult jsonResult = new JsonResult();
        try {
            NetArticleMonitor.initFrame();
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            return jsonResult;
        }catch (Exception e){
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败");
            jsonResult.setData("message:"+ e.toString());
            return jsonResult;
        }

    }

}
