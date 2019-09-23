package com.woniu.mzjOrder.controller;

import com.woniu.mzjOrder.service.CommonLocalResourceService;
import com.woniu.mzjOrder.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/local")
public class CommonLocalResource {

    @Autowired
    private CommonLocalResourceService localResourceService;

    @GetMapping(value = "/gisMap")
    public JsonResult getLocalGisMap(@RequestParam(name = "areaCode",required = true) String areaCode){
        JsonResult jsonResult = new JsonResult();
        try{
            String gisMap = localResourceService.getLocalJsonFile(areaCode);
            jsonResult.setReturnCode("0");
            jsonResult.setReturnMsg("成功");
            jsonResult.setData(gisMap);
            return jsonResult;
        } catch (Exception e) {
            log.error("Query Charts model error:{}", e);
            jsonResult.setReturnCode("1");
            jsonResult.setReturnMsg("查询失败");
            return jsonResult;
        }
    }


}
