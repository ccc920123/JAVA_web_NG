/**
 * 考核查询详情
 */
var bt = '';
var khid = '';
$(function () {
    var postData = GetRequest();
    khid = decodeURI(postData.id);
    getAssessMessage(khid);

})

function getAssessMessage(khid) {
    doGet('/assess/getAssessComplexMessage', {id: khid}, function (response) {
        if (response.success == true) {
            $("#gzzb").html(response.data.gzpmzb);
            $("#ngzb").html(response.data.ngkhzb);
            bt = decodeURI(response.data.bt);
            $('#pmxmmc').html(bt);
            getAssessDetail(khid);
        }
    });
}

function getAssessDetail(khid) {
    doPost('/assess/getAssessComplexDetail', JSON.stringify({
        id: khid,
        pageNo: pageNo
    }), function (response) {
        if (response.success == true) {
            detailPage(response.total);
            showDetail(response.data);
        } else {
            closeIframe();
        }
    })
}

/**
 * 详情展示
 */
function showDetail(data) {
    var debody = "";
    $("#debody").empty();
    $.each(data, function (n, value) {
        var str = "";
        var pm = (pageNo - 1) * 10 + n + 1;
        var gzpmHtml = "<a href=\"#\" class=\"tablelink\" onclick='JobRankDetail(this);return false' pid='" + value.gzpmid + "' khqy='" + value.khdx + "'>" + value.gzpmdf + "</a>"
        var khdfHtml = "<a href=\"#\" class=\"tablelink\" onclick='AssessDetail(this);return false' pid='" + value.ngkhid + "' khqy='" + value.khdx + "'> " + value.ngkhdf + "</a>";
        str += "<tr>" +
            "<td>" + pm + "</td>" +
            "<td>" + value.qyname + "</td>" +
            "<td>" + gzpmHtml + "</td>" +
            "<td>" + khdfHtml + "</td>" +
            "<td>" + (parseInt(value.gzpmdf) + parseInt(value.ngkhdf)) + "</td>" +
            "</tr>";
        debody += str;
    })
    $("#debody").append(debody);

}

function JobRankDetail(span) {
    var gzpmid = $(span).attr("pid");
    var khqy = $(span).attr("khqy");
    window.location.href = 'Job_ranking_info.html?' +
        'id=' + encodeURI(gzpmid) +
        '&khqy=' + encodeURI(khqy);
}

function AssessDetail(span) {
    var ngkhid = $(span).attr("pid");
    var khqy = $(span).attr("khqy");
    window.location.href = 'assess_info.html?' +
        'khid=' + encodeURI(ngkhid) +
        '&khdx=' + encodeURI(khqy);
}

/**
 * 关闭iframe
 */
function closeIframe() {
    window.location.href = '../../backside/assess_complex.html';
}

/**
 *详情显示的分页
 */
var pageNo = 1;
var detailPage = function (count) {
    $('#detotal').html(count);
    $('#depageNo').html(pageNo + '/' + Math.ceil(count / 100));
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