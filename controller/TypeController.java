package com.john.wep.controller;

import com.john.pojo.Type;
import com.john.service.TypeService;
import com.john.wep.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping("/get.do")
    @ResponseBody
    public List getAll(){
         List<Type> all = typeService.getAll();
         return all;
    }

    @RequestMapping("/into.do")
    @ResponseBody
    public Map into(Type type, HttpServletResponse rep){
        typeService.inster(type);
        HashMap<String, String> map = new HashMap<>();
        map.put("url","/settings/dictionary/type/index.html");
        return map;
    }

    @RequestMapping("/delet.do")
    @ResponseBody
    public Map delet(String id, HttpServletResponse rep){
        String[] ids = id.split(",");
        typeService.delet(ids);
        HashMap<String, String> map = new HashMap<>();
        map.put("url","/settings/dictionary/type/index.html");
        return map;
    }

    @RequestMapping("/update.to")
    public ModelAndView editTo(String id){
        ModelAndView mv = new ModelAndView();
        mv.addObject("type", typeService.get(id));
        mv.setViewName("settings/dictionary/type/edit");
        return mv;
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public Map editDo(Type type){
        int i = typeService.update(type);
        System.out.println(i);
        return Result.SUCCESS;
    }
}
