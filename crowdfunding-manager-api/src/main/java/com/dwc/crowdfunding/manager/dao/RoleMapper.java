package com.dwc.crowdfunding.manager.dao;


import com.dwc.crowdfunding.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List<Role> queryList(@Param("start") Integer startIndex, @Param("size") Integer pagesize);

    Integer queryCount();

    int deleteRoleBatchByVo(List<Integer> ids);

    List<Role> queryListByParam(Map<String, Object> paramMap);

    Integer queryCountByParam(Map<String, Object> paramMap);

    int deleteRolePermissionByRoleId(Integer roleId);

    int insertRolePermissionRelationship(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);
}