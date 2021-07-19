package com.dwc.crowdfunding.manager.dao;


import com.dwc.crowdfunding.bean.Message;

import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    Message selectByPrimaryKey(Integer id);

    List<Message> selectAll();

    int updateByPrimaryKey(Message record);
}