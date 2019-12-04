package com.woniu.mzjOrder.service.impl;

import com.woniu.mzjOrder.entity.AuthPrivilege;
import com.woniu.mzjOrder.entity.AuthRole;
import com.woniu.mzjOrder.entity.AuthUser;
import com.woniu.mzjOrder.service.AuthUserService;
import com.woniu.mzjOrder.vo.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/3
 */
@Service("mzjUserDetailService")
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthUserService loginService;

    @Override
    public UserDetails loadUserByUsername(String userId) {

        List<AuthUser> authUserList = new ArrayList<>();
        AuthUser user;
        try {
            authUserList = loginService.getUserById(userId);

            if (CollectionUtils.isEmpty(authUserList)) {
                throw new UsernameNotFoundException("用户账号：" + userId + "，不存在");
            } else {
                user = authUserList.get(0);
                Set<GrantedAuthority> authorities = new HashSet<>();
                //获取该用户所有的权限信息
                List<AuthRole> roles = loginService.getRoleByUser(user);
                List<AuthPrivilege> authPrivileges = loginService.getPrivilegeByRoles(roles);
                authPrivileges.forEach(
                        authPrivilege -> authorities.add(new SimpleGrantedAuthority(authPrivilege.getPrivilegeId())));

                return new JwtUser(userId, user.getUserName(), user.getUserPassWd(), user.getLastLoginTime(), user.getIsLocked(), authorities);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JwtUser(null, null, null, null, null, null);
    }
}
