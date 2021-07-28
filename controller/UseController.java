package com.john.wep.controller;

import com.john.pojo.Use;
import com.john.service.UseService;
import com.john.wep.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UseController {
    @Autowired
    private UseService useService;

    @RequestMapping("/login.do")
    @ResponseBody
    public Map use(String username, String password, boolean remember, HttpServletRequest req,HttpServletResponse rep){
        String ip = req.getRemoteAddr();
        Use use = useService.get(username, password,ip);
        req.getSession().setAttribute("user",use);
//        选择十天免登录
        if (remember){
            int maxAge = 3600*24*10;
            Cookie cookie1 = new Cookie("username",username);
            cookie1.setMaxAge(maxAge);
            cookie1.setPath("/");
            Cookie cookie2 = new Cookie("password",password);
            cookie2.setMaxAge(maxAge);
            cookie2.setPath("/");
            rep.addCookie(cookie1);
            rep.addCookie(cookie2);
        }
        return Result.SUCCESS;
    }

    @RequestMapping("logout.do")
    public String logOut(HttpServletResponse rep){
        Cookie cookie = new Cookie("username", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        rep.addCookie(cookie);
        return "redirect:/";
    }

@RequestMapping("/owner.do")
@ResponseBody
    public List getOwners(){
       return useService.getOwners();
    }
}
