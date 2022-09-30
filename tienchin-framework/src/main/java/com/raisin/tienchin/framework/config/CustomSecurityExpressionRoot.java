package com.raisin.tienchin.framework.config;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * 自定义权限匹配器
 * @author: Raisin
 * @date: 2022/9/29
 */
public class CustomSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * Creates a new instance
     * @param authentication the {@link Authentication} to use. Cannot be null.
     */
    public CustomSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    /**
     * 判断当前对象是否具备某个权限
     * @return permission
     */
    public boolean hasPermission(String permission) {
        // 获取当前登录用户所具有的权限
        // 这里实际上调用到的是 LoginUser.getAuthorities 方法的返回值
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (antPathMatcher.match(authority.getAuthority(), permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否具备多个权限中的任意一个权限
     * @return
     */
    public boolean hasAnyPermission(String... permissions) {
        if (null == permissions || permissions.length == 0) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            for (String permission : permissions) {
                if (authority.equals(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param permissions
     * @return
     */
    public boolean hasAllPermissions(String... permissions) {
        if (null == permissions || permissions.length == 0) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            boolean flag = false;
            for (String permission : permissions) {
                if (antPathMatcher.match(authority.getAuthority(), permission)) {
                    flag = true;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
