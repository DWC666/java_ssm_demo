package com.dwc.crowdfunding.manager.dao;

import com.dwc.crowdfunding.bean.Project;

import java.util.List;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Project record);

    Project selectByPrimaryKey(Integer id);

    List<Project> selectAll();

    int updateByPrimaryKey(Project record);
}