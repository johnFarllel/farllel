package com.john.wep.controller;


import com.john.Utils.ControllerUtils;
import com.john.Utils.UUIDUtils;
import com.john.pojo.Activities;
import com.john.service.ActivitiesService;
import com.john.wep.Result;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/act")
public class ActivitiesController {

    @Autowired
    private ActivitiesService activitiesService;

    @RequestMapping("/get.do")
    @ResponseBody
    public List getMore(@RequestParam Map data){
        return activitiesService.getMore(data);
    }

    @RequestMapping("/getOne.do")
    @ResponseBody
    public Map get(String id){
        return activitiesService.get(id);
    }

    @PostMapping("/into.do")
    @ResponseBody
    public Map inDO(Activities activities){
        activities.setId(UUIDUtils.getUUID());
        activitiesService.insert(activities);
        return Result.SUCCESS;
    }


   @RequestMapping("/exportBtn.do")
   @ResponseBody
    public Map exportBtn(@RequestParam Map data, HttpServletResponse response){
//        一个空的excel文件
       HSSFWorkbook sheets = new HSSFWorkbook();
//          创建页签
       HSSFSheet sheet = sheets.createSheet();
//       创建行
       int i = 0;
       HSSFRow row = sheet.createRow(i++);
//       创建单元格（列）
       int j = 0;
       row.createCell(j++).setCellValue("名称");
       row.createCell(j++).setCellValue("所有者");
       row.createCell(j++).setCellValue("开始日期");
       row.createCell(j++).setCellValue("结束日期");

       List<Activities> activities = activitiesService.getMore(data);
       for (Activities activity : activities) {
           row = sheet.createRow(i++);
           j = 0;
           row.createCell(j++).setCellValue(activity.getOwner());
           row.createCell(j++).setCellValue(activity.getName());
           row.createCell(j++).setCellValue(activity.getStartDate());
           row.createCell(j++).setCellValue(activity.getEndDate());
       }
       response.addHeader("Content-Disposition", "attachment;filename=activity.xls");

       try {
           sheets.write(response.getOutputStream());
       } catch (IOException e) {
           e.printStackTrace();
       }
       return Result.SUCCESS;
   }

   @RequestMapping("/import.do")
   @ResponseBody
   public Map importDo(MultipartFile file, HttpSession session){
       try {
           HSSFWorkbook sheets = new HSSFWorkbook(file.getInputStream());
           HSSFSheet sheet = sheets.getSheetAt(0);
           int i = 1;
           HSSFRow row;

           ArrayList<Activities> activities1 = new ArrayList<>();

           while ((row = sheet.getRow(i++))!=null){
               int j = 0;
               String name = row.getCell(j++).getStringCellValue();
               String owner = row.getCell(j++).getStringCellValue();
               String startDate = row.getCell(j++).getStringCellValue();
               String endDate = row.getCell(j++).getStringCellValue();
               Activities activities = new Activities();
               ControllerUtils.initSave(activities,session);
               activities.setName(name);
               activities.setOwner(owner);
               activities.setStartDate(startDate);
               activities.setEndDate(endDate);
               activities1.add(activities);
           }
        activitiesService.insertList(activities1);
       } catch (IOException e) {
           e.printStackTrace();
       }
       return Result.SUCCESS;
    }

}
