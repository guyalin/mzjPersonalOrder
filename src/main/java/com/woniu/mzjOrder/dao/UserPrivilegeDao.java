package com.woniu.mzjOrder.dao;

import com.woniu.mzjOrder.entity.AuthPrivilege;
import com.woniu.mzjOrder.entity.AuthRole;
import com.woniu.mzjOrder.entity.AuthUser;
import com.woniu.mzjOrder.entity.AuthUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/1
 */
@Repository
public interface UserPrivilegeDao {

    AuthUserInfo queryUserInfoById(String userAccount);

    void updUserLoginInfo(String userAccount);

    List<AuthUser> queryUserById(String userId);

    List<AuthRole> queryRoleByUser(AuthUser user);

    List<AuthPrivilege> queryPrivilegeByRole(String roleIds);
}
