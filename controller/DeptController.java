package com.john.wep.controller;

import com.john.Utils.UUIDUtils;
import com.john.pojo.Dept;
import com.john.pojo.Type;
import com.john.service.DeptService;
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
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;


    @RequestMapping("/get.do")
    @ResponseBody
    public List getAll(){
        List<Dept> all = deptService.getAll();
        return all;
    }

    @RequestMapping("/into.do")
    @ResponseBody
    public Map into(Dept dept, HttpServletResponse rep){
        dept.setId(UUIDUtils.getUUID());
        deptService.inster(dept);
        return Result.SUCCESS;
    }

    @RequestMapping("/delet.do")
    @ResponseBody
    public Map delet(String id, HttpServletResponse rep){
        String[] ids = id.split(",");
        deptService.delet(ids);
        HashMap<String, String> map = new HashMap<>();
        map.put("url","/settings/dept/index.html");
        return map;
    }

    @RequestMapping("/update.to")
    @ResponseBody
    public Dept editTo(String id){
        return deptService.get(id);
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public Map editDo(Dept dept){
        deptService.update(dept);
        return Result.SUCCESS;
    }

}
