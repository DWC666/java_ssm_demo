package com.dwc.crowdfunding.manager.service.impl;

import com.dwc.crowdfunding.bean.Advertisement;
import com.dwc.crowdfunding.bean.User;
import com.dwc.crowdfunding.manager.dao.AdvertisementMapper;
import com.dwc.crowdfunding.manager.service.AdvertService;
import com.dwc.crowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdvertServiceImpl implements AdvertService {
    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Override
    public Integer insertAdvert(Advertisement advert) {
        return advertisementMapper.insert(advert);
    }

    @Override
    public Page<Advertisement> queryPage(Map<String, Object> paramMap) {
        Page<Advertisement> page = new Page<>((Integer) paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));
        Integer startIndex = page.getStartIndex();
        paramMap.put("startIndex", startIndex);
        List<Advertisement> adverts = advertisementMapper.queryList(paramMap);
        Integer count = advertisementMapper.queryCount(paramMap);
        page.setTotalsize(count);
        page.setData(adverts);
        return page;
    }

    @Override
    public int deleteAdvert(Integer id) {
        return advertisementMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteAdvertBatch(List<Integer> ids) {

        return advertisementMapper.deleteAdvertBatch(ids);
    }

    @Override
    public Advertisement queryByPrimaryKey(Integer id) {
        return advertisementMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateAdvert(Advertisement advertisement) {
        return advertisementMapper.updateByPrimaryKey(advertisement);
    }
}
