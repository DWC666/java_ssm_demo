package com.dwc.crowdfunding.manager.controller;

import com.dwc.crowdfunding.bean.Permission;
import com.dwc.crowdfunding.manager.service.PermissionService;
import com.dwc.crowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index(){
        return "permission/index";
    }

    @RequestMapping("/add")
    public String add(){
        return "permission/add";
    }

    @RequestMapping("/update")
    public String update(Integer id, Map<String, Object> map){
        Permission permission = permissionService.queryPermissionById(id);
        map.put("permission", permission);
        return "permission/update";
    }

    @RequestMapping("/loadData")
    @ResponseBody
    public Object loadData(){
        AjaxResult result = new AjaxResult();
        try{
            List<Permission> root = new ArrayList<>();
            Map<Integer, Permission> map = new HashMap<>();
            List<Permission> permissions = permissionService.queryAllPermission();
            for(Permission permission : permissions){
                map.put(permission.getId(), permission);
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
            result.setSuccess(true);
            result.setData(root);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("加载许可树数据失败");
        }
        return result;
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(Permission permission){
        AjaxResult result = new AjaxResult();
        try{
            int count = permissionService.savePermission(permission);
            result.setSuccess(count==1);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("添加许可失败");
        }
        return result;
    }

    @RequestMapping("/doUpdate")
    @ResponseBody
    public Object doUpdate(Permission permission){
        AjaxResult result = new AjaxResult();
        try{
            int count = permissionService.updatePermission(permission);
            result.setSuccess(count==1);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("修改许可失败");
        }
        return result;
    }

    @RequestMapping("/doDelete")
    @ResponseBody
    public Object doDelete(Integer id){
        AjaxResult result = new AjaxResult();
        try{
            int count = permissionService.deletePermission(id);
            result.setSuccess(count==1);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("删除许可失败");
        }
        return result;
    }
}
