package com.dwc.crowdfunding.potal.service.impl;

import com.dwc.crowdfunding.bean.Member;
import com.dwc.crowdfunding.bean.Ticket;
import com.dwc.crowdfunding.potal.dao.MemberMapper;
import com.dwc.crowdfunding.potal.dao.TicketMapper;
import com.dwc.crowdfunding.potal.service.MemberService;
import com.dwc.crowdfunding.potal.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    TicketMapper ticketMapper;

    @Override
    public Ticket queryTicketByMemberId(Integer id) {
        return ticketMapper.queryTicketByMemberId(id);
    }

    @Override
    public int insertTicket(Ticket ticket) {
        return ticketMapper.insert(ticket);
    }

    @Override
    public int updateStep(Ticket ticket) {
        return ticketMapper.updateStep(ticket);
    }

    @Override
    public int updateTicket(Ticket ticket) {
        return ticketMapper.updateTicket(ticket);
    }

    @Override
    public Member queryMemberByPiid(String processInstanceId) {
        return ticketMapper.queryMemberByPiid(processInstanceId);
    }

    @Override
    public int updateStatus(Member member) {
        return ticketMapper.updateStatus(member);
    }
}
