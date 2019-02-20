/**
 * 入口函数
 */
$(function () {
    justPression();
    if (justPressionBy("201")) {
        getDtInfo();
    } else {
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("该用户暂无此权限！", {icon: 5});
        });
    }
})

function getDtInfoList() {
    doPost('/dtgl/getDtgl', JSON.stringify({
        condition: $('#condition').val().replace(/(\s*$)/g, ""),
        pageNo: pageNo
    }), function (response) {
        page(response.total);
        showDtInfo(response.data);
    })
}

/**
 * 获取动态信息
 */
function getDtInfo() {
    pageNo = 1;
    getDtInfoList();
}

/**
 * 展示动态信息
 */
function showDtInfo(info) {
    var tbody = "";
    $("#tbody").empty();
    var change1 = justPressionBy("202");
    var change2 = justPressionBy("203");
    var xzjb = getUserXzjbStr();

    $.each(info, function (n, value) {
        var tit = "修改";
        var op = 'change';
        var str = "";
        var shzt = "";
        var tszt = "";
        if (value.SHZT == 0) {
            shzt = "未审核";
        } else if (value.SHZT == 1) {
            shzt = "审核通过";
        } else if (value.SHZT == 2) {
            shzt = "审核未通过"
        } else {
            shzt = "--";
        }
        if (value.TSZT == 0) {
            tszt = "未审核";
        } else if (value.TSZT == 1) {
            tszt = "审核通过";
        } else if (value.TSZT == 2) {
            tszt = "审核未通过"
        } else {
            tszt = "--";
        }
        if (value.TSZT == 1 && value.SHZT == 1) {
            tit = "查看";
            op = 'look';
        }
        var change1H = "<a href=\"#\" class=\"tablelink\" onclick=\"window.location.href = 'AddNews.html?ly=info&op=" + op + "&dtid=" + value.ID + "'; return false\">" + tit + "</a>";
        var change2H = "<a href=\"#\" class=\"tablelink\" onclick='deleteAsk(this);return false' dynamicId='" + value.ID + "'>删除</a>"
        var html = "";
        if (change1) {
            html += change1H;
        }
        if (change2) {
            html += change2H;
        }
        /*<option value="02">区域特色</option>
                            <option value="03" id="laws">法律法规</option>
                            <option value="04">工作动态</option>
                            <option value="05">图片新闻</option>
                            <option value="06">通知通报</option>
                            <option value="07">宣传警示</option>*/
        var fbwz = "";
        switch (value.FBWZ) {
            case '02':
                fbwz = "区域特色";
                break;
            case '03':
                fbwz = "法律法规";
                break;
            case '04':
                fbwz = "工作动态";
                break;
            case '05':
                fbwz = "图片新闻";
                break;
            case '06':
                fbwz = "通知通报";
                break;
            case '07':
                fbwz = "宣传警示";
                break;
        }
        str += "<tr>" +
            "<td>" + value.QYNAME + "</td>" +
            "<td>" + fbwz + "</td>" +
            "<td>" + value.BT + "</td>" +
            "<td>" + value.NGR + "</td>" +
            // "<td>" + getFormateDate(value.FBSJ) + "</td>" +
            "<td>" + value.FBSJ + "</td>" +
            "<td>" + tszt + "</td>" +
            "<td>" + shzt + "</td>" +
            " <td>" + html +
            "</td>" +
            "</tr>";
        tbody += str;
    })
    $("#tbody").append(tbody);
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
                    getDtInfoList();
                    return;
                }
            }
        })
    })
}

/**
 * 删除询问层
 */
function deleteAsk(att) {
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.confirm('是否删除该动态信息', {
            btn: ['确认', '取消']
        }, function () {
            var dynamicId = $(att).attr('dynamicId');
            sureDelete(dynamicId);
        }, function (index) {
            layer.close(index);
        })
    })
}

/**
 * 确认删除动态信息
 */
function sureDelete(id) {
    doPost('/dtgl/deleteDtgl', id, function (response) {
        console.log(response);
        if (response.success == true) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('删除动态信息成功', {icon: 6});
                getDtInfoList();
            })
        } else {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('删除动态信息失败', {icon: 5});
            })
        }
    })
}
