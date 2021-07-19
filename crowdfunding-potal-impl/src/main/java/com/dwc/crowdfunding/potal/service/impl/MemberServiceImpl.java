package com.dwc.crowdfunding.potal.service.impl;

import com.dwc.crowdfunding.bean.Member;
import com.dwc.crowdfunding.potal.dao.MemberMapper;
import com.dwc.crowdfunding.potal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberMapper memberMapper;

    @Override
    public Member queryMemberLogin(Map<String, Object> map) {
        return memberMapper.queryMebmerlogin(map);
    }

    @Override
    public int updateAcctType(Member member) {
        return memberMapper.updateAcctType(member);
    }

    @Override
    public int updateBasicInfo(Member member) {
        return memberMapper.updateBasicInfo(member);
    }

    @Override
    public void updateEmail(Member loginMember) {
        memberMapper.updateEmail(loginMember);
    }

    @Override
    public int updateAuthstatus(Member loginMember) {
        return memberMapper.updateAuthstatus(loginMember);
    }

    @Override
    public Member queryMemberByid(Integer memberid) {
        return memberMapper.selectByPrimaryKey(memberid);
    }

    @Override
    public List<Map<String, Object>> queryCertByMemberID(Integer memberid) {
        return memberMapper.queryCertByMemberID(memberid);
    }
}
