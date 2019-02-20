/**
 *新闻列表展示
 */
var target = '';
var qyid = '';
var bt = '';
var op='';
var pageSize = 10;
var pageNo = 1;
$(function () {
    console.log("picter");
    getDataFromIndex();
    getNewsFromDatabase();
    getQYID();
})

/**
 * 获取初始的区域ID,并拼接区域导航
 */
function getQYID() {
    doPost('/area/getAreaID', {}, function (response) {
        qyid = response.data;
        getAreaName();
    })
}

/**
 * 根据区域所传的条件ID等于数据库中的QYID或SJQYID获取区域名和区域ID
 */
function getAreaName() {
    doPost('/area/getregion', qyid, function (response) {
        showChoiceArea(response.data);
        getNewsFromDatabase();
    })
}

/**
 * 获取搜索数据
 */
function getNewsFromDatabase() {
    var fbwz = "";
    switch (target) {
        case 'picture':
            fbwz = '05';
            $('#goal').html('图片新闻');
            $('#aim').html('图片新闻');
            break;
        case 'dynamic':
            fbwz = '04';
            $('#goal').html('工作动态');
            $('#aim').html('工作动态');
            break;
        case 'inform':
            fbwz = '06';
            $('#goal').html('通知通报');
            $('#aim').html('通知通报');
            break;
        case 'laws':
            fbwz = '03';
            $('#goal').html('法律法规');
            $('#aim').html('法律法规');
            break;
        case 'publicitiy':
            fbwz = '07';
            $('#goal').html('宣传警示');
            $('#aim').html('宣传警示');
            break;
        //全站搜索
        case 'alls':
            fbwz = '00';
            $('#goal').html('全部数据');
            $('#aim').html('全部数据');
            break;

        default:
            fbwz = '00';
            $('#goal').html('全部数据');
            $('#aim').html('全部数据');
            break;
    }

    //判断是否通过全局搜索。
    if(op=="all") {
        doGet("/allshearch/getAll", {
            fbwz: fbwz,
            bt: bt,
            pageNo: pageNo,
            pageSize: pageSize
        }, function (res) {

            page(res.total);
            if (res.total > 0) {
                newsShow(res.data);
            }
        })
    }else
    {
        doPost('/dtgl/getNews',JSON.stringify({
            fbwz:fbwz,
            pageNo:pageNo
        }),function (response) {
            page(response.total);
            if (response.total>0){
                newsShowC(response.data);
            }else{
                $('#tbody').empty();
            }

        })
    }
}
function AllShearch()
{
    var wz = "";
    switch ($("#selectBox").val()) {
        case 'picture':
            wz = '05';
            $('#goal').html('图片新闻');
            $('#aim').html('图片新闻');
            break;
        case 'dynamic':
            wz = '04';
            $('#goal').html('工作动态');
            $('#aim').html('工作动态');
            break;
        case 'inform':
            wz = '06';
            $('#goal').html('通知通报');
            $('#aim').html('通知通报');
            break;
        case 'laws':
            wz = '03';
            $('#goal').html('法律法规');
            $('#aim').html('法律法规');
            break;
        case 'publicitiy':
            wz = '07';
            $('#goal').html('宣传警示');
            $('#aim').html('宣传警示');
            break;
        //全站搜索
        case 'allsearch':
            wz = '00';
            $('#goal').html('全部数据');
            $('#aim').html('全部数据');
            break;

        default:
            break;
    }
    doGet("/allshearch/getAll", {
        fbwz: wz,
        bt: $("#shearchInput").val(),
        pageNo: 1,
        pageSize: 10
    }, function (res) {

        page(res.total);
        if (res.total > 0) {
            newsShow(res.data);
        }
    })
}

/**
 * 新闻信息列表展示
 */
function newsShow(news) {
    var tbody = '';
    $('#tbody').empty();
    $.each(news, function (n, value) {

        tbody += "<li><a href='#' goal='"+value.fbwz+"' onclick='goInfo(this)' dtid='" + value.id + "'>" + value.bt + "</a><span>" + value.fbsj + "</span></li>";
    })
    $('#tbody').html(tbody);
}
function newsShowC(news) {
    var tbody = '';
    $('#tbody').empty();
    $.each(news, function (n, value) {
        tbody += "<li><a href='#' onclick='goInfo(this)' dtid='" + value.ID + "'>" + value.BT + "</a><span>" + value.FBSJ + "</span></li>";
    })
    $('#tbody').html(tbody);
}
/**
 * 切换到动态信息详情展示界面
 */
function goInfo(att) {
    if (target==undefined||target=="alls"){
        var gol=$(att).attr('goal');
        switch (gol) {
            case "02":
                target="feature";
                break;
            case "03":
                target="laws";
                break;
            case "04":
                target="dynamic";
                break;
            case "05":
                target="picture";
                break;
            case "06":
                target="inform";
                break;
            case "07":
                target="publicitiy";
                break;

        }
    }
    window.location.href = 'info.html?dtid=' + $(att).attr('dtid') + '&goal=' + target;
}

/**
 *分页
 */



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
                    getNewsFromDatabase();
                    return;
                }
            }
        })
    })
}

/**
 * 接收从页面传过来的数据
 */
function getDataFromIndex() {
    var postData = GetRequest();
    target = postData.goal;
    op=postData.op;
    bt = decodeURI(postData.bt);


}

/**
 * 跳转到后台
 */
function gobackside() {
    window.open('../backside/login.html');
}

/**
 * 切换到不同的news
 * @param goal所切换的目标表示的内容
 */
function goreload(goal) {
    target = goal;
    op="";
    getNewsFromDatabase();
}

/**
 * 切换到基础数据页面
 */
function gobaseData() {
    op="";
    window.location.href = "chart.html";
}

/**
 * 返回首页
 */
function goindex() {
    window.location.href = "../../index.html";
}


/**
 * 区域选择显示
 */
function showChoiceArea(area) {
    //先找到当前行政级别的下一级行政级别
    var xzjb = 0;
    for (var i = 0; i < area.length; i++) {
        if (area[i].QYID === qyid) {
            xzjb = area[i].XZJB + 1;
            $('#currentQyid').html(area[i].QYNAME);
            $('#currentBt').html(area[i].QYNAME);
        } else {
        }
    }
    //循环显示区域
    var tbody = "";
    $('#xjbody').empty();
    for (var i = 0; i < area.length; i++) {
        if (area[i].XZJB === xzjb) {
            tbody += "<li><a class='third-link2' onclick='choiceArea(this)' qyid='" + area[i].QYID + "'>" + area[i].QYNAME + "</a><ul class='fourth-menu'>";
            for (var j = 0; j < area.length; j++) {
                if (area[i].QYID === area[j].SJQYID) {
                    tbody += "<li><a onclick='choiceArea(this)' qyid='" + area[j].QYID + "'>" + area[j].QYNAME + "</a></li>";
                }
            }
            tbody += "</ul></li>";
        }
    }
    $('#xjbody').append(tbody);
    $('#sjbody').empty();
    var sjbody = "<li><a class='third-link2' onclick='choiceArea(this)' qyid='51'>四川省</a></li>";
    for (var i = 0; i < area.length; i++) {
        if (area[i].QYID === qyid && area[i].XZJB === 3) {
            for (var j = 0; j < area.length; j++) {
                if (area[i].SJQYID === area[j].QYID) {
                    sjbody += "<li><a class='third-link2' onclick='choiceArea(this)' qyid='" + area[j].QYID + "'>" + area[j].QYNAME + "</a></li>";
                    break;
                }
            }
            sjbody += "<li><a class='third-link2' onclick='choiceArea(this)' qyid='" + area[i].QYID + "'>" + area[i].QYNAME + "</a></li>";
            break;
        } else if (area[i].QYID === qyid && area[i].XZJB === 2) {
            sjbody += "<li><a class='third-link2' onclick='choiceArea(this)' qyid='" + area[i].QYID + "'>" + area[i].QYNAME + "</a></li>";
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
