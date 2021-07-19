<%--
  Created by IntelliJ IDEA.
  User: dwc
  Date: 2020/1/17
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<div id="navbar" class="navbar-collapse collapse" style="float:right;">
    <ul class="nav navbar-nav">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i> ${member.username}<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li><a href="member.htm"><i class="glyphicon glyphicon-scale"></i> 会员中心</a></li>
                <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
                <li class="divider"></li>
                <li><a href="${APP_PATH}/logout.do"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
            </ul>
        </li>
    </ul>
</div>
