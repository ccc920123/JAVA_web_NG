/**
 *新闻列表展示
 */
var target = '';
var qyid = '';
$(function () {
    getDataFromIndex();
    getNewsFromDatabase();
    getQYID();
})
/**
 * 登录系统后台管理函数
 */
function loginHt() {
    window.open('../backside/login.html');
}
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
 * 从后台获取新闻信息
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
        default:
            break;
    }
    doPost('/dtgl/getNews', JSON.stringify({
        fbwz: fbwz,
        pageNo: pageNo
    }), function (response) {
        page(response.total);
        if (response.total > 0) {
            newsShow(response.data);
        }else{
            $('#tbody').html("");
        }

    })
}

function AllShearch() {
    var searchBt = encodeURI($("#shearchInput").val());
    window.location.href = "all_search_list.html?goal=" + $("#selectBox").val() + "&bt=" + searchBt+"&op=all";

}

/**
 * 新闻信息列表展示
 */
function newsShow(news) {
    var tbody = '';
    $('#tbody').empty();
    $.each(news, function (n, value) {
        if (value.ISTOP) {
            tbody += "<li><a href='#' onclick='goInfo(this)' dtid='" + value.ID + "'>" + value.BT + "<label style='font-size: 5px;color: #af0000'>&nbsp;[置顶]</label></a><span>" + value.FBSJ + "</span></li>";
        }else{
            tbody += "<li><a href='#' onclick='goInfo(this)' dtid='" + value.ID + "'>" + value.BT + "</a><span>" + value.FBSJ + "</span></li>";
        }

    })
    console.log(tbody);
    $('#tbody').html(tbody);
}

/**
 * 切换到动态信息详情展示界面
 */
function goInfo(att) {
    window.open('./info.html?dtid=' + $(att).attr('dtid') + '&goal=' + target);
}

/**
 *分页
 */
var pageNo = 1;
var page = function (count) {
    /*  $('#total').html(count);
      $('#pageNo').html(pageNo + '/' + Math.ceil(count/10));*/
    layui.use('laypage', function () {
        var laypage = layui.laypage;
        laypage.render({
            elem: 'page',
            count: count,
            limit: 15,
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
    getNewsFromDatabase();
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
