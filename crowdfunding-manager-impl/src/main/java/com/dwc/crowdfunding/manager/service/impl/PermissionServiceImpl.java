package com.dwc.crowdfunding.manager.service.impl;

import com.dwc.crowdfunding.bean.Permission;
import com.dwc.crowdfunding.manager.dao.PermissionMapper;
import com.dwc.crowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> queryAllPermission() {
        return permissionMapper.selectAll();
    }

    @Override
    public int savePermission(Permission permission) {
        return permissionMapper.insert(permission);
    }

    @Override
    public Permission queryPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updatePermission(Permission permission) {
        return permissionMapper.updateByPrimaryKey(permission);
    }

    @Override
    public int deletePermission(Integer id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Integer> queryPermisssionByRoleId(Integer roleId) {
        return permissionMapper.queryPermisssionByRoleId(roleId);
    }
}
