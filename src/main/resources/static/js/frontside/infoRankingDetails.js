/**
 * 前端页面工作排名详情展示
 */
var id ='';
var pmmc = '';
var khsj = '';
$(function () {
    var postData = GetRequest();
    id = decodeURI(postData.id);
    pmmc = decodeURI(postData.bt);
    khsj = decodeURI(postData.khsj);
    $('#projectTitle').html(pmmc);
    $('#pmkssj').html(khsj);
    getQYID();
})

/**
 * 获取工作排名信息
 */
function getJobRankInfo() {
    doGet('/assess/getAssessComplexMessage',{id: id}, function (response) {
        if (response.success == true){
            $("#gzzb").html(response.data.gzpmzb);
            $("#ngzb").html(response.data.ngkhzb);
            getAssessDetail(id);
        }else {
            console.log(response.data);
        }
    })
}

function getAssessDetail(id) {
    doPost('/assess/getAssessComplexDetail', JSON.stringify({
        id: id,
        pageNo: pageNo
    }), function (response) {
        if (response.success == true) {
            showJobRank(response.data);
        } else {
            closeIframe();
        }
    })
}
/**
 *详情显示的分页
 */
var pageNo = 1;
var detailPage = function (count) {
    //$('#detotal').html(count);
    //$('#depageNo').html(pageNo + '/' + Math.ceil(count/10));
    layui.use('laypage', function () {
        var laypage = layui.laypage;
        laypage.render({
            elem: 'depage',
            count: count,
            limit: 15,
            curr: pageNo,
            jump: function (obj, first) {
                if (!first) {
                    pageNo = obj.curr;
                    getJobRankInfo();
                    return;
                }
            }
        })
    })
}

function showJobRank(job) {
    $('#tbody').empty();
    var tbody = '';
    $.each(job,function (n,value) {
        var str = "";
        var pm = (pageNo - 1) * 10 + n + 1;
        str += "<tr>" +
            "<td>" + pm + "</td>" +
            "<td>" + value.qyname + "</td>" +
            "<td>" + value.gzpmdf + "</td>" +
            "<td>" + value.ngkhdf + "</td>" +
            "<td>" + (parseInt(value.gzpmdf) + parseInt(value.ngkhdf)) + "</td>" +
            "</tr>";
        tbody += str;
    })
    $('#tbody').html(tbody);
}

/**
 * 返回首页
 */
function goindex() {
    window.location.href = "../../index.html";
}

function gogzpm() {
    window.location.href = "info_Ranking.html";
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
    if (goal === undefined || goal === null){
        goal = currentGoal;
    }else{}
    window.location.href = "list.html?goal=" + goal;
}


/**
 * 获取初始的区域ID,并拼接区域导航
 */
function getQYID() {
    doPost('/area/getAreaID',{},function (response) {
        qyid = response.data;
        getAreaName();

        getJobRankInfo();
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

