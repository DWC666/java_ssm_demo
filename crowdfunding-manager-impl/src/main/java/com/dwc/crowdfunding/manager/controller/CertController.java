package com.dwc.crowdfunding.manager.controller;

import com.dwc.crowdfunding.bean.Cert;
import com.dwc.crowdfunding.manager.service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cert")
public class CertController {
    @Autowired
    private CertService certService;

    @RequestMapping("/index")
    public String cert(HttpSession session){
        // 读取资质数据集合
        List<Cert> allCerts = certService.queryAll();
        session.setAttribute("allCerts", allCerts);

        return "cert/index";
    }
}
