package com.john.wep.controller;

import com.john.pojo.Use;
import com.john.service.UseService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CommonController {
    @Autowired
    private UseService useService;

    @RequestMapping("/")
    public String autoLogin(@CookieValue(name="username",required = false) String username,
                            @CookieValue(name="password",required = false) String password, HttpServletRequest req){
        // empty:  null or ""
        // blank:  null or ""  or  "    "
        if (StringUtils.isNoneBlank(username,password)){
            String ip = req.getRemoteAddr();
            Use user = useService.getUserForAutoLogin(username, password, ip);
                if (user!=null){
                    req.getSession().setAttribute("user",user);
                    return "redirect:/workbench/index.html";
                }

        }

        return "index";
    }

    @RequestMapping("/**/*.html")
    public String page(HttpServletRequest request){
        String uri = request.getRequestURI();
        return uri.substring(1,uri.indexOf("."));
    }

}
