package com.woniu.mzjOrder.entity;

import com.woniu.mzjOrder.vo.NetLabelVo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/11/30
 */
@Data
public class AuthUserInfo {
    private String userId;
    private String userName;
    private String token;
    private String lastLoginTime;
    private List<NetLabelVo> netLabelVos;
    private Collection<? extends GrantedAuthority> privileges;

}
