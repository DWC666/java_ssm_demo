package com.dwc.crowdfunding.manager.dao;

import com.dwc.crowdfunding.bean.AccountTypeCert;

import java.util.List;
import java.util.Map;

public interface AccountTypeCertMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccountTypeCert record);

    AccountTypeCert selectByPrimaryKey(Integer id);

    List<AccountTypeCert> selectAll();

    int updateByPrimaryKey(AccountTypeCert record);

    List<Map<String, Object>> queryAcctTypeCerts();

    int deleteAcctTypeCert(AccountTypeCert accountTypeCert);

}