/**
 * 入口函数
 */
var xzjb = 0;
$(function () {
    justPression();
    xzjb = getUserXzjbStr();
    if (justPressionBy("211")) {
        if (xzjb == 4){
            $('#total').html(0);
            $('#pageNo').html(pageNo + '/' + Math.ceil(0/10));
            return;
        }
        getDtInfoList();
    } else {
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("该用户暂无此权限！", {icon: 5});
        });
    }


})

function getDtInfoList() {
    doPost('/dtgl/getDtAudit',JSON.stringify({
        areacon:$('#areacon').val().replace(/(\s*$)/g, ""),
        btcon:$('#btcon').val().replace(/(\s*$)/g, ""),
        shzt:$('#audit').val(),
        pageNo:pageNo
    }),function (response) {
        page(response.total);
        showDtInfo(response.data);
    })
}

/**
 * 获取动态信息
 */
function getDtInfo() {
    if (xzjb == 4){
        return;
    }
    pageNo = 1;
    getDtInfoList();
}

/**
 * 展示审核动态信息
 */
function showDtInfo(info) {
    var tbody = "";
    var op = 'change';
    $("#tbody").empty();
    var change1 = justPressionBy("212");
    var change2 = justPressionBy("213");
    var change3 = justPressionBy("214");
    $.each(info, function (n, value) {
        var tit = "审核";
        var str = "";
        var shzt = "";
        var tszt = "";
        if (value.SHZT == 0){
            shzt = "未审核";
        }else if (value.SHZT == 1){
            shzt = "审核通过";
        }else if(value.SHZT == 2){
            shzt = "审核未通过"
        } else {
            shzt = "--";
        }
        if (value.TSZT == 0){
            tszt = "未审核";
        }else if (value.TSZT == 1){
            tszt = "审核通过";
        }else if(value.TSZT == 2){
            tszt = "审核未通过"
        } else {
            tszt = "--";
        }
        if (value.TSZT == 1 && value.SHZT == 1) {
            tit = "查看";
            op = 'look';
        }
        var change1H =  "<a  href=\"#\" class=\"tablelink\" onclick='auditDt(this); return false' id='"+value.ID+"' op='"+op+"'>" + tit + "</a>"
        /* var change2H = "<a href=\"#\" class=\"tablelink\" onclick='sureDelete(this)' account='" + value.ACCOUNT + "'> 删除</a>"*/
        var change3H = "<a  href=\"#\" class=\"tablelink\" onclick='deleteAsk(this); return false' id='"+value.ID+"'>删除</a>"
        var html = "";
        if (change1) {
            html += change1H;
        }
        // if (change2) {
        //     html += change2H;
        // }
        if (change3) {
            html += change3H;
        }
        var fbwz = "区域特色";
        switch (value.FBWZ) {
            case '02':

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
            "<td>" + value.FBSJ + "</td>" +
            "<td>" + tszt + "</td>" +
            "<td>" + shzt + "</td>" +
            "<td >" +
            html +
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
    $('#pageNo').html(pageNo + '/' + Math.ceil(count/10));
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
 * 审核动态信息
 */
function auditDt(att) {
    window.location.href = 'AddNews.html?op=' + $(att).attr('op') + '&dtid='+$(att).attr('id') + '&ly=audit';
}

/**
 * 删除询问层
 */
function deleteAsk(att) {
    layui.use('layer',function () {
        var layer = layui.layer;
        layer.confirm('是否确认删除该动态信息',{
            btn:['确认','取消']
        },function () {
            var shid = $(att).attr('id');
            sureDelete(shid);
        },function (index) {
            layer.close(index);
        })
    })
}

/**
 * 删除动态信息
 */
/*function deleteDt(att) {
  shid = $(att).attr('id');
    document.getElementById('deleteMyDiv').style.display = 'block';
    document.getElementById('deletefade').style.display = 'block';
    var bgdiv = document.getElementById('deletefade');
    bgdiv.style.width = document.body.scrollWidth;
    $("#deletefade").height($(document).height());
}*/
function sureDelete(shid) {
    doPost('/dtgl/deleteDtgl',shid,function (response) {
        if(response.success == true){
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.msg('信息删除成功', {icon: 6});
                getDtInfoList();
            })
        }else{
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.msg('信息删除失败', {icon: 5});
            })
        }
    })
}