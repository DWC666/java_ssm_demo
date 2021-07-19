package com.dwc.crowdfunding.manager.service.impl;

import com.dwc.crowdfunding.bean.AccountTypeCert;
import com.dwc.crowdfunding.manager.dao.AccountTypeCertMapper;
import com.dwc.crowdfunding.manager.service.CertTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CertTypeServiceImpl implements CertTypeService {
    @Autowired
    private AccountTypeCertMapper accountTypeCertMapper;

    @Override
    public List<Map<String, Object>> queryAcctTypeCerts() {
        return accountTypeCertMapper.queryAcctTypeCerts();
    }

    @Override
    public int insertAcctTypeCert(AccountTypeCert accountTypeCert) {
        return accountTypeCertMapper.insert(accountTypeCert);
    }

    @Override
    public int deleteAcctTypeCert(AccountTypeCert accountTypeCert) {
        return accountTypeCertMapper.deleteAcctTypeCert(accountTypeCert);
    }
}
