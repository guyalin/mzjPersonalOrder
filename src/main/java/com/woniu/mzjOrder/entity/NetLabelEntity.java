package com.woniu.mzjOrder.entity;

import com.woniu.mzjOrder.filter.SecurityUserHelper;
import com.woniu.mzjOrder.util.UUIDUtil;
import com.woniu.mzjOrder.vo.NetLabelVo;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/6
 */
@Data
public class NetLabelEntity {

    private String labelId;
    private String labelName;
    private String netList;
    private String createTime;
    private String description;
    private String createUserId;

    public static NetLabelEntity convertVo2Entity(NetLabelVo netLabelVo){
        NetLabelEntity netLabelEntity = new NetLabelEntity();
        if (StringUtils.isBlank(netLabelVo.getLabelId())){
            netLabelEntity.setLabelId(UUIDUtil.getUUID32());
        }else
            netLabelEntity.setLabelId(netLabelVo.getLabelId());
        netLabelEntity.setCreateTime(netLabelVo.getCreateTime());
        netLabelEntity.setLabelName(netLabelVo.getLabelName());
        netLabelEntity.setNetList(netLabelVo.getNetList());
        netLabelEntity.setCreateUserId(SecurityUserHelper.getCurrentPrincipal().getUsername());
        return netLabelEntity;
    }
}
