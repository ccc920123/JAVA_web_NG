/**
 * 入口函数
 */
var addOrUpdate = '';
var layer;
var qyid = '';
var xzjb = '';
$(function () {
    layui.use('layer', function () {
        layer = layui.layer;
    })

    xzjb = getUserXzjbStr();
    qyid = getUserqyidStr();

    getAjqy();

    var postData = GetRequest();
    var account = decodeURI(postData.account);//获取页面传入的账户
    if (account === 'no') {//新增用户信息
        addOrUpdate = 'add';

        $('#account').removeAttr('readonly');
        $('#password').removeAttr('readonly');

        showUserQy(qyid);
    } else {//修改用户信息
        $("#title").html("修改用户信息");
        $("#title2").html("修改用户信息");
        $('#account').attr('readonly', 'readonly');
        $('#password').attr('readonly', 'readonly');
        addOrUpdate = 'update';
        getUserInfo(account)
    }
})

/**
 * 获取用户信息
 */
function getUserInfo(account) {
    doPost('/user/getUserInfoByAccount', account, function (response) {
        //给页面赋值
        $.each(response.data, function (key, value) {
            $("#" + key).val(value);
        })

        showUserQy(response.data.qyid);
    })
}


/**
 * 确认信息提交
 */
function operationUser() {
    if ($('#relname').val() == undefined || $('#relname').val() == "") {
        warning("姓名不能为空");
        return;
    }
    if ($('#sfzhm').val() == undefined || $('#sfzhm').val() == "") {
        warning("身份证号码不能为空");
        return;
    }
    if ($('#sex').val() == undefined || $('#sex').val() == "") {
        warning("请选择性别");
        return;
    }
    // if ($('#lxdh').val() === undefined){
    //     warning("联系电话不能为空");
    //     return;
    // }else
    if ($('#lxdh').val() != undefined && $('#lxdh').val() != "") {
        if (($('#lxdh').val()).length != 11) {
            warning("请输入十一位手机号码");
            return;
        }
    }
    if ($('#account').val() == undefined||$('#account').val()=='') {
        warning("账号不能为空");
        return;
    }
    if ($('#password').val() == undefined||$('#password').val()=="") {
        warning("密码不能为空");
        return;
    }
    if ($('#state').val() == undefined || $('#state').val() == "") {
        warning("请选择用户状态");
        return;
    }
    if (getUserQyid() == undefined || getUserQyid() == "") {
        warning("请选择所属区域");
        return;
    }

    //判断是用户新增还是删除
    if (addOrUpdate === 'add') {
        addUser();
    } else {
        updateUser();
    }
}

function submitDialog() {
    layui.use('layer', function () {
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
    window.location.href = "./user.html";
}

/**
 * 新增用户
 */
function addUser() {
var password=$('#password').val();
if (password==undefined||password==""){
    layer.msg("密码不能为空", {icon: 5});
    return;
}
    password=hex_md5(password);
    doPost('/user/add', JSON.stringify({
        qyid: getUserQyid(),
        relname: $('#relname').val(),
        yhsp: $('#yhsp').val(),
        sfzhm: $('#sfzhm').val(),
        sex: $('#sex').val(),
        lxdh: $('#lxdh').val(),
        account: $('#account').val(),
        password: password,
        state: $('#state').val()
    }), function (response) {
        if (response.success == true) {
            submitDialog();
        } else {
                layer.msg(response.data, {icon: 5});
        }
    })
}

/**
 * 修改用户
 */
function updateUser() {
    doPost('/user/update', JSON.stringify({
        qyid: getUserQyid(),
        relname: $('#relname').val(),
        yhsp: $('#yhsp').val(),
        sfzhm: $('#sfzhm').val(),
        sex: $('#sex').val(),
        lxdh: $('#lxdh').val(),
        account: $('#account').val(),
        password: $('#password').val(),
        state: $('#state').val()
    }), function (response) {
        if (response.success == true) {
            submitDialog();
        } else {
                layer.msg('修改失败', {icon: 5});
        }
    })
}

/**
 * 用户所属区域，四个选择框的展示
 * getAjqy获取省级区域
 * getBjqy获取市级区域
 * getCjqy获取区县级区域
 * getDjqy获取乡镇级区域
 */
function getAjqy() {
    doPost('/area/getQyname', JSON.stringify({
        sjqyid: undefined,
        xzjb: 1
    }), function (response) {
        $('#ajqy').empty();
        var abody = '<select id="aqyid" onchange="getBjqy()" style="height: 30px;line-height: 30px;"><option value="">省级行政区域&nbsp;&nbsp;</option>';
        $.each(response.data, function (n, value) {
            abody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
        })
        $('#ajqy').append(abody + "</select>");

    })
}

function getBjqy(qyid) {
    doPost('/area/getQyname', JSON.stringify({
        sjqyid: $('#aqyid').val(),
        xzjb: 2
    }), function (response) {
        $('#bjqy').empty();
        $('#cjqy').empty();
        $('#djqy').empty();
        if (isNotEmpty($('#aqyid').val())) {
            var bbody = '<select id="bqyid" onchange="getCjqy()" style="height: 30px;line-height: 30px;"><option value="">市级行政区域&nbsp;&nbsp;</option>';
            $.each(response.data, function (n, value) {
                bbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
            })
            $('#bjqy').append(bbody + "</select>");
        }
    })
    if (isNotEmpty(qyid)) {
        $('#bqyid').val(qyid)
    } else {
    }
}

function getCjqy(qyid) {
    doPost('/area/getQyname', JSON.stringify({
        sjqyid: $('#bqyid').val(),
        xzjb: 3
    }), function (response) {
        $('#cjqy').empty();
        $('#djqy').empty();
        if (isNotEmpty($('#bqyid').val())) {
            var cbody = '<select id="cqyid" onchange="getDjqy()" style="height: 30px;line-height: 30px;"><option value="">区县级行政区域&nbsp;&nbsp;</option>';
            $.each(response.data, function (n, value) {
                cbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
            })
            $('#cjqy').append(cbody + "</select>");
        }
    })
    if (isNotEmpty(qyid)) {
        $('#cqyid').val(qyid)
    } else {
    }
}

function getDjqy(qyid) {
    doPost('/area/getQyname', JSON.stringify({
        sjqyid: $('#cqyid').val(),
        xzjb: 4
    }), function (response) {
        $('#djqy').empty();
        if (isNotEmpty($('#cqyid').val())) {
            var dbody = '<select id="dqyid" style="height: 30px;line-height: 30px;"><option value="">乡镇级行政区域&nbsp;&nbsp;</option>';
            $.each(response.data, function (n, value) {
                dbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
            })
            $('#djqy').append(dbody + "</select>");
        }
    })
    if (isNotEmpty(qyid)) {
        $('#dqyid').val(qyid)
    } else {
    }
}

/**
 * 修改用户信息时，用户所属区域<select>初始显示信息。
 */
function showUserQy(qyid) {
    doPost('/area/getSjQyname', qyid, function (response) {
        var areas = response.data;
        //赋值省级区域
        $('#aqyid').val(areas[0].QYID);
        $("#aqyid ").prop("disabled", true);
        if (1 < areas.length) {//有市级区域
            doPost('/area/getQyname', JSON.stringify({
                sjqyid: $('#aqyid').val(),
                xzjb: 2
            }), function (response) {
                $('#bjqy').empty();
                var bbody = '<select id="bqyid" onchange="getCjqy()" style="height: 30px;line-height: 30px;"><option value="">市级行政区域&nbsp;&nbsp;</option>';
                $.each(response.data, function (n, value) {
                    bbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
                })
                $('#bjqy').append(bbody + "</select>");
                //赋值市级区域
                $('#bqyid').val(areas[1].QYID);
                $("#aqyid ").prop("disabled", true);
                $("#bqyid ").prop("disabled", true);
                if (2 < areas.length) {//有区县级区域
                    doPost('/area/getQyname', JSON.stringify({
                        sjqyid: $('#bqyid').val(),
                        xzjb: 3
                    }), function (response) {
                        $('#cjqy').empty();
                        var cbody = '<select id="cqyid" onchange="getDjqy()" style="height: 30px;line-height: 30px;"><option value="">区县级行政区域&nbsp;&nbsp;</option>';
                        $.each(response.data, function (n, value) {
                            cbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
                        })
                        $('#cjqy').append(cbody + "</select>");
                        //赋值区县级区域
                        $('#cqyid').val(areas[2].QYID);
                        $("#aqyid ").prop("disabled", true);
                        $("#bqyid ").prop("disabled", true);
                        $("#cqyid ").prop("disabled", true);
                        if (3 < areas.length) {//有乡镇级区域
                            doPost('/area/getQyname', JSON.stringify({
                                sjqyid: $('#cqyid').val(),
                                xzjb: 4
                            }), function (response) {
                                $('#djqy').empty();
                                var dbody = '<select id="dqyid" style="height: 30px;line-height: 30px;"><option value="">乡镇级行政区域&nbsp;&nbsp;</option>';
                                $.each(response.data, function (n, value) {
                                    dbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
                                })
                                $('#djqy').append(dbody + "</select>");
                                //赋值乡镇级区域
                                $('#dqyid').val(areas[3].QYID);
                                $("#aqyid ").prop("disabled", true);
                                $("#bqyid ").prop("disabled", true);
                                $("#cqyid ").prop("disabled", true);
                                $("#dqyid ").prop("disabled", true);
                            })
                        } else {
                            getDjqy();
                            return;
                        }
                    })
                } else {
                    getCjqy();
                    return;
                }
            })
        } else {
            getBjqy();
            return;
        }
    })
}

/**
 * 获取用户所属区域ID
 */
function getUserQyid() {
    var qyid = '';
    if (isNotEmpty($('#dqyid').val())) {
        qyid = $('#dqyid').val();
    } else if (isNotEmpty($('#cqyid').val())) {
        qyid = $('#cqyid').val();
    } else if (isNotEmpty($('#bqyid').val())) {
        qyid = $('#bqyid').val();
    } else if (isNotEmpty($('#aqyid').val())) {
        qyid = $('#aqyid').val()
    } else {
    }
    return qyid;
}

/**
 * 随着用户姓名的输入该变用户首拼
 */
function setFirstCharacter() {
    var Chinese = $('#relname').val();
    var firstCharacter = getPinYinFirstCharacter(Chinese, null, false);
    $('#yhsp').val(firstCharacter.replace(/\s+/g, ""));
}

/**
 * 提示弹出层
 */
function warning(message) {
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.msg(message, {icon: 7});
    })
}