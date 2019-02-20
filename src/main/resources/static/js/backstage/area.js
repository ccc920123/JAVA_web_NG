/**
 * 入口函数
 */

$(function () {
    justPression();
    if(justPressionBy("101")){
        getArea();
    } else {
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("该用户暂无此权限！", {icon: 5});
        });
    }
})

/**
 * 获取区域信息
 */
function getArea() {
    doPost('/area/getAreas', JSON.stringify({
        condition: $('#condition').val().replace(/(\s*$)/g, ""),
        pageNo: pageNo
    }), function (response) {
        console.log(response);
        page(response.total);
        showArea(response.data);
    })
}

/**
 * 条件搜索按钮函数
 */
function search() {
    pageNo = 1;
    getArea();
}
/**
 * 点击修改按钮获取修改权限
 */
function areaAlter(att) {
    doPost('/area/alterAuthority', $(att).attr('sjqyid'), function (response) {
        if (response.success == true) {

            window.location.href = 'region_edit.html?' +
                'flag=up&qyid=' + encodeURI($(att).attr('qyid'));
        } else {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg(response.data, {icon: 5});
            });
        }
    })
}
/**
 * 展示区域信息   href="javascript:void(0);"
 */
function showArea(areas) {
    var tbody = "";
    $("#tbody").empty();
   var changepression= justPressionBy("102");
    $.each(areas, function (n, value) {
        var str = "";
        var changeHtml= "<td><a href=\"#\" class=\"tablelink\"  onclick='areaAlter(this);return false' qyid='" + value.QYID + "' sjqyid='" + value.SJQYID + "'>修改</a>" +
            "<a  href=\"#\" class=\"tablelink\" onclick='deleteAsk(this)' id='"+value.QYID+"' qyname='"+value.QYNAME+"'>删除</a></td>";
        if (!changepression){
            changeHtml="<td></td>";
        }
        str += "<tr>" +
            "<td>" + value.QYNAME + "</td>" +
            "<td>" + nullData(value.QYSP) + "</td>" +
            "<td>" + nullData(value.FJQYNAME) + "</td>" +
            "<td>" + nullData(value.FJQYSP) + "</td>" +changeHtml +
            "</tr>";
        tbody += str;
    })
    $("#tbody").append(tbody);
    $('.tablelist tbody tr:odd').addClass('odd');//给偶数行添加背景
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
                    getArea();
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
    layui.use('layer',function () {
        var layer = layui.layer;
        layer.confirm('是否确认删除该区域信息: ' + $(att).attr('qyname'),{
            btn:['确认','取消']
        },function () {
            var qyid = $(att).attr('id');
            sureDelete(qyid);
        },function (index) {
            layer.close(index);
        })
    })
}
function sureDelete(qyid) {
    doPost('/area/deleteAreas',qyid,function (response) {
        if(response.success == true){
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.msg('信息删除成功', {icon: 6});
                getArea();
            })
        }else{
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.msg('信息删除失败', {icon: 5});
            })
        }
    })
}


