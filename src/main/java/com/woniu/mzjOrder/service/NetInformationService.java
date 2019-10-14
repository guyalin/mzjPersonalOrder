package com.woniu.mzjOrder.service;

import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.util.DateUtil;
import com.woniu.mzjOrder.util.DocumentUtil;
import com.woniu.mzjOrder.vo.ChildDocumentRule;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import com.woniu.mzjOrder.vo.NodeRule;
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

    void queryNetNewsArticleToFile(NetInfoQueryParamVo infoQueryParamVo) throws FileNotFoundException;

    default List<UrlMonitorEntity> getChildMonitorEntity(Document rootDocument, ChildDocumentRule childDocumentRule, String parentName, String parentArea){
        List<UrlMonitorEntity> monitorEntities = new ArrayList<>();

        String childRootTag = childDocumentRule.getRootTag();
        String rootAreaTag = childDocumentRule.getRootAreaTag();
        Integer rootAreaTagIndex = childDocumentRule.getRootAreaTagIndex();
        TextLocationEnum areaLocation = childDocumentRule.getAreaLocation();
        String areaAttrName = childDocumentRule.getAreaAttrName();
        String recordTag = childDocumentRule.getRecordTag();
        String childRootUrlTag = childDocumentRule.getChildRootUrlTag();
        Integer childRootUrlTagIndex = childDocumentRule.getChildRootUrlTagIndex();
        TextLocationEnum childRootUrlLocation = childDocumentRule.getChildRootUrlLocation();
        String urlAttrName = childDocumentRule.getUrlAttrName();
        String childNameTag = childDocumentRule.getChildNameTag();
        Integer childNameTagIndex = childDocumentRule.getChildNameTagIndex();
        TextLocationEnum nameLocation = childDocumentRule.getNameLocation();
        String nameAttr = childDocumentRule.getNameAttr();

        Element rootE = rootDocument.select(childRootTag).first();
        //String entityArea = rootE.select(rootAreaTag).first().ownText(); //所有子地区的所属公共地区名
        //String entityArea = DocumentUtil.getLocationText(rootE, areaLocation, rootAreaTag, rootAreaTagIndex, areaAttrName); //所有子地区的所属公共地区名
        String entityArea = parentArea;
        Elements childRecords = rootE.select(recordTag);

        for (Element childRecord : childRecords) {
            String childName = parentName.concat("-").concat(DocumentUtil.getLocationText(childRecord, nameLocation, childNameTag, childNameTagIndex, nameAttr));
            String childRootUrl = DocumentUtil.getLocationText(childRecord, childRootUrlLocation, childRootUrlTag, childRootUrlTagIndex, urlAttrName);
            UrlMonitorEntity urlMonitorEntity = new UrlMonitorEntity(entityArea, childName, childRootUrl, childRootUrl,"");
            monitorEntities.add(urlMonitorEntity);
        }

        return monitorEntities;
    }

}
