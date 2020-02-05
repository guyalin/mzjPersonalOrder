package com.woniu.mzjOrder.controller;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.filter.SecurityUserHelper;
import com.woniu.mzjOrder.service.NetInformationService;
import com.woniu.mzjOrder.service.NetLabelService;
import com.woniu.mzjOrder.vo.JsonResult;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import com.woniu.mzjOrder.vo.NetLabelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/net")
public class NetInformationController {

    @Autowired
    private NetInformationService netInformationService;
    @Autowired
    private NetLabelService netLabelService;

    /**
     * 更新网页内容
     * @param netList
     * @return
     */
    @PostMapping(value = "/important/article/persistence")
    public JsonResult loadNetNewsArticleToDB(@RequestBody(required = false) String netList) {
        JsonResult jsonResult = new JsonResult();
        try {
            netInformationService.loadNetNewsArticleToDB(netList);
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            return jsonResult;
        } catch (Exception e) {
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败");
            jsonResult.setData("message:" + e.toString());
            return jsonResult;
        }
    }

    /**
     * 本地调试用，后期废弃
     *
     * @param queryParamVo
     * @return
     */
/*    @PostMapping(value = "/important/article/download")
    public JsonResult queryNetInfoArticleToLocalFile(@RequestBody NetInfoQueryParamVo queryParamVo) {
        JsonResult jsonResult = new JsonResult();
        try {
            netInformationService.queryNetNewsArticle(queryParamVo);
            //netInformationService.saveToLocalFile();
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            return jsonResult;
        } catch (Exception e) {
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败");
            jsonResult.setData("message:" + e.toString());
            return jsonResult;
        }
    }*/

    /**
     * 查询新闻列表记录
     * @param queryParamVo
     * @return
     */
    @PostMapping(value = "/important/article/recordInfo")
    public JsonResult queryNetArticleInfo(@RequestBody NetInfoQueryParamVo queryParamVo) {
        JsonResult jsonResult = new JsonResult();
        try {
            List<ArticleRecord> articleRecords = netInformationService.queryNetNewsArticle(queryParamVo);
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            jsonResult.setData(articleRecords);
            return jsonResult;
        } catch (Exception e) {
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败");
            jsonResult.setData("message:" + e.toString());
            return jsonResult;
        }
    }

    /**
     * 查询监控新闻列表
     * @return
     */
    @GetMapping(value = "/important/article/netUrl")
    public JsonResult queryActiveNetEntities() {
        JsonResult jsonResult = new JsonResult();
        List<UrlMonitorEntity> urlEntities = new ArrayList<>();
        try {
            urlEntities = netInformationService.queryUrlEntities();
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            jsonResult.setData(urlEntities);
            return jsonResult;
        } catch (Exception e) {
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败");
            jsonResult.setData("message:" + e.toString());
            return jsonResult;
        }

    }

    /**
     * 保存新增网址及过滤规则
     *
     * @param urlMonitorEntity
     * @return
     */
    @PostMapping(value = "/important/url/save")
    public JsonResult saveNetUrl(@RequestBody UrlMonitorEntity urlMonitorEntity) {
        JsonResult jsonResult = new JsonResult();
        try {
            jsonResult = netInformationService.saveNetUrl(urlMonitorEntity);
            return jsonResult;
        } catch (Exception e) {
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败" + e.toString());
            return jsonResult;
        }
    }

    /**
     * 测试新增网址
     *
     * @param monitorEntity
     * @return
     */
    @PostMapping(value = "/important/url/test")
    public JsonResult testNetUrl(@RequestBody UrlMonitorEntity monitorEntity) {
        JsonResult jsonResult = new JsonResult();
        List<ArticleRecord> articleRecords;
        try {
            articleRecords = netInformationService.testUrlEntity(monitorEntity);
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            jsonResult.setData(articleRecords);
            return jsonResult;
        } catch (Exception e) {
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败" + e.toString());
            return jsonResult;
        }

    }

    /**
     * 查询标签
     * @return
     */
    @GetMapping("/label/query")
    public JsonResult netLabelQuery(){
        JsonResult jsonResult = new JsonResult();
        try {
            String userId = SecurityUserHelper.getCurrentPrincipal().getUsername();
            List<NetLabelVo> netLabelVos = netLabelService.getNetLabelByUserId(userId);
            jsonResult.setReturnCode(JsonResult.SUCC);
            jsonResult.setData(netLabelVos);
            jsonResult.setReturnMsg("查询成功");
        }catch (Exception e){
            jsonResult.setReturnCode(JsonResult.FAIL);
            jsonResult.setReturnMsg("查询失败，"+ e.toString());
        }
        return jsonResult;
    }

    /**
     * 保存标签
     * @param netLabelVo
     * @return
     */
    @PostMapping(value = "/label/save")
    public JsonResult netLabelSave(@RequestBody NetLabelVo netLabelVo){
        JsonResult jsonResult = new JsonResult();
        try {
            Integer i = netLabelService.saveOrUpdNetLabel(netLabelVo);
            jsonResult.setReturnCode(JsonResult.SUCC);
            jsonResult.setReturnMsg("插入成功, 新增"+i+"条");
        }catch (Exception e){
            jsonResult.setReturnCode(JsonResult.FAIL);
            jsonResult.setReturnMsg("新增标签失败，"+ e.toString());
        }
        return jsonResult;
    }

    /**
     * 删除标签
     * @param labelId
     * @return
     */
    @RequestMapping(value = "/label/del", method = RequestMethod.POST)
    public JsonResult netLabelDelete(@RequestBody String labelId){
        JsonResult jsonResult = new JsonResult();
        try {
            Integer i = netLabelService.delNetLabel(labelId);
            jsonResult.setReturnCode(JsonResult.SUCC);
            jsonResult.setReturnMsg("删除成功, 删除"+i+"条");
        }catch (Exception e){
            jsonResult.setReturnCode(JsonResult.FAIL);
            jsonResult.setReturnMsg("删除标签失败，"+ e.toString());
        }
        return jsonResult;

    }


}
