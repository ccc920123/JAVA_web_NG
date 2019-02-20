var layer;
$(function () {
    layui.use('layer', function () {
        layer = layui.layer;
    });
});

function changePwd() {
    var old=$('#oldPwd').val();
    var newPwd=$('#newPwd').val();
    if (old==undefined||old==""){
        layer.msg("请输入原始密码", {icon: 5});
        return;
    }
    if (newPwd==undefined||newPwd==""){
        layer.msg("请输入新密码", {icon: 5});
        return;
    }
    old=hex_md5(old);
    newPwd=hex_md5(newPwd);
    if ($('#surePwd').val() == $('#newPwd').val()) {
        doPost('/user/changePassword', JSON.stringify({
            oldPwd: old,
            newPwd: newPwd
        }), function (response) {
            if (response.success == true) {
                    // layer.msg('修改成功',{icon: 6});
                    layer.alert('密码修改成功，请重新登录', {
                        closeBtn: 0
                    }, function () {
                        doPost('/user/exit', {}, function (response) {
                            if (response == true) {
                                layui.data('pression', null);
                                layui.data('userqyid', null);
                                layui.data('userqyname', null);
                                layui.data('userxzjb', null);
                                parent.location.href = './login.html';
                            } else {
                            }
                        })
                    });
            } else {
                    layer.msg(response.data, {icon: 5});
            }
        })
    } else {
            layer.msg("修改密码与确认密码不一致", {icon: 5});
    }

}

/**
 * 取消就回到区域管理页面
 */
function goback() {
    window.location.href = "backMain.html";
}