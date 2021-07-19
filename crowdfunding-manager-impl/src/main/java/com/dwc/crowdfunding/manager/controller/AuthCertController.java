package com.dwc.crowdfunding.manager.controller;

import com.dwc.crowdfunding.bean.Member;
import com.dwc.crowdfunding.bean.MemberCert;
import com.dwc.crowdfunding.bean.Ticket;
import com.dwc.crowdfunding.controller.BaseController;
import com.dwc.crowdfunding.potal.service.MemberService;
import com.dwc.crowdfunding.potal.service.TicketService;
import com.dwc.crowdfunding.util.Const;
import com.dwc.crowdfunding.util.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/authCert")
public class AuthCertController extends BaseController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String index(){
        return "authCert/index";
    }

    @RequestMapping("/showCert")
    public String showCert(Integer memberId, Map<String, Object> map){
        Member member = memberService.queryMemberByid(memberId);
        List<Map<String, Object>> certs = memberService.queryCertByMemberID(memberId);

        map.put("member", member);
        map.put("certs", certs);

        return "authCert/showCert";
    }

    @ResponseBody
    @RequestMapping("/queryPage")
    public Object queryPage(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize){
        start();
        try {
            Page page = new Page(pageno, pagesize);
            //查询认证审核任务
            TaskQuery taskQuery = taskService.createTaskQuery().processDefinitionKey("auth")
                    .taskCandidateGroup("backuser");
            List<Task> taskList = taskQuery.listPage(page.getStartIndex(), pagesize);

            //任务总数
            int count = (int) taskQuery.count();

            List<Map<String, Object>> data = new ArrayList<>();

            for(Task task : taskList){
                Map<String, Object> map = new HashMap<>();
                map.put("taskId", task.getId());
                map.put("taskName", task.getName());

                ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                        .processDefinitionId(task.getProcessDefinitionId()).singleResult();
                map.put("procDefName", processDefinition.getName());
                map.put("procDefVersion", processDefinition.getVersion());

                //根据流程实例id查询对应会员
                Member member = ticketService.queryMemberByPiid(task.getProcessInstanceId());
                map.put("member", member);

                data.add(map);
            }

            page.setData(data);
            page.setTotalsize(count);

            param("page", page);
            success(count == data.size());
        }catch (Exception e){
            success(false);
            message("数据查询失败");
            e.printStackTrace();
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/pass")
    public Object pass(String taskId, Integer memberId){
        start();
        try {
            // 传递参数，让流程继续执行
            taskService.setVariable(taskId, "flag", true);
            taskService.setVariable(taskId, "memberId", memberId);
            taskService.complete(taskId);
            success(true);
        }catch (Exception e){
            success(false);
            message("审核失败");
            e.printStackTrace();
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/refuse")
    public Object refuse(String taskId, Integer memberId){
        start();
        try {
            // 传递参数，让流程继续执行
            taskService.setVariable(taskId, "flag", false);
            taskService.setVariable(taskId, "memberId", memberId);
            taskService.complete(taskId);
            success(true);
        }catch (Exception e){
            success(false);
            message("审核失败");
            e.printStackTrace();
        }
        return end();
    }
}
