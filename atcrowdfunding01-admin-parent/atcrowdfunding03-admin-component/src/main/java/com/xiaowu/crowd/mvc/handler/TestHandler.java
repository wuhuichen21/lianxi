package com.xiaowu.crowd.mvc.handler;


import com.xiaowu.crowd.entity.po.Admin;
import com.xiaowu.crowd.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestHandler {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap map){
//        List<Admin> adminList = adminService.getAll();
//
//
//        for (Integer bbb:aaa) {
//            System.out.println(bbb);
//        }
//        modelMap.addAttribute("adminList",adminList);
        List<Admin> all = adminService.getAll();
        map.addAttribute("adminList",all);
        return "target";
    }

    @RequestMapping(value = "aaaaa",method = RequestMethod.POST)
    public String test(@RequestBody List<Integer> aa){

        for ( Integer bb:aa) {
            System.out.println(bb);
        }
        return "成功";
    }
}
