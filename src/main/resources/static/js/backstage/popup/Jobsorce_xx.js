
/**
 * 考核查询详情
 */
$(function () {
    var postData = GetRequest();
    var pmid = decodeURI(postData.pmid);
    var khqyid = decodeURI(postData.khqyid);
    getJobSorceDetail(pmid, khqyid);
})

function getJobSorceDetail(pmid, khqyid) {
    doGet('/job/getXXSorceTable', {
        pmid: pmid,
        khqyid: khqyid
    }, function (response) {
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
    // console.log(data);
    $("#xzqh").html(data.detail.qyname);
    $("#pmdf").html(data.detail.pmdf);
    $("#pmsj").html(data.detail.pmsj);
    $("#pmmc").html(data.detail.pmmc);
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



/**
 * 关闭iframe
 */
function closeIframe() {
    window.location.href = './Job_ranking_info.html';
}