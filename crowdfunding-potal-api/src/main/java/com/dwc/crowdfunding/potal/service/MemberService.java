package com.dwc.crowdfunding.potal.service;

import com.dwc.crowdfunding.bean.Member;
import com.dwc.crowdfunding.bean.Ticket;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Member queryMemberLogin(Map<String, Object> map);

    int updateAcctType(Member member);

    int updateBasicInfo(Member loginMember);

    void updateEmail(Member loginMember);

    int updateAuthstatus(Member loginMember);

    Member queryMemberByid(Integer memberid);

    List<Map<String, Object>> queryCertByMemberID(Integer memberid);
}
