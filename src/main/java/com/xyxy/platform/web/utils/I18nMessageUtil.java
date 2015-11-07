package com.xyxy.platform.web.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/11/7 0007.
 */
public class I18nMessageUtil {

    public static String get(HttpServletRequest request, String messageKey, Object ... args) {
        ApplicationContext ctx = RequestContextUtils.getWebApplicationContext(request);
        String msg = ctx.getMessage(messageKey, args, RequestContextUtils.getLocale(request));
        return msg;
    }

}
