/**
 * 入口函数
 */
var qyid = '51';//默认一开始的区域ID是四川省
// 每次点击选择区域的时候后端需要存储当前的区域ID，名字暂时约定为AreaId
$(function () {

    qyid = getUserXzqyidStr();
    if (qyid == undefined || qyid == '') {
        qyid = '51';
    }
    getAreaName();//获取区域选则信息

})

/**
 * 获取图片新闻/新闻动态
 */
function getPictureNews() {
    doPost('/dtgl/pictureNews', {}, function (response) {
        $('#picture').empty();
        $('#imgone').empty();
        $('#imgbt').empty();
        $('#imgindex').empty();
        // jQuery(".focusBox").slide({
        //     startFun:clearTimeout(K)
        // });
        if (response.success == true) {
            var tbody = '';
            $.each(response.data.news, function (n, value) {
                if (value.ISTOP) {
                    tbody += "<li><a  onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=picture')\">" +
                        value.BT + "<label style='font-size: 8px;color: #af0000'>&nbsp;[置顶]</label></a><span>" + value.FBSJ + "</span></li>";
                } else {
                    tbody += "<li><a  onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=picture')\">" +
                        value.BT + "</a><span>" + value.FBSJ + "</span></li>";
                }
            })
            $('#picture').html(tbody);

            var imgone = "";
            var imgbt = "";
            var imgindex = "";
            $.each(response.data.imgs, function (n, value) {
                imgone += " <li ><a  target=\"_blank\" onclick=\"window.open('pages/frontside/info.html?dtid=" + response.data.news[n].ID + "&goal=picture')\"><img src='" + value + "'></a></li>";
                imgbt += " <li><a href=\"#\">" + response.data.news[n].BT + "</a></li>";
                imgindex += "<li><a>" + (n + 1) + "</a><span></span></li>";
            });

            $('#imgone').html(imgone);
            $('#imgbt').html(imgbt);
            $('#imgindex').html(imgindex);

            jQuery(".focusBox").slide({
                titCell: ".num li",
                mainCell: ".pic",
                effect: "fold",
                autoPlay: true,
                trigger: "click",
                startFun: function (i) {
                    jQuery(".focusBox .txt li").eq(i).animate({"bottom": 0}).siblings().animate({"bottom": -50});
                }
            });

        } else {
            console.log(response.data);
        }
    })
}

/**
 * 获取区域特色
 */
function getAreaFeature() {
    doPost('/dtgl/getNewsByFBWZ', JSON.stringify({
        fbwz: '02'
    }), function (response) {
        $('#areaFeature').empty();
        if (response.success == true) {
            var tbody = '';
            $.each(response.data, function (n, value) {
                if (value.ISTOP) {
                    tbody += "<p><a href=\"#\" onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=feature')\">" +
                        value.BT + "<label style='font-size: 8px;color: #af0000'>&nbsp;[置顶]</label></a><span>" + value.FBSJ + "</span></p>";
                } else {
                    tbody += "<p><a href=\"#\" onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=feature')\">" +
                        value.BT + "</a><span>" + value.FBSJ + "</span></p>";
                }
            })
            $('#areaFeature').html(tbody);
        } else {
            console.log(response.data);
        }
    })
}

/**
 * 获取通知通报
 */
function getInform() {
    doPost('/dtgl/getNewsByFBWZ', JSON.stringify({
        fbwz: '06'
    }), function (response) {
        $('#inform').empty();
        if (response.success == true) {
            var tbody = '';
            $.each(response.data, function (n, value) {
                if (value.ISTOP) {
                    tbody += "<li><a href=\"#\" onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=inform')\">" +
                        value.BT + "<label style='font-size: 8px;color: #af0000'>&nbsp;[置顶]</label></a><span>" + value.FBSJ + "</span></li>";
                } else {
                    tbody += "<li><a href=\"#\" onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=inform')\">" +
                        value.BT + "</a><span>" + value.FBSJ + "</span></li>";
                }

                $('#inform').html(tbody);
            });
        } else {
            console.log(response.data);
        }
    })
}

/**
 * 工作动态
 */
function getDynamic() {
    doPost('/dtgl/getNewsByFBWZ', JSON.stringify({
        fbwz: '04'
    }), function (response) {
        $('#dynamic').empty();
        if (response.success == true) {
            var tbody = '';
            $.each(response.data, function (n, value) {
                if (value.ISTOP) {
                    tbody += "<li><a href=\"#\" onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=dynamic')\">" +
                        value.BT + "<label style='font-size: 8px;color: #af0000'>&nbsp;[置顶]</label></a><span>" + value.FBSJ + "</span></li>";
                } else {
                    tbody += "<li><a href=\"#\" onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=dynamic')\">" +
                        value.BT + "</a><span>" + value.FBSJ + "</span></li>";
                }
            })
            $('#dynamic').html(tbody);
        } else {
            console.log(response.data)
        }
    })
}

/**
 * 法律法规
 */
function getLaws() {
    doPost('/dtgl/getNewsByFBWZ', JSON.stringify({
        fbwz: '03'
    }), function (response) {
        $('#laws').empty();
        if (response.success == true) {
            var tbody = '';
            $.each(response.data, function (n, value) {
                if (value.ISTOP) {
                    tbody += "<li><a href=\"#\" onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=laws')\">" +
                        value.BT + "<label style='font-size: 8px;color: #af0000'>&nbsp;[置顶]</label></a><span>" + value.FBSJ + "</span></li>";
                } else {
                    tbody += "<li><a href=\"#\" onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=laws')\">" +
                        value.BT + "</a><span>" + value.FBSJ + "</span></li>";
                }
            })
            $('#laws').html(tbody);
        } else {
            console.log(response.data);
        }
    })
}

/**
 * 宣传警示
 */
function getPublicitiy() {
    doPost('/dtgl/getNewsByFBWZ', JSON.stringify({
        fbwz: '07'
    }), function (response) {
        $('#publicitiy').empty();
        if (response.success == true) {
            var tbody = '';
            $.each(response.data, function (n, value) {
                if (value.ISTOP) {
                    tbody += "<li><a href=\"#\" onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=publicitiy')\">" +
                        value.BT + "<label style='font-size: 8px;color: #af0000'>&nbsp;[置顶]</label></a><span>" + value.FBSJ + "</span></li>";
                } else {
                    tbody += "<li><a href=\"#\" onclick=\"window.open('pages/frontside/info.html?dtid=" + value.ID + "&goal=publicitiy')\">" +
                        value.BT + "</a><span>" + value.FBSJ + "</span></li>";
                }

            })
            $('#publicitiy').html(tbody);
        } else {
            console.log(response.data);
        }
    })
}

/**
 * 获取区域简介
 */
function getAreaIntro() {
    doPost('/area/areaIntro',qyid , function (response) {
        $('#areaIntro').empty();
        $('#areaIntro').html(nullData1(response.data));
    })
}

/**
 * 获取新闻信息排名
 */
function getNewsRanking() {
    doPost('/dtgl/newsRank', {}, function (response) {
        $('#newsBody').empty();
        if (response.success == true) {
            var tbody = '';
            $.each(response.data, function (n, value) {
                tbody += "<tr>" +
                    "<td><span>TOP" + (n + 1) + "</span></td>" +
                    "<td>" + value.QYNAME + "</td>" +
                    "<td>" + value.TOTAL + "</td>" +
                    "</tr>";
            })
            $('#newsBody').html(tbody);
        } else {
            console.log(response.data);
        }
    })
}

/**
 * 获取综合排名
 */
function getJobRanking() {
    var myDate = new Date();
    var year = myDate.getFullYear() + '年';
    var month = myDate.getMonth() + 1 + '月';
    var date = myDate.getDate() + '日';
    $('#pmrq').text([year, month, date].join(' '));
    doPost('/assess/assessrank', {}, function (response) {
        $('#jobody').empty();
        $('#pmmc').html(response.data.pmmc);
        if (response.success == true) {
            var jobody = '';
            $.each(response.data.ranks, function (n, value) {
                jobody += "<tr>" +
                    "<td><span>TOP" + (n + 1) + "</span></td>" +
                    "<td>" + value.qyname + "</td>" +
                    "<td>" + (parseInt(value.gzpmdf) + parseInt(value.ngkhdf)) + "</td>" +
                    "</tr>";
            })
            $('#jobody').html(jobody);
        } else {
            console.log(response.data);
            $('#jobody').html(response.data);
        }
    })
//     $('#pmrq').text([year,month,date].join(' '));
// doPost('/job/jobrank',{},function (response) {
//     $('#jobody').empty();
//     if (response.success == true){
//         var jobody = '';
//         $.each(response.data,function (n,value) {
//             jobody += "<tr>" +
//                 "<td><span>TOP"+(n+1)+"</span></td>" +
//                 "<td>"+value.QYNAME+"</td>" +
//                 "<td>"+value.ZF+"</td>" +
//                 "<td>"+value.KF+"</td>" +
//                 "</tr>";
//         })
//         $('#jobody').html(jobody);
//     }else{
//         console.log(response.data);
//         $('#jobody').html(response.data);
//     }
// })
}

/**
 * 根据区域所传的条件ID等于数据库中的QYID或SJQYID获取区域名和区域ID
 * 并且将当前的区域ID存入session
 */
function getAreaName() {
    doPost('/area/getregion', qyid, function (response) {
        showChoiceArea(response.data);
         getAreaIntro();//获取区域简介
        getAreaFeature();//获取区域特色
        getInform();//获取通知通告
        getDynamic();//获取工作动态
        getLaws();//获取法律法规
        getPublicitiy();//宣传警示
        getNewsRanking();//获取新闻信息排名
        getJobRanking();//获取工作信息排名
        getPictureNews();//获取图片新闻
        getLink();//获取友情链接
    })
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
            qyid = area[i].QYID;
            $('#currentQyid').html(area[i].QYNAME);
            $('#currentBt').html(area[i].QYNAME);
        } else {
        }
    }
    //循环显示区域
    var tbody = "";
    $('#tbody').empty();
    for (var i = 0; i < area.length; i++) {
        var tbodyChild = "";
        if (area[i].XZJB === xzjb) {
            tbody += "<li><a class='third-link2' onclick='choiceArea(this)' qyid='" + area[i].QYID + "'>" + area[i].QYNAME + "</a>";
                for (var j = 0; j < area.length; j++) {
                    if (area[i].QYID === area[j].SJQYID) {
                        tbodyChild += "<li><a onclick='choiceArea(this)' qyid='" + area[j].QYID + "'>" + area[j].QYNAME + "</a></li>";
                    }
                }
            if (tbodyChild != "") {
                tbody += "<ul class='fourth-menu'>" + tbodyChild + "</ul>";
            }
             tbody += "</li>";
        }
    }
    $('#tbody').html(tbody);
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
    console.log(tbody);
    console.log(sjbody);
    test();
}
function AllShearch() {
    var searchBt = encodeURI($("#shearchInput").val());
    window.location.href = "pages/frontside/all_search_list.html?goal=" + $("#selectBox").val() + "&bt=" + searchBt+"&op=all";

}
/**
 * 查询友情链接
 */
function getLink() {

    doGet("/link/getLink", {
        isWeb:true,
        pageNo: 1,
        pageSize: 10000
    }, function (response) {
        $('#hraf_link').empty();
        var hrafLik = '';
        if (response.data){
            $.each(response.data, function (row, value) {
                var str = "";

                str += "<a href=\"" + value.lj + "\" target=\"_blank\">" + value.bt + "</a>"
                console.log(str);
                hrafLik += str;

            });

            $("#hraf_link").append(hrafLik);
        }



    })


}

/**
 * 选择页面的区域时，绑定qyid的值
 */
function choiceArea(att) {

    qyid = $(att).attr("qyid");
    layui.data('xzqyid', {
        key: 'xzqyid'
        ,value: qyid
    });
    var pression_divs=  $(".pression");
    pression_divs.each(function(i, div) {
        var menuid=$(div).attr("pression");
        if(!isInArray3(menuids,menuid)){
            $(div).css('display','none');
        }
    });

    window.location.reload()

    // getAreaName();
}

/**
 * 登录系统后台管理函数
 */
function loginHt() {
    window.open('pages/backside/login.html');
}

/**
 * 切换到不同的news
 * @param goal所切换的目标表示的内容
 */
function goto(goal) {
    window.location.href = "pages/frontside/list.html?goal=" + goal;
}

/**
 * 切换到基础数据页面
 */
function gobaseData() {
    window.location.href = "pages/frontside/chart.html";
}

