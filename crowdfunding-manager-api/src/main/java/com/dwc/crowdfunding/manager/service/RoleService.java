package com.dwc.crowdfunding.manager.service;

import com.dwc.crowdfunding.bean.Role;
import com.dwc.crowdfunding.util.Page;

import java.util.List;
import java.util.Map;

public interface RoleService {
//    Page queryPage(Integer pageno, Integer pagesize);

    Role queryRoleById(Integer id);

    int updateRole(Role role);

    int deleteRole(Integer id);

    int insertRole(Role role);

    int deleteRoleBatchByVo(List<Integer> ids);

    Page queryPage(Map<String, Object> paramMap);

    int assignPermission(Integer roleId, List<Integer> ids);
}
