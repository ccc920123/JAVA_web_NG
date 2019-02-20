/**
 * 入口函数
 */
$(function () {
    justPression();
    if (justPressionBy("121")) {
        getUsers();
    } else {
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("该用户暂无此权限！", {icon: 5});
        });
    }

})
var pageSize = 10;
var layer;
// var TableHeader = [[ //表头
//     // {type:'checkbox',align:'center'},
//     // {type: 'radio'},
//     // {field: 'id',align:'center', title: 'ID'}
//     {field: 'NAME', align: 'center', title: '角色名称'}
//     , {field: 'DMMC', align: 'center', title: '所属层级'}
//     , {title: '操作', align: 'center', toolbar: '#barDemo'}
//
// ]];

var id;

/**
 * 获取用户列表信息
 */
function getUsers() {
    initLayui()
    getRoleList();
}

function initLayui() {
    layui.use(['table', 'layer'], function () {
        layer = layui.layer;

    });
}
function getRoleList() {
    doGet('/RoleManage/getRoleListData', {
        rolecon: $('#rolecon').val().replace(/(\s*$)/g, ""),
        pageNo: pageNo,
        pageSize: pageSize
    }, function (response) {
        console.log(response);
        page(response.data.total);
        showRole(response.data.list);
    })
}

/**
 * 条件搜索函数
 */
function search() {
    pageNo = 1;
    getUsers();
}

/**
 *分页
 */
var pageNo = 1;
var page = function (count) {
    $('#total').html(count);
    $('#pageNo').html(pageNo + '/' + Math.ceil(count / 10));
    layui.use('laypage', function () {
        var laypage = layui.laypage;
        laypage.render({
            elem: 'page',
            count: count,
            limit: 10,
            curr: pageNo,
            jump: function (obj, first) {
                if (!first) {
                    pageNo = obj.curr;
                    getUsers();
                    return;
                }
            }
        })
    })
}

function impowerRoleModal(span) {
    var sid = $(span).attr("id");
    id = sid;
    getMenuRole(sid);
    ShowDiv('MyDiv', 'fade');

}

function impowerRole() {
    saveMenuIDandRole(id)
}

/**
 * 授权保存
 * @param roleid
 * @param index
 */
function saveMenuIDandRole(roleid) {
    var menuIDs = onCheck();
    doPost("/RoleManage/setRoleMenus", JSON.stringify({
        roleId: roleid,
        menuIds: menuIDs
    }), function (response) {
        layer.msg(response.data);
        if (!Boolean(response.success)) {
            // mymsg(response.meta.message, 5);
            layer.msg(response.data, {icon: 5});
        } else {
            // 关闭
            CloseDiv('MyDiv', 'fade')
            layer.msg(response.data, {icon: 6});
            // 弹出提示
            // mymsg("授权成功", 6);
        }
    });
}

function getMenuRole(roleId) {
    //获取角色信息
    doGet("/RoleManage/getRoleMenusTree", {
        roleId: roleId

    }, function (response) {
        if (response.data == null || response.data.length == 0 || response.data.length == 1) {
            return true;
        }
        var zNodes = [];
        var zindex = 0
        $.each(response.data, function (index, dept) {
                zNodes[zindex++] = {
                    id: dept.ID
                    , pId: dept.PID
                    , name: dept.NAME
                    , checked: dept.FLAG == 1 ? true : false
                    , icon: '../../images/images/dept012.png'
                    , open: true

                }
            }
        );

        loadZtree(zNodes);

    });
}

function loadZtree(zNodes) {
    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: null
            }
        },
        key: {
            name: "name"
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "ps", "N": "ps"}
        }
        ,
        callback: {
            //onClick: zTreeOnClick
            // beforeCheck: true,
            // onCheck: onCheck
        }
    };
    $.fn.zTree.init($("#menuTree"), setting, zNodes);

}

function onCheck(e, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("menuTree"),
        nodes = treeObj.getCheckedNodes(true);
    var menuids = new Array();
    for (var i = 0; i < nodes.length; i++) {
        menuids.push(nodes[i].id);
    }
    return menuids;
}

/**
 * 展示用户信息
 */
function showRole(data) {
    var tbody = "";
    $("#tbody").empty();
    var change1 = justPressionBy("122");
    var change2 = justPressionBy("123");
    var change3 = justPressionBy("124");
    $.each(data, function (n, value) {
        var str = "";
        var change1H = "<a href=\"#\" class=\"tablelink\" onclick=\"window.location.href ='AddRole.html?op=edit&id=" + value.ID + " ';return false\">修改</a>";
        var change2H = "<a href=\"#\" class=\"tablelink\" onclick='impowerRoleModal(this);return false' id='" + value.ID + "'>授权</a>"
        var change3H = "<a href=\"#\" class=\"tablelink\" onclick='deleteRole(this);return false' id='" + value.ID + "'> 删除</a>";
        var html = "";
        if (change1) {
            html += change1H;
        }
        if (change2) {
            html += change2H;
        }
        if (change3) {
            html += change3H;
        }
        str += "<tr>" +
            "<td>" + value.NAME + "</td>" +
            "<td>" + value.DMMC + "</td>" +
            "<td>" +
            html +
            "</td></tr>";
        tbody += str;
    });
    $("#tbody").append(tbody);


}

function deleteRole(span) {
    var id = $(span).attr("id");
    //询问框
    var index = layer.confirm('确定删除当前角色？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        doPost("/RoleManage/deleteRole", JSON.stringify({roleId: id}), function (response) {
            if (response.success) {
                getUsers();
                layer.msg("删除成功", {icon: 6});
                layer.close(index);
            } else {
                layer.msg(response.data, {icon: 5});
            }
        })
    }, function () {
        layer.close(index);
    });
}






