package com.dwc.crowdfunding.potal.dao;


import com.dwc.crowdfunding.bean.Member;
import com.dwc.crowdfunding.bean.Ticket;

import java.util.List;
import java.util.Map;

public interface TicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ticket ticket);

    Ticket selectByPrimaryKey(Integer id);

    List<Ticket> selectAll();

    int updateByPrimaryKey(Ticket record);

    Ticket queryTicketByMemberId(Integer id);

    int updateStep(Ticket ticket);

    int updateTicket(Ticket ticket);

    Member queryMemberByPiid(String processInstanceId);

    int updateStatus(Member member);
}