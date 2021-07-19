package com.dwc.crowdfunding.manager.dao;

import com.dwc.crowdfunding.bean.Permission;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    List<Integer> queryPermisssionByRoleId(Integer roleId);
}