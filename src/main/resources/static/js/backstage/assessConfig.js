/**
 * 考核查询详情
 */
var layerm;
$(function () {

    timedate();
    justPression();
    doPost('/assess/getkhlx', {}, function (response) {
        var tkhlx = "<option value=''>----选择考核类型----</option>";
        $.each(response.data, function (n, value) {
            tkhlx += "<option value='" + value.DMZ + "'>" + value.DMMC + "</option>";
        })
        $('#khlx').html(tkhlx);

        getAssessConfig();
    })
})

function getAssessConfig() {
    doPost('/assess/getAssessConfig', {}, function (response) {
        if (response.success == true) {
            showDetail(response.data);
        } else {
            closeIframe();
        }
    })
}

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

var dxObj = {};
var xxObj = {};
var dbzObj = {};
/**
 * 详情展示
 */
var khqy = '';

function showDetail(data) {
    console.log(data);
    khqy = data[0].khqy;
    $("#configMc").html(data[0].qyname + '农村道路交通安全管理系统工作情况考核配置');
    $("#debody").empty();
    var flag = '';
    var str = "";
    $.each(data, function (n, value) {
        if (flag != value.dx) {
            flag = value.dx;
            dxObj[value.dx] = value.dxfz;
            xxObj[value.dx + '-' + value.xx + '-' + value.dxmc] = value.xxfz;
            dbzObj[value.dx + '-' + value.xx + '-' + value.dxmc] = value.dbz;
            str += "<tr>\n" +
                "<td rowspan='" + value.total + "' align=\"middle\" valign='middle' style='width: 10%'>" + value.dxmc + "</td>\n" +
                "<td rowspan='" + value.total + "' align=\"middle\" valign='middle' style='width: 8%'>" +
                "   <input id='" + value.dx + "dx' class=\"input_border\" value='" + nullData1(value.dxfz) + "'" +
                "           onKeyPress='return onlyIntegerKeyPress(event)' onkeyup='sumDX(event, this)' dx='" + value.dx + "'>" +
                "</td>\n" +
                "<td style='width: 15%'>" + value.xxmc + "</td>\n" +
                "<td style='width: 8%'>" +
                "   <input id='" + value.dx + value.xx + "xx' class=\"input_border\" value='" + nullData1(value.xxfz) + "'" +
                "           onKeyPress='return onlyIntegerKeyPress(event)' onkeyup='sumXX(event, this)' dx='" + value.dx + "' dxmc='" + value.dxmc + "' xx='" + value.xx + "'>" +
                "</td>\n" +
                "<td style='width: 8%'>" +
                "   <input id='" + value.dx + value.xx + "dbz' class=\"input_border\" value='" + nullData1(value.dbz) + "'" +
                "           onKeyPress='return onlyIntegerKeyPress(event)' onkeyup='sumDbz(event, this)' dx='" + value.dx + "' dxmc='" + value.dxmc + "' xx='" + value.xx + "'>" +
                "</td>\n" +
                "<td style='width: 51%;white-space:normal;text-align: left;word-break:normal;padding: 0px 10px;'>" + nullData(value.khgz) + "</td>\n" +
                "</tr>\n";
        } else {
            xxObj[value.dx + '-' + value.xx + '-' + value.dxmc] = value.xxfz;
            dbzObj[value.dx + '-' + value.xx + '-' + value.dxmc] = value.dbz;
            str += "<tr>\n" +
                "<td style='width: 15%'>" + value.xxmc + "</td>\n" +
                "<td style='width: 8%'>" +
                "   <input id='" + value.dx + value.xx + "xx' class=\"input_border\" value='" + nullData1(value.xxfz) + "'" +
                "           onKeyPress='return onlyIntegerKeyPress(event)' onkeyup='sumXX(event, this)' dx='" + value.dx + "' dxmc='" + value.dxmc + "' xx='" + value.xx + "'>" +
                "</td>\n" +
                "<td style='width: 8%'>" +
                "   <input id='" + value.dx + value.xx + "dbz' class=\"input_border\" value='" + nullData1(value.dbz) + "'" +
                "           onKeyPress='return onlyIntegerKeyPress(event)' onkeyup='sumDbz(event, this)' dx='" + value.dx + "' dxmc='" + value.dxmc + "' xx='" + value.xx + "'>" +
                "</td>\n" +
                "<td style='width: 51%;white-space:normal;text-align: left;word-break:normal;padding: 0px 10px;'>" + nullData(value.khgz) + "</td>\n" +
                "</tr>\n";
        }
    })
    $("#debody").append(str);
}

var tipError = '';

function operateSure() {
    checkSumEquals();
    if (subflag == 0) {
        subflag = 1;
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert(tipError, {icon: 5});
        })
    } else {

        var khlx = $('#khlx').val();
        var khkssj = '';
        var khjssj = '';
        if (khlx == 1) {
            var d = $('#ydkhsj').val();
            if (d == undefined || d == '') {
                layerm.msg("未选择日期", {icon: 5});
                return;
            }
            var date = new Date();
            var time = $('#ydkhsj').val().split('-');
            date.setFullYear(parseInt(time[0]));
            date.setDate(1);
            date.setMonth(parseInt(time[1]));
            var odatef = new Date();
            odatef.setFullYear(date.getFullYear());
            odatef.setDate(1);
            odatef.setMonth(date.getMonth() + 1);
            if (date.getMonth() == 0){
                khkssj = (date.getFullYear() - 1) + '-12-1';
            } else {
                khkssj = date.getFullYear() + '-' + date.getMonth() + '-1';
            }
            if (odatef.getMonth() == 0) {
                khjssj = (odatef.getFullYear() - 1) + '-12-' + odatef.getDate();
            } else {
                khjssj = odatef.getFullYear() + '-' + odatef.getMonth() + '-' + odatef.getDate();
            }
        } else if (khlx == 2) {
            var d = $('#jdkhsj').val();
            if (d == undefined || d == '') {
                layerm.msg("未选择年", {icon: 5});
                return;
            }
            var khsjn = new Date();
            khsjn.setFullYear(parseInt(($('#jdkhsj').val()).replace('-', "/")));
            var khlxjd = $('#jdkhxzjd').val();
            var dateks = new Date(khsjn);
            var datejs = new Date(khsjn);
            if (khlxjd == 1) {
                khkssj = dateks.getFullYear() + '-01-01';
                khjssj = datejs.getFullYear() + '-04-01';
            } else if (khlxjd == 2) {
                khkssj = dateks.getFullYear() + '-04-01';
                khjssj = datejs.getFullYear() + '-07-01';
            } else if (khlxjd == 3) {
                khkssj = dateks.getFullYear() + '-07-01';
                khjssj = datejs.getFullYear() + '-10-01';
            } else if (khlxjd == 4) {
                khkssj = dateks.getFullYear() + '-10-01';
                khjssj = (dateks.getFullYear() + 1) + '-01-01';
            }
        } else if (khlx == 3) {
            var d = $('#ndkhsj').val();
            if (d == undefined || d == '') {
                layerm.msg("未选择日期", {icon: 5});
                return;
            }
            var date = new Date();
            date.setFullYear(parseInt(($('#ndkhsj').val()).replace('-', "/")));
            date.setMonth(1);
            date.setDate(1);
            khkssj = date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate();
            khjssj = (date.getFullYear() + 1) + '-' + date.getMonth() + '-' + date.getDate();
        } else {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('请选择考核类型', {icon: 5});
            })
            return;
        }
        if (khkssj == '' || khjssj == '') {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('请选择考核时间', {icon: 5});
            })
            return;
        }
        doGet('/assess/justIsExist',{
            khkssj: khkssj,
            khjssj: khjssj,
            khlx: khlx
        },function (response) {
            if (response.success ==true){
                    if (response.data.isExist==true){
                        layerm.confirm("当前区域存在当前时间类型考核，是否重新生成?", {
                            btn: ['重新生成', '取消'] //可以无限个按钮
                        }, function () {
                            saveAssessData(khkssj,khjssj,khlx);
                        }, function (index) {
                            layerm.close(index);
                        });
                    }else{
                        saveAssessData(khkssj,khjssj,khlx);
                    }
            } else{
                layerm.alert(response.data, {icon: 5});
            }
        });




    }

}
function  saveAssessData(khkssj,khjssj,khlx) {
    var conditions = [];
    $.each(xxObj, function (n, value) {
        var key = n.split('-');
        var demo = new Object();
        demo.khqy = khqy;
        demo.dx = key[0];
        demo.dxfz = dxObj[key[0]];
        demo.xx = key[1];
        demo.xxfz = value;
        demo.dbz = dbzObj[n];
        conditions.push(demo);
    });
    var index = layerm.load(0, {shade: false}); //0代表加载的风格，支持0-2
    doPost('/assess/getAssessSave', JSON.stringify({
        conditions: conditions,
        khkssj: khkssj,
        khjssj: khjssj,
        khlx: khlx
    }), function (response) {
        layerm.close(index);
        console.log(response);
        if (response.success == true) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.confirm(response.data, {
                    btn: ['查看考核表', '返回'] //可以无限个按钮
                }, function () {
                    window.location.href = 'assessQuery.html';
                }, function (index) {
                    layer.close(index);
                });
            })
        } else {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert(response.data, {icon: 5});
            })
        }

    })
}

var subflag = 1;

// 判断分值是否相等
function checkSumEquals() {
    tipError = '';
    $.each(dbzObj, function (n, value) {
        if (value == undefined || value == '') {
            tipError += "达标值不能为空</br>";
            // layui.use('layer', function() {
            //     var layer = layui.layer;
            //     layer.msg("达标值不能为空", {icon: 5});
            // })
            subflag = 0
            return false;
        }
    });
    var dxSum = 0;
    $.each(dxObj, function (n, value) {
        if (value == undefined || value == '') {
            tipError += "大项分值不能为空</br>";
            // layui.use('layer', function() {
            //     var layer = layui.layer;
            //     layer.msg("大项分值不能为空", {icon: 5});
            // })
            subflag = 0
            return false;
        }
        dxSum += parseInt(value);
    });
    var xxSum = 0;
    var xxList = {};
    var xxFlag = '';
    $.each(xxObj, function (n, value) {
        if (value == undefined || value == '') {
            tipError += "小项分值不能为空</br>"
            // layui.use('layer', function() {
            //     var layer = layui.layer;
            //     layer.msg("小项分值不能为空", {icon: 5});
            // })
            subflag = 0
            return false;
        }
        var key = n.split('-');
        if (key[0] != xxFlag) {
            xxFlag = key[0];
            xxSum = 0;
            xxSum += parseInt(value);
            xxList[key[0] + '-' + key[2]] = xxSum;
        } else {
            xxSum += parseInt(value);
            xxList[key[0] + '-' + key[2]] = xxSum;
        }
    });
    $.each(xxList, function (n, value) {
        var key = n.split('-');
        if (value != dxObj[key[0]]) {
            tipError += key[1] + "大项分值与该小项分值和不等</br>"
            // layui.use('layer', function () {
            //     var layer = layui.layer;
            //     layer.msg(key[1] + "大项分值与该小项分值和不等", {icon: 5});
            // })
            subflag = 0
            return false;
        }
    });
    if (dxSum != 100) {
        tipError += "所有大项分值和必须等于100</br>"
        // layui.use('layer', function() {
        //     var layer = layui.layer;
        //     layer.msg("所有大项分值和必须等于100", {icon: 5});
        // })
        subflag = 0
    }
    ;
}

// 记录大项分值
function sumDX(e, att) {
    if (e === undefined) {
        e = window.event;
    }
    var obj = e.srcElement ? e.srcElement : e.target;
    var pattern = /[^\d]/ig;
    var val = obj.value;
    if (pattern.test(val)) {
        var i = getCursortPosition(e);
        obj.value = val.replace(pattern, '');
        setCaretPosition(e, i);
    }
    var dx = $(att).attr('dx');
    var dxfz = $('#' + dx + "dx").val();
    if (dxfz > 100) {
        $('#' + dx + "dx").val('100');
    } else if (dxfz < 0) {
        $('#' + dx + "dx").val('0');
    }
    dxObj[dx] = dxfz;
}

// 记录小项分值
function sumXX(e, att) {
    if (e === undefined) {
        e = window.event;
    }
    var obj = e.srcElement ? e.srcElement : e.target;
    var pattern = /[^\d]/ig;
    var val = obj.value;
    if (pattern.test(val)) {
        var i = getCursortPosition(e);
        obj.value = val.replace(pattern, '');
        setCaretPosition(e, i);
    }

    var dx = $(att).attr('dx');
    var xx = $(att).attr('xx');
    var dxmc = $(att).attr('dxmc');
    var xxfz = $('#' + dx + xx + "xx").val();
    if (xxfz > 100) {
        $('#' + dx + xx + "xx").val('100');
    } else if (xxfz < 0) {
        $('#' + dx + xx + "xx").val('0');
    }
    xxObj[dx + '-' + xx + '-' + dxmc] = xxfz;

}

// 记录达标分值
function sumDbz(e, att) {
    if (e === undefined) {
        e = window.event;
    }
    var obj = e.srcElement ? e.srcElement : e.target;
    var pattern = /[^\d]/ig;
    var val = obj.value;
    if (pattern.test(val)) {
        var i = getCursortPosition(e);
        obj.value = val.replace(pattern, '');
        setCaretPosition(e, i);
    }

    var dx = $(att).attr('dx');
    var xx = $(att).attr('xx');
    var dxmc = $(att).attr('dxmc');
    var dbz = $('#' + dx + xx + "dbz").val();
    dbzObj[dx + '-' + xx + '-' + dxmc] = dbz;

}

/************************************************
 * 获取光标位置
 *
 * @param ctrl
 * @returns {Number}
 */
getCursortPosition = function (event) {// 获取光标位置函数
    if (event === undefined || event === null) {
        event = arguments.callee.caller.arguments[0] || window.event;
    }
    var obj = event.srcElement ? event.srcElement : event.target;
    var CaretPos = 0;   // IE Support
    if (document.selection) {
        obj.focus();
        var Sel = document.selection.createRange();
        Sel.moveStart('character', -obj.value.length);
        CaretPos = Sel.text.length;
    } else if (obj.selectionStart || obj.selectionStart == '0') {
        // Firefox support
        CaretPos = obj.selectionStart;
    }

    return (CaretPos);
};

/**********************************************
 * 设置光标位置
 *
 * @param ctrl
 * @returns {Number}
 */
setCaretPosition = function (event, pos) {// 设置光标位置函数
    if (event === undefined || event === null) {
        event = arguments.callee.caller.arguments[0] || window.event;
    }
    var obj = event.srcElement ? event.srcElement : event.target;
    if (pos > 0) {
        pos = pos - 1;//因为把不匹配的字符删除之后,光标会往后移动一个位置
    }
    if (obj.setSelectionRange) {
        obj.focus();
        obj.setSelectionRange(pos, pos);
    } else if (obj.createTextRange) {
        var range = obj.createTextRange();
        range.collapse(true);
        range.moveEnd('character', pos);
        range.moveStart('character', pos);
        range.select();
    }
};
/***
 * [0-9]<br>
 *     12:ok;1.2:error
 * @param event
 * @returns {boolean}
 */
onlyIntegerKeyPress = function onlyIntegerKeyPress(event) {
    event = event || window.event || arguments.callee.caller.arguments[0];
    //console.log(event);
    var charCode2;
    if ('charCode' in event) {//IE7 and IE8 no charCode
        charCode2 = event.charCode;
    } else {
        //console.log('no charCode');
        charCode2 = event.keyCode;
    }
    //console.log(charCode2);
    if (event.keyCode === 8/*back*/ || event.keyCode === 13/*Enter*/ || event.keyCode === 9/*Tab*/ || event.keyCode === 37/*<- */ || event.keyCode === 39/* ->*/) {
        return true;
    } else if (charCode2 < 48 || charCode2 > 57) {/*0-9*/
        event.returnValue = false;
        return false;
    } else {
        return true;
    }
};

function timedate() {
    layui.use(['laydate', 'layer'], function () {
        var laydate = layui.laydate;
        layerm = layui.layer;
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

function goback() {
    window.location.href = "assessQuery.html";
}