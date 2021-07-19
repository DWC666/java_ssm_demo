package com.dwc.crowdfunding.potal.controller;

import com.dwc.crowdfunding.bean.Cert;
import com.dwc.crowdfunding.bean.Member;
import com.dwc.crowdfunding.bean.MemberCert;
import com.dwc.crowdfunding.bean.Ticket;
import com.dwc.crowdfunding.controller.BaseController;
import com.dwc.crowdfunding.manager.service.CertService;
import com.dwc.crowdfunding.potal.listener.PassListener;
import com.dwc.crowdfunding.potal.listener.RefuseListener;
import com.dwc.crowdfunding.potal.service.MemberService;
import com.dwc.crowdfunding.potal.service.TicketService;
import com.dwc.crowdfunding.util.Const;
import com.dwc.crowdfunding.vo.Data;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CertService certService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @RequestMapping("/index")
    public String index(){
        return "member/index";
    }

    @RequestMapping("/apply")
    public String apply(HttpSession session){
        Member loginMember = (Member)session.getAttribute(Const.LOGIN_MEMBER);

        Ticket ticket = ticketService.queryTicketByMemberId(loginMember.getId());

        if ( ticket == null ) {
            // 创建流程审批单
            ticket = new Ticket();
            ticket.setMemberid(loginMember.getId()); //会员id
            ticket.setStep(Const.APPLY_STEP_0);
            ticket.setStatus("0"); //0 正在审批中    1 审批完成
            ticketService.insertTicket(ticket);
            return "member/acctType";//账户类型选择页面
        } else {
            // 根据流程审批单的步骤，跳转页面
            String step = ticket.getStep();
            if ( Const.APPLY_STEP_0.equals(step) ) { //如果账户类型选择完成,去到基本信息页面
                return "member/acctType";
            }else if ( Const.APPLY_STEP_1.equals(step) ) { //如果基本信息页面提交完成,则去到上传资质图片页面
                return "member/basicInfo";
            }else if( Const.APPLY_STEP_2.equals(step)){
                //根据当前用户查询账户类型，再根据账户类型查询需要哪些资质文件
                List<Cert> certs =  certService.queryCertByAcctType(loginMember.getAccttype());
                session.setAttribute("certs", certs);

                return "member/uploadCert";
            }else if( Const.APPLY_STEP_3.equals(step)){
                return "member/checkEmail";
            }else if( Const.APPLY_STEP_4.equals(step)){
                return "member/checkAuthCode";
            }else if( Const.APPLY_STEP_5.equals(step)){
                return "member/index";
            }
        }
        return "member/acctType";
    }

    @RequestMapping("/basicInfo")
    public String basicInfo(){
        return "member/basicInfo";
    }


    @RequestMapping("/uploadCert")
    public String uploadCert(){
        return "member/uploadCert";
    }

    @RequestMapping("/checkEmail")
    public String checkEmail(){
        return "member/checkEmail";
    }

    @ResponseBody
    @RequestMapping("/updateAcctType")
    public Object updateAcctType(Integer acctType, HttpSession session){
        start();
        try {
            Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);
            member.setAccttype(acctType);
            int count = memberService.updateAcctType(member);

            //记录流程步骤
            Ticket ticket = ticketService.queryTicketByMemberId(member.getId());
            ticket.setStep(Const.APPLY_STEP_1);
            ticketService.updateStep(ticket);

            success(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
        }
        return end();
    }

    /**
     *更新基本信息
     * @param member
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateBasicInfo")
    public Object updateBasicInfo(Member member, HttpSession session){
        start();
        try {
            Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);
            loginMember.setRealname(member.getRealname());
            loginMember.setCardnum(member.getCardnum());
            loginMember.setPhone(member.getPhone());

            int count = memberService.updateBasicInfo(loginMember);

            //记录流程步骤
            Ticket ticket = ticketService.queryTicketByMemberId(loginMember.getId());
            ticket.setStep(Const.APPLY_STEP_2);
            ticketService.updateStep(ticket);

            success(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/doUploadCert")
    public Object doUploadCert(HttpSession session, Data data){
        start();
        try {
            Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);
            String realPath = session.getServletContext().getRealPath("/pictures");

            List<MemberCert> memberCerts = data.getMemberCerts();
            for(MemberCert memberCert : memberCerts){
                MultipartFile imgFile = memberCert.getImgFile();

                String name = imgFile.getOriginalFilename();//java.jpg
                String extname = name.substring(name.lastIndexOf(".")); // .jpg
                //为文件设置新的名字，放置文件名重复
                String fileName =  UUID.randomUUID().toString() + extname;
                String iconPath = realPath + "/cert/" + fileName; //232243343.jpg
                //将文件从临时目录保存到指定目录下
                imgFile.transferTo(new File(iconPath));

                memberCert.setIconpath(fileName);
                memberCert.setMemberid(loginMember.getId());
            }

            int count = certService.saveMemberCertBatch(memberCerts);
            //记录流程步骤
            Ticket ticket = ticketService.queryTicketByMemberId(loginMember.getId());
            ticket.setStep(Const.APPLY_STEP_3);
            ticketService.updateStep(ticket);

            success(count == memberCerts.size());
        }catch (Exception e){
            e.printStackTrace();
            success(false);
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/startProcess")
    public Object startProcess(HttpSession session, String email){
        start();
        try {
            Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);
            //如果用户输入新的邮箱，则替换旧的邮箱
            if(!email.equals(loginMember.getEmail())){
                loginMember.setEmail(email);
                memberService.updateEmail(loginMember);
            }

            StringBuilder authcode = new StringBuilder();
            for(int i=1;i<=4;i++){
                authcode.append(new Random().nextInt(10));
            }
            //启动认证流程，系统发送给邮箱发送验证码
            Map<String, Object> variables = new HashMap<>();
            variables.put("toEmail", email);
            variables.put("authcode", authcode.toString());
            variables.put("loginacct", loginMember.getLoginacct());
            variables.put("passListener", new PassListener());
            variables.put("refuseListener", new RefuseListener());

            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("auth").singleResult();
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), variables);


            //记录流程步骤
            Ticket ticket = ticketService.queryTicketByMemberId(loginMember.getId());
            ticket.setStep(Const.APPLY_STEP_4);
            ticket.setPiid(processInstance.getId());
            ticket.setAuthcode(authcode.toString());
            int count = ticketService.updateTicket(ticket);

            success(count == 1);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
        }
        return end();
    }

    @ResponseBody
    @RequestMapping("/finishApply")
    public Object finishApply(HttpSession session, String authCode){
        start();
        try {
            Member loginMember = (Member) session.getAttribute(Const.LOGIN_MEMBER);

            Ticket ticket = ticketService.queryTicketByMemberId(loginMember.getId());

            if(authCode.equals(ticket.getAuthcode())){
                //让当前用户完成验证码审核任务
                Task task = taskService.createTaskQuery().processInstanceId(ticket.getPiid()).taskAssignee(loginMember.getLoginacct()).singleResult();
                taskService.complete(task.getId());

                //更新当前用户认证状态
                loginMember.setAuthstatus("1");
                memberService.updateAuthstatus(loginMember);

                //记录流程步骤
                ticket.setStep(Const.APPLY_STEP_5);
                int count = ticketService.updateStep(ticket);
                success(count == 1);
            }else {
                success(false);
                message("验证码错误，请重新输入！");
            }
        }catch (Exception e){
            e.printStackTrace();
            success(false);
            message("验证失败！");
        }
        return end();
    }
}
