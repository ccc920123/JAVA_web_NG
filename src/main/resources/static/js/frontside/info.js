/**
 * 详情展示
 */
var currentGoal = "";
$(function () {
    var postData = GetRequest();
    var id = postData.dtid;//获取展示详情的动态id
    getNewsInfoById(id);
    currentGoal = postData.goal;
    switch (postData.goal) {
        case 'picture': $('#goal').html('图片新闻');break;
        case 'dynamic': $('#goal').html('工作动态');break;
        case 'inform': $('#goal').html('通知通报');break;
        case 'laws': $('#goal').html('法律法规');break;
        case 'publicitiy': $('#goal').html('宣传警示');break;
        case 'feature': $('#goal').html('区域特色');break;
        default:break;
    }
   getQYID();
})

/**
 * 根据ID获取动态信息
 */
function getNewsInfoById(id) {
   doPost('/dtgl/getNewsInfo',id,function (response) {
       console.log(response.data);
       $('#bt').html(response.data.bt);
       $('#fbsj').html(response.data.fbsj);
       $('#ngr').html(response.data.ngr);
        //$('#dtnr')[0].innerHTML= '<object type=\'text/html\' data=\''+response.data.dtnr+'\' width=\'100%\' height=\'100%\'></object>';
        $('#dtnr')[0].innerHTML= response.data.dtnr;
       uParse('#dtnr', {
           rootPath: '../../plugs/'
       })
   })
}
/**
 * 登录系统后台管理函数
 */
function loginHt() {
    window.open('../backside/login.html');
}
/**
 * 跳转到后台
 */
function gobackside() {
    window.open('../backside/login.html');
}
function AllShearch()
{
    var searchBt= encodeURI($("#shearchInput").val());
    window.location.href = "all_search_list.html?goal=" + $("#selectBox").val()+"&bt="+searchBt+"&op=all";

}
/**
 * 切换到不同的news
 * @param goal所切换的目标表示的内容
 */
function goto(goal) {
    if (goal === undefined || goal === null){
        goal = currentGoal;
    }else{}
    window.location.href = "list.html?goal=" + goal;
}

/**
 * 切换到基础数据页面
 */
function gobaseData() {
    window.location.href = "chart.html";
}

/**
 * 返回首页
 */
function goindex() {
    window.location.href = "../../index.html";
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

    getAreaNameAndToList();
}
function getAreaNameAndToList() {
    doPost('/area/getregion',qyid,function (response) {
        switch (currentGoal) {
            case 'picture': goto('picture');break;
            case 'dynamic': goto('dynamic');break;
            case 'inform': goto('inform');break;
            case 'laws': goto('laws');break;
            case 'publicitiy': goto('publicitiy');break;
            case 'feature': $('#goal').html('区域特色');break;
            default:break;
        }
    })
}
