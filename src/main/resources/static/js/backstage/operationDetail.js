$(function () {
    var postData = GetRequest();
  var  id = postData.id;

    doGet("/operationLog/queryOperationDetail",{id:id},function (response) {
        if (response.success) {
            $.each(response.data,function (key ,value) {
                $('#'+key).val(value);
            });
        }
    });

});