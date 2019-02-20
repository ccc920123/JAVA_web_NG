/**
 * 入口函数
 */
$(function () {
    justPression();
    if (justPressionBy("152")) {
        getLinkData();
    } else {
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("该用户暂无此权限！", {icon: 5});
        });
    }
})
var pageSize = 10;
var pageNo=1;
/**
 * 获取链接数据列表
 */
function getLinkData() {


    doGet("/link/getLink",{
        isWeb:false,
        pageNo: pageNo,
        pageSize: pageSize
    },function (response) {
        //展示分页
        page(response.total)
        //开始将数据绑定到界面
        showLink(response.data)
    })

}
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
                    getLinkData();
                    return;
                }
            }
        })
    })
}
function showLink(data) {
    var tbody = "";
    $("#tbody").empty();
    var change1 = justPressionBy("150");
    var change2 = justPressionBy("151");
    $.each(data, function (n, value) {
        var str = "";
        var change1H = "<a href=\"javascript:;\" class=\"tablelink\" onclick=\"window.location.href ='Add_Friendshiplink.html?op=edit&id=" + value.id + " '\">修改</a>";
        var change2H = "<a href=\"javascript:;\" class=\"tablelink\" onclick='deleteRole(this);return false;' id='" + value.id + "'> 删除</a>";
        var html = "";
        if (change1) {
            html += change1H;
        }
        if (change2) {
            html += change2H;
        }
        str += "<tr>" +
            "<td>" + value.bt + "</td>" +
            "<td>" + value.lj + "</td>" +
            "<td>" +
            html +
            "</td></tr>";
        tbody += str;
    });
    $("#tbody").append(tbody);


}

function deleteRole(span) {
    var id = $(span).attr("id");
    //询问框
    layui.use('layer',function () {
        var index = layer.confirm('确定删除当前友情链接？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            doPost("/link/deleteLink",JSON.stringify({id: id}), function (response) {
                if (response.success) {
                    getLinkData();
                    layer.msg("删除成功", {icon: 6});
                    layer.close(index);
                } else {
                    layer.msg(response.data, {icon: 5});
                }
            })
        }, function () {
            layer.close(index);
        });
    });
}