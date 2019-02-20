/**
 * 排名项目增加和修改
 */
var upOrAdd = "";
var id = '';
var layer;
$(function () {
    layui.use('layer', function () {
        layer = layui.layer;
    });
    doPost('/assess/getkhlx', {}, function (response) {
        var tkhlx = "<option value=''>----选择排名类型----</option>";
        $.each(response.data, function (n, value) {
            tkhlx += "<option value='" + value.DMZ + "'>" + value.DMMC + "</option>";
        })
        $('#khlx').html(tkhlx);
    })

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

        initPages();
    })

})

function gradeChange() {
    var objS = document.getElementById("khlx");
    var khlx = objS.options[objS.selectedIndex].value;
    if (khlx == 1) {
        $('#ydkh').removeAttr('style');
        $('#ndkh').css('display', 'none');
        $('#jdkh').css('display', 'none');
        $('#jdkhxz').css('display', 'none');
    } else if (khlx == 2) {
        $('#ydkh').css('display', 'none');
        $('#jdkh').removeAttr('style');
        $('#jdkhxz').removeAttr('style');
        $('#ndkh').css('display', 'none');
    } else if (khlx == 3) {
        $('#ydkh').css('display', 'none');
        $('#jdkh').css('display', 'none');
        $('#jdkhxz').css('display', 'none');
        $('#ndkh').removeAttr('style');
    }
}

/**
 * 页面初始化
 */
function initPages() {
    var postData = GetRequest();
    var ly = postData.ly;
    if (ly === 'add') {//新增
        upOrAdd = "add";
        timedate();
        var id_user = getUserqyidStr();
        showQy(id_user);
    } else {//修改
        upOrAdd = "up";
        timedate2();
        id = decodeURI(postData.id);
        var pmmc = decodeURI(postData.pmmc);
        var pmsp = decodeURI(postData.pmsp);
        var kssj = decodeURI(postData.kssj);
        var pmqy = decodeURI(postData.pmqy);
        var khlx = decodeURI(postData.khlx);
        $('#pmmc').val(pmmc);
        $('#pmsp').val(pmsp);
        $("#khlx").val(khlx);
        if (khlx == 1) {
            $('#ydkh').removeAttr('style');
            $('#ndkh').css('display', 'none');
            $('#jdkh').css('display', 'none');
            $('#jdkhxz').css('display', 'none');
            $('#ydkhsj').val(kssj.substring(0, 7));
        } else if (khlx == 2) {
            $('#ydkh').css('display', 'none');
            $('#jdkh').removeAttr('style');
            $('#jdkhxz').removeAttr('style');
            $('#ndkh').css('display', 'none');
            $('#jdkhsj').val(kssj.substring(0, 4));
            var kssjY = kssj.substring(5, 7).split("");
            if (kssjY[0] == 0) {
                $('#jdkhxzjd').val(parseInt((parseInt(kssjY[1]) + 2) / 3));
            } else {
                $('#jdkhxzjd').val(parseInt((parseInt(kssj.substring(5, 7)) + 2) / 3));
            }
        } else if (khlx == 3) {
            $('#ydkh').css('display', 'none');
            $('#jdkh').css('display', 'none');
            $('#jdkhxz').css('display', 'none');
            $('#ndkh').removeAttr('style');
            $('#ndkhsj').val(kssj.substring(0, 4));
        }
        showQy(pmqy);
    }
}


/**
 * 增加排名项目
 * 所有新增默认启用
 */
function addPmxm() {
    Getkhsj();
    var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    doPost('/job/addxm', JSON.stringify({
        pmmc: $('#pmmc').val(),
        pmsp: $('#pmsp').val(),
        pmqy: getQyid(),
        khlx: $('#khlx').val(),
        kssj: khkssj,
        jssj: khjssj,
        state: 1
    }), function (response) {
        layer.close(index);
        if (response.success == true) {
            submitDialog();
        } else {
            layer.msg(response.data, {icon: 5});
        }
    })
}

/**
 * 修改排名项目
 * 所有修改默认启用
 */
function updatePmxm() {
    Getkhsj();
    var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    doPost('/job/alterxm', JSON.stringify({
        id: id,
        pmmc: $('#pmmc').val(),
        pmsp: $('#pmsp').val(),
        pmqy: getQyid(),
        khlx: $('#khlx').val(),
        kssj: khkssj,
        jssj: khjssj,
        state: 1
    }), function (response) {
        layer.close(index);
        if (response.success == true) {
            submitDialog();
        } else {
            layer.msg('修改失败', {icon: 5});
        }
    })
}

/**
 * 判断是新增还是修改
 */
function operationJob() {
    var pmmc = $('#pmmc').val();
    if (pmmc == '') {
        layer.msg('请填写排名名称', {icon: 5});
        return;
    }

    if (upOrAdd === "up") {
        updatePmxm();
    } else if (upOrAdd === "add") {
        addPmxm();
    }
}

/**
 * 设置考核时间
 * @type {string}
 */
var khkssj = '';
var khjssj = '';

function Getkhsj() {
    var khlx = $('#khlx').val();
    khkssj = '';
    khjssj = '';
    if (khlx == 1) { // 月
        var date = new Date();
        var time = $('#ydkhsj').val().split('-');
        date.setFullYear(parseInt(time[0]));
        date.setDate(1);
        date.setMonth(parseInt(time[1]));
        var odatef = new Date();
        odatef.setFullYear(date.getFullYear());
        odatef.setDate(1);
        odatef.setMonth(date.getMonth() + 1);
        if (date.getMonth() == 0) {
            khkssj = (date.getFullYear() - 1) + '-12-1';
        } else {
            khkssj = date.getFullYear() + '-' + date.getMonth() + '-1';
        }
        if (odatef.getMonth() == 0) {
            khjssj = (odatef.getFullYear() - 1) + '-12-' + odatef.getDate();
        } else {
            khjssj = odatef.getFullYear() + '-' + odatef.getMonth() + '-' + odatef.getDate();
        }
    } else if (khlx == 2) {  // 季
        var khsjn = new Date();
        khsjn.setFullYear(parseInt(($('#jdkhsj').val()).replace('-', "/")));
        var khlxjd = $('#jdkhxzjd').val();
        var dateks = new Date(khsjn);
        var datejs = new Date(khsjn);
        if (khlxjd == 1) {
            dateks.setMonth(1);
            dateks.setDate(1);
            datejs.setMonth(4);
            datejs.setDate(1);
        } else if (khlxjd == 2) {
            dateks.setMonth(4);
            dateks.setDate(1);
            datejs.setMonth(7);
            datejs.setDate(1);
        } else if (khlxjd == 3) {
            dateks.setMonth(7);
            dateks.setDate(1);
            datejs.setMonth(10);
            datejs.setDate(1);
        } else if (khlxjd == 4) {
            dateks.setMonth(10);
            dateks.setDate(1);
            datejs.setFullYear(dateks.getFullYear() + 1);
            datejs.setMonth(1);
            datejs.setDate(1);
        }
        khkssj = dateks.getFullYear() + '-' + dateks.getMonth() + '-' + dateks.getDate();
        khjssj = datejs.getFullYear() + '-' + datejs.getMonth() + '-' + datejs.getDate();

    } else if (khlx == 3) {  // 年
        var date = new Date();
        date.setFullYear(parseInt(($('#ndkhsj').val()).replace('-', "/")));
        date.setMonth(1);
        date.setDate(1);
        khkssj = date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate();
        khjssj = (date.getFullYear() + 1) + '-' + date.getMonth() + '-' + date.getDate();
    } else {
        layer.alert('请选择考核类型', {icon: 5});
        return;
    }
    if (khkssj == '' || khjssj == '') {
        layer.alert('请选择考核时间', {icon: 5});
        return;
    }
}

function submitDialog() {
    layer.alert('提交成功', {
        closeBtn: 0
    }, function () {
        goback();
    });

}

/**
 * 返回job_ranking.html
 */
function goback() {
    window.location.href = "./Job_ranking.html";
}

/**
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
        var bbody = '<select id="bqyid" onchange="getCjqy()" style="height: 30px;line-height: 30px;"><option value="">市级行政区域&nbsp;&nbsp;</option>';
        $.each(response.data, function (n, value) {
            bbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
        })
        $('#bjqy').append(bbody + "</select>");

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
        var cbody = '<select id="cqyid" onchange="getDjqy()" style="height: 30px;line-height: 30px;"><option value="">区县级行政区域&nbsp;&nbsp;</option>';
        $.each(response.data, function (n, value) {
            cbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
        })
        $('#cjqy').append(cbody + "</select>");

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
        var dbody = '<select id="dqyid" style="height: 30px;line-height: 30px;"><option value="">乡镇级行政区域&nbsp;&nbsp;</option>';
        $.each(response.data, function (n, value) {
            dbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
        })
        $('#djqy').append(dbody + "</select>");

    })
    if (isNotEmpty(qyid)) {
        $('#dqyid').val(qyid)
    } else {
    }
}

/**
 * 修改排名信息时，所属区域<select>初始显示信息。
 */
function showQy(qyid) {
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
                var bbody = '<select id="bqyid" onchange="getCjqy()" class="layui-input"><option value="">市级行政区域&nbsp;&nbsp;</option>';
                $.each(response.data, function (n, value) {
                    bbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
                })
                $('#bjqy').append(bbody + "</select>");
                //赋值市级区域
                $('#bqyid').val(areas[1].QYID);
                $("#bqyid ").prop("disabled", true)
                if (2 < areas.length) {//有区县级区域
                    doPost('/area/getQyname', JSON.stringify({
                        sjqyid: $('#bqyid').val(),
                        xzjb: 3
                    }), function (response) {
                        $('#cjqy').empty();
                        var cbody = '<select id="cqyid" onchange="getDjqy()" class="layui-input"><option value="">区县级行政区域&nbsp;&nbsp;</option>';
                        $.each(response.data, function (n, value) {
                            cbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
                        })
                        $('#cjqy').append(cbody + "</select>");
                        //赋值区县级区域
                        $('#cqyid').val(areas[2].QYID);
                        $("#cqyid ").prop("disabled", true)
                        if (3 < areas.length) {//有乡镇级区域
                            doPost('/area/getQyname', JSON.stringify({
                                sjqyid: $('#cqyid').val(),
                                xzjb: 4
                            }), function (response) {
                                $('#djqy').empty();
                                var dbody = '<select id="dqyid" class="layui-input"><option value="">乡镇级行政区域&nbsp;&nbsp;</option>';
                                $.each(response.data, function (n, value) {
                                    dbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
                                })
                                $('#djqy').append(dbody + "</select>");
                                //赋值乡镇级区域
                                $('#dqyid').val(areas[3].QYID);
                                $("#dqyid ").prop("disabled", true)
                            })
                        } else {
                            return;
                        }
                    })
                } else {
                    return;
                }
            })
        } else {
            return;
        }
    })
}

/**
 * 获取所属区域ID
 */
function getQyid() {
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
 * 随着排名名称的输入该变排名名称首拼
 */
function setFirstCharacter() {
    var Chinese = $('#pmmc').val();
    var firstCharacter = getPinYinFirstCharacter(Chinese, null, false);
    $("#pmsp").val(firstCharacter.replace(/\s+/g, ""));
}

function getqyidArr(userqyid) {

    var userArr = new Array();

    if (userqyid.length == 2) {
        userArr.push(userqyid.substring(0, userqyid.length));
    } else if (userqyid.length > 2 && userqyid.length <= 4) {
        userArr.push(userqyid.substring(0, 2));
        userArr.push(userqyid.substring(0, userqyid.length));
    } else if (userqyid.length > 4 && userqyid.length <= 6) {
        userArr.push(userqyid.substring(0, 2));
        userArr.push(userqyid.substring(0, 4));
        userArr.push(userqyid.substring(0, userqyid.length));
    } else if (userqyid.length > 6) {
        userArr.push(userqyid.substring(0, 2));
        userArr.push(userqyid.substring(0, 4));
        userArr.push(userqyid.substring(0, 6));
        userArr.push(userqyid.substring(0, userqyid.length));
    }
    return userArr;
}


function timedate() {
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //年月选择器
        laydate.render({
            elem: '#ydkhsj'
            , type: 'month'
            , value: new Date()
        });

        //年选择器
        laydate.render({
            elem: '#ndkhsj'
            , type: 'year'
            , value: new Date()
        });

        //年月范围
        laydate.render({
            elem: '#jdkhsj'
            , type: 'year'
            , value: new Date()
        });
    });

}

function timedate2() {
    layui.use('laydate', function () {
        var laydate = layui.laydate;

        //年月选择器
        laydate.render({
            elem: '#ydkhsj'
            , type: 'month'
        });

        //年选择器
        laydate.render({
            elem: '#ndkhsj'
            , type: 'year'
        });

        //年月范围
        laydate.render({
            elem: '#jdkhsj'
            , type: 'year'
        });
    });

}