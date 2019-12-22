package com.woniu.mzjOrder.dao;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.ArticleRecordFilter;
import com.woniu.mzjOrder.entity.NetChildFilter;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.vo.NetBodyMapVo;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NetInformationDao {

    void saveArticleRecords(List<ArticleRecord> records);

    List<NetBodyMapVo> queryArticleRecordsByParams(@Param("paramVo") NetInfoQueryParamVo paramVo);

    List<UrlMonitorEntity> queryNetUrlEntity(String netList);


    Integer queryUrlEntity(String urlId);

    void saveUrlEntity(@Param("urlMonitorEntity") UrlMonitorEntity urlMonitorEntity);

    Integer queryChildFilter(String childFilterId);

    void saveNetChildFilter(@Param("netChildFilter") NetChildFilter netChildFilter);

    Integer queryArticleRecordFilter(String recordFilterId);

    void saveRecordFilter(@Param("filter") ArticleRecordFilter filter);

    Integer queryUrlRecordRelation(String name);

    void saveUrlRecordRelation(Map<String, String> paramMap);
}
