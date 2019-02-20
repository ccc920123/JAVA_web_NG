/**
 * 入口函数
 */
var xzjb = 0;
$(function(){
    // 设置默认时间为今天
    var myDate = new Date();
    var valTime = myDate.getFullYear() + '-' + (myDate.getMonth()+1) + '-' + myDate.getDate();
    $('#dp11').val(valTime + '~' + valTime).attr('kssj', valTime);
    $('#dp11').val(valTime + '~' + valTime).attr('jssj', valTime);
    var id_user = getUserqyidStr();
    getSjqy(id_user);
    justPression();
    $("#sj").change(function () {
        getXjqy();
    })
})


/**
 * 获取市级行政区域
 */
function getSjqy(qyid) {
    xzjb = getUserXzjbStr();
    var sjqyid =  new Array();
    doPost('/area/getArea', qyid, function (response) {

        var tsjqy = "<option value=''>----请选择市级----</option>";
        $.each(response.data,function (n,value) {
            if (value.xzjb == 2){//行政级别为市级
                tsjqy += "<option value='"+ value.qyid +"'>"+ value.qyname +"</option>";
                sjqyid[n] = value.qyid;
            }
        })
        $("#sj").html(tsjqy);
        if (xzjb == 1){

        } else if (xzjb == 2) {
            $("#sj").val(qyid);
            $("#sj ").prop("disabled", true);

            getXjqy();
        } else if (xzjb == 3) {
            var txjqy = "<option value=''>----请选择区县----</option>";
            $.each(response.data, function (n, value) {
                if (value.xzjb == 3) {
                    txjqy += "<option value='" + value.qyid + "'>" + value.qyname + "</option>";
                }
            })
            $('#xj').html(txjqy);
            $("#sj").val(sjqyid[1]);
            $("#xj").val(qyid);
            $("#sj ").prop("disabled", true);
            $("#xj ").prop("disabled", true);
        } else if (xzjb == 4){
            $("#sj ").prop("disabled", true);
            $("#xj ").prop("disabled", true);
        }

        if (justPressionBy("221")) {
            statisticsDtInfo();
        } else {
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.msg("该用户暂无此权限！", {icon: 5});
            });
        }
    })
}
/**
 * 获取县级区域
 */
function getXjqy() {
    var sjname = $('#sj').val();
    if (sjname == ''){
        $('#xj').empty();
        var txjqy = "<option value=''>----请选择区县----</option>";
        $('#xj').html(txjqy);
        return;
    }
    doPost('/area/getArea',$('#sj').val(),function (response) {

        $('#xj').empty();
        var txjqy = "<option value=''>----请选择区县----</option>";
        $.each(response.data,function(n,value){
            if (value.xzjb == 3){
                txjqy += "<option value='"+ value.qyid +"'>"+ value.qyname +"</option>";
            }
        })
        $('#xj').html(txjqy);
    })
}

/**
 * 获取排名统计信息
 */
function statisticsDtInfo() {
    if (xzjb == 4){
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.msg("该用户行政级别无权限查看新闻统计数量",{icon: 5});
        });
        return;
    }

    doPost('/dtgl/getStatistic',JSON.stringify({
        kssj:$('#dp11').attr('kssj'),
        jssj:$('#dp11').attr('jssj'),
        sjqy:$('#sj').val(),
        qxqy:$('#xj').val()
    }),function (response) {
        var tbody = "";
        if (response.success == true){
            $('#pmkssj').html($('#dp11').attr('kssj'));
            $('#pmjssj').html($('#dp11').attr('jssj'));
            $('#tbody').empty();
            $.each(response.data,function (n,value) {
                $('#sjqyname').html(value.SJQYNAME);
                tbody += "<tr>" +
                    "<td>"+ (n + 1) +"</td>" +
                    "<td>" + value.QYNAME + "</td>" +
                    "<td>" + value.TOTAL +"</td>" +
                    "<td>" + value.SUBMIT +"</td>" +
                    "</tr>";
            })
        }else {

        }
        $('#tbody').html(tbody);

    })
}

