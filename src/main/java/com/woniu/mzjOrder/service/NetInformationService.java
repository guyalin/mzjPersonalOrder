package com.woniu.mzjOrder.service;

import com.woniu.mzjOrder.controller.WebSocketServer;
import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.ArticleRecordFilter;
import com.woniu.mzjOrder.entity.NetChildFilter;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.util.DocumentUtil;
import com.woniu.mzjOrder.vo.JsonResult;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import com.woniu.mzjOrder.vo.NetUrlVo;
import com.woniu.mzjOrder.vo.TextLocationEnum;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface NetInformationService {

    void loadNetNewsArticleToDB() throws IOException;

    List<ArticleRecord> queryNetNewsArticle(NetInfoQueryParamVo infoQueryParamVo);

    void saveToLocalFile() throws FileNotFoundException;

    List<UrlMonitorEntity> queryUrlEntities();

    void sendWebSocketMessage(WebSocketServer socketServer, Object message);

    JsonResult saveNetUrl(UrlMonitorEntity netUrlVo, String sid);

    List<ArticleRecord> testUrlEntity(UrlMonitorEntity urlMonitorEntity);

    default List<UrlMonitorEntity> getChildMonitorEntity(Document rootDocument, UrlMonitorEntity parentUrlEntity){
        List<UrlMonitorEntity> monitorEntities = new ArrayList<>();
        NetChildFilter childDocumentRule = parentUrlEntity.getNetChildFilter();
        ArticleRecordFilter articleRecordFilter = parentUrlEntity.getArticleRecordFilter();
        String parentName = parentUrlEntity.getName();
        String parentArea = parentUrlEntity.getArea();

        String childRootTag = childDocumentRule.getRootTag();
        /*String rootAreaTag = childDocumentRule.getRootAreaTag();
        Integer rootAreaTagIndex = childDocumentRule.getRootAreaTagIndex();
        TextLocationEnum areaLocation = childDocumentRule.getAreaLocation();
        String areaAttrName = childDocumentRule.getAreaAttrName();*/
        String recordTag = childDocumentRule.getRecordTag();
        String childRootUrlTag = childDocumentRule.getUrlTag();
        Integer childRootUrlTagIndex = childDocumentRule.getUrlTagIndex();
        TextLocationEnum childRootUrlLocation = DocumentUtil.strToEnum(childDocumentRule.getUrlLocation());
        String urlAttrName = childDocumentRule.getUrlAttrName();
        String childNameTag = childDocumentRule.getNameTag();
        Integer childNameTagIndex = childDocumentRule.getNameTagIndex();
        TextLocationEnum nameLocation = DocumentUtil.strToEnum(childDocumentRule.getNameLocation());
        String nameAttr = childDocumentRule.getNameAttrName();

        Element rootE = rootDocument.select(childRootTag).first();
        //String entityArea = rootE.select(rootAreaTag).first().ownText(); //所有子地区的所属公共地区名
        //String entityArea = DocumentUtil.getLocationText(rootE, areaLocation, rootAreaTag, rootAreaTagIndex, areaAttrName); //所有子地区的所属公共地区名
        String entityArea = parentArea;
        Elements childRecords = rootE.select(recordTag);

        for (Element childRecord : childRecords) {
            String childName = parentName.concat("-").concat(DocumentUtil.getLocationText(childRecord, nameLocation, childNameTag, childNameTagIndex, nameAttr));
            String childRootUrl = DocumentUtil.getLocationText(childRecord, childRootUrlLocation, childRootUrlTag, childRootUrlTagIndex, urlAttrName);
            UrlMonitorEntity urlMonitorEntity = new UrlMonitorEntity(entityArea, childName, childRootUrl, childRootUrl,"");
            urlMonitorEntity.setArticleRecordFilter(articleRecordFilter);
            monitorEntities.add(urlMonitorEntity);
        }

        return monitorEntities;
    }

}
