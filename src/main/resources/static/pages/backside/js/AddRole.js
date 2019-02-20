/**
 * 入口函数
 */
var addOrUpdate = '';
var id = '';
var layer ;
$(function () {
    layui.use(['table', 'layer'], function () {
        table = layui.table;
        layer = layui.layer;
    });
    addOrUpdate = GetRequest().op;
    id = GetRequest().id;
    if(addOrUpdate == 'edit'){
        $("#title").html("修改角色信息");
        $("#title2").html("修改角色信息");
    }
    getLevel();

});

function getLevel() {
    var  select ="";
    doGet("/RoleManage/getLevel",{},function (response) {
        $.each(response.data,function (index,data) {
            select+="<option value="+data.ID+">"+data.NAME+"</option>"
        });
        $("#rolelevel").html(select);
        if (addOrUpdate == 'edit') {
            getRoleDetail(id);
        }
    });

}

function getRoleDetail(id) {
    doGet("/RoleManage/getRoleDetail", {id: id}, function (response) {
        if (response.success) {
            $.each(response.data, function (key, value) {
                $("#" + key).val(value);
            });
        }else{
            layer.msg(response.data,{icon:5});
        }
    });
}

/**
 * 确认信息提交
 */
function operationUser() {
    //判断是用户新增还是删除
    if (addOrUpdate === 'add') {
        addUser();
    } else {
        updateUser();
    }
}

function submitDialog() {
    layui.use('layer',function () {
        var layer = layui.layer;
        layer.alert('提交成功', {
            closeBtn: 0
        }, function () {
            goback();
        });
    })

}
/**
 * 取消按钮，返回user.html
 */
function goback() {
    window.location.href = "./role_manage.html";
}

/**
 * 新增用户
 */
function addUser() {
    doPost('/RoleManage/addRoleData', JSON.stringify({
        name: $('#name').val(),
        pmsp: $('#pmsp').val(),
        rolelevel: $('#rolelevel').val(),
        state: $('#state').val()
    }), function (response) {
        if (response.success == true) {
            // layui.use('layer', function () {
            //     var layer = layui.layer;
            //     layer.msg('新增成功', {icon: 6});
                submitDialog();
            // })
        } else {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('提交失败', {icon: 5});
            })
        }
    })
}

/**
 * 修改用户
 */
function updateUser() {
    doPost('/RoleManage/upDataRoleData', JSON.stringify({
        id: id,
        name: $('#name').val(),
        pmsp: $('#pmsp').val(),
        rolelevel: $('#rolelevel').val(),
        state: $('#state').val()
    }), function (response) {
        if (response.success) {
            getRoleDetail(id);
            submitDialog();
        } else {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('提交失败', {icon: 5});
            })
        }
    })
}

/**
 * 随着排名名称的输入该变排名名称首拼
 */
function setFirstCharacter() {
    var Chinese = $('#name').val();
    var firstCharacter = getPinYinFirstCharacter(Chinese,null,false);
    $("#pmsp").val(firstCharacter.replace(/\s+/g,""));
}
