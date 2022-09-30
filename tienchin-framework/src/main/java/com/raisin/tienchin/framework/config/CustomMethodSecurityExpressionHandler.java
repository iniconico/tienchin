package com.raisin.tienchin.framework.config;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * @author: Raisin
 * @date: 2022/9/29
 */
public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, MethodInvocation invocation) {
        CustomSecurityExpressionRoot customSecurityExpressionRoot = new CustomSecurityExpressionRoot(authentication);
        customSecurityExpressionRoot.setTrustResolver(getTrustResolver());
        customSecurityExpressionRoot.setPermissionEvaluator(getPermissionEvaluator());
        customSecurityExpressionRoot.setRoleHierarchy(getRoleHierarchy());
        return customSecurityExpressionRoot;
    }
}
