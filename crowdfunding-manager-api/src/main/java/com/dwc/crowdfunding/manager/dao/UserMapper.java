package com.dwc.crowdfunding.manager.dao;

import com.dwc.crowdfunding.bean.Permission;
import com.dwc.crowdfunding.bean.Role;
import com.dwc.crowdfunding.bean.User;
import com.dwc.crowdfunding.vo.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User user);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

	User queryUserlogin(Map<String, Object> paramMap);
    //参数个数大于1时，需要使用@Param为其指定名称
//    List<User> queryList(@Param("startIndex") Integer startIndex, @Param("pagesize")Integer pagesize);

    Integer queryCount(Map<String, Object> paramMap);

    List<User> queryList(Map<String, Object> paramMap);

    int deleteUserBatchByVo(List<User> datas);

    List<Integer> queryRoleByUserId(Integer userId);

    List<Role> queryAllRole();

    int saveUserRoleRelationship(@Param("userid") Integer userid, @Param("data") Data data);

    int deleteUserRoleRelationship(@Param("userid")Integer userid, @Param("data")Data data);

    List<Permission> queryPermissionByUserId(Integer id);
}