package com.dwc.crowdfunding.manager.service.impl;

import com.dwc.crowdfunding.bean.Role;
import com.dwc.crowdfunding.manager.dao.RoleMapper;
import com.dwc.crowdfunding.manager.service.RoleService;
import com.dwc.crowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

//    @Override
    public Page queryPage(Integer pageno, Integer pagesize) {
        Page page = new Page(pageno, pagesize);
        Integer startIndex = page.getStartIndex();
        List<Role> roles = roleMapper.queryList(startIndex, pagesize);
        page.setData(roles);
        Integer totalsize = roleMapper.queryCount();
        page.setTotalsize(totalsize);
        return page;
    }

    @Override
    public Role queryRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public int deleteRole(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int deleteRoleBatchByVo(List<Integer> ids) {

        return roleMapper.deleteRoleBatchByVo(ids);
    }

    @Override
    public Page queryPage(Map<String, Object> paramMap) {
        Page page = new Page((Integer) paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));
        Integer startIndex = page.getStartIndex();
        paramMap.put("startIndex", startIndex);
        List<Role> roles = roleMapper.queryListByParam(paramMap);
        page.setData(roles);
        Integer count = roleMapper.queryCountByParam(paramMap);
        page.setTotalsize(count);
        return page;
    }

    @Override
    public int assignPermission(Integer roleId, List<Integer> ids) {
        roleMapper.deleteRolePermissionByRoleId(roleId);
        int count = 0;
        for(int permissionId : ids){
            count += roleMapper.insertRolePermissionRelationship(roleId, permissionId);
        }
        return count;
    }
}
