//在模态框中装载auth的树形结构
function fillAuthTree() {
//    发送ajax请求
    var ajaxReturn = $.ajax({
        "url": "assgin/get/all/auth.json",
        "type": "get",
        "dataTpye": "json",
        "async": false
    });

    if (ajaxReturn.status!=200){
        layer.msg(" 请 求 处 理 出 错 ！ 响 应 状 态 码 是 ： "+ajaxReturn.status+" 说 明 是 ： "+ajaxReturn.statusText);
        return;
    }
    // 从服务器端查询到的 list 不需要组装成树形结构，这里我们交给 zTree 去组装
    var authList = ajaxReturn.responseJSON.data;

    //3,准备对zTree进行设置的JSON对象
    var setting = {
        "data": {
            "simpleData": {
            //    开启简单的json功能
                "enable": true,
            //    使用categoryId属性关联父节点,不用默认的Pid
                "pIdKey": "categoryId"
            },
            "key": {
            //  使用 title 属性显示节点名称，不用默认的 name 作为属性名了
                "name": "title"
            }
        },
        "check": {
            "enable": true
        }

    };

//    4,生成树形结构
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);

//    获取ztreeObj对象
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
//    调用 zTreeObj 对象的方法，把节点展开
    zTreeObj.expandAll(true);

//    5，查询已分配的auth的Id组成的数组
    ajaxReturn = $.ajax({
            "url": "assign/get/assigned/auth/id/by/role/id.json",
            "type": "post",
            "data": {
                "roleId":window.roleId
            },
            "dataType": "json",
            "async": false
        });
        if (ajaxReturn.status!=200){
            layer.msg(" 请求处理出错！响应状态码是："+ajaxReturn.status+" 说明是： "+ajaxReturn.statusText);
            return ;
        }

//        从响应结果中获取authIdArray
        var authIdArray = ajaxReturn.responseJSON.data;

        if (authIdArray!=null){
            for (var i = 0; i < authIdArray.length;i++){
                var authId = authIdArray[i];

                //    根据Id查询树形结构中对应的节点
                var treeNode = zTreeObj.getNodeByParam("id", authId);

                var checked = true;
                // checkTypeFlag 设置为 false，表示不“联动”，不联动是为了避免把不该勾选的勾 选上
                var checkTypeFlag =false;
                // 执行
                zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
            }
        }
}




//声明专门的函数显示确认模态框
function showConfirmModal(roleArray) {
//    打开模态框
    $("#confirmModal").modal("show");

//    清除旧的数据
    $("#roleNameDiv").empty();

//    在全局变量范围创建数组用来存放角色id
    window.roleIdArray = [];

//    遍历roleIdArray数组
    for (var i = 0;i < roleArray.length;i++){
        var role = roleArray[i];
        var roleName = role.roleName;
        $("#roleNameDiv").append(roleName+"<br/>");

        var roleId = role.roleId;
        // 调用数组对象的push()方法存入新元素
        window.roleIdArray.push(roleId);
    }


}

//执行分页，生成页面效果，任何时候调用这个函数都会重新加载页面
function generatePage() {
//    1，获取分页数据
    var pageInfo = getPageInfoRemote();

//    2，填充表格
    fillTableBody(pageInfo);

}

//远程访问服务器端程序获取pageInfo数据
function getPageInfoRemote() {
    //1,调用$.ajax()函数发送请求并接受$.ajax()函数的返回值
    var ajaxResult = $.ajax({
        "url": "role/get/page/info.json",
        "type": "post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "async": false,
        "dataType": "json"
    });
    //控制台打印出数据
    console.log(ajaxResult);

//   2, 获取返回值响应码进行判断是否为200
    var statusCode = ajaxResult.status;
//   3, 如果当前响应状态码不是 200，说明发生了错误或其他意外情况，显示提示消息，让当前函数停止执行
    if (statusCode != 200) {
        layer.msg("失败！响应状态码=" + statusCode + " 说明信息=" + ajaxResult.statusText);
        return null;
    }
//   4,如果响应状态码是 200，说明请求处理成功，获取 pageInfo
    var resultEntity = ajaxResult.responseJSON;

//   5,从resultEntity取出result属性
    var result = resultEntity.result;
//   6,判断 result是否为失败
    if (result == "FAILED") {
        layer.msg(resultEntity.message);
        return null;
    }
//   7，确认result为成功后获取pageInfo
    var pageInfo = resultEntity.data;
    return pageInfo;

}

//填充表格
function fillTableBody(pageInfo) {
//清除tbody中的旧数据
    $("#rolePageBody").empty();
// 这里清空是为了没有搜索结果的时候不显示搜索页码导航条
    $("#Pagination").empty();
// 判断pageInfo对象是否有效
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜 索的数据！</td></tr>");
        return;
    }
//使用pageInfo 的 list 属性填充 tbody
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];

        var roleId = role.id;

        var roleName = role.name;

        var numberTd = "<td>" + (i + 1) + "</td>";

        var checkboxTd = "<td><input id='"+roleId+"' class='itemBox' type='checkbox'></td>";

        var roleNameTd = "<td>" + roleName + "</td>";

        var checkBtn = "<button id='"+roleId+"' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";

        var pencilBtn = "<button id='"+roleId+"' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";

        var removeBtn = "<button id='"+roleId+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";

        var tr = "<tr>"+numberTd+checkboxTd+roleNameTd+buttonTd+"</tr>";

        $("#rolePageBody").append(tr);
    }
//    生成分页导航条
    generateNavigator(pageInfo);
}

//生成分页页码导航条
function generateNavigator(pageInfo) {
//    获取总记录数
    var totalRecord = pageInfo.total;

//    声明相关属性
    var properties = {
        "num_edge_entries": 3,
        "num_display_entries": 5,
        "callback": paginationCallBack,
        "items_per_page": pageInfo.pageSize,
        "current_page": pageInfo.pageNum - 1,
        "prev_text": "上一页",
        "next_text": "下一页"
    }
//    调用pagination()函数
    $("#Pagination").pagination(totalRecord,properties);

}

//翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {
//修改window对象的pageNum属性
    window.pageNum = pageIndex+1;
// 调用分页函数
    generatePage();
// 取消页码超链接默认行为
    return false;

}