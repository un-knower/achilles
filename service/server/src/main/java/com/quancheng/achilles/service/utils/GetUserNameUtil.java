package com.quancheng.achilles.service.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextImpl;

/**
 * @author lijun jiang
 * @version 创建时间：2016年10月11日下午4:55:54
 */
public class GetUserNameUtil {

    public static String getUserName(HttpServletRequest request) {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String userName = securityContextImpl.getAuthentication().getName();
        return userName;
    }

}
