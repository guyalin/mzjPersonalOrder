package com.woniu.mzjOrder.dao;

import com.woniu.mzjOrder.entity.NetLabelEntity;
import com.woniu.mzjOrder.vo.NetLabelVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/6
 */
@Repository
public interface NetLabelDao {

    List<NetLabelVo> queryNetLabelByUserId(@Param("userId") String userId);

    Integer saveOrUpdNetLabel(NetLabelEntity netLabelEntity);

    Integer delNetLabelByIdAndUser(String labelId, String userId);

}
