package com.woniu.mzjOrder.service;

import com.woniu.mzjOrder.vo.NetInfoQueryParamVo;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface NetInformationService {

    void loadNetNewsArticleToDB() throws IOException;

    void queryNetNewsArticleToFile(NetInfoQueryParamVo infoQueryParamVo) throws FileNotFoundException;
}
