/**
 * 前端页面新闻排名详情展示
 */
$(function () {
    getQYID();
    getNewsRankInfo();
})

/**
 * 获取新闻统计排名
 */
function getNewsRankInfo() {
    doPost('/dtgl/newsRankMore',{},function (response) {
        if (response.success == true){
            $('#area').html(response.data.SJQYNAME);
            $('#tbody').empty();
            var tbody = '';
            $.each(response.data,function (n,value) {
                tbody += "<tr>" +
                    "<td>"+ (n + 1) +"</td>" +
                    "<td>" + value.QYNAME + "</td>" +
                    "<td>" + value.TOTAL +"</td>" +
                    "</tr>";
            })
            $('#tbody').html(tbody);
        }else{
            console.log(response.data);
        }
    })
}

/**
 * 返回首页
 */
function goindex() {
    window.location.href = "../../index.html";
}

/**
 * 切换到基础数据页面
 */
function gobaseData() {
    window.location.href = "chart.html";
}

/**
 * 切换到不同的news
 * @param goal所切换的目标表示的内容
 */
function goreload(goal) {

    window.location.reload();
}

function AllShearch()
{
    var searchBt= encodeURI($("#shearchInput").val());
    window.location.href = "all_search_list.html?goal=" + $("#selectBox").val()+"&bt="+searchBt+"&op=all";

}
/**
 * 获取初始的区域ID,并拼接区域导航
 */
function getQYID() {
    doPost('/area/getAreaID',{},function (response) {
        qyid = response.data;
        getAreaName();
    })
}

/**
 * 根据区域所传的条件ID等于数据库中的QYID或SJQYID获取区域名和区域ID
 */
function getAreaName() {
    doPost('/area/getregion',qyid,function (response) {
        showChoiceArea(response.data);
    })
}

/**
 * 区域选择显示
 */
function showChoiceArea(area) {
    //先找到当前行政级别的下一级行政级别
    var xzjb = 0;
    for (var i = 0;i < area.length;i++){
        if (area[i].QYID === qyid){
            xzjb = area[i].XZJB + 1;
            $('#currentQyid').html(area[i].QYNAME);
            $('#currentBt').html(area[i].QYNAME);
        }else{}
    }
    //循环显示区域
    var tbody = "";
    $('#xjbody').empty();
    for (var i = 0; i < area.length;i++){
        if(area[i].XZJB === xzjb){
            tbody += "<li><a class='third-link2' onclick='choiceArea(this)' qyid='"+area[i].QYID+"'>"+area[i].QYNAME+"</a><ul class='fourth-menu'>";
            for (var j = 0; j < area.length;j++){
                if (area[i].QYID === area[j].SJQYID){
                    tbody += "<li><a onclick='choiceArea(this)' qyid='"+area[j].QYID+"'>"+area[j].QYNAME+"</a></li>";
                }
            }
            tbody += "</ul></li>";
        }
    }
    $('#xjbody').append(tbody);
    $('#sjbody').empty();
    var sjbody = "<li><a class='third-link2' onclick='choiceArea(this)' qyid='51'>四川省</a></li>";
    for (var i = 0; i < area.length;i++){
        if (area[i].QYID === qyid && area[i].XZJB === 3){
            for (var j = 0; j < area.length;j++){
                if (area[i].SJQYID === area[j].QYID){
                    sjbody += "<li><a class='third-link2' onclick='choiceArea(this)' qyid='"+area[j].QYID+"'>"+area[j].QYNAME+"</a></li>";
                    break;
                }
            }
            sjbody += "<li><a class='third-link2' onclick='choiceArea(this)' qyid='"+area[i].QYID+"'>"+area[i].QYNAME+"</a></li>";
            break;
        }else if(area[i].QYID === qyid && area[i].XZJB === 2){
            sjbody += "<li><a class='third-link2' onclick='choiceArea(this)' qyid='"+area[i].QYID+"'>"+area[i].QYNAME+"</a></li>";
            break;
        }
    }
    $('#sjbody').html(sjbody);
    test();
}

/**
 * 选择页面的区域时，绑定qyid的值
 */
function choiceArea(att) {
    qyid = $(att).attr("qyid");
    getAreaName();
}

/**
 * 跳转到后台
 */
function gobackside() {
    window.open('../backside/login.html');
}

