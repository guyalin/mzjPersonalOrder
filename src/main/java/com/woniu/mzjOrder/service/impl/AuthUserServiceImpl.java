package com.woniu.mzjOrder.service.impl;

import com.woniu.mzjOrder.dao.UserPrivilegeDao;
import com.woniu.mzjOrder.entity.AuthPrivilege;
import com.woniu.mzjOrder.entity.AuthRole;
import com.woniu.mzjOrder.entity.AuthUser;
import com.woniu.mzjOrder.entity.AuthUserInfo;
import com.woniu.mzjOrder.service.AuthUserService;
import com.woniu.mzjOrder.util.JwtTokenUtil;
import com.woniu.mzjOrder.vo.AuthUserVo;
import com.woniu.mzjOrder.vo.JsonResult;
import com.woniu.mzjOrder.vo.JwtUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/1
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private UserPrivilegeDao userPrivilegeDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    @Qualifier("mzjUserDetailService")
    private UserDetailsService jwtUserDetailsService;

    @Override
    public JsonResult authUserLoginChecking(AuthUserVo authUser) {
        JsonResult jsonResult = new JsonResult();
        /*if (StringUtils.isEmpty(authUser.getUserId())){
            jsonResult.setReturnMsg("用户名不能为空");
            jsonResult.setReturnCode("-1");
            return jsonResult;
        }
        if (StringUtils.isEmpty(authUser.getPassWd())){
            jsonResult.setReturnMsg("用户密码不能为空");
            jsonResult.setReturnCode("-1");
            return jsonResult;
        }*/

        AuthUserInfo userInfo = userPrivilegeDao.queryUserInfoById(authUser.getUserId());
        return null;

    }

    @Override
    @Transactional
    public JsonResult loginChecking(AuthUserVo authUser) {
        JsonResult jsonResult = new JsonResult();
        String returnMsg = "";
        if (StringUtils.isEmpty(authUser.getUserId())){
            jsonResult.setReturnMsg("用户名不能为空");
            jsonResult.setReturnCode("-1");
            return jsonResult;
        }
        if (StringUtils.isEmpty(authUser.getPassWd())){
            jsonResult.setReturnMsg("用户密码不能为空");
            jsonResult.setReturnCode("-1");
            return jsonResult;
        }
        try {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(authUser.getUserId(), authUser.getPassWd());
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authUser.getUserId());
            if (((JwtUser)userDetails).getIsLocked() == 1){
                jsonResult.setReturnMsg("用户已锁定");
                jsonResult.setReturnCode("-2");
                return jsonResult;
            }
            String token = jwtTokenUtil.generateToken(userDetails);
            AuthUserInfo userInfo = new AuthUserInfo();
            userInfo.setUserId(userDetails.getUsername());
            userInfo.setUserName(((JwtUser)userDetails).getUserRealName());
            userInfo.setLastLoginTime(((JwtUser)userDetails).getLastLoginTime());
            userInfo.setPrivileges(userDetails.getAuthorities());
            userInfo.setToken(token);
            userPrivilegeDao.updUserLoginInfo(authUser.getUserId());
            jsonResult.setReturnMsg("登录成功");
            jsonResult.setReturnCode("200");
            jsonResult.setData(userInfo);
            return jsonResult;
        }catch (Exception e){
            if (e instanceof BadCredentialsException) {
                returnMsg = "密码错误";
            } else if (e instanceof UsernameNotFoundException) {
                returnMsg = "账号不存在";
            }
            jsonResult.setReturnCode("-2");
            jsonResult.setReturnMsg(returnMsg);
            return jsonResult;
        }

    }

    @Override
    public List<AuthUser> getUserById(String userId) {
        return userPrivilegeDao.queryUserById(userId);
    }

    @Override
    public List<AuthRole> getRoleByUser(AuthUser authUser) {
        return userPrivilegeDao.queryRoleByUser(authUser);
    }

    @Override
    public List<AuthPrivilege> getPrivilegeByRoles(List<AuthRole> authRoles) {

        if (CollectionUtils.isEmpty(authRoles)){
            return null;
        }
        String roles = authRoles.stream().map(AuthRole::getRoleId).map(String::valueOf).
                collect(Collectors.joining(","));
        return userPrivilegeDao.queryPrivilegeByRole(roles);

    }
}
