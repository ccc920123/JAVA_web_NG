/**
 * 入口函数
 */
$(function () {

    addOrUpdate = GetRequest().op;
    id = GetRequest().id;
    if(addOrUpdate == 'edit'){
        $("#title").html("修改友情链接");
        $("#title2").html("修改友情链接");
    }

    getIdLinkData(id)


})
var id='';
function getIdLinkData(id) {

        doGet("/link/IdLink",{id:id},function (response) {


        setData(response.data)
    })


}

function setData(data) {

    $("#linkname").val(data.bt);
    $("#linkhref").val(data.lj);

}
//提交数据
function submit() {
    if(addOrUpdate == 'edit'){
        console.log(GetRequest().id);
        doPost("/link/updateLink", JSON.stringify({
            id:GetRequest().id,
            bt: $("#linkname").val(),
            lj: $("#linkhref").val(),
        }), function (response) {
            if (response.success == true){
                goback();
            }else{
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg('修改失败',{icon: 5});
                });
            }
        })
    }else {
        doPost("/link/insertLink", JSON.stringify({
            bt: $("#linkname").val(),
            lj: $("#linkhref").val(),
        }), function (response) {
            if (response.success == true){
                goback();
            }else{
                layui.use('layer', function(){
                    var layer = layui.layer;
                    layer.msg(response.data,{icon: 5});
                });
            }

        })
    }

}

/**
 * 取消和返回都要返回到Friendship_link.html
 */
function goback() {
    window.location.href = 'Friendship_link.html';
}