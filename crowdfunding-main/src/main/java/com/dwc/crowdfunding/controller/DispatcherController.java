package com.dwc.crowdfunding.controller;

import com.dwc.crowdfunding.bean.Member;
import com.dwc.crowdfunding.bean.Permission;
import com.dwc.crowdfunding.bean.User;
import com.dwc.crowdfunding.manager.service.UserService;
import com.dwc.crowdfunding.potal.service.MemberService;
import com.dwc.crowdfunding.util.AjaxResult;
import com.dwc.crowdfunding.util.Const;
import com.dwc.crowdfunding.util.MD5Util;
import com.dwc.crowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DispatcherController {
    @Autowired
    private UserService userService;

    @Autowired
    private MemberService memberService;

    @RequestMapping("/index")
    public String index(){
        System.out.println("welcome !!!");
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpSession session){
        //判断是否需要自动登录
        //如果之前登录过，cookie中存放了用户信息，需要获取cookie中的信息，并进行数据库的验证

        boolean needLogin = true;
        String logintype = null ;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){ //如果客户端禁用了Cookie，那么无法获取Cookie信息

            for (Cookie cookie : cookies) {
                if("logincode".equals(cookie.getName())){
                    String logincode = cookie.getValue();
                    System.out.println("获取到Cookie中的键值对"+cookie.getName()+"===== " + logincode);
                    //loginacct=admin&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=member
                    String[] split = logincode.split("&");
                    if(split.length == 3){
                        String loginacct = split[0].split("=")[1];
                        String userpwd = split[1].split("=")[1];
                        logintype = split[2].split("=")[1];

                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("loginacct", loginacct);
                        paramMap.put("userpswd", userpwd);
                        paramMap.put("type", logintype);

                        if("user".equals(logintype)){
                            User user = userService.queryUserLogin(paramMap);

                            if(user!=null){
                                session.setAttribute(Const.LOGIN_USER, user);
                                needLogin = false ;

                                /*----------------获取当前登录用户的权限 --------------------*/
                                List<Permission> permissions = userService.queryPermissionByUserId(user.getId());
                                Permission rootPermission = null;
                                Set<String> authUris = new HashSet<>();
                                Map<Integer, Permission> map = new HashMap<>();

                                for(Permission permission : permissions){
                                    map.put(permission.getId(), permission);
                                    if(StringUtil.isNotEmpty(permission.getUrl())){
                                        authUris.add("/" + permission.getUrl());
                                    }
                                }
                                for(Permission permission : permissions){
                                    if(permission.getPid()==null){ //没有父节点，则为根节点
                                        rootPermission = permission;
                                    }else{ // 有父节点
                                        // 查找父节点
                                        Permission parent = map.get(permission.getPid());
                                        // 将子节点加入父节点的子节点集合中
                                        parent.getChildren().add(permission);
                                    }
                                }
                                session.setAttribute("rootPermission", rootPermission);
                                session.setAttribute(Const.LOGIN_AUTH_PERMISSION_URI, authUris);
                            }
                        }else if("member".equals(logintype)){
                            Member member = memberService.queryMemberLogin(paramMap);

                            if(member!=null){
                                session.setAttribute(Const.LOGIN_MEMBER, member);
                                needLogin = false ;
                            }
                        }
                    }
                }
            }
        }

        if(!needLogin){
            if ("user".equals(logintype)) {
                return "redirect:/main.htm";
            } else if ("member".equals(logintype)) {
                return "redirect:member/index.htm";
            }
        }
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //销毁session域，或者删除session域里面对应的属性值也可以
        session.invalidate();
        return "redirect:/index.htm";
    }

    @RequestMapping("/main")
    public String main(HttpSession session){
        return "main";
    }

    //同步的请求
/*    @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String userpswd, String type, HttpSession session){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("loginacct", loginacct);
        paramMap.put("userpswd", userpswd);
        paramMap.put("type", type);
        User user = userService.queryUserLogin(paramMap);
        session.setAttribute(Const.LOGIN_USER, user);
        // 使用重定向而不是转发，防止刷新页面时重复提交表单
        return "redirect:/main.htm";
    }*/

    //异步请求
    //@ResponseBody 结合Jackson组件, 将返回结果转换为字符串，将json串以流的形式返回给客户端
    @ResponseBody
    @RequestMapping("/doLogin")
    public Object doLogin(String loginacct, String userpswd, String type,
                          String rememberme, HttpServletResponse response, HttpSession session){

        AjaxResult result = new AjaxResult();;
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("loginacct", loginacct);
            paramMap.put("userpswd", MD5Util.digest(userpswd));
            paramMap.put("type", type);

            if("member".equals(type)){
                Member member = memberService.queryMemberLogin(paramMap);
                session.setAttribute(Const.LOGIN_MEMBER, member);

                // 记住我，设置cookie
                if("1".equals(rememberme)){
                    String logincode = "loginacct="+member.getLoginacct()+"&userpwd="+member.getUserpswd()+"&logintype=member";
                    //loginacct=admin&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=member
                    System.out.println("用户-存放到Cookie中的键值对：logincode::::::::::::"+logincode);
                    Cookie c = new Cookie("logincode",logincode);

                    c.setMaxAge(60*60*24*14); //2周时间Cookie过期     单位秒
                    c.setPath("/"); //表示任何请求路径都可以访问Cookie

                    response.addCookie(c);
                }

            }else if("user".equals(type)){
                User user = userService.queryUserLogin(paramMap);
                session.setAttribute(Const.LOGIN_USER, user);

                if("1".equals(rememberme)){
                    String logincode = "loginacct="+user.getLoginacct()+"&userpwd="+user.getUserpswd()+"&logintype=user";
                    //loginacct=admin&userpwd=21232f297a57a5a743894a0e4a801fc3&logintype=1
                    System.out.println("用户-存放到Cookie中的键值对：logincode::::::::::::"+logincode);
                    Cookie c = new Cookie("logincode",logincode);

                    c.setMaxAge(60*60*24*14); //2周时间Cookie过期     单位秒
                    c.setPath("/"); //表示任何请求路径都可以访问Cookie

                    response.addCookie(c);
                }

                /*----------------获取当前登录用户的权限 --------------------*/
                List<Permission> permissions = userService.queryPermissionByUserId(user.getId());
                Permission rootPermission = null;
                Set<String> authUris = new HashSet<>();
                Map<Integer, Permission> map = new HashMap<>();

                for(Permission permission : permissions){
                    map.put(permission.getId(), permission);
                    if(StringUtil.isNotEmpty(permission.getUrl())){
                        authUris.add("/" + permission.getUrl());
                    }
                }
                for(Permission permission : permissions){
                    if(permission.getPid()==null){ //没有父节点，则为根节点
                        rootPermission = permission;
                    }else{ // 有父节点
                        // 查找父节点
                        Permission parent = map.get(permission.getPid());
                        // 将子节点加入父节点的子节点集合中
                        parent.getChildren().add(permission);
                    }
                }
                session.setAttribute("rootPermission", rootPermission);
                session.setAttribute(Const.LOGIN_AUTH_PERMISSION_URI, authUris);
            }

            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("登陆失败！");
            e.printStackTrace();
            result.setSuccess(false);
            //{"success":true, "message":"登陆失败！"}
        }
        return result;
    }


}
