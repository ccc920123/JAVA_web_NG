var menuIds;

function justPression() {
    var localTest = layui.data('pression');
    if (localTest == undefined) {
        parent.location.href = "./login.html";
    } else {
        menuIds = localTest.menuIds;
        var pression_divs = $(".pression");
        pression_divs.each(function (i, div) {
            var menuid = $(div).attr("pression");
            if (!isInArray3(menuIds, menuid)) {
                $(div).addClass("my_hide");
            }
        });
    }
}
function getUserqyidStr() {
    var localTest = layui.data('userqyid');
    var userqyid = "";
    if (localTest == undefined){

    } else {
        userqyid = localTest.userqyid;
    }
    return userqyid;
}
function getUserXzqyidStr() {
    var localTest = layui.data('xzqyid');
    var xzqyid = "";
    if (localTest == undefined){

    } else {
        xzqyid = localTest.xzqyid;
    }
    return xzqyid;
}
function getUserQynameStr() {
    var localTest = layui.data('userqyname');
    var userQyname = "";
    if (localTest == undefined){

    } else {
        userQyname = localTest.userqyname;
    }
    return userQyname;
}
function getUserXzjbStr() {
    var localTest = layui.data('userxzjb');
    var userxzjb = "";
    if (localTest == undefined){

    } else {
        userxzjb = localTest.userxzjb;
    }
    return userxzjb;
}
function getUserqyid() {
    var localTest = layui.data('userqyid');
    var userqyid;
    var userArr = new Array();
    if (localTest == undefined){

    } else {
        userqyid = localTest.userqyid;
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
    }
    return userArr;
}

function justPressionBy(menuid) {
    return isInArray3(menuIds, menuid);

}
