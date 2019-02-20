
/**
 * 用户登录函数，bootstrap表单验证通过才能执行
 *
 */
var layer;
$(function () {
    layui.use('layer', function() {
        layer = layui.layer;

    })
    $('body').keydown(function () {
        if(event.keyCode == '13'){
            $('#btnLogin').click();
        }
    })
})

function login() {
    var account=$("#uname").val();
    var password=$("#pwd").val();

    if (account==undefined||account==""){
        layer.msg("请输入账号", {icon: 5});
        return;
    }
    if (password==undefined||password==""){
        layer.msg("请输入密码", {icon: 5});
        return;
    }
    // account=encryptByDES(account);
    password=hex_md5(password);

     doPost("/user/login",JSON.stringify({
         account:account,
         password:password
     }),function (response) {
         if (response.success === false){
                 layer.msg(response.data, {icon: 5});
         }else if(response == null){
             layer.msg("系统内部异常", {icon: 5});
         } else {
             window.location.href = './main.html';
         }
     })

}


