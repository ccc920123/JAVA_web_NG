/**
 * 入口函数
 */
$(function () {
   getAuthority();
})

/**
 * 获取用户名,若信息为空则返回登录页面
 */
function getAuthority() {
    doPost('/user/getAuth',{},function (response) {
        console.log(response);
        console.log(response.data.sysMenuIds);
        var menuids= response.data.sysMenuIds;
        var userqyid = response.data.user.qyid;
        var userqyname = response.data.user.qyname;
        var userxzjb = response.data.user.xzjb;
        if(response.success == false){
            parent.location.href = "./login.html";
        }else{
            if (menuids==undefined||menuids.length==0){
                alert("当前账号没有任何权限")
                parent.location.href = "./login.html";
            }
            $('#uname').html(response.data.user.relname);
            layui.data('pression', {
                key: 'menuIds'
                ,value: menuids
            });
            layui.data('userqyid', {
                key: 'userqyid'
                ,value: userqyid
            });
            layui.data('userqyname', {
                key: 'userqyname'
                ,value: userqyname
            });
            layui.data('userxzjb', {
                key: 'userxzjb'
                ,value: userxzjb
            });
          var pression_divs=  $(".pression");
            pression_divs.each(function(i, div) {
                var menuid=$(div).attr("pression");
               if(!isInArray3(menuids,menuid)){
                   $(div).css('display','none');
               }
            });
        }
    })
}

/**
 * 用户退出
 */
function userExit() {
    doPost('/user/exit',{},function (response) {
        if (response == true){
            layui.data('pression',null);
            layui.data('userqyid',null);
            layui.data('userqyname',null);
            layui.data('userxzjb',null);
            parent.location.href = './login.html';
        }else {

        }
    })
}


/**
 * 切换至区域管理
 */
function tzqygl() {
    parent.document.getElementById("rightFrame").src = 'region.html';
}
/**
 * 切换至用户信息
 */
function tzyhgl() {
    parent.document.getElementById("rightFrame").src = "user.html";
}
/**
 * 切换至角色信息
 */
function tzjsgl() {
    parent.document.getElementById("rightFrame").src = "role_manage.html";
}
/**
 * 切换至工作排名
 */
function tzgzpm() {
    parent.document.getElementById("rightFrame").src = 'Job_ranking.html';
}


/**
**友情链接
 * */
function tzyqlj() {
    parent.document.getElementById("rightFrame").src = 'Friendship_link.html';
}
/**
**日志管理
 * */
function tzrzgl() {
    parent.document.getElementById("rightFrame").src = 'operationlog.html';
}



/**
 * 切换至用户密码修改
 */
function tzpwd() {
    $(".menu_body a").removeClass("menu_body_bg");
    parent.document.getElementById("rightFrame").src = 'edit_password.html';
}
/**
 * 切换至动态信息
 */
function tzdtgl() {
    parent.document.getElementById("rightFrame").src = 'news.html';
}
/**
 * 切换至动态审核
 */
function tzdtsh() {
    parent.document.getElementById("rightFrame").src = 'news_review.html';
}
/**
 * 切换至统计排名
 */
function tztjpm() {
    parent.document.getElementById("rightFrame").src = 'news_ranking.html';
}
/**
 * 切换至考核配置
 */
function tzkhpz() {
    parent.document.getElementById("rightFrame").src = 'assess_config.html';
}
/**
 * 切换至考核生成
 */
function tzkhsc() {
    parent.document.getElementById("rightFrame").src = 'news_ranking.html';
}
/**
 * 切换至考核查询
 */
function tzkhcx() {
    parent.document.getElementById("rightFrame").src = 'assessQuery.html';
}
/**
 * 切换至综合考核
 */
function tzzhkh() {
    parent.document.getElementById("rightFrame").src = 'assess_complex.html';
}



function systemManage() {
    if ($('#systemMenu').css('display') === 'block'){
        $('#systemMenu').css('display','none');
        $('#systemManage').removeClass("current");
    }else{
        $('#systemMenu').css('display','block');
        $('#systemManage').addClass("current");
    }
    $('#dynamicMenu').css('display','none');
    $('#dynamicManage').removeClass("current");
    $('#assessMenu').css('display','none');
    $('#assessManage').removeClass("current");
}

function dynamicManage() {
    if ($('#dynamicMenu').css('display') === 'block'){
        $('#dynamicMenu').css('display','none');
        $('#dynamicManage').removeClass("current");
    }else{
        $('#dynamicMenu').css('display','block');
        $('#dynamicManage').addClass("current");
    }
    $('#systemMenu').css('display','none');
    $('#systemManage').removeClass("current");
    $('#assessMenu').css('display','none');
    $('#assessManage').removeClass("current");
}
function assessManage() {
    if ($('#assessMenu').css('display') === 'block'){
        $('#assessMenu').css('display','none');
        $('#assessManage').removeClass("current");
    }else{
        $('#assessMenu').css('display','block');
        $('#assessManage').addClass("current");
    }
    $('#systemMenu').css('display','none');
    $('#systemManage').removeClass("current");
    $('#dynamicMenu').css('display','none');
    $('#dynamicManage').removeClass("current");
}
