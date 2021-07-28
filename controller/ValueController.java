package com.john.wep.controller;

import com.john.Utils.UUIDUtils;
import com.john.pojo.Value;
import com.john.service.ValueService;
import com.john.wep.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/value")
public class ValueController {

    @Autowired
    private ValueService valueService;

    @RequestMapping("/get.do")
    @ResponseBody
    public List getAll(){
        List<Value> all = valueService.getAll();
        return all;
    }

    @RequestMapping("/into.do")
    @ResponseBody
    public Map into(Value value, HttpServletResponse rep){
        value.setId(UUIDUtils.getUUID());
        valueService.inster(value);
        HashMap<String, String> map = new HashMap<>();
        map.put("url","/settings/dictionary/value/index.html");
        return map;
    }

    @RequestMapping("/delet.do")
    @ResponseBody
    public Map delet(String id, HttpServletResponse rep){
        String[] ids = id.split(",");
        valueService.delet(ids);
        HashMap<String, String> map = new HashMap<>();
        map.put("url","/settings/dictionary/value/index.html");
        return map;
    }

    @RequestMapping("/update.to")
    public ModelAndView editTo(String id){
        ModelAndView mv = new ModelAndView();
        mv.addObject("type", valueService.get(id));
        mv.setViewName("settings/dictionary/value/edit");
        return mv;
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public Map editDo(Value value){
        valueService.update(value);
        return Result.SUCCESS;
    }
}
