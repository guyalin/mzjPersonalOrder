package com.woniu.mzjOrder.service.impl;

import com.woniu.mzjOrder.dao.NetLabelDao;
import com.woniu.mzjOrder.entity.NetLabelEntity;
import com.woniu.mzjOrder.filter.SecurityUserHelper;
import com.woniu.mzjOrder.service.NetLabelService;
import com.woniu.mzjOrder.vo.JwtUser;
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
        String userId1 = userId;
        String userId2 = SecurityUserHelper.getCurrentPrincipal().getUsername();
        return netLabelDao.queryNetLabelByUserId(userId2);
    }

    @Override
    public Integer saveOrUpdNetLabel(NetLabelVo netLabelVo) {
        NetLabelEntity netLabelEntity = NetLabelEntity.convertVo2Entity(netLabelVo);
        Integer insertCnt = netLabelDao.saveOrUpdNetLabel(netLabelEntity);
        return insertCnt;
    }

    @Override
    public Integer delNetLabel(String labelId) {
        String userId = SecurityUserHelper.getCurrentPrincipal().getUsername();
        Integer insertCnt = netLabelDao.delNetLabelByIdAndUser(labelId, userId);
        return insertCnt;
    }
}
