/**
 * 考核查询详情
 */
var khid = '';
$(function () {
    var postData = GetRequest();
    khid = decodeURI(postData.id);
    getAssessDetail(khid);
})

function getAssessDetail(khid) {
    doPost('/assess/getAssessDetail',JSON.stringify({
        id:khid,
        pageNo:pageNo
    }),function (response) {
        if (response.success == true){
            detailPage(response.total);
            showDetail(response.data);
        }else {
            closeIframe();
        }
    })
}

/**
 * 详情展示
 */
function showDetail(data) {
    $('#pmxmmc').html(data[0].bt);
    $('#pmkssj').html(data[0].khsj);
    var debody = "";
    $("#debody").empty();
    $.each(data, function (n, value) {
        var str = "";
        var pm = (pageNo - 1) * 10 + n + 1;
        str += "<tr>" +
            "<td>" + pm + "</td>" +
            "<td>" + value.qyname + "</td>" +
            "<td>" + value.khdf + "</td>" +
            "<td onclick='details(this)' khdx='"+ value.khdx+"' style='cursor: pointer;color: blue;'>查看明细</td>" +
            "</tr>";
        debody += str;
    })
    $("#debody").append(debody);

}

function details(att) {
    window.location.href = 'assess_info.html?' +
        'khid=' + encodeURI(khid)+
        '&khdx=' + encodeURI($(att).attr('khdx'));
}


/**
 * 关闭iframe
 */
function closeIframe() {
    window.location.href = '../../backside/assessQuery.html';
}

/**
 *详情显示的分页
 */
var pageNo = 1;
var detailPage = function (count) {
    $('#detotal').html(count);
    $('#depageNo').html(pageNo + '/' + Math.ceil(count/100));
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
                    getAssessDetail(khid);
                    return;
                }
            }
        })
    })
}