package com.woniu.mzjOrder.service;

import com.woniu.mzjOrder.entity.AuthPrivilege;
import com.woniu.mzjOrder.entity.AuthRole;
import com.woniu.mzjOrder.entity.AuthUser;
import com.woniu.mzjOrder.entity.AuthUserInfo;
import com.woniu.mzjOrder.vo.AuthUserVo;
import com.woniu.mzjOrder.vo.JsonResult;

import java.util.List;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/11/30
 */
public interface AuthUserService {

    JsonResult authUserLoginChecking(AuthUserVo authUser);

    JsonResult loginChecking(AuthUserVo authUser);

    List<AuthUser> getUserById(String userId);

    List<AuthRole> getRoleByUser(AuthUser authUser);

    List<AuthPrivilege> getPrivilegeByRoles(List<AuthRole> authRoles);

    Integer saveOrUpdUser(AuthUser authUser);

}
