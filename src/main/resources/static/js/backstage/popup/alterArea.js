/**
 * 区域修改
 */
var qyid = '';
var flag = '';
var xzjb = 1;
$(function () {
    getAjqy();
    var postData = GetRequest();
    flag = postData.flag;
    if (flag === 'up') {
        qyid = decodeURI(postData.qyid);

        doPost('/area/getAreaDetails',qyid, function (response) {
            $('#qyid').val(qyid);
            $('#qyname').val(nullData1(response.data.qyname));
            $('#qysp').val(nullData1(response.data.qysp));
            $('#cjr').val(nullData1(response.data.cjr));
            $('#qyjj').val(nullData1(response.data.qyjj));
            showQy(response.data.sjqyid);

        })
        // showQy(decodeURI(postData.sjqyid));
    } else {
        $("#tag_title").html("添加区域");
        $("#tag_title1").html("添加区域");
        showQy('');
    }
})

/**
 * 判断是新增还是修改
 */
var sjqyid = '';
var sjqyname2 = '';
function operationJob() {
    var qyid1 = $('#qyid').val();
    if (qyid1 === undefined || qyid1 === null || qyid1.length < 2){
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("请填写区域编号", {icon: 5});
        })
        return;
    }
    var qyname = $('#qyname').val();
    if (qyname === undefined || qyname === null || qyname === ''){
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("请填写区域名称", {icon: 5});
        })
        return;
    }
    sjqyid = getQyid();

    if (xzjb === 1){
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("无权限增加省级行政区域", {icon: 5});
        })
        return;
    } else if (xzjb === 5){
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg("所选父级行政区域已经是最低行政级别", {icon: 5});
        })
        return;
    }

    if (flag === "up") {
        alterAreal(qyid1);
    } else if (flag === "add") {
        addAreas(qyid1);
    }
    // if (qyid1.length <= 6 && qyid1.length % 2 != 0){
    //     layui.use('layer', function() {
    //         var layer = layui.layer;
    //         layer.msg("区域编号不正确", {icon: 5});
    //     })
    //     return;
    // }
    // if (qyid1.length > 6 && qyid1.length != 9){
    //     layui.use('layer', function() {
    //         var layer = layui.layer;
    //         layer.msg("区域编号不正确", {icon: 5});
    //     })
    //     return;
    // }
    // var sjqyid2 = getQyid();
    // var sjqyname = '';
    // var qyname = $('#qyname').val();
    // if (qyid1.length == 9) {
    //     if (isNotEmpty($('#cqyid').val())) {
    //         sjqyid = $('#cqyid').val();
    //         sjqyname = $('#cqyid').find("option:selected").text();
    //     }
    // } else if (qyid1.length == 6) {
    //     if (isNotEmpty($('#bqyid').val())) {
    //         sjqyid = $('#bqyid').val();
    //         sjqyname = $('#bqyid').find("option:selected").text();
    //     }
    // } else  if (qyid1.length == 4) {
    //     if (isNotEmpty($('#aqyid').val())) {
    //         sjqyid = $('#aqyid').val();
    //         sjqyname = $('#aqyid').find("option:selected").text();
    //     }
    // } else if (qyid1.length == 2) {
    //     sjqyid = '';
    //     sjqyname = '';
    // }
    // if (qyname == sjqyname){
    //     layui.use('layer', function() {
    //         var layer = layui.layer;
    //         layer.msg("区域名称和父级区域名称相同", {icon: 5});
    //     })
    //     return;
    // }
    // if (qyid1.length < sjqyid2.length) {
    //     layui.use('layer', function() {
    //         var layer = layui.layer;
    //         layer.msg("区域编号和所属区域层级不匹配", {icon: 5});
    //     })
    //     return;
    // }
    // if (qyid1.length == sjqyid2.length && qyname != sjqyname2) {
    //     layui.use('layer', function() {
    //         var layer = layui.layer;
    //         layer.msg("区域编号和所属区域层级不匹配", {icon: 5});
    //     })
    //     return;
    // }
    // if (qyid1.length > sjqyid.length && qyid1.length - sjqyid.length == 2) {
    //     if (flag === "up") {
    //         alterAreal(qyid1);
    //     } else if (flag === "add") {
    //         addAreas(qyid1);
    //     }
    // } else if (qyid1.length > sjqyid.length && qyid1.length - sjqyid.length == 3) {
    //     if (flag === "up") {
    //         alterAreal(qyid1);
    //     } else if (flag === "add") {
    //         addAreas(qyid1);
    //     }
    // } else {
    //     layui.use('layer', function() {
    //         var layer = layui.layer;
    //         layer.msg("区域编号和所属区域层级不匹配", {icon: 5});
    //     })
    //     return;
    // }

}

/**
 * 增加
 */
function addAreas(qyid1) {

    doPost('/area/addAreas',JSON.stringify({
        qyid:qyid1,
        qyname:$('#qyname').val(),
        sjqyid:sjqyid,
        cjr:$('#cjr').val(),
        xzjb:xzjb,
        qysp:$('#qysp').val(),
        qyjj:$('#qyjj').val()
    }),function (response) {
        if (response.success == true){
            submitDialog();
        }else{
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg(response.data,{icon: 5});
            });
        }
    })
}

/**
 * 区域信息修改提交
 */
function alterAreal(qyid1) {

    doPost('/area/alterArea',JSON.stringify({
        qyid:qyid1,
        qyname:$('#qyname').val(),
        sjqyid:sjqyid,
        cjr:$('#cjr').val(),
        xzjb:xzjb,
        qysp:$('#qysp').val(),
        qyjj:$('#qyjj').val()
    }),function(respsonse){
        if (respsonse.success == true){
            submitDialog();
        }else{
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.msg(respsonse.data,{icon: 5});
            });
        }
    })
}

function submitDialog() {
    layui.use('layer',function () {
        var layer = layui.layer;
        layer.alert('提交成功', {
            closeBtn: 0
        }, function () {
            goback();
        });
    })

}
/**
 * 取消和返回都要返回到region.html
 */
function goback() {
    window.location.href = 'region.html';
}
/**
 * getAjqy获取省级区域
 * getBjqy获取市级区域
 * getCjqy获取区县级区域
 * getDjqy获取乡镇级区域
 */
function getAjqy() {
    doPost('/area/getQyname',JSON.stringify({
        sjqyid:undefined,
        xzjb:1
    }),function (response) {
        $('#ajqy').empty();
        var abody = '<select id="aqyid" onchange="getBjqy()" style="height: 30px;line-height: 30px;"><option value="">省级行政区域&nbsp;&nbsp;</option>';
        $.each(response.data,function (n,value) {
            abody += "<option value='"+value.QYID+"'>"+value.QYNAME+"</option>";
        })
        $('#ajqy').append(abody + "</select>");
    })
}

function getBjqy(qyid) {
    doPost('/area/getQyname',JSON.stringify({
        sjqyid:$('#aqyid').val(),
        xzjb:2
    }),function (response) {
        $('#bjqy').empty();
        $('#cjqy').empty();
        $('#djqy').empty();
        if (isNotEmpty($('#aqyid').val())) {
            var bbody = '<select id="bqyid" onchange="getCjqy()" style="height: 30px;line-height: 30px;"><option value="">市级行政区域&nbsp;&nbsp;</option>';
            $.each(response.data, function (n, value) {
                bbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
            })
            $('#bjqy').append(bbody + "</select>");
        }
    })
    if (isNotEmpty(qyid)){
        $('#bqyid').val(qyid)
    }else{}
}

function getCjqy(qyid) {
    doPost('/area/getQyname',JSON.stringify({
        sjqyid:$('#bqyid').val(),
        xzjb:3
    }),function (response) {
        $('#cjqy').empty();
        $('#djqy').empty();
        if (isNotEmpty($('#bqyid').val())) {
            var cbody = '<select id="cqyid" onchange="getDjqy()" style="height: 30px;line-height: 30px;"><option value="">区县级行政区域&nbsp;&nbsp;</option>';
            $.each(response.data, function (n, value) {
                cbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
            })
            $('#cjqy').append(cbody + "</select>");
        }
    })
    if (isNotEmpty(qyid)){
        $('#cqyid').val(qyid)
    }else{}
}

function getDjqy(qyid) {
    doPost('/area/getQyname',JSON.stringify({
        sjqyid:$('#cqyid').val(),
        xzjb:4
    }),function (response) {
        $('#djqy').empty();
        if (isNotEmpty($('#cqyid').val())) {
            var dbody = '<select id="dqyid" style="height: 30px;line-height: 30px;"><option value="">乡镇级行政区域&nbsp;&nbsp;</option>';
            $.each(response.data, function (n, value) {
                dbody += "<option value='" + value.QYID + "'>" + value.QYNAME + "</option>";
            })
            $('#djqy').append(dbody + "</select>");
        }
    })
    if (isNotEmpty(qyid)){
        $('#dqyid').val(qyid)
    }else{}
}

/**
 * 修改排名信息时，所属区域<select>初始显示信息。
 */
function showQy(qyid) {
    doPost('/area/getSjQyname',qyid,function (response) {
        var areas = response.data;
        //赋值省级区域
        $('#aqyid').val(areas[0].QYID);
        if (1 < areas.length){//有市级区域
            doPost('/area/getQyname',JSON.stringify({
                sjqyid:$('#aqyid').val(),
                xzjb:2
            }),function (response) {
                $('#bjqy').empty();
                var bbody = '<select id="bqyid" onchange="getCjqy()" class="layui-input"><option value="">市级行政区域&nbsp;&nbsp;</option>';
                $.each(response.data,function (n,value) {
                    bbody += "<option value='"+value.QYID+"'>"+value.QYNAME+"</option>";
                })
                $('#bjqy').append(bbody + "</select>");
                //赋值市级区域
                $('#bqyid').val(areas[1].QYID);

                if (2 < areas.length){//有区县级区域
                    doPost('/area/getQyname',JSON.stringify({
                        sjqyid:$('#bqyid').val(),
                        xzjb:3
                    }),function (response) {
                        $('#cjqy').empty();
                        var cbody = '<select id="cqyid" onchange="getDjqy()" class="layui-input"><option value="">区县级行政区域&nbsp;&nbsp;</option>';
                        $.each(response.data,function (n,value) {
                            cbody += "<option value='"+value.QYID+"'>"+value.QYNAME+"</option>";
                        })
                        $('#cjqy').append(cbody + "</select>");
                        //赋值区县级区域
                        $('#cqyid').val(areas[2].QYID);

                        if (3 < areas.length){//有乡镇级区域
                            doPost('/area/getQyname',JSON.stringify({
                                sjqyid:$('#cqyid').val(),
                                xzjb:4
                            }),function (response) {
                                $('#djqy').empty();
                                var dbody = '<select id="dqyid" class="layui-input"><option value="">乡镇级行政区域&nbsp;&nbsp;</option>';
                                $.each(response.data,function (n,value) {
                                    dbody += "<option value='"+value.QYID+"'>"+value.QYNAME+"</option>";
                                })
                                $('#djqy').append(dbody + "</select>");
                                //赋值乡镇级区域
                                $('#dqyid').val(areas[3].QYID);
                            })
                        }else{
                            return;
                        }
                    })
                }else{
                    return ;
                }
            })
        }else{
            return;
        }
    })
}

/**
 * 获取所属区域ID
 */
function getQyid() {
    var qyid = '';
    if (isNotEmpty($('#dqyid').val())){
        qyid = $('#dqyid').val();
        sjqyname2 = $('#dqyid').find("option:selected").text();
        xzjb = 5;
    }else if(isNotEmpty($('#cqyid').val())){
        qyid = $('#cqyid').val();
        sjqyname2 = $('#cqyid').find("option:selected").text();
        xzjb = 4;
    }else if(isNotEmpty($('#bqyid').val())){
        qyid = $('#bqyid').val();
        sjqyname2 = $('#bqyid').find("option:selected").text();
        xzjb = 3;
    }else if (isNotEmpty($('#aqyid').val())){
        qyid = $('#aqyid').val();
        sjqyname2 = $('#aqyid').find("option:selected").text();
        xzjb = 2;
    }else {
        xzjb = 1;
    }
    return qyid;
}


/**
 * 随着区域名称的输入该变区域名称首拼
 */
function setFirstCharacter() {
    var Chinese = $('#qyname').val();
    var firstCharacter = getPinYinFirstCharacter(Chinese,null,false);
    $("#qysp").val(firstCharacter.replace(/\s+/g,""));
}


function getqyidArr(userqyid) {

    var userArr = new Array();

    if (userqyid.length == 2) {
        userArr.push(userqyid.substring(0, userqyid.length));
    } else if (userqyid.length > 2 && userqyid.length <= 4) {
        userArr.push(userqyid.substring(0, 2));
        userArr.push(userqyid.substring(0, userqyid.length));
    } else if (userqyid.length > 4 && userqyid.length <= 6){
        userArr.push(userqyid.substring(0, 2));
        userArr.push(userqyid.substring(0, 4));
        userArr.push(userqyid.substring(0, userqyid.length));
    } else if (userqyid.length > 6){
        userArr.push(userqyid.substring(0, 2));
        userArr.push(userqyid.substring(0, 4));
        userArr.push(userqyid.substring(0, 6));
        userArr.push(userqyid.substring(0, userqyid.length));
    }
    return userArr;
}

