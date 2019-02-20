/**
 * 入口函数
 */
$(function () {
    var myDate = new Date();
    var valTime = myDate.getFullYear() + '-' + (myDate.getMonth()+1) + '-' + myDate.getDate()
                  + ' ' + (myDate.getHours()) + ':' + myDate.getMinutes() + ':' + myDate.getSeconds();
    var postData = GetRequest();
    dtid = decodeURI(postData.dtnr);//获取动态ID
    $('#currentBt').html(getUserQynameStr());
    $('#bt').html(decodeURI(postData.bt));
    $('#ngr').html(decodeURI(postData.ngr));
    $('#fbsj').html(valTime);

    getDtInfoById(dtid);

})

/**
 * 通过Id获取动态信息
 */

function getDtInfoById(id) {
    doPost('/dtgl/getDtyl',JSON.stringify({
        id:id
    }),function (response) {
        //给页面赋值
        var content = response.data.dtnr;
        if (content == undefined){
            content = "";
        }
         $("#dtnr")[0].innerHTML= content;
        uParse('#dtnr', {
            rootPath: '../../plugs/'
        })
        //给发布区域赋值
    })
}

// /**
//  * 返回
//  */
// function goback() {
//         window.location.href = 'AddNews.html?dtid=no&ly=info';
//
// }