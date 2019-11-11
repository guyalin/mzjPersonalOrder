package com.woniu.mzjOrder.dao;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.ArticleRecordFilter;
import com.woniu.mzjOrder.entity.NetChildFilter;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NetInformationDao {

    void saveArticleRecords(List<ArticleRecord> records);

    List<ArticleRecord> queryArticleRecordsByParams(@Param("paramVo") NetInfoQueryParamVo paramVo);

    List<UrlMonitorEntity> queryNetUrlEntity();

    void saveUrlEntity(UrlMonitorEntity urlMonitorEntity);

    void saveNetChildFilter(NetChildFilter netChildFilter);

    void saveRecordFilter(ArticleRecordFilter filter);

    void saveUrlRecordRelation(Map<String, String> paramMap);
}
