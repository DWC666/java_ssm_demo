package com.dwc.crowdfunding.manager.dao;


import com.dwc.crowdfunding.bean.Advertisement;

import java.util.List;
import java.util.Map;

public interface AdvertisementMapper {
    Integer deleteByPrimaryKey(Integer id);

    Integer insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    Integer updateByPrimaryKey(Advertisement record);

    List<Advertisement> queryList(Map<String, Object> paramMap);

    Integer queryCount(Map<String, Object> paramMap);

    Integer deleteAdvertBatch(List<Integer> ids);
}