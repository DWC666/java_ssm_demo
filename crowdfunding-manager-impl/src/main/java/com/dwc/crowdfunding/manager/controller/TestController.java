package com.dwc.crowdfunding.manager.controller;

import com.dwc.crowdfunding.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping("/test")
    public String test(){
        Byte b;
        System.out.println("TestController");
        testService.insert();
        return "success";
    }

}
