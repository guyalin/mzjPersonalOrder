package com.woniu.mzjOrder.service;

import com.woniu.mzjOrder.entity.NetLabelEntity;
import com.woniu.mzjOrder.vo.NetLabelVo;

import java.util.List;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/6
 */
public interface NetLabelService {

    List<NetLabelVo> getNetLabelByUserId(String userId);

    Integer saveOrUpdNetLabel(NetLabelVo netLabelVo);

    Integer delNetLabel(String labelId);
}
