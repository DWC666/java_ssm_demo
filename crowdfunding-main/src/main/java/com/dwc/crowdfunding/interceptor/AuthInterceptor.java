package com.dwc.crowdfunding.interceptor;

import com.dwc.crowdfunding.util.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ServletContext application = request.getSession().getServletContext();
        Set<String> allPermissionUris = (Set<String>) application.getAttribute(Const.ALL_PERMISSION_URI);
        String servletPath = request.getServletPath();
        HttpSession session = request.getSession();

        if(allPermissionUris.contains(servletPath)){
            Set<String> authUris = (Set<String>)session.getAttribute(Const.LOGIN_AUTH_PERMISSION_URI);
            if(authUris != null){
                if(authUris.contains(servletPath)){
                    return true;
                }else{
                    response.sendRedirect(request.getContextPath() + "/error/auth.htm");
                    return false;
                }
            }else {
                return true;
            }

        }else{ //不需要权限控制的,直接放行
            return true;
        }
    }
}
