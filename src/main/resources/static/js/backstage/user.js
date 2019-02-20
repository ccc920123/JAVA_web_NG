/**
 * 入口函数
 */
var layer;
$(function () {
    justPression();
    layui.use('layer', function () {
        layer = layui.layer;
    })
    if (justPressionBy("111")) {
        getUsers();
    } else {
        layer.msg("该用户暂无此权限！", {icon: 5});
    }

})

/**
 * 获取用户列表信息
 */
function getUsers() {

    doPost('/user/getusers', JSON.stringify({
        areacon: $('#areacon').val().replace(/(\s*$)/g, ""),
        usercon: $('#usercon').val().replace(/(\s*$)/g, ""),
        pageNo: pageNo
    }), function (response) {
        console.log(response);
        page(response.total);
        showUser(response.data);
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


/**
 * 展示用户信息
 */
function showUser(users) {
    var tbody = "";
    $("#tbody").empty();
    var change0 = justPressionBy("112");
    var change1 = justPressionBy("113");
    var change2 = justPressionBy("114");
    var change3 = justPressionBy("115");
    $.each(users, function (n, value) {
        var str = "";
        var change0H = "<a href=\"#\" class=\"tablelink\" onclick='impowerRoleModal(this);return false' account='" + value.ACCOUNT + "' roleid='" + value.ROLEID + "'>授权</a>";
        var change1H = "<a href=\"#\" class=\"tablelink\" onclick=\"window.location.href ='AddUser.html?account=" + encodeURI(value.ACCOUNT) + " ';return false\">修改</a>";
        var change2H = "<a href=\"#\" class=\"tablelink\" onclick='deleteAsk(this);return false' account='" + value.ACCOUNT + "'> 删除</a>"
        var change3H = "<a href=\"#\" class=\"tablelink\" onclick='resetPwd(this); return false' account='" + value.ACCOUNT + "'> 重置密码</a>";
        var html = "";
        if (change1) {
            html += change1H;
        }
        if (change2) {
            html += change2H;
        }
        if (change0) {
            html += change0H;
        }
        if (change3) {
            html += change3H;
        }
        str += "<tr>" +
            "<td>" + value.QYNAME + "</td>" +
            "<td>" + value.RELNAME + "</td>" +
            "<td>" + value.ACCOUNT + "</td>" +
            "<td>" + value.SFZHM + "</td>" +
            "<td>" + value.LXDH + "</td>" +
            "<td>" +
            html +
            "</td></tr>";
        tbody += str;
    })
    $("#tbody").append(tbody);
    $('.tablelist tbody tr:odd').addClass('odd');//给偶数行添加背景
}

/**
 * 重置密码
 */
function resetPwd(att) {
    var account = $(att).attr("account");
    doPost('/user/resetPwd', account, function (response) {
        if (response.success == true) {
            layer.msg('重置密码成功,重置后的密码为:888888', {icon: 6});
        } else {
            layer.msg('重置密码失败', {icon: 5});
        }
    })
}

/**
 * 用户授权
 */
function impowerRoleModal(span) {
    var account = $(span).attr("account");
    var roleid = $(span).attr("roleid");
    $('#userAccount').val(account);

    var selectHtml = "<option value=''>选择角色</option>";
    doGet('/RoleManage/getRoleListData', {
        rolecon: "",
        pageNo: 1,
        pageSize: 100000
    }, function (response) {
        $.each(response.data.list, function (index, data) {
            selectHtml += "<option value=" + data.ID + ">" + data.NAME + "</option>";
        });
        $("#role").html(selectHtml);
        if (roleid == undefined || roleid == 'undefined') {
            $("#role").val("");
        } else {
            $("#role").val(roleid);
        }

        ShowDiv('MyDiv', 'fade');
    })


}

/**
 * 删除询问层
 */
function deleteAsk(att) {
    layer.confirm("是否删除当前用户", {
        btn: ['确认', '取消']
    }, function () {
        var account = $(att).attr("account");
        sureDelete(account);
    }, function (index) {
        layer.close(index);
    })
}

/**
 *  确认删除用户函数
 */

function sureDelete(account) {

    doPost('/user/delete', account, function (response) {
        if (response.success == true) {
            getUsers();
            layer.msg('删除成功', {icon: 6});
        } else {
            layer.msg('删除失败' + response.data, {icon: 5});
        }
    })
}

function impowerRole() {
    doPost('/user/impower', JSON.stringify({
        account: $('#userAccount').val(),
        roleId: $('#role').val()
    }), function (response) {
        CloseDiv('MyDiv', 'fade');
        if (response.success == true) {
            layer.msg('授权成功', {icon: 6});
        } else {
            layer.msg('授权失败:' + response.data, {icon: 5});
        }
    })
}
