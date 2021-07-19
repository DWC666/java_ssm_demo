package com.dwc.crowdfunding.manager.service;

import com.dwc.crowdfunding.bean.AccountTypeCert;

import java.util.List;
import java.util.Map;

public interface CertTypeService {

    List<Map<String, Object>> queryAcctTypeCerts();

    int insertAcctTypeCert(AccountTypeCert accountTypeCert);

    int deleteAcctTypeCert(AccountTypeCert accountTypeCert);
}
