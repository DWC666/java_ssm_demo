<%--
  Created by IntelliJ IDEA.
  User: dwc
  Date: 2020/1/7
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH }/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH }/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH }/css/main.css">
    <link rel="stylesheet" href="${APP_PATH }/css/doc.min.css">
    <link rel="stylesheet" href="${APP_PATH }/ztree/zTreeStyle.css">
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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 角色维护</a></div>
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
                <%@include file="/WEB-INF/jsp/common/menu.jsp" %>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="glyphicon glyphicon-th-list"></i> 权限分配列表<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <button id="assignPermissionBtn" class="btn btn-success">分配许可</button>
                    <br><br>
                    <ul id="permissionTree" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH }/jquery/layer/layer.js"></script>
<script type="text/javascript" src="${APP_PATH }/ztree/jquery.ztree.all-3.5.min.js"></script>

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

        var setting = {
            check : {
                enable : true  //在树节点前显示复选框
            },
            view: {
                selectedMulti: false,
                addDiyDom: function(treeId, treeNode){
                    var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
                    if ( treeNode.icon ) {
                        icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background","");
                    }
                }
            },
            async: {
                enable: true, //采用异步
                url:"${APP_PATH}/role/loadDataAsync.do?roleId=${param.id}", // ?id=1&n=xxx&lv=2
                autoParam:["id", "name=n", "level=lv"]
            },
            callback: {
                onClick : function(event, treeId, json) {

                }
            }
        };

        //异步加载树:注意问题,服务器端返回的结果必须是一个数组.
        $.fn.zTree.init($("#permissionTree"), setting); //异步加载树的数据.
        //$.fn.zTree.init($("#permissionTree"), setting , ztreeJSON);//同步加载树的数据.
    });

    $("#assignPermissionBtn").click(function(){

        var jsonObj = {
            roleId : ${param.id}
        };

        var treeObj = $.fn.zTree.getZTreeObj("permissionTree"); //获取许可树对象

        var checkedNodes = treeObj.getCheckedNodes(true); // 获取被选中的节点

        $.each(checkedNodes,function(i,n){
            jsonObj["ids["+i+"]"] = n.id;
        });

        if(checkedNodes.length === 0){
            layer.msg("请至少选择一个许可!", {time:2000, icon:5, shift:6});
        }else{
            var loadingIndex = -1 ;
            $.ajax({
                type : "POST",
                url : "${APP_PATH}/role/doAssignPermission.do",
                data : jsonObj,
                beforeSend : function(){
                    loadingIndex = layer.msg('正在分配许可...', {icon: 16});
                    return true ;
                },
                success : function(result){

                    layer.close(loadingIndex);

                    if(result.success){
                        layer.msg("分配成功", {time:2000, icon:6});
                        window.location.href = "${APP_PATH}/role/index.htm";
                    }else{
                        layer.msg(result.message, {time:2000, icon:5, shift:6});
                    }
                },
                error : function(){
                    layer.msg("操作失败!", {time:2000, icon:5, shift:6});
                }
            });
        }

    });
</script>
</body>
</html>
