package com.dwc.crowdfunding.potal.service;

import com.dwc.crowdfunding.bean.Member;
import com.dwc.crowdfunding.bean.Ticket;

public interface TicketService {

    Ticket queryTicketByMemberId(Integer id);

    int insertTicket(Ticket ticket);

    int updateStep(Ticket ticket);

    int updateTicket(Ticket ticket);

    Member queryMemberByPiid(String processInstanceId);

    int updateStatus(Member member);
}
