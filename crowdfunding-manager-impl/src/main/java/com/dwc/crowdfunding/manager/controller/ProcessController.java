package com.dwc.crowdfunding.manager.controller;

import com.dwc.crowdfunding.controller.BaseController;
import com.dwc.crowdfunding.util.AjaxResult;
import com.dwc.crowdfunding.util.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class ProcessController extends BaseController {
    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping("/index")
    public String index(){
        return "process/index";
    }

    @RequestMapping("/showImg")
    public String showImg(){
        return "process/showImg";
    }

    @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                          @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize){
        start();
        try {
            Page page = new Page(pageno, pagesize);
            ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
            //查询流程定义集合数据可能出现了自关联，导致Jackson组件无法将集合转化为json串，需要自己重新包装数据
            List<ProcessDefinition> processDefinitions = processDefinitionQuery.listPage(page.getStartIndex(), pagesize);
            List<Map<String, Object>> data = new ArrayList<>();
            for(ProcessDefinition p : processDefinitions){
                Map<String, Object> map = new HashMap<>();
                map.put("id", p.getId());
                map.put("name", p.getName());
                map.put("key", p.getKey());
                map.put("version", p.getVersion());
                data.add(map);
            }
            long count = processDefinitionQuery.count();
            page.setData(data);
            page.setTotalsize((int) count);

            param("page", page);
            success(true);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
            message("流程定义查询失败");
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/deploy")
    public Object deploy(HttpServletRequest request){
        start();
        try {
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)request;
            MultipartFile mfile = mreq.getFile("processDefFile");
            //部署流程定义
            repositoryService.createDeployment()
                    .addInputStream(mfile.getOriginalFilename(), mfile.getInputStream())
                    .deploy();

            success(true);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
            message("流程定义部署失败");
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(String id){
        start();
        try {

            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(id).singleResult();
            //删除部署
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true); //true表示级联删除

            success(true);
        } catch (Exception e) {
            success(false);
            message("修改失败");
            e.printStackTrace();
        }
        return end(); //将对象转化为json串，以流的形式返回
    }

    /**
     * 返回流程定义图片
     * @param id
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/doShowImg")
    public void doShowImg(String id, HttpServletResponse response) throws IOException {

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(id).singleResult();
        InputStream resourceAsStream = repositoryService.
                getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());

        ServletOutputStream outputStream = response.getOutputStream();
        //将图片的输入流拷贝到输出流，实现返回图片
        IOUtils.copy(resourceAsStream, outputStream);
    }
}
