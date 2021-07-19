package com.dwc.crowdfunding.potal.dao;


import com.dwc.crowdfunding.bean.Member;

import java.util.List;
import java.util.Map;

public interface MemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Member record);

    Member selectByPrimaryKey(Integer id);

    List<Member> selectAll();

    int updateByPrimaryKey(Member record);

	Member queryMebmerlogin(Map<String, Object> paramMap);

	int updateAcctType(Member member);

	int updateBasicInfo(Member member);

    void updateEmail(Member member);

    int updateAuthstatus(Member member);

    List<Map<String, Object>> queryCertByMemberID(Integer memberid);
}