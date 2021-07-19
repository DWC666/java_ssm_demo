package com.dwc.crowdfunding.manager.service.impl;

import com.dwc.crowdfunding.bean.Permission;
import com.dwc.crowdfunding.bean.Role;
import com.dwc.crowdfunding.bean.User;
import com.dwc.crowdfunding.exception.LoginFailException;
import com.dwc.crowdfunding.manager.dao.UserMapper;
import com.dwc.crowdfunding.manager.service.UserService;
import com.dwc.crowdfunding.util.Const;
import com.dwc.crowdfunding.util.MD5Util;
import com.dwc.crowdfunding.util.Page;
import com.dwc.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserLogin(Map<String, Object> paramMap) {
        User user = userMapper.queryUserlogin(paramMap);
        if(user == null){
            throw new LoginFailException("用户名或密码错误！");
        }
        return user;
    }

/*    @Override
    public Page queryPage(Integer pageno, Integer pagesize) {
        Page page = new Page(pageno, pagesize);
        Integer startIndex = page.getStartIndex();
        List<User> users = userMapper.queryList(startIndex, pagesize);
        page.setData(users);
        Integer totalsize = userMapper.queryCount();
        page.setTotalsize(totalsize);
        return page;
    }*/

    @Override
    public int saveUser(User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(new Date());
        user.setCreatetime(createTime);
        user.setUserpswd(MD5Util.digest(Const.DEFAULT_PASSWORD));
        return userMapper.insert(user);
    }

    @Override
    public Page queryPage(Map<String, Object> paramMap) {
        Page page = new Page((Integer) paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));
        Integer startIndex = page.getStartIndex();
        paramMap.put("startIndex", startIndex);
        List<User> users = userMapper.queryList(paramMap);
        page.setData(users);
        Integer totalsize = userMapper.queryCount(paramMap);
        page.setTotalsize(totalsize);
        return page;
    }

    @Override
    public User queryUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteUserBatch(Integer[] ids) {
        int countTotal = 0;
        for(Integer id : ids){
            countTotal += userMapper.deleteByPrimaryKey(id);
        }
        if(countTotal != ids.length){
            throw new RuntimeException("批量删除失败");
        }
        return countTotal;
    }

    @Override
    public int deleteUserBatchByVo(List<User> datas) {
        return userMapper.deleteUserBatchByVo(datas);
    }

    @Override
    public List<Integer> queryRoleByUserId(Integer userId) {
        return userMapper.queryRoleByUserId(userId);
    }

    @Override
    public List<Role> queryAllRole() {
        return userMapper.queryAllRole();
    }

    @Override
    public int saveUserRoleRelationship(Integer userid, Data data) {
        return userMapper.saveUserRoleRelationship(userid, data);
    }

    @Override
    public int deleteUserRoleRelationship(Integer userid, Data data) {
        return userMapper.deleteUserRoleRelationship(userid, data);
    }

    @Override
    public List<Permission> queryPermissionByUserId(Integer id) {
        return userMapper.queryPermissionByUserId(id);
    }
}
