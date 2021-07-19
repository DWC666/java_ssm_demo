package com.dwc.crowdfunding.manager.controller;

import com.dwc.crowdfunding.bean.Role;
import com.dwc.crowdfunding.bean.User;
import com.dwc.crowdfunding.manager.service.UserService;
import com.dwc.crowdfunding.util.AjaxResult;
import com.dwc.crowdfunding.util.Page;
import com.dwc.crowdfunding.util.StringUtil;
import com.dwc.crowdfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(){
        return "user/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "user/add";
    }

    @RequestMapping("/assignRole")
    public String assignRole(Integer id, Map<String, List> map){
        List<Role> allRoleList = userService.queryAllRole();
        List<Integer> roleIds = userService.queryRoleByUserId(id);
        List<Role> assignedRoles = new ArrayList<>();
        List<Role> unassignedRoles = new ArrayList<>();
        for(Role role : allRoleList){
            if(roleIds.contains(role.getId())){
                assignedRoles.add(role);
            }else {
                unassignedRoles.add(role);
            }
        }
        map.put("leftRoleList", unassignedRoles);
        map.put("rightRoleList", assignedRoles);
        return "user/assignRole";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Map<String, Object> map){
        User user = userService.queryUserById(id);
        map.put("user", user);
        return "user/update";
    }

    // 同步请求
    /*@RequestMapping("/index")
    public String index(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                       @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize,
                       Map<String, Page> map){
        Page page = userService.queryPage(pageno, pagesize);
        map.put("page", page);
        return "user/index";
    }*/

    //条件查询
    @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize,
                        String queryText){
        AjaxResult result = new AjaxResult();
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("pageno",pageno);
            paramMap.put("pagesize",pagesize);
            if(StringUtil.isNotEmpty(queryText)){
                if(queryText.contains("%")){
                    // 将 % 替换为 /%
                    queryText = queryText.replaceAll("%", "////%");
                }
                paramMap.put("queryText",queryText);
            }
            Page page = userService.queryPage(paramMap);
            result.setPage(page);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询数据失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }

    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(User user){
        AjaxResult result = new AjaxResult();
        try {
            int count = userService.saveUser(user);
            result.setSuccess(count==1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("添加用户失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }

    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(User user){
        AjaxResult result = new AjaxResult();
        try {
            int count = userService.updateUser(user);
            result.setSuccess(count==1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("修改用户失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }

    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(Integer id){
        AjaxResult result = new AjaxResult();
        try {
            int count = userService.deleteUser(id);
            result.setSuccess(count==1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("修改用户失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }

    // 接收多条数据，每条数据含有多个参数
    @ResponseBody
    @RequestMapping("/doDeleteBatch")
    public Object doDeleteBatch(Data data) {
        AjaxResult result = new AjaxResult();
        try {
            int count = userService.deleteUserBatchByVo(data.getDatas());
            result.setSuccess(count == data.getDatas().size());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除用户失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }

    // 分配角色
    @ResponseBody
    @RequestMapping("/doAssignRole")
    public Object doAssignRole(Integer userid, Data data) {
        AjaxResult result = new AjaxResult();
        try {
            int count = userService.saveUserRoleRelationship(userid, data);
            System.out.println("savecount: " + count);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("分配角色失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }

    // 取消角色
    @ResponseBody
    @RequestMapping("/doUnAssignRole")
    public Object doUnAssignRole(Integer userid, Data data) {
        AjaxResult result = new AjaxResult();
        try {
            int count = userService.deleteUserRoleRelationship(userid, data);
            result.setSuccess(data.getIds().size()==count);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("取消角色失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }

    //接收一个参数名，带多个值
/*    @ResponseBody
    @RequestMapping("/doDeleteBatch")
    //函数参数名(id)要与传过来的字符串(id=1&id=2&id=3)中的参数名一致
    public Object doDeleteBatch(Integer[] id){
        AjaxResult result = new AjaxResult();
        try {
            int count = userService.deleteUserBatch(id);
            result.setSuccess(count==id.length);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除用户失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }*/

    // 异步请求
    /*@ResponseBody
    @RequestMapping("/index")
    public Object index(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize){
        AjaxResult result = new AjaxResult();
        try {
            Page page = userService.queryPage(pageno, pagesize);
            result.setPage(page);
            result.setSuccess(true);
            System.out.println("ajax");
            System.out.println(page.getData().size());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询数据失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }*/
}
