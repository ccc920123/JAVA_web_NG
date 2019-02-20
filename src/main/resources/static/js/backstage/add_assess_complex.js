/**
 *  考核查询
 */
/**
 * 入口函数
 */
var layer;
$(function () {

    timedate();
    justPression();

    doPost('/assess/getkhlx', {}, function (response) {
        var tkhlx = "<option value=''>----选择考核类型----</option>";
        $.each(response.data, function (n, value) {
            tkhlx += "<option value='" + value.DMZ + "'>" + value.DMMC + "</option>";
        })
        $('#khlx').html(tkhlx);
    })
    var dz = getUserQynameStr();
    if (dz == undefined || dz == '') {
        dz = '四川省';
    }
    $('#dz').html(dz + '农村道路交通安全信息工作综合考核表');
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

function saveComplex() {
    if (justPressionBy("334")) {
        saveComplexData();
    } else {
        layer.msg("该用户暂无此权限！", {icon: 5});
    }
}

function goback() {
    window.location.href = "assess_complex.html";
}

/**
 * 获取列表
 */
function saveComplexData() {
    var khlx = $('#khlx').val();
    var khkssj = '';
    var khjssj = '';
    if (khlx == 1) {
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
            khkssj = (date.getFullYear() - 1) + '-12-01';
        } else {
            khkssj = date.getFullYear() + '-' + (date.getMonth()<10?'0'+date.getMonth():date.getMonth) + '-01';
        }
        if (odatef.getMonth() == 0) {
            khjssj = (odatef.getFullYear() - 1) + '-12-' + (odatef.getDate()<10?'0'+ odatef.getDate(): odatef.getDate());
        } else {
            khjssj = odatef.getFullYear() + '-' + (odatef.getMonth()<10?'0'+odatef.getMonth():odatef.getMonth) + '-' + (odatef.getDate()<10?'0'+ odatef.getDate(): odatef.getDate());
        }
    } else if (khlx == 2) {
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
        var date = new Date();
        date.setFullYear(parseInt(($('#ndkhsj').val()).replace('-', "/")));
        date.setMonth(1);
        date.setDate(1);
        khkssj = date.getFullYear() + '-' + (date.getMonth()<10?'0'+date.getMonth():date.getMonth) + '-' + (date.getDate()<10?'0'+ date.getDate(): date.getDate());
        khjssj = (date.getFullYear() + 1) + '-' + (date.getMonth()<10?'0'+date.getMonth():date.getMonth) + '-' + (date.getDate()<10?'0'+ date.getDate(): date.getDate());
    } else {
        layer.alert('请选择考核类型', {icon: 5});
        return;
    }
    if (khkssj == '' || khjssj == '') {
        layer.alert('请选择考核时间', {icon: 5});
        return;
    }
    var gzpm = $('#gzpm').val();
    var ngkh = $('#ngkh').val();
    if (gzpm == '' || ngkh == '') {
        layer.alert('请填写完整数据', {icon: 5});
        return;
    }
    if (parseInt(gzpm) + parseInt(ngkh) != 100) {
        layer.alert('工作排名占比和农管考核之和必须等于100', {icon: 5});
        return;
    }
    var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    doPost('/compreAssess/saveCompreAssess', JSON.stringify({
        khlx: $('#khlx').val(),
        khkssj: khkssj,
        khjssj: khjssj,
        gzpmzb: gzpm,
        ngkhzb: ngkh,
        state: 0
    }), function (response) {
        console.log(response);
        layer.close(index);
        if (response.success == true) {
            goback();
        } else {
            layer.alert(response.data, {icon: 5});
        }
    })

}


function timedate() {
    layui.use(['laydate', 'layer'], function () {
        var laydate = layui.laydate;
        layer = layui.layer;
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

function checkNum(e, attr) {

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

    var id = $(attr).attr('id');
    var number = $('#' + id).val();
    if (number > 100) {
        $('#' + id).val('100');
    } else if (number < 0) {
        $('#' + id).val('0');
    }
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
