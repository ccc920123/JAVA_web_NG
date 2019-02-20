/**
 * 考核查询详情
 */
$(function () {
    var postData = GetRequest();
    var khid = decodeURI(postData.khid);
    var khdx = decodeURI(postData.khdx);
    getAssessDetail(khid, khdx);
})

function getAssessDetail(khid, khdx) {
    doPost('/assess/getAssessInfo', JSON.stringify({
        khid: khid,
        khdx: khdx
    }), function (response) {
        if (response.success == true){
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
    console.log(data);
    $("#xzqh").html(data.gyxx[0].qyname);
    $("#khdf").html(data.gyxx[0].khdf);
    $("#khqj").html(data.gyxx[0].khsj);
    $("#khlx").html(data.gyxx[0].khlx);
    $("#debody").empty();
    var flag = '';
    var str = "";
    $.each(data.fxxx, function (n, value) {
        if (flag != value.dx) {
            flag = value.dx;
            str += "<tr>\n" +
                "<td rowspan='" + value.total + "' align=\"middle\" valign='middle'>"+ value.dmmc +"</td>\n" +
                "<td >"+ value.xxmc +"</td>\n" +
                "<td >"+ nullData(value.dxdf) +"</td>\n" +
                "<td >"+ nullData(value.mxjg) +"</td>\n" +
                "</tr>\n";
        } else {
            str += "<tr>" +
                "<td >"+ value.xxmc +"</td>\n" +
                "<td >"+ nullData(value.dxdf) +"</td>\n" +
                "<td >"+ nullData(value.mxjg) +"</td>\n" +
                "</tr>";
        }
    })
    $("#debody").append(str);

}

function details(att) {
    window.location.href = 'assess_info.html?' +
        'id=' + encodeURI($(att).attr('id'));
}


/**
 * 关闭iframe
 */
function closeIframe() {
    window.location.href = './assess_sore.html';
}
