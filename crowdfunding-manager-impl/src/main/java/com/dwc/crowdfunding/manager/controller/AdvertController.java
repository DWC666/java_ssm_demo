package com.dwc.crowdfunding.manager.controller;

import com.dwc.crowdfunding.bean.Advertisement;
import com.dwc.crowdfunding.bean.User;
import com.dwc.crowdfunding.controller.BaseController;
import com.dwc.crowdfunding.manager.service.AdvertService;
import com.dwc.crowdfunding.util.AjaxResult;
import com.dwc.crowdfunding.util.Const;
import com.dwc.crowdfunding.util.Page;
import com.dwc.crowdfunding.util.StringUtil;
import com.dwc.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/advert")
public class AdvertController extends BaseController {
    @Autowired
    private AdvertService advertService;

    @RequestMapping("/index")
    public String index(){
        return "advert/index";
    }

    @RequestMapping("/add")
    public String add(){
        return "advert/add";
    }

    @RequestMapping("/update")
    public String update(Integer id, Map<String, Object> map){
        Advertisement advert = advertService.queryByPrimaryKey(id);
        map.put("advert", advert);
        return "advert/update";
    }

    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(Advertisement advertisement){
        start();
        try{
            int count = advertService.updateAdvert(advertisement);
            success(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
            message("修改失败");
        }
        return end();
    }
    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(HttpServletRequest request, Advertisement advert , HttpSession session){
        start();
        try {
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;

            MultipartFile mfile = mreq.getFile("advpic");

            String name = mfile.getOriginalFilename();//java.jpg
            String extname = name.substring(name.lastIndexOf(".")); // .jpg
            //为文件设置新的名字，放置文件名重复
            String iconpath = UUID.randomUUID().toString()+extname; //232243343.jpg

            ServletContext servletContext = session.getServletContext();
            String realpath = servletContext.getRealPath("/pictures");
            String path =realpath+ "\\adv\\"+iconpath;

            //将文件从临时目录保存到指定目录下
            mfile.transferTo(new File(path));

            User user = (User)session.getAttribute(Const.LOGIN_USER);
            advert.setUserid(user.getId());
            advert.setStatus("1");
            advert.setIconpath(iconpath);

            int count = advertService.insertAdvert(advert);
            success(count==1);
        } catch ( Exception e ) {
            e.printStackTrace();
            success(false);
            message("添加失败");
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/queryPage")
    public Object queryPage(String queryText, Integer pageno, Integer pagesize){
        start();
        try{
            // 查询资质数据
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("pageno", pageno);
            paramMap.put("pagesize", pagesize);
            if ( StringUtil.isNotEmpty(queryText) ) {
                // 将字符串中的 '%' 替换为 '/%'
                queryText = queryText.replaceAll("%", "\\\\%");
            }
            paramMap.put("queryText", queryText);
            // 分页查询
            Page<Advertisement> page = advertService.queryPage(paramMap);
            param("page", page);
            success(true);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
            message("数据查询失败");
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(Integer id){
        start();
        try{
            int count = advertService.deleteAdvert(id);
            success(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
            message("删除失败");
        }
        return end();
    }

    // 接收多条数据，每条数据含有多个参数
    @ResponseBody
    @RequestMapping("/doDeleteBatch")
    public Object doDeleteBatch(Data data) {
        start();
        try {
            int count = advertService.deleteAdvertBatch(data.getIds());
            success(count == data.getIds().size());
        } catch (Exception e) {
            success(false);
            message("删除失败");
            e.printStackTrace();
        }
        return end();
    }
}
