package com.john.wep.controller;


import com.john.pojo.Page;
import com.john.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/tra")
public class TransactionController {

   @Autowired
   private TransactionService transactionService;

   @RequestMapping("/page.json")
   public Page page(@RequestParam Map params,Page page){
      System.out.println(params);
      transactionService.getPage(params,page);
      return  page;
   }
}
