/**
 * 入口函数
 */
$(function () {
    justPression();
    if (justPressionBy("131")) {
        getJobRankList();
    } else {
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("该用户暂无此权限！", {icon: 5});
        });
    }

})

function getJobRankList(){
    doPost('/job/joblist', JSON.stringify({
        condition: $('#condition').val().replace(/(\s*$)/g, ""),
        pageNo: pageNo
    }), function (response) {
        page(response.total);
        showJobRank(response.data);
    })
}

/**
 * 获取考评项目列表信息
 */
function getJobRank() {
    pageNo = 1;
    getJobRankList();
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
                    getJobRankList();
                    return;
                }
            }
        })
    })
}


/**
 * 展示用户信息
 */
function showJobRank(jobs) {
    var tbody = "";
    $("#tbody").empty();
    var change1 = justPressionBy("132");
    var change2 = justPressionBy("133");
    var change3 = justPressionBy("134");
    var change4 = justPressionBy("135");
    var change6 = justPressionBy("136");
    $.each(jobs, function (n, value) {
        var str = "";
        var isStart = "";
        var state = "";
        if (value.state == 0) {
            isStart = "启用";
            state = "未启用";
        } else {
            isStart = "停用";
            state = "已启用";
        }
        var change1H = "<a href=\"#\" class=\"tablelink\" onclick='alterpmxm(this)' id='" + value.id + "' pmmc='" + value.pmmc + "' pmsp='" + value.pmsp + "' khlx='" + value.khlx + "' kssj='" + value.kssj + "' pmqy='" + value.pmqy + "'>修改</a>";
        var change2H = "<a href=\"#\" class=\"tablelink\" onclick='deleteAsk(this)' jobId = '" + value.id + "'> 删除</a>"
        var change5H = "<a href=\"#\" class=\"tablelink\" onclick='settings(this)' jobId = '" + value.id + "'> 设置</a>"
        var change3H = "<a href=\"#\" class=\"tablelink\" onclick='getDetail(this)'pmmc='" + value.pmmc + "' id='" + value.id + "' kssj='" + value.kssj + "' jssj='" + value.jssj + "'> 打分</a>"
        var change4H = "<a href=\"#\" class=\"tablelink\" style='display: none' onclick='startOrStop(this)' state = '" + value.state + "' id = '" + value.id + "'>" + isStart + "</a>";
        var change6H = "<a href=\"#\" class=\"tablelink\" onclick='lookDetails(this)'  id='" + value.id + "' pmmc='" + value.pmmc + "' pmsp='" + value.pmsp + "' khlx='" + value.khlx + "' kssj='" + value.kssj + "' pmqy='" + value.pmqy + "'>查看</a>";
        var html = "";
        if (change6) {
            html += change6H;
        }
        if (change1) {
            html += change1H;
        }
        html+=change5H;
        if (change3) {
            html += change3H;
        }
        if (change4) {
            html += change4H;
        }
        if (change2) {
            html += change2H;
        }
        str += "<tr>" +
            "<td>" + value.pmmc + "</td>" +
            "<td>" + value.qyname + "</td>" +
            // "<td>" + getFormateDate(value.pmsj) + "</td>" +
            "<td>" + value.kssj + '~' + value.jssj + "</td>" +
            // "<td>" + state + "</td>" +
            "<td>" +
            html +
            "</td>" +
            "</tr>";
        tbody += str;
    })
    $("#tbody").append(tbody);
    $('.tablelist tbody tr:odd').addClass('odd');//给偶数行添加背景
}
function settings(span) {
    var jobId = $(span).attr("jobId");
    window.location.href = "Set_JobRanking.html?id="+jobId;
}

/**
 * 查看明细
 * @param span
 */
function lookDetails(att) {
    window.location.href = 'Job_ranking_info.html?ly=info'+
    '&id=' + encodeURI($(att).attr("id")) +
        "&pmmc=" + encodeURI($(att).attr('pmmc')) +
        "&pmsp=" + encodeURI($(att).attr('pmsp')) +
        '&kssj=' + encodeURI($(att).attr("kssj")) +
        '&khlx=' + encodeURI($(att).attr("khlx")) +
        "&pmqy=" + encodeURI($(att).attr('pmqy'));
}

/**
 * 修改考评项目
 */
function alterpmxm(att) {
    window.location.href = "Add_JobRanking.html?" +
        "ly=up&id=" + encodeURI($(att).attr('id')) +
        "&pmmc=" + encodeURI($(att).attr('pmmc')) +
        "&pmsp=" + encodeURI($(att).attr('pmsp')) +
        '&kssj=' + encodeURI($(att).attr("kssj")) +
        '&khlx=' + encodeURI($(att).attr("khlx")) +
        "&pmqy=" + encodeURI($(att).attr('pmqy'));
}

/**
 * 项目启用或者停用
 */
function startOrStop(att) {
    var id = $(att).attr("id");
    var state = $(att).attr("state");
    if (state == 0) {
        state = 1;
    } else {
        state = 0;
    }
    doPost('/job/state', JSON.stringify({
        id: id,
        state: state
    }), function (response) {
        if (response.success == true) {
            getJobRank();
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('操作成功', {icon: 6});
            });
        } else {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('操作失败', {icon: 5});
            });
        }
    })
}

/**
 * 删除询问层
 */
function deleteAsk(att) {
    layui.use('layer',function () {
        var layer = layui.layer;
        layer.confirm("是否删除当前工作考评项目",{
            btn:['确认','取消']
        },function () {
            var jobRankId = $(att).attr("jobId");
            deletePmxm(jobRankId);
        },function (index) {
            layer.close(index);
        })
    })
}

/**
 * 删除考评项目
 */
function deletePmxm(jobRankId) {
    doPost('/job/deletexm', jobRankId, function (response) {
        if (response.success == true) {
            getJobRank();
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('删除成功', {icon: 6});
            });
        } else {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('删除失败', {icon: 5});
            });
        }
    })
}


/**
 * 查看详情并打分
 */
function getDetail(att) {
    window.location.href = 'Job_ranking_info.html?ly=df' +
        '&id=' + encodeURI($(att).attr("id")) +
        '&pmmc=' + encodeURI($(att).attr("pmmc")) +
        '&kssj=' + encodeURI($(att).attr("kssj")) +
        '&jssj=' + encodeURI($(att).attr("jssj"));
}



