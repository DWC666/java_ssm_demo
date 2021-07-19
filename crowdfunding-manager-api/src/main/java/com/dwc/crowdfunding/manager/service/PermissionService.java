package com.dwc.crowdfunding.manager.service;

import com.dwc.crowdfunding.bean.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> queryAllPermission();

    int savePermission(Permission permission);

    Permission queryPermissionById(Integer id);

    int updatePermission(Permission permission);

    int deletePermission(Integer id);

    List<Integer> queryPermisssionByRoleId(Integer roleId);
}
