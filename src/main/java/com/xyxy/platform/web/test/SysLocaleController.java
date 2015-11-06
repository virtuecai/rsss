package com.xyxy.platform.web.test;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Created by Administrator on 2015/11/5 0005.
 */
@Controller
@RequestMapping("/locale")
public class SysLocaleController {
    @Autowired
    private LocaleResolver localeResolver;

    @RequestMapping("/changeLocale")
    public String changeLocale(String locale, HttpServletRequest request, HttpServletResponse response) {
        Locale l = new Locale(locale);
        localeResolver.setLocale(request, response, l);
        return "redirect:/demo/index.do";
    }
}
