package com.woniu.mzjOrder.controller;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.service.NetInformationService;
import com.woniu.mzjOrder.vo.JsonResult;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 本地调试用，后期废弃
     * @param queryParamVo
     * @return
     */
    @PostMapping(value = "/important/article/download")
    public JsonResult queryNetInfoArticleToLocalFile(@RequestBody NetInfoQueryParamVo queryParamVo){
        JsonResult jsonResult = new JsonResult();
        try {
            netInformationService.queryNetNewsArticle(queryParamVo);
            netInformationService.saveToLocalFile();
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

    @PostMapping(value = "/important/article/recordInfo")
    public JsonResult queryNetArticleInfo(@RequestBody NetInfoQueryParamVo queryParamVo) {
        JsonResult jsonResult = new JsonResult();
        try {
            List<ArticleRecord> articleRecords = netInformationService.queryNetNewsArticle(queryParamVo);
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            jsonResult.setData(articleRecords);
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

    @GetMapping(value = "/important/article/netUrl")
    public JsonResult queryActiveNetEntities(){
        JsonResult jsonResult = new JsonResult();
        List<UrlMonitorEntity> urlEntities = new ArrayList<>();
        try {
            urlEntities = netInformationService.queryUrlEntities();
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            jsonResult.setData(urlEntities);
            return jsonResult;
        }catch (Exception e){
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败");
            jsonResult.setData("message:"+ e.toString());
            return jsonResult;
        }

    }

}
