package com.dwc.crowdfunding.interceptor;

import com.dwc.crowdfunding.bean.Member;
import com.dwc.crowdfunding.bean.User;
import com.dwc.crowdfunding.util.Const;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //定义不需要拦截的uri（白名单）
        HashSet uris = new HashSet();
        uris.add("user/reg.do");
        uris.add("user/reg.htm");
        uris.add("/index.htm");
        uris.add("/login.htm");
        uris.add("/login.do");
        uris.add("/doLogin.do");
        uris.add("/logout.do");

        //判断请求路径
        String servletPath = request.getServletPath();
        if(uris.contains(servletPath)){
            return true;
        }

        //判断用户是否登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Const.LOGIN_USER);
        Member member = (Member) session.getAttribute(Const.LOGIN_MEMBER);

        if(user == null && member == null){
            response.sendRedirect(request.getContextPath() + "/login.htm");
            return false;
        }
        return true;
    }
}
