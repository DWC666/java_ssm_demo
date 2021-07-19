package com.dwc.crowdfunding.manager.dao;

import com.dwc.crowdfunding.bean.ProjectType;

import java.util.List;

public interface ProjectTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectType record);

    ProjectType selectByPrimaryKey(Integer id);

    List<ProjectType> selectAll();

    int updateByPrimaryKey(ProjectType record);
}