package com.dwc.crowdfunding.manager.service;

import com.dwc.crowdfunding.bean.Advertisement;
import com.dwc.crowdfunding.bean.User;
import com.dwc.crowdfunding.util.Page;

import java.util.List;
import java.util.Map;

public interface AdvertService {

    Integer insertAdvert(Advertisement advert);

    Page<Advertisement> queryPage(Map<String, Object> paramMap);

    int deleteAdvert(Integer id);

    int deleteAdvertBatch(List<Integer> ids);

    Advertisement queryByPrimaryKey(Integer id);

    int updateAdvert(Advertisement advertisement);
}
