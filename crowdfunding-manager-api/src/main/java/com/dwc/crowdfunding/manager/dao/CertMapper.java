package com.dwc.crowdfunding.manager.dao;

import com.dwc.crowdfunding.bean.Cert;
import com.dwc.crowdfunding.bean.MemberCert;

import java.util.List;
import java.util.Map;

public interface CertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cert record);

    Cert selectByPrimaryKey(Integer id);

    List<Cert> selectAll();

    int updateByPrimaryKey(Cert record);

    List<Cert> queryCertByAcctType(Integer acctType);

    int saveMemberCert(MemberCert memberCert);
}