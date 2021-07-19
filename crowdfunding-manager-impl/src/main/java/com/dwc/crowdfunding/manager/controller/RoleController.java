package com.dwc.crowdfunding.manager.controller;

import com.dwc.crowdfunding.bean.Permission;
import com.dwc.crowdfunding.bean.Role;
import com.dwc.crowdfunding.controller.BaseController;
import com.dwc.crowdfunding.manager.service.PermissionService;
import com.dwc.crowdfunding.manager.service.RoleService;
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
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index(){
        return "role/index";
    }

    @RequestMapping("/add")
    public String add(){
        return "role/add";
    }

    @RequestMapping("/update")
    public String update(Integer id, Map<String, Object> map){
        Role role = roleService.queryRoleById(id);
        map.put("role", role);
        return "role/update";
    }

    @RequestMapping("/assignPermission")
    public String assignPermission(){
        return "role/assignPermission";
    }
    // 异步请求
  /*  @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(@RequestParam(value = "pageno", required = false, defaultValue = "1") Integer pageno,
                        @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize){
        AjaxResult result = new AjaxResult();
        try {
            Page page = roleService.queryPage(pageno, pagesize);
            result.setPage(page);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询数据失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
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
            Page page = roleService.queryPage(paramMap);
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
    public Object doAdd(Role role){
        AjaxResult result = new AjaxResult();
        try {
            int count = roleService.insertRole(role);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("新增角色失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }

    @ResponseBody
    @RequestMapping("/doUpdate")
    public Object doUpdate(Role role){
        AjaxResult result = new AjaxResult();
        try {
            int count = roleService.updateRole(role);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("修改角色失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }

    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(Integer id){
        AjaxResult result = new AjaxResult();
        try {
            int count = roleService.deleteRole(id);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }

    @ResponseBody
    @RequestMapping("/doDeleteBatch")
    public Object doDeleteBatch(Data data){
        AjaxResult result = new AjaxResult();
        try {
            int count = roleService.deleteRoleBatchByVo(data.getIds());
            result.setSuccess(count == data.getIds().size());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除失败");
            e.printStackTrace();
        }
        return result; //将对象转化为json串，以流的形式返回
    }

    @ResponseBody
    @RequestMapping("/loadDataAsync")
    public Object loadDataAsync(Integer roleId){
        List<Permission> root = new ArrayList<>();
        Map<Integer, Permission> map = new HashMap<>();
        List<Permission> permissions = permissionService.queryAllPermission();
        List<Integer> permissionIdsForRole = permissionService.queryPermisssionByRoleId(roleId);
        for(Permission permission : permissions){
            map.put(permission.getId(), permission);
            if(permissionIdsForRole.contains(permission.getId())){
                permission.setChecked(true);
            }
        }
        for(Permission permission : permissions){
            if(permission.getPid()==null){ //没有父节点，则为根节点
                root.add(permission);
            }else{ // 有父节点
                // 查找父节点
                Permission parent = map.get(permission.getPid());
                // 将子节点加入父节点的子节点集合中
                parent.getChildren().add(permission);
            }
        }
        return root;
    }

    // 分配许可
    @ResponseBody
    @RequestMapping("/doAssignPermission")
    public Object doAssignPermission(Integer roleId, Data data){
        start();
        try {
            int count = roleService.assignPermission(roleId, data.getIds());
            success(count == data.getIds().size());
        } catch (Exception e) {
            success(false);
            message("分配许可失败");
            e.printStackTrace();
        }
        return end();
    }
}
