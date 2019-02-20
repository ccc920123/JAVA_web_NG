/**
 *  考核查询
 */
/**
 * 入口函数
 */
$(function () {

    justPression();

    doPost('/assess/getkhlx', {}, function (response) {
        var tkhlx = "<option value=''>----选择考核类型----</option>";
        $.each(response.data,function(n,value){
            tkhlx += "<option value='"+ value.DMZ +"'>"+ value.DMMC +"</option>";
        })
        $('#khlx').html(tkhlx);

        search();
    })


})
function search() {
    if (justPressionBy("331")) {
        pageNo = 1;
        getAssessList();
    } else {
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("该用户暂无此权限！", {icon: 5});
        });
    }
}
function add() {
    if (justPressionBy("332")) {
        window.location.href = 'add_assess_complex.html';
    } else {
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("该用户暂无此权限！", {icon: 5});
        });
    }
}
/**
 * 获取列表
 */
function getAssessList() {

    doPost('/assess/getComplexList', JSON.stringify({
        khlx: $('#khlx').val(),
        pageNo: pageNo
    }), function (response) {
        console.log(response);
        page(response.total);
        if (response.success == true) {
            showList(response.data);
        } else {
            $("#tbody").empty();
        }
    })

}

function showList(list) {
    var tbody = "";
    $("#tbody").empty();
    var change1 = justPressionBy("333");
    var change2 = justPressionBy("335");
    var change3 = justPressionBy("336");
    $.each(list, function (n, value) {
        var xh = (pageNo - 1) * 10 + n + 1;
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
        var changeHtml1= "<a  href=\"#\" class=\"tablelink\" onclick='queryDetails(this)' id='"+value.id + "' bt='"+value.bt + "'>详情</a>";
        var changeHtml2= "<a  href=\"#\" class=\"tablelink\" onclick='deleted(this)' id='"+value.id + "' bt='"+value.bt + "' state = '" + value.state + "'>删除</a>";
        var changeHtml3 = "<a href=\"#\" class=\"tablelink\" onclick='startOrStop(this)' state = '" + value.state + "' id = '" + value.id + "'>" + isStart + "</a>";
        var html = "";
        if (change1) {
            html += changeHtml1;
        }
        if (change2) {
            html += changeHtml2;
        }
        if (change3) {
            html += changeHtml3;
        }
        str += "<tr>" +
            "<td style='text-align: center'>" + xh + "</td>" +
            "<td>" + nullData(value.bt) + "</td>" +
            "<td>" + nullData(value.khsj) + "</td>" +
            "<td>" + value.dmmc + "</td>" +
            "<td>" + state + "</td>" +
            "<td>" + html + "</td>" +
            "</tr>";
        tbody += str;
    })
    $("#tbody").append(tbody);
    $('.tablelist tbody tr:odd').addClass('odd');//给偶数行添加背景
}

function queryDetails(att) {
    window.location.href = 'assess_complex_sore.html?' +
        'id=' + encodeURI($(att).attr('id')) +
        '&bt=' + encodeURI($(att).attr('bt'));
}

function deleted(att) {
    if ($(att).attr('state') == 1){
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.msg('该考核信息已启用，不能删除！！！', {icon: 5});
        });
    } else {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.confirm('是否确认删除该考核信息: ' + $(att).attr('bt'), {
                btn: ['确认', '取消']
            }, function () {
                doPost('/assess/getAssessComplexDeleted', $(att).attr('id'), function (response) {
                    if (response.success == true) {
                        layui.use('layer', function () {
                            var layer = layui.layer;
                            layer.msg("删除成功", {icon: 6});
                        })
                        getAssessList();
                    } else {
                        layui.use('layer', function () {
                            var layer = layui.layer;
                            layer.msg("删除失败", {icon: 5});
                        })
                    }
                })
            }, function (index) {
                layer.close(index);
            })
        })
    }
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
    doPost('/assess/state', JSON.stringify({
        id: id,
        state: state
    }), function (response) {
        if (response.success == true) {
            getAssessList();
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
                    getAssessList();
                    return;
                }
            }
        })
    })
}



