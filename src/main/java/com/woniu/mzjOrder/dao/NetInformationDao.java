package com.woniu.mzjOrder.dao;

import com.woniu.mzjOrder.entity.ArticleRecord;
import com.woniu.mzjOrder.entity.UrlMonitorEntity;
import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetInformationDao {

    void saveArticleRecords(List<ArticleRecord> records);

    List<ArticleRecord> queryArticleRecordsByParams(@Param("paramVo") NetInfoQueryParamVo paramVo);

    List<UrlMonitorEntity> queryNetUrlEntity();


}
