package com.dwc.crowdfunding.manager.service;

import com.dwc.crowdfunding.bean.Cert;
import com.dwc.crowdfunding.bean.MemberCert;

import java.util.List;
import java.util.Map;

public interface CertService {

    List<Cert> queryAll();

    List<Cert> queryCertByAcctType(Integer accttype);

    int saveMemberCertBatch(List<MemberCert> memberCerts);
}
