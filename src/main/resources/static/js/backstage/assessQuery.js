/**
 *  考核查询
 */
/**
 * 入口函数
 */
$(function () {
    timedate();
    justPression();
    // 设置默认时间为今天
    // var myDate = new Date();
    // var valKssj = myDate.getFullYear() + '-' + (myDate.getMonth()+1) + '-01';
    // var valJssj = myDate.getFullYear() + '-' + (myDate.getMonth()+1) + '-' + myDate.getDate();
    // $('#kssj').val(valKssj);
    // $('#jssj').val(valJssj);

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
    if (justPressionBy("321")) {
        pageNo = 1;
        getAssessList();
    } else {
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("该用户暂无此权限！", {icon: 5});
        });
    }
}
function addkhpz() {
    if (justPressionBy("325")) {
        window.location.href = 'assess_config.html';
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
    var kssj = $('#kssj').val();
    var jssj = $('#jssj').val();
    // if (kssj == undefined || kssj == '' || jssj == undefined || jssj == '') {
    //     layui.use('layer', function() {
    //         var layer = layui.layer;
    //         layer.msg("请选择完整考核期间", {icon: 5});
    //     })
    //     return;
    // }
    if (kssj > jssj){
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("开始时间不能大于结束时间", {icon: 5});
        });
        return;
    }
    doPost('/assess/getAssessList', JSON.stringify({
        khlx: $('#khlx').val(),
        kssj:kssj,
        jssj:jssj,
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
    var change1 = justPressionBy("322");
    var changeSc = justPressionBy("324");
    $.each(list, function (n, value) {
        var xh = (pageNo - 1) * 10 + n + 1;
        var str = "";
        var xqHtml= "<a  href=\"#\" class=\"tablelink\" onclick='queryDetails(this)' id='"+value.id+"'>详情</a>";
        var scHtml= "<a  href=\"#\" class=\"tablelink\" onclick='deleteDetails(this)' id='"+value.id+"' bt='"+value.bt+"'>删除</a>";
        var html = "";
        if (change1) {
            html += xqHtml;
        }
        if (changeSc) {
            html += scHtml;
        }
        str += "<tr>" +
            "<td style=\"width: 10%; text-align: center\">" + xh + "</td>" +
            "<td style=\"width: 30%\">" + nullData(value.bt) + "</td>" +
            "<td style=\"width: 20%\">" + nullData(value.khsj) + "</td>" +
            "<td style=\"width: 20%\">" + value.dmmc + "</td>" +
            "<td style=\"width: 15%\">" + html + "</td>" +
            "</tr>";
        tbody += str;
    })
    $("#tbody").append(tbody);
    $('.tablelist tbody tr:odd').addClass('odd');//给偶数行添加背景
}

function queryDetails(att) {
    window.location.href = 'assess_sore.html?' +
        'id=' + encodeURI($(att).attr('id'));
}
/**
 * 删除询问层
 */
function deleteDetails(att) {
    layui.use('layer',function () {
        var layer = layui.layer;
        layer.confirm('是否确认删除该考核信息: ' + $(att).attr('bt'),{
            btn:['确认','取消']
        },function () {
            doPost('/assess/getAssessdeleted', $(att).attr('id'), function (response) {
                if (response.success == true) {
                    layui.use('layer', function() {
                        var layer = layui.layer;
                        layer.msg("删除成功", {icon: 6});
                    })
                    getAssessList();
                } else {
                    layui.use('layer', function() {
                        var layer = layui.layer;
                        layer.msg("删除失败", {icon: 5});
                    })
                }
            })
        },function (index) {
            layer.close(index);
        })
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

