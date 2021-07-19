package com.dwc.crowdfunding.listener;

import com.dwc.crowdfunding.bean.Permission;
import com.dwc.crowdfunding.manager.service.PermissionService;
import com.dwc.crowdfunding.util.Const;
import com.dwc.crowdfunding.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StartSystemListener implements ServletContextListener {
    //服务器启动时，创建application对象要执行的方法
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //将项目的上下文路径放到application域中
        ServletContext application = sce.getServletContext();
        String contextPath = application.getContextPath();
        application.setAttribute(Const.APP_PATH, contextPath);
        System.out.println("项目已启动...");

        //获取系统中所有权限许可菜单
        ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(application);
        PermissionService permissionService = ioc.getBean(PermissionService.class); //可能报空指针异常,监听器配置顺序问题.
        List<Permission> allPermission = permissionService.queryAllPermission();
        Set<String> allPermissionUrls = new HashSet<String>();
        for (Permission permission : allPermission) {
            if(StringUtil.isNotEmpty(permission.getUrl())){
                allPermissionUrls.add("/"+permission.getUrl());
            }
        }
        application.setAttribute(Const.ALL_PERMISSION_URI, allPermissionUrls);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
