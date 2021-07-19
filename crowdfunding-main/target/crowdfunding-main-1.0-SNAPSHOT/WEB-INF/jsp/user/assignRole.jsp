<%--
  Created by IntelliJ IDEA.
  User: dwc
  Date: 2019/12/26
  Time: 19:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <link rel="stylesheet" href="${APP_PATH}/css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 用户维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <jsp:include page="/WEB-INF/jsp/common/userInfo.jsp" />
                </li>
                <li style="margin-left:10px;padding-top:8px;">
                    <button type="button" class="btn btn-default btn-danger">
                        <span class="glyphicon glyphicon-question-sign"></span> 帮助
                    </button>
                </li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"/>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline">
                        <div class="form-group">
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select id="leftRoleList" class="form-control" multiple size="10" style="width:250px;overflow-y:auto;">
                                <c:forEach items="${leftRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>

                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="left2rightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="right2leftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select id="rightRoleList" class="form-control" multiple size="10" style="width:250px;overflow-y:auto;">
                                <c:forEach items="${rightRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>

<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });
    
    $("#left2rightBtn").click(function () {
        var leftSelected = $("#leftRoleList option:selected");
        if(leftSelected.length===0){
            layer.msg("请选择角色",{time:1000, icon:5, shift:6});
            return false;
        }
        var jsonObj = {"userid" : "${param.id}"};
        $.each(leftSelected, function (i, n) {
            jsonObj["ids["+i+"]"] = n.value;
        });
        var loadingIndex = -1;
        $.ajax({
            type : "POST",
            data : jsonObj,
            url : "${APP_PATH}/user/doAssignRole.do",
            beforeSend : function () {
                loadingIndex = layer.msg('正在分配角色...', {icon: 16});
                return true;
            },
            success : function (result) {
                layer.close(loadingIndex);
                if (result.success){
                    $("#rightRoleList").append(leftSelected.clone());
                    leftSelected.remove();
                }else {
                    layer.msg(result.message, {time:2000, icon:5, shift:6});
                }
            },
            error : function () {
                layer.msg("分配角色失败", {time:2000, icon:5, shift:6});
            }
        });
    });

    $("#right2leftBtn").click(function () {
        var rightSelected = $("#rightRoleList option:selected");
        if(rightSelected.length===0){
            layer.msg("请选择角色",{time:1000, icon:5, shift:6});
            return false;
        }
        var jsonObj = {"userid" : "${param.id}"};
        $.each(rightSelected, function (i, n) {
            jsonObj["ids["+i+"]"] = n.value;
        });
        var loadingIndex = -1;
        $.ajax({
            type : "POST",
            data : jsonObj,
            url : "${APP_PATH}/user/doUnAssignRole.do",
            beforeSend : function () {
                loadingIndex = layer.msg('正在取消角色...', {icon: 16});
                return true;
            },
            success : function (result) {
                layer.close(loadingIndex);
                if (result.success){
                    $("#leftRoleList").append(rightSelected.clone());
                    rightSelected.remove();
                }else {
                    layer.msg(result.message, {time:2000, icon:5, shift:6});
                }
            },
            error : function () {
                layer.msg("取消角色失败", {time:2000, icon:5, shift:6});
            }
        });
    });
</script>
</body>
</html>

