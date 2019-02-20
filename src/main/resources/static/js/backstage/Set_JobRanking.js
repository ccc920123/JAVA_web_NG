var pmid;
var layer;
$(function () {
    var postData = GetRequest();
    pmid = decodeURI(postData.id);


    initLayer();
    getConfig();
    // var spotMax = 30;
    // if ($('div.spot').size() >= spotMax) {
    //     $(obj).hide();
    // }
    $("input.add").click(function () {
        var dx = $(this).attr("dx");
        addSpot(dx, "", "", 1, pmid);
    });
});

function initLayer() {
    layui.use(['layer'], function () {
        layer = layui.layer;
    });
}

/**
 * 获取小项
 */
function getConfig() {
    doGet("/job/getConfigByPMID", {pmid: pmid}, function (response) {
        if (response.success == true && response.data.length > 0) {
            $.each(response.data, function (index, config) {
                var dx = config.dx;
                var xxid = config.id;
                var pmid = config.pmid;
                var value = config.xxname;
                addSpot(dx, value, xxid, 2, pmid);
            });
        }
    });
}

function addSpot(dx, value, xxid, addtype, pmidd) {
    var html = '<div class="spot queryItem">';
    if (addtype == "1") {
        html = '<div class="spot addItem">'
    }
    html +=
        '<input type="text" name="spot_title" dx="' + dx + '"  xxid="' + xxid + '"  pmid="' + pmidd + '" class="spot_title" value="' + value + '"/> ' +
        '<input type="button" class="remove" dx="' + dx + '" addType="' + addtype + '"   xxid="' + xxid + '" value="X" onclick="deleteSpan(this)"/></div>'
    $('div#spots' + dx).append(html);
    // $(html).find("input.remove").click(function () {

    // });


};

function deleteSpan(span) {
    var addtype = $(span).attr("addType");
    var dx = $(span).attr("dx");
    var childrenDivs = $('div#spots' + dx).children("div");
    if (childrenDivs.length > 1) {

        if (addtype == 1) {//addtype是1说明是新添加的
            $(span).parent().remove();
        } else if (addtype == 2) {//addtype是2说明是查询出来的
            var xxid = $(span).attr("xxid");
            var d = $(span).parent();
            doPost("/job/DeleteConfigByID", JSON.stringify({xxid: xxid}), function (response) {
                if (response.success == true) {
                    $(d).remove();
                } else {
                    layer.msg(response.data, {icon: 5});
                }
            })
        }
    } else {
        layer.msg("每个大项至少一个小项", {icon: 5});
    }
}

/**
 * 返回到页面
 */
function goback() {
    window.location.href = 'Job_ranking.html';
}

var bean = {
    id: '',
    pmid: '',
    dx: "",
    xxname: ""
}

/**
 * 提交小项
 */
function sumbitConfig() {
    var configs = $("input[name='spot_title']");
    if (configs == undefined || configs.length == 0) {
        buildSorce();
        return;
    }
    var list = new Array();
    $.each(configs, function (index, config) {
        var dx = $(config).attr("dx");
        var xxid = $(config).attr("xxid");
        var pmid = $(config).attr("pmid");
        var value = $(config).val();
        if (value == undefined || value == ""||value.trim()=="") {
            layer.msg("小项名字不能为空，请确认小项", {icon: 5});
            list.clear();
            return;
        }
        var beanconfig = new Object();
        beanconfig.id = xxid;
        beanconfig.pmid = pmid;
        beanconfig.dx = dx;
        beanconfig.xxname = value;
        list.push(beanconfig);

    });
    if (list.length > 0) {
        doPost("/job/saveConfigBean", JSON.stringify(list), function (response) {
            if (response.success == true) {
                buildSorce();
            } else {
                layer.msg(response.data, {icon: 5});
            }
        });
    }

}

/**
 * 生成小项总分
 */
function buildSorce() {
    var index = layer.load(0, {shade: false}); //0代表加载的风格，支持0-2
    doPost("/job/saveSorceTable", JSON.stringify({
        pmid: pmid
    }), function (response) {
        layer.close(index);
        if (response.success == true) {
            layer.msg("保存成功", {icon: 6});
            goback();
        } else {
            layer.msg(response.data, {icon: 5});
        }
    });

}

/**
 * 生成小项总分
 */
function cancelF() {
    buildSorce();
}
