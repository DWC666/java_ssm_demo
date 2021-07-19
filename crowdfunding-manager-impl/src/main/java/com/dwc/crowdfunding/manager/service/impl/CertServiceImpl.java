package com.dwc.crowdfunding.manager.service.impl;

import com.dwc.crowdfunding.bean.Cert;
import com.dwc.crowdfunding.bean.MemberCert;
import com.dwc.crowdfunding.manager.dao.CertMapper;
import com.dwc.crowdfunding.manager.service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CertServiceImpl implements CertService {
    @Autowired
    private CertMapper certMapper;

    @Override
    public List<Cert> queryAll() {
        return certMapper.selectAll();
    }

    @Override
    public List<Cert> queryCertByAcctType(Integer acctType) {
        return certMapper.queryCertByAcctType(acctType);
    }

    @Override
    public int saveMemberCertBatch(List<MemberCert> memberCerts) {
        int count = 0;
        for(MemberCert memberCert : memberCerts){
            count += certMapper.saveMemberCert(memberCert);
        }
        return count;
    }

}
