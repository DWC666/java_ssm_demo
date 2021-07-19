package com.dwc.crowdfunding.manager.service;

import com.dwc.crowdfunding.bean.Permission;
import com.dwc.crowdfunding.bean.Role;
import com.dwc.crowdfunding.bean.User;
import com.dwc.crowdfunding.util.Page;
import com.dwc.crowdfunding.vo.Data;

import java.util.List;
import java.util.Map;

public interface UserService {
    User queryUserLogin(Map<String, Object> paramMap);

//    Page queryPage(Integer pageno, Integer pagesize);

    int saveUser(User user);

    Page queryPage(Map<String, Object> paramMap);

    User queryUserById(Integer id);

    int updateUser(User user);

    int deleteUser(Integer id);

    int deleteUserBatch(Integer[] ids);

    int deleteUserBatchByVo(List<User> datas);

    List<Integer> queryRoleByUserId(Integer userId);

    List<Role> queryAllRole();

    int saveUserRoleRelationship(Integer userid, Data data);

    int deleteUserRoleRelationship(Integer userid, Data data);

    List<Permission> queryPermissionByUserId(Integer id);
}
