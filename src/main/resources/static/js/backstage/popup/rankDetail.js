/**
 * 入口函数
 */
var ly = '';
var pmid = '';
var pmmc = '';
var kssj = '';
var jssj = '';
var khqy;
var layer;
$(function () {
    var postData = GetRequest();
    ly = decodeURI(postData.ly);
    pmid = decodeURI(postData.id);
    khqy = decodeURI(postData.khqy);
    layui.use('layer', function () {
        layer = layui.layer;
    });
    $("#sub_button").css("display", "none");
    $("#excel").css("display", "none");
    if (ly == 'info') {
        $("#excel").removeAttr("style");
    } else {
        $("#sub_button").removeAttr("style");
    }
    getJobRankMessage(pmid);


})

function getJobRankMessage(pmid) {
    doGet('/job/jobRankMessage', {pmid: pmid}, function (response) {
        if (response.success == true) {
            pmmc = decodeURI(response.data.pmmc);
            kssj = decodeURI(response.data.kssj);
            jssj = decodeURI(response.data.jssj);
            getJobDetail();
        } else {

        }
    });
}

function getJobDetail() {
    doPost('/job/detail', JSON.stringify({
        id: pmid,
        pageNo: pageNo
    }), function (response) {
        if (response.success == true) {
            detailPage(response.total);
            showDetail(response.data);
        } else {
            closeIframe();
        }
    })
}

/**
 * 关闭iframe
 */
function closeIframe() {
    window.location.href = '../../backside/Job_ranking.html';
}

/**
 *详情显示的分页
 */
var pageNo = 1;
var detailPage = function (count) {
    $('#detotal').html(count);
    $('#depageNo').html(pageNo + '/' + Math.ceil(count / 100));
    layui.use('laypage', function () {
        var laypage = layui.laypage;
        laypage.render({
            elem: 'depage',
            count: count,
            limit: 100,
            curr: pageNo,
            jump: function (obj, first) {
                if (!first) {
                    pageNo = obj.curr;
                    getJobDetail();
                    return;
                }
            }
        })
    })
}

/**
 * 详情展示
 */
var list;

function showDetail(data) {
    list = data;
    $('#pmxmmc').html(pmmc);
    $('#pmkssj').html(kssj);
    $('#pmjssj').html(jssj);
    var debody = "";
    $("#debody").empty();
    var headxx = data.head;
    var bodyData = data.tbody;
    var thead = '';
    $.each(headxx, function (n, value) {
        $("#dx_" + value.dx).attr("colspan", value.total);
        thead += "<th>" + value.xxname + "</th>"
    });
    $("#xx_th").html(thead);
    $.each(bodyData, function (n, value) {
        var str = "";
        var pm = (pageNo - 1) * 10 + n + 1;
        var jobDetailData = value.sysJobRankDetailBeans;
        var qyid = value.qyid;
        var thtml = "";
        if (ly == 'df') {
            $.each(jobDetailData, function (index, sysJobRankDetail) {
                thtml += "<td><input id='" + sysJobRankDetail.id + "' class='sysdetail' dx='" + sysJobRankDetail.dx + "' style=\"  width:90%;border: none;border-bottom: 1px dotted #ddd;text-align:center;\" value='" + sysJobRankDetail.sorce + "'onKeyPress='return onlyIntegerKeyPress(event)' onkeyup='sum(event, this)' pid='" + sysJobRankDetail.id + "' n='" + n + "'></td>";
            });
        } else {
            $.each(jobDetailData, function (index, sysJobRankDetail) {
                thtml += "<td>" + sysJobRankDetail.sorce + "</td>";
            });
        }
        var trhtml = "<tr>";
        if (khqy != undefined && khqy == qyid) {
            trhtml = "<tr class='showGaoLiang'>";
        }
        if (ly == 'df') {
            trhtml +=
                "<td>" + value.qyname + "</td>" +
                // "<td><input id='" + n + "zzld' style=\"width: 85%; border: none;border-bottom: 1px dotted #ddd;\" value='" + value.zzld + "'onKeyPress='return onlyIntegerKeyPress(event)' onkeyup='sum(event, this)' n='" + n + "'></td>" +
                // "<td><input id='" + n + "gcls' style=\"width: 85%; border: none;border-bottom: 1px dotted #ddd;\" value='" + value.gcls + "'onKeyPress='return onlyIntegerKeyPress(event)' onkeyup='sum(event, this)' n='" + n + "'></td>" +
                // "<td><input id='" + n + "ddjc' style=\"width: 85%; border: none;border-bottom: 1px dotted #ddd;\" value='" + value.ddjc + "'onKeyPress='return onlyIntegerKeyPress(event)' onkeyup='sum(event, this)' n='" + n + "'></td>" +
                // "<td><input id='" + n + "gzcx' style=\"width: 85%; border: none;border-bottom: 1px dotted #ddd;\" value='" + value.gzcx + "'onKeyPress='return onlyIntegerKeyPress(event)' onkeyup='sum(event, this)' n='" + n + "'></td>" +
                // "<td><input id='" + n + "kf' style=\"width: 85%; border: none;border-bottom: 1px dotted #ddd;color: #A60000\" value='" + value.kf + "'onKeyPress='return onlyIntegerKeyPress(event)' onkeyup='sum(event, this)' n='" + n + "'></td>" +
                thtml +
                "<td><input id='" + n + "zf' style=\"  width:90%;border: none;border-bottom: 1px dotted #ddd;color: blue;text-align:center;\" readonly=\"readonly\" value='" + value.zf + "'  n='" + n + "'></td>" +
                // "<td style='color: #1e7ccc; text-align: left'>" + pm + "</td>" +
                // "<td onclick='grade(this)' kpqyid='"+ value.kpqyid+"' n='" + n +"'" +
                // "style='cursor: pointer;color: blue; text-align: left''>保存</td>" +
                "</tr>";
            // if (khqy!=undefined&&khqy==qyid){
            //     $(trhtml).addClass("showGaoLiang");
            // }
        } else {
            trhtml +=
                "<td>" + value.qyname + "</td>" +
                thtml +
                "<td>" + value.zf + "</td>" +
                "</tr>";
        }
        str += trhtml;
        debody += str;
    })
    $("#debody").append(debody);

}

/**
 * 求和
 * @param e
 * @param att
 */
function sum(e, att) {
    if (e === undefined) {
        e = window.event;
    }
    var obj = e.srcElement ? e.srcElement : e.target;
    var pattern = /[^\d]/ig;
    var val = obj.value;
    if (val == undefined || val == "") {
        val = 0;
    }
    if (val < 0) {
        val = 0;
    }
    if (val > 100) {
        val = 100;
    }
    $(att).val(parseInt(val));
    if (pattern.test(val)) {
        var i = getCursortPosition(e);
        obj.value = val.replace(pattern, '');
        setCaretPosition(e, i);
    }
    var n = $(att).attr('n');
    var eleList = $(att).parent().parent("tr").find(".sysdetail");
    var total = 0;
    $.each(eleList, function (index, input) {
        var valuei = $(input).val();
        if (valuei == undefined || valuei == "") {
            valuei = 0;
        }
        if (valuei < 0) {
            valuei = 0;
        }
        if (valuei > 100) {
            valuei = 100;
        }
        var dx = $(input).attr('dx');
        if (dx == '05') {
            total -= parseInt(valuei);
        } else {
            total += parseInt(valuei);
        }
    });

    $("#" + n + "zf").val(total);


}


var detailBean = {
    id: "",
    sorce: 0
}

/**
 * 提交打分
 */
function operateSure() {
    var eleList = $(".sysdetail");
    var listDetails = new Array();
    $.each(eleList, function (index, input) {
        var detailId = $(input).attr("pid");
        var valuei = $(input).val();
        if (valuei == undefined || valuei == "") {
            valuei = 0;
        }
        var obj = new Object();
        obj.id = detailId;
        obj.sorce = valuei;
        if (valuei < 0 || valuei > 100) {
            layer.msg('分值在0~100之间', {icon: 6});
        }
        listDetails.push(obj);
    });
    var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    doPost('/job/grade', JSON.stringify({
        qyid: pmid,
        sysJobRankDetailBeans: listDetails
    }), function (response) {
        layer.close(index);
        if (response.success == true) {
            getJobDetail();
            layer.msg('打分成功', {icon: 6});
        } else {
            layer.msg('打分失败', {icon: 5});
        }
    })
}

/**
 * 打分
 */
function grade(att) {
    var n = $(att).attr('n');
    doPost('/job/grade', JSON.stringify({
        kpqyid: $(att).attr('kpqyid'),
        pmxm: id,
        zzld: $('#' + n + "zzld").val(),
        gcls: $('#' + n + "gcls").val(),
        ddjc: $('#' + n + "ddjc").val(),
        gzcx: $('#' + n + "gzcx").val(),
        kf: $('#' + n + "kf").val()
    }), function (response) {
        if (response.success == true) {
            getJobDetail();
                layer.msg('打分成功', {icon: 6});
        } else {
                layer.msg('打分失败', {icon: 5});
        }

    })
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