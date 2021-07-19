package com.dwc.crowdfunding.manager.controller;

import com.dwc.crowdfunding.bean.AccountTypeCert;
import com.dwc.crowdfunding.bean.Cert;
import com.dwc.crowdfunding.controller.BaseController;
import com.dwc.crowdfunding.manager.service.CertService;
import com.dwc.crowdfunding.manager.service.CertTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/certType")
public class CertTypeController extends BaseController {
    @Autowired
    private CertService certService;

    @Autowired
    private CertTypeService certTypeService;

    @RequestMapping("/index")
    public String index(HttpSession session){
        // 读取资质数据集合
        List<Cert> allCerts = certService.queryAll();
        session.setAttribute("allCerts", allCerts);

        // 获取已经建立好的账户类型与所需资质文件的关系数据
        List<Map<String, Object>> acctTypeCertList = certTypeService.queryAcctTypeCerts();//用于回显
        session.setAttribute("acctTypeCertList", acctTypeCertList);

        return "certType/index";
    }

    @ResponseBody
    @RequestMapping("/insertAcctTypeCert")
    public Object insertAcctTypeCert(AccountTypeCert accountTypeCert){
        start();
        try {
            int count = certTypeService.insertAcctTypeCert(accountTypeCert);
            success(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/deleteAcctTypeCert")
    public Object deleteAcctTypeCert(AccountTypeCert accountTypeCert){
        start();
        try {
            int count = certTypeService.deleteAcctTypeCert(accountTypeCert);
            success(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
        }
        return end();
    }
}
