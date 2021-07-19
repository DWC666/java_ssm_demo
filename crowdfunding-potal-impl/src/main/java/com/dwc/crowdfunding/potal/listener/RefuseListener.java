package com.dwc.crowdfunding.potal.listener;

import com.dwc.crowdfunding.bean.Member;
import com.dwc.crowdfunding.potal.service.MemberService;
import com.dwc.crowdfunding.potal.service.TicketService;
import com.dwc.crowdfunding.util.ApplicationContextUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;

public class RefuseListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        // 获取变量
        Integer memberId = (Integer) execution.getVariable("memberId");
        Integer taskId = (Integer) execution.getVariable("taskId");

        // 获取Spring容器
        ApplicationContext context = ApplicationContextUtils.applicationContext;

        // 获取service
        MemberService memberService = context.getBean(MemberService.class);
        TicketService ticketService = context.getBean(TicketService.class);

        // 改变会员的状态
        Member member = memberService.queryMemberByid(memberId);
        //实名认证状态: 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证
        member.setAuthstatus("0");
        memberService.updateAuthstatus(member);

        // 改变流程审批单的状态
        ticketService.updateStatus(member);
    }
}
