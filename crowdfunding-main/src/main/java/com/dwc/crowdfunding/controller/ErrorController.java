package com.dwc.crowdfunding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/auth")
    public String authError(){
        return "error/auth";
    }
}
