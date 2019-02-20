/**
 * 基础数据页面
 */
$(function () {
    getQYID();
})

function AllShearch() {
    var searchBt = encodeURI($("#shearchInput").val());
    window.location.href = "all_search_list.html?goal=" + $("#selectBox").val() + "&bt=" + searchBt + "&op=all";

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
        getStatisticDrv(qyid);
        getStatisticVeh(qyid);
        getStatisticRoad(qyid);
        getTableLZStatisData(qyid);
        getTableLYStatisData(qyid);
    })


}

/**
 * 两员
 * @param qyid
 */
function getTableLYStatisData(qyid) {
    doGet("/baseData/getTableLYStatisData", {qyid: qyid}, function (response) {
        if (response.success && response.data.length > 0) {
            var table="";
            $.each(response.data, function (index, map) {
                var tr = " <tr>"
                if (index == response.data.length - 1) {
                    tr+=
                        "                            <td colspan='2' >"+map.DW_NAME+"</td>\n" +
                        "                            <td >"+map.TOTAL+"</td>\n" +
                        "                            <td >"+map.JGY+"</td>\n" +
                        "                            <td >"+map.JXY+"</td>\n" +
                        "                            <td >"+map.JQY+"</td>\n" +
                        "                            <td >"+map.ZJ+"</td>"
                } else {
                    tr+="<td >"+map.RN+"</td>\n" +
                        "                            <td  >"+map.DW_NAME+"</td>\n" +
                        "                            <td >"+map.TOTAL+"</td>\n" +
                        "                            <td >"+map.JGY+"</td>\n" +
                        "                            <td >"+map.JXY+"</td>\n" +
                        "                            <td >"+map.JQY+"</td>\n" +
                        "                            <td >"+map.ZJ+"</td>"
                }
                tr+="</tr>";
                table+=tr;
            });
$("#ly_tbody").html(table);
        }
    });
}

/**
 * 两站
 * @param qyid
 */
function getTableLZStatisData(qyid) {
    doGet("/baseData/getTableLZStatisData", {qyid: qyid}, function (response) {
        if (response.success && response.data.length > 0) {
            var table="";
            $.each(response.data, function (index, map) {
                var tr = " <tr>"
                if (index == response.data.length - 1) {
                    tr+=
                        "                            <td colspan='2' >"+map.DW_NAME+"</td>\n" +
                        "                            <td >"+map.A+"</td>\n" +
                        "                            <td >"+map.B+"</td>\n" +
                        "                            <td >"+map.C+"</td>\n" +
                        "                            <td >"+map.D+"</td>\n" +
                        "                            <td >"+map.E+"</td>"
                } else {
                    tr+="<td >"+map.RN+"</td>\n" +
                        "                            <td >"+map.DW_NAME+"</td>\n" +
                        "                            <td >"+map.A+"</td>\n" +
                        "                            <td >"+map.B+"</td>\n" +
                        "                            <td >"+map.C+"</td>\n" +
                        "                            <td >"+map.D+"</td>\n" +
                        "                            <td >"+map.E+"</td>"
                }
                tr+="</tr>";
                table+=tr;
            });
            $("#lz_tbody").html(table);
        }
    });

}
//四川省共21个区域，共0名驾驶人。 成都市0名驾驶人，占0%； 自贡市0名驾驶人，占0%；
// 攀枝花市0名驾驶人，占0%； 泸州市0名驾驶人，占0%； 德阳市0名驾驶人，占0%；
// 绵阳市0名驾驶人，占0%； 广元市0名驾驶人，占0%； 遂宁市0名驾驶人，占0%； 内江市0名驾驶人，占0%；
// 乐山市0名驾驶人，占0%； 南充市0名驾驶人，占0%； 眉山市0名驾驶人，占0%； 宜宾市0名驾驶人，占0%；
// 广安市0名驾驶人，占0%； 达州市0名驾驶人，占0%； 雅安市0名驾驶人，占0%； 巴中市0名驾驶人，占0%；
// 资阳市0名驾驶人，占0%；
// 阿坝藏族羌族自治州0名驾驶人，占0%； 甘孜藏族自治州0名驾驶人，占0%； 凉山彝族自治州0名驾驶人，占0%；
function getStatisticDrv(qyid) {
    doGet("/baseData/getDrvStatisData", {qyid: qyid}, function (response) {
        if (response.success && response.data.length > 0) {
            var t = response.data[response.data.length - 1];
            var html = t.QYNAME + "共" + (response.data.length-1) + "个区域,共" + t.TJZL + "名驾驶人。"
            var th_head = " <tr>"
            var th_body = " <tr>"
            $.each(response.data, function (index, map) {
                if (index == response.data.length - 1) {
                } else {
                    html += map.QYNAME + map.TJZL + "名驾驶人，占" + toDecimal((parseFloat(map.TJZL) / parseFloat(t.TJZL)) * 100) + "%；"
                    th_head += "<th><span>" + map.QYNAME + "</span></th>"
                    th_body += "<td>" + map.TJZL + "</td>"
                }
            });
            th_head += " </tr>"
            th_body += " </tr>"

            $("#content_drv").html(html);
            $("#drv_thead").html(th_head);
            $("#drv_tbody").html(th_body);
        }


    });

}

function getStatisticVeh(qyid) {
    doGet("/baseData/getVehStatisData", {qyid: qyid}, function (response) {
        if (response.success && response.data.length > 0) {
            var t = response.data[response.data.length - 1];
            var html = t.QYNAME + "共" + (response.data.length-1) + "个区域,共" + t.TJZL + "辆机动车。"
            var th_head = " <tr>"
            var th_body = " <tr>"
            $.each(response.data, function (index, map) {
                if (index == response.data.length - 1) {
                } else {
                    html += map.QYNAME + map.TJZL + "辆，占" + toDecimal((parseFloat(map.TJZL) / parseFloat(t.TJZL)) * 100) + "%；"
                    th_head += "<th><span>" + map.QYNAME + "</span></th>"
                    th_body += "<td>" + map.TJZL + "</td>"
                }
            });
            th_head += " </tr>"
            th_body += " </tr>"

            $("#content_veh").html(html);
            $("#veh_thead").html(th_head);
            $("#veh_tbody").html(th_body);
        }


    });
}

function getStatisticRoad(qyid) {
    doGet("/baseData/getRoadStatisData", {qyid: qyid}, function (response) {
        if (response.success && response.data.length > 0) {
            var t = response.data[response.data.length - 1];
            var html = t.QYNAME + "共" + (response.data.length-1) + "个区域,共" + t.TJZL + "公里。"
            var th_head = " <tr>"
            var th_body = " <tr>"
            $.each(response.data, function (index, map) {
                if (index == response.data.length - 1) {
                } else {
                    html += map.QYNAME + map.TJZL + "公里，占" + toDecimal((parseFloat(map.TJZL) / parseFloat(t.TJZL)) * 100) + "%；"
                    th_head += "<th><span>" + map.QYNAME + "</span></th>"
                    th_body += "<td>" + map.TJZL + "</td>"
                }
            });
            th_head += " </tr>"
            th_body += " </tr>"

            $("#content_road").html(html);
            $("#road_thead").html(th_head);
            $("#road_tbody").html(th_body);
        }


    });
}

function toDecimal(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return;
    }
    f = Math.round(x * 100) / 100;
    return f;
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
            $('#lz_DWMC').html(area[i].QYNAME);
            $('#ly_DWMC').html(area[i].QYNAME);
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
function goto(goal) {
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

