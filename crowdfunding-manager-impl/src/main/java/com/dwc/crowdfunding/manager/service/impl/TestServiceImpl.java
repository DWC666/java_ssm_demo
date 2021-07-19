package com.dwc.crowdfunding.manager.service.impl;

import com.dwc.crowdfunding.manager.dao.TestDao;
import com.dwc.crowdfunding.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;

    public void insert() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhang3");
        System.out.println("TestServiceImpl.insert()");
        testDao.insert(map);
    }
}
