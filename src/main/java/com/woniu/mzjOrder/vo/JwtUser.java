package com.woniu.mzjOrder.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @description:
 * @author: guyalin
 * @date: 2019/12/3
 */
public class JwtUser implements UserDetails {
    private String userId;
    private String userName;
    private String passWord;
    private String lastLoginTime;
    private Integer isLocked;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(String userId, String userName, String passWord, String lastLoginTime, Integer isLocked, Collection<? extends GrantedAuthority> authorities){
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.lastLoginTime = lastLoginTime;
        this.isLocked = isLocked;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.passWord;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    public String getUserRealName() {
        return this.userName;
    }

    public String getLastLoginTime(){
        return this.lastLoginTime;
    }

    public Integer getIsLocked(){
        return this.isLocked;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
