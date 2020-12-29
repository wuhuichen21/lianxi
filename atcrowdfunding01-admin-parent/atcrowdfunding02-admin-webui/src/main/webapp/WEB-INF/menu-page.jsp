<%--
  Created by IntelliJ IDEA.
  User: 980951938
  Date: 2020/12/14
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@include file="/WEB-INF/include-header.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my-menu.js"></script>
<script type="text/javascript">
    $(function () {
        //调用封装好的获取树形结构方法
        generateTree();

        //给添加子节点按钮绑定单击响应函数
        $("#treeDemo").on("click",".addBtn",function () {
        //    将当前节点的id,作为新节点的pid保存到全局变量
            window.pid = this.id;
        //    打开模态框
            $("#menuAddModal").modal("show");
            return false;
        });
    //    给添加子节点的模态框中的保存按钮绑定单击响应函数
        $("#menuSaveBtn").click(function () {
        //    收集用户输入的数据
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
        //    单选按钮要定位到"被选中"那一个
            var icon = $("#menuAddModal [name=icon]:checked").val();

        //    发送ajax请求添加
            $.ajax({
                "url": "menu/save.json",
                "type": "post",
                "data": {
                    "pid": window.pid,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS"){
                        layer.msg("添加成功!");
                    //    重新加载树形结构，注意：要在确认服务器端完成保存操作后再刷新
                    //    否则有可能刷新不到最新的数据，因为这里是异步的
                        generateTree();
                    }else {
                        layer.msg("添加失败!"+response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status+""+response.statusText);
                }
            });
        //    关闭模态框
            $("#menuAddModal").modal("hide");
        //    清空表单
            $("#menuResetBtn").click();
        });


        //给更新按钮绑定单击响应函数
        $("#treeDemo").on("click",".editBtn",function () {
        //    1,将当前的Id保存到全局变量
            window.id = this.id;
        //    2,打开模态框
            $("#menuEditModal").modal("show");
        //    3,获取zTreeObj对象
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
        //    4,根据id属性查询节点对象
            var key = "id";
        //    5,用来搜索节点的属性值
            var value = window.id;
            var currentNode = zTreeObj.getNodeByParam(key,value);

        //    回显表单数据
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);

            $("#menuEditModal [name=icon]").val([currentNode.icon]);
            return false;
        })

    //    给更新模态框中的更新按钮绑定单击事件
        $("#menuEditBtn").click(function () {
        //    收集表单数据
            var name = $("#menuEditModal [name=name]").val();
            var url = $("#menuEditModal [name=url]").val();
            var icon = $("#menuEditModal [name=icon]:checked").val();

        //    发送ajax请求
            $.ajax({
                "url": "menu/update.json",
                "type": "post",
                "data": {
                    "id": window.id,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;
                    if (result == "SUCCESS"){
                        layer.msg("修改成功!");
                    //    重新加载
                        generateTree();
                    }else {
                        layer.msg("修改失败!"+response.message)
                    }
                },
                "error": function (response) {
                    layer.msg(response.status+""+response.statusText);
                }
            });
            //    关闭模态框
            $("#menuEditModal").modal("hide");
        });

    //给删除按钮绑定单击响应函数
        $("#treeDemo").on("click",".removeBtn",function () {
        //    将当前节点的Id保存到全局变量
            window.id = this.id;
        //    打开模态框
            $("#menuConfirmModal").modal("show");
        //    获取zTreeObj 对象
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
        //    搜索节点属性
            var key = "id";

            var value = window.id;
            var currentNode = zTreeObj.getNodeByParam(key,value);

            $("#removeNodeSpan").html(" 【 <i class='"+currentNode.icon+"'></i>"+currentNode.name+"】");
            return false;
        })
    //给模态框的OK按钮绑定单击事件，发送ajax请求
        $("#confirmBtn").click(function () {
            $.ajax({
                "url": "menu/remove.json",
                "type": "post",
                "data": {
                    "id": window.id
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;

                    if (result == "SUCCESS"){
                        layer.msg("删除成功!");
                    //    获取新的列表
                        generateTree();
                    }else {
                        layer.msg("删除失败!");
                    }
                },
                "error": function (response) {
                    layer.msg(response.status+" "+response.statusText);
                }
            });
        //    关闭模态框
            $("#menuConfirmModal").modal("hide");
        });

    });
</script>
<body>
<%@include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <%--这个ul标签是ztree动态生成的节点所依附的静态节点--%>
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-menu-add.jsp" %>
<%@include file="/WEB-INF/modal-menu-confirm.jsp" %>
<%@include file="/WEB-INF/modal-menu-edit.jsp" %>
</body>
</html>