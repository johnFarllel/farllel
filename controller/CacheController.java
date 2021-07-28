package com.john.wep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/cache")
public class CacheController {
    @RequestMapping("/options.json")
    public Object getValues(String type, HttpServletRequest request){
        ServletContext context = request.getServletContext();
        Map map = (Map)context.getAttribute("dicMapList");
        return map.get(type);
    }
}
