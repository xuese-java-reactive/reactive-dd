$(function(){
    //默认首页
    toHtml('home/right');

    $("#logout-btn").on("click",function(ev){
        localStorage.clear();
        location.replace("/");
    })
})

//跳转页面
function toHtml(obj){
    $.ajax({
        url:'/page/'+obj,
        dataType:"html",
        type:"GET",
        success:function(req){
            $('#content-wrapper').html(req)
        }
    });
}