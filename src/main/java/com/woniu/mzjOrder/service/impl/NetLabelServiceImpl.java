package com.woniu.mzjOrder.service.impl;

import com.woniu.mzjOrder.dao.NetLabelDao;
import com.woniu.mzjOrder.entity.NetLabelEntity;
import com.woniu.mzjOrder.service.NetLabelService;
import com.woniu.mzjOrder.vo.NetLabelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/6
 */
@Service
public class NetLabelServiceImpl implements NetLabelService {

    @Autowired
    private NetLabelDao netLabelDao;

    @Override
    public List<NetLabelVo> getNetLabelByUserId(String userId) {
        return netLabelDao.queryNetLabelByUserId(userId);
    }

    @Override
    public Integer saveOrUpdNetLabel(NetLabelEntity netLabelEntity) {
        return null;
    }
}
