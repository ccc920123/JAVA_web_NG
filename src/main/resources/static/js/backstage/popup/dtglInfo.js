/**
 * 入口函数
 */
var addOrUpdate = '';
var ly = '';//来源
var shid = '';//审核用
var ue;
var op;
var laydate, layer;
$(function () {

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //日期时间选择器
        laydate.render({
            elem: '#fbsj'
            ,type: 'datetime'
        });
    });

    showUeditor();
    // getAjqy();
    doPost('/area/getQyname', JSON.stringify({
        sjqyid: undefined,
        xzjb: 1
    }), function (response) {
        $('#aqyid').empty();
        var abody = '<option value="">省级行政区域&nbsp;&nbsp;</option>';
        $.each(response.data, function (n, value) {
            abody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
        })
        $('#aqyid').append(abody);

        initPages();
    });
    $('#fbwz').change(function () {
        if ($('#fbwz').val() == '05') {
            $('#chose_table').removeClass("layui-hide");
        } else {
            $('#chose_table').addClass("layui-hide");
        }
    });

    $('input[type=radio][name=istop]').change(function () {
        if (this.value == '0') {
            $('#top_td').addClass("layui-hide");
        }
        else if (this.value == '1') {
            $('#top_td').removeClass("layui-hide");
            //初始赋值
            laydate.render({
                elem: '#topdate'
                , value: new Date()
                , isInitValue: true
            });
        }
    });
});

/**
 * 页面初始化
 */
var xzjb = 1;
function initPages() {
    manageLaws();
    var postData = GetRequest();
    ly = postData.ly;//判断是从审核调过来的还是动态信息跳过来的
    op = postData.op;
    var dtid = postData.dtid;//获取动态ID
    xzjb = getUserXzjbStr();
    shid = dtid;
    layui.use(['laydate','layer'], function(){
        laydate = layui.laydate;
        layer=layui.layer;
        if (ly == 'info') {
            $('#auditShow').css('display', 'none');
            $('#fbsjTr').css('display', 'none');
            if (op == 'look') {
                $('#sub_button').css('display', 'none');
                $('#can_button').val('关闭');
                $('.scinput').attr("readonly", "readonly");

            }
            if (dtid === 'no') {//新增动态信息
                $('#addname').html('动态新增');
                addOrUpdate = 'add';
                var id_user = getUserqyidStr();
                showFbqy(id_user);

            } else {//修改动态信息
                $('#addname').html('动态修改');
                addOrUpdate = 'update';
                getDtInfoById(dtid);
            }
        } else {
            $('#addname').html('动态审核');
            if (op == 'look') {
                $('#sub_button').css('display', 'none');
                $('#can_button').val('关闭');
                $('.scinput').attr("readonly", "readonly");
                $('#fbsj').attr("readonly", "readonly");
                $('#shzt').attr("disabled", "disabled");
                $('#submit').attr("disabled", "disabled");
            }
            $('#auditShow').css('display', 'block');
            $('#fbsjTr').css('display', 'block');
            $('#sfzd').removeClass('layui-hide');
            getDtInfoById(dtid);
        }
    });


}
/**
 * 判断只有是省级管理员才能显示法律法规（即省级管理员管理法律法规）
 */
function manageLaws() {
    doPost('/user/manageLaws', {}, function (response) {
        if (response.success == true) {
            $('#laws').css('display', 'block');
        } else {
            $('#laws').css('display', 'none');
        }
    })
}

/**
 * 通过Id获取动态信息
 */
var dtid = '';//动态ID
var tjcj = '';//提交层级
var shzt = '';//审核状态
var tszt = '';//推送状态
function getDtInfoById(id) {
    dtid = id;
    doPost('/dtgl/getInfoById', JSON.stringify({
        dtid: id
    }), function (response) {
        console.log(response);
        //提交层级和审核状态赋值
        tjcj = response.data.tjcj;
        shzt = response.data.shzt;
        tszt = response.data.tszt;
        if (shzt == undefined || shzt == null || shzt == 'null' || shzt == ''){
            shzt = 0;
        }
        if (tszt == undefined || tszt == null || tszt == 'null' || tszt == ''){
            tszt = 0;
        }
        //给页面赋值
        $.each(response.data, function (key, value) {
            $("#" + key).val(value);
        })
        if (response.data.bz != undefined || response.data.bz != null){
            $('#remark').css('display', 'block');
            $('#remarks').val(response.data.bz);
        } else {
            $('#remark').css('display', 'none');
        }
        //给发布区域赋值
        $("input[type=radio][name=istop][value='" + response.data.istop + "']").attr("checked", true);
        if (ly != 'info' && response.data.istop == '1') {
            $('#top_td').removeClass("layui-hide");
            laydate.render({
                elem: '#topdate'
                , value: response.topdate
                , isInitValue: true
            });
        }
        showFbqy(response.data.fbqyid);
        oldhtml = response.data.dtnr;
        ue.ready(function () {
            ue.setContent(oldhtml);
            if (op == 'look') {
                ue.setDisabled();
                $('#fbwz').attr("disabled", "disabled");
                $('.coverImg').off('mouseenter').unbind('mouseleave');
            }
        })
        if (response.data.fbwz == '05') {
            $('#chose_table').removeClass("layui-hide");
            $("#coverImg_img").attr("src", response.data.firstpic);
        }
    })
}

/**
 * 返回到动态信息或动态审核页面
 */
function goback() {
    window.history.back();
    // if (ly == 'info') {
    //     window.location.href = 'news.html';
    // } else {
    //     window.location.href = 'news_review.html';
    // }
}

function operateYulan() {
    var newhtml = ue.getContent();
    doPost('/dtgl/savedtyl', JSON.stringify({
        dtnr: newhtml
    }), function (response) {
        if (response.success == true) {
            gotoReview(response.data);
        } else {
            layer.msg(response.data, {icon: 5});
        }
    })
}

function gotoReview(id) {
    window.open('./YulanNews.html?' +
        'bt=' + encodeURI($('#bt').val()) +
        '&ngr=' + encodeURI($('#ngr').val()) +
        '&dtnr=' + encodeURI(id));
}


/**
 * 操作确认，判断是新增还是修改
 */
function operateSure() {
    //在新增或者修改前都要先处理好动态信息的上传
    var cover = $('#coverImg_img').attr("src")
   var fw= $('#fbwz').val() ;
    if(fw==undefined||fw==""){
        layer.msg("请选择发布位置", {icon: 5});
        return;
    }
    var bt= $('#bt').val() ;
    if(bt==undefined||bt==""){
        layer.msg("请填写标题", {icon: 5});
        return;
    }
    var btsp= $('#btsp').val() ;
    if(btsp==undefined||btsp==""){
        layer.msg("请填写标题首拼", {icon: 5});
        return;
    }
    var ngr= $('#ngr').val() ;
    if(ngr==undefined||ngr==""){
        layer.msg("请填写拟稿人", {icon: 5});
        return;
    }
    if (addOrUpdate === 'add') {
        if ($('#fbwz').val() == '05') {
            if (cover == undefined || cover == "" || cover == '../../images/coverImg.jpg') {
                layer.msg("请选择一张封面图片", {icon: 5});
                return;
            }
        }
        shzt = 1;
        addDtgl();
    } else if (ly == 'info') {//动态信息页面过来的修改
        if ($('#fbwz').val() == '05') {
            if (cover == undefined || cover == "" || cover == '../../images/coverImg.jpg') {
                layer.msg("请选择一张封面图片", {icon: 5});
                return;
            }
        }
        shzt = 1;
        updateDtgl();
    } else {//审核页面过来的，则可以先进行审核再修改
        //判断是否要提交到上一层级/alterDtgl/alterDtgl
        shzt = 0;
        if ($('#shzt').val() === undefined || $('#shzt').val() === null) {
            layer.msg("请选择审核状态", {icon: 5});
            return;
        }
        shzt = $('#shzt').val();
        if ($('#submit').val() == '1' && tjcj == '1') {//当前层级已经是省级层级
            layer.msg("当前已是省级层级不能继续提交", {icon: 5});
            return;
        }
        if (shzt == '2' && $('#submit').val() == '1'){
            layer.msg("审核未通过不能提交到上级", {icon: 5});
            return;
        }
        if (shzt === '0') {//不提交
            sureAudit(shzt);
        } else if (shzt === '1') {//提交,判断选择的审核状态为审核通过
            updateDtgl();
            // sureAudit(shzt);
            if ($('#submit').val() == '1') {
                submitInfo();
            }
        } else {
            if ($('#submit').val() == '0') {
                updateDtgl();
                // sureAudit(shzt);
            } else {
                layer.msg("审核未通过不能提交到上级", {icon: 5});
            }
        }
    }
}

/**
 * 新增动态管理
 */
function addDtgl() {
    var topsort=($('#topsort').val()==undefined||$('#topsort').val()=="")?0:$('#topsort').val();

    doPost('/dtgl/addDtgl', JSON.stringify({
        bt: $('#bt').val(),
        btsp: $('#btsp').val(),
        dtlx: $('#dtlx').val(),
        ngr: $('#ngr').val(),
        fbqyid: getFbqyid(),
        fbwz: $('#fbwz').val(),
        istop: $("input[name='istop']:checked").val(),
        topsort: topsort,
        topdate: $('#topdate').val(),
        firstpic: $('#coverImg_img').attr("src")
    }), function (response) {
        if (response.success == true) {//返回的是动态ID
            shid = response.data;
            updateUeditor(response.data);
        } else {
            layer.msg(response.data, {icon: 5});
        }
    })
}

/**
 * 修改动态管理
 */
function updateDtgl() {
    var topsort=($('#topsort').val()==undefined||$('#topsort').val()=="")?0:$('#topsort').val();
    doPost('/dtgl/alterDtgl', JSON.stringify({
        id: dtid,
        bt: $('#bt').val(),
        btsp: $('#btsp').val(),
        dtlx: $('#dtlx').val(),
        ngr: $('#ngr').val(),
        fbqyid: getFbqyid(),
        fbwz: $('#fbwz').val(),
        fbsj: $('#fbsj').val(),
        istop: $("input[name='istop']:checked").val(),
        topsort: topsort,
        topdate: $('#topdate').val(),
        firstpic: $('#coverImg_img').attr("src"),
        tjcj: tjcj, //用于判断该用户是否有修改权限
        shzt: shzt,
        tszt: tszt,
        submit: $('#submit').val()
    }), function (response) {
        if (response.success == true) {
            //判断动态信息内容上传是否成功
            shid = dtid;
            updateUeditor(dtid);
        } else {
            layer.msg(response.data, {icon: 5});
        }
    })
}

/**
 * 实例化编辑器,显示富文本编辑框
 * 初始化时获取页面富文本的内容，用于后面判断富文本是否改变
 */
var oldhtml = "";

function showUeditor() {
    ue = UE.getEditor('container', {
        elementPathEnabled: false,
        allowDivTransToP: false
    });
    // var proinfo=templates[0].html
    ue.ready(function () {
        oldhtml = ue.getContent();
        ue.setHeight(400);
        ue.execCommand('fontfamily', '宋体, SimSun');   //字体
        ue.execCommand('lineheight', 1);          //行间距
        ue.execCommand('forecolor', 'rgb(0, 0, 0)');          //行间距
        ue.execCommand('fontsize', '18px');
        // ue.setContent(proinfo);  //赋值给UEditor
    })
    ue.addListener("blur", setPic);

    function setPic() {
        //监听失去焦点时间
        var newhtml = ue.getContent();
        var reg = "(<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>)";
        // var reg = "(]+src\s*=\s*'\"['\"][^>]*>)";
        if (newhtml.match(reg)) {
            $("#coverImg_img").attr("src", newhtml.match(reg)[2])
            ue.removeListener("blur", setPic);
        }
    }
}

/**
 * 富文本动态内容编辑
 */
function updateUeditor(id) {
    var newhtml = ue.getContent();
    //判断富文本是否被改变
    // if (newhtml === oldhtml) {
    //     submitDialog();
    // } else {//富文本被改变，存入数据库
        doPost('/dtgl/updateDtnr', JSON.stringify({
            id: id,
            dtnr: newhtml
        }), function (response) {
            if (response.success == true) {
                sureAudit(shzt);
                // submitDialog();
            } else {
                layer.msg(response.data, {icon: 5});
            }
        })
    // }
}

function submitDialog() {
    layer.alert('提交成功', {
        closeBtn: 0
    }, function () {
        goback();
    });

}

/**
 * 获取发布区域ID
 */
function getFbqyid() {
    var fbqy = '';
    if (isNotEmpty($('#dqyid').val())) {
        fbqy = $('#dqyid').val();
    } else if (isNotEmpty($('#cqyid').val())) {
        fbqy = $('#cqyid').val();
    } else if (isNotEmpty($('#bqyid').val())) {
        fbqy = $('#bqyid').val();
    } else if (isNotEmpty($('#aqyid').val())) {
        fbqy = $('#aqyid').val()
    } else {
    }
    return fbqy;
}

/**
 * 发布区域，四个选择框的展示
 * getAjqy获取省级区域
 * getBjqy获取市级区域
 * getCjqy获取区县级区域
 * getDjqy获取乡镇级区域
 */
function getAjqy() {
    doPost('/area/getQyname', JSON.stringify({
        sjqyid: undefined,
        xzjb: 1
    }), function (response) {
        $('#aqyid').empty();
        var abody = '<option value="">省级行政区域&nbsp;&nbsp;</option>';
        $.each(response.data, function (n, value) {
            abody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
        })
        $('#aqyid').append(abody);
    });
    //  selectrender();
}

function getBjqy() {
    doPost('/area/getQyname', JSON.stringify({
        sjqyid: $('#aqyid').val(),
        xzjb: 2
    }), function (response) {
        $('#bjqy').empty();
        if (isNotEmpty($('#aqyid').val())) {
            var bbody = '<select id="bqyid" onchange="getCjqy()" class="select3" style="height: 30px;line-height: 30px;"><option value="">市级行政区域&nbsp;&nbsp;</option>';
            $.each(response.data, function (n, value) {
                bbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
            })
            $('#bjqy').append(bbody + "</select>");
        }
    })
}

function getCjqy() {
    doPost('/area/getQyname', JSON.stringify({
        sjqyid: $('#bqyid').val(),
        xzjb: 3
    }), function (response) {
        $('#cjqy').empty();
        if (isNotEmpty($('#bqyid').val())) {
            var cbody = '<select id="cqyid" onchange="getDjqy()"  class="select3" style="height: 30px;line-height: 30px;"><option value="">区县级行政区域&nbsp;&nbsp;</option>';
            $.each(response.data, function (n, value) {
                cbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
            })
            $('#cjqy').append(cbody + "</select>");
        }
    })
}

function getDjqy() {
    doPost('/area/getQyname', JSON.stringify({
        sjqyid: $('#cqyid').val(),
        xzjb: 4
    }), function (response) {
        $('#djqy').empty();
        if (isNotEmpty($('#cqyid').val())) {
            var dbody = '<select id="dqyid" class="select3" style="height: 30px;line-height: 30px;"><option value="">乡镇级行政区域&nbsp;&nbsp;</option>';
            $.each(response.data, function (n, value) {
                dbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
            })
            $('#djqy').append(dbody + "</select>");
        }
    })
}

/**
 * 修改动态信息时，发布区域<select>初始显示信息。
 */
function showFbqy(qyid) {
    doPost('/area/getSjQyname', qyid, function (response) {
        var areas = response.data;
        //赋值省级区域
        $('#aqyid').val(areas[0].QYID);
        $("#aqyid ").prop("disabled", true);
        if (1 < areas.length) {//有市级区域
            doPost('/area/getQyname', JSON.stringify({
                sjqyid: $('#aqyid').val(),
                xzjb: 2
            }), function (response) {
                $('#bjqy').empty();
                var bbody = '<select id="bqyid" class="select3" onchange="getCjqy()" style="height: 30px;line-height: 30px;"><option value="">市级行政区域</option>';
                $.each(response.data, function (n, value) {
                    bbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
                })
                $('#bjqy').append(bbody + "</select>");
                //赋值市级区域
                $('#bqyid').val(areas[1].QYID);
                $("#bqyid ").prop("disabled", true)
                if (2 < areas.length) {//有区县级区域
                    doPost('/area/getQyname', JSON.stringify({
                        sjqyid: $('#bqyid').val(),
                        xzjb: 3
                    }), function (response) {
                        $('#cjqy').empty();
                        var cbody = '<select id="cqyid" class="select3" onchange="getDjqy()" style="height: 30px;line-height: 30px;"><option value="">区县级行政区域</option>';
                        $.each(response.data, function (n, value) {
                            cbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
                        })
                        $('#cjqy').append(cbody + "</select>");
                        //赋值区县级区域
                        $('#cqyid').val(areas[2].QYID);
                        $("#cqyid ").prop("disabled", true)
                        if (3 < areas.length) {//有乡镇级区域
                            doPost('/area/getQyname', JSON.stringify({
                                sjqyid: $('#cqyid').val(),
                                xzjb: 4
                            }), function (response) {
                                $('#djqy').empty();
                                var dbody = '<select id="dqyid" class="select3" style="height: 30px;line-height: 30px;"><option value="">乡镇级行政区域</option>';
                                $.each(response.data, function (n, value) {
                                    dbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
                                })
                                $('#djqy').append(dbody + "</select>");
                                //赋值乡镇级区域
                                $('#dqyid').val(areas[3].QYID);
                                $("#dqyid ").prop("disabled", true)
                            })
                        } else {
                            return;
                        }
                    })
                } else {
                    return;
                }
            })
        } else {
            return;
        }

    })
}

/**
 * 该变动态标题的值，其动态首拼自动生成对应的拼音首字母
 */
function setFirstCharacter() {
    var Chinese = $('#bt').val();
    var firstCharacter = getPinYinFirstCharacter(Chinese, null, false);
    $('#btsp').val(firstCharacter.replace(/\s+/g, ""));
}

/**
 * 审核
 */
function sureAudit(shzt) {
    var shbz = '';
    if (shzt == '2' && ly != 'info'){
        shbz = $('#remarks').val();
    }
    doPost('/dtgl/dtAudit', JSON.stringify({
        dtid: shid,
        shzt: shzt,
        bz: shbz
    }), function (response) {
        if (response.success == true) {
            if (addOrUpdate == 'add' && xzjb == 4){
                submitInfo();
            } else {
                if ($('#submit').val() == '0'){
                    submitInfo();
                } else {
                    submitDialog();
                }
            }
        } else {
            layer.msg('审核提交失败', {icon: 5});
        }
    })
}

/**
 * 提交动态信息到下一级
 */
function submitInfo() {
    doPost('/dtgl/submitInfo', JSON.stringify({
        dtid: shid,
        sftj: $('#submit').val()
    }), function (response) {
        if (response.success == false) {
            layer.msg(response.data, {icon: 5});
        } else {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.confirm('提交成功', {
                    btn: ['确定'] //可以无限个按钮
                }, function () {
                    goback();
                });
            })
        }
    })
}

/**
 * 选择审核通过或者不通过触发事件
 */
function remarkShow() {
    var shzt = $('#shzt').val();
    if (shzt === '1') {
        //就不显示备注框
        $('#remark').css('display', 'none');
    } else if (shzt === '2') {//审核不通过显示备注信息输入框
        $('#remark').css('display', 'block');
    } else {
    }
}

function selectrender() {
    $(".select3").uedSelect({
        width: 152
    });
}


function getqyidArr(userqyid) {

    var userArr = new Array();

    if (userqyid.length == 2) {
        userArr.push(userqyid.substring(0, userqyid.length));
    } else if (userqyid.length > 2 && userqyid.length <= 4) {
        userArr.push(userqyid.substring(0, 2));
        userArr.push(userqyid.substring(0, userqyid.length));
    } else if (userqyid.length > 4 && userqyid.length <= 6) {
        userArr.push(userqyid.substring(0, 2));
        userArr.push(userqyid.substring(0, 4));
        userArr.push(userqyid.substring(0, userqyid.length));
    } else if (userqyid.length > 6) {
        userArr.push(userqyid.substring(0, 2));
        userArr.push(userqyid.substring(0, 4));
        userArr.push(userqyid.substring(0, 6));
        userArr.push(userqyid.substring(0, userqyid.length));
    }
    return userArr;
}