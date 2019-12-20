package com.woniu.mzjOrder.filter;

import com.woniu.mzjOrder.vo.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 */
public class SecurityUserHelper {
    /**
     * 获取当前用户
     *
     * @return
     */
    public static Authentication getCurrentUserAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public static JwtUser getCurrentPrincipal() {
        return (JwtUser) getCurrentUserAuthentication().getPrincipal();
    }


}
