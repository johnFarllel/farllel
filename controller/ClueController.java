package com.john.wep.controller;

import com.john.pojo.Page;
import com.john.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/clue")
@ResponseBody
public class ClueController {

    @Autowired
    private ClueService clueServiceImp;

    @RequestMapping("/page.json")
    public Page page(@RequestParam Map param,Page page){
        clueServiceImp.getPage(param,page);
        return page;
    }
}
