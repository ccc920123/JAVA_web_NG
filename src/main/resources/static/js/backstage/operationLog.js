/**
 * 入口函数
 */
var layer;
$(function(){
    timedate();
    justPression();
    layui.use('layer', function() {
         layer = layui.layer;

    });
    if (justPressionBy("161")) {
        operationLogList();
    } else {
        layer.msg("该用户暂无此权限！", {icon: 5});
    }

});



/**
 * 获取排名统计信息
 */
function operationLogList() {
    doPost('/operationLog/getList',JSON.stringify({
        kssj:$('#kssj').val(),
        jssj:$('#jssj').val(),
        pageNo: pageNo,
        content:$('#condition').val()
    }),function (response) {
        var tbody = "";

        if (response.success == true){
            page(response.total);
            $.each(response.data,function (n,value) {
                var change2H = "<a href=\"#\" class=\"tablelink\" onclick='lookDetails(this);return false' op_id='" + value.id + "'>查看</a>"
                tbody += "<tr>" +
                    "<td>"+ value.rn +"</td>" +
                    "<td>" + value.opUrl + "</td>" +
                    "<td>" + value.realname +"</td>" +
                    "<td>" + value.opTime +"</td>" +
                    "<td>" +change2H +"</td>" +
                    "</tr>";
            })
        }else {

        }
        $('#tbody').html(tbody);

    })
}

/**
 * 搜索函数
 */
function search() {
    pageNo = 1;
    operationLogList();
}
function lookDetails(span) {
    var id=$(span).attr("op_id");
    layer.open({
        type: 2,
        shade: [0.5, '#000', false],
        area: ['900px', '400px'],
        title: "日志详情",
        content: '/ncweb/static/pages/backside/operationDetail.html?id=' + id,
        success: function (layero, index) {
            var body = layui.layer.getChildFrame('body', index);

        },
        cancel: function () {
        }

    })
}
function timedate() {
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#kssj' //指定元素
        });
        //执行一个laydate实例
        laydate.render({
            elem: '#jssj' //指定元素
        });
    });
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
                    operationLogList();
                    return;
                }
            }
        })
    })
}
