$(function(){
    //默认首页
    toHtml('home/right');
})

//跳转页面
function toHtml(obj){
    $.ajax({
        url:'/page/'+obj,
        dataType:"html",
        type:"GET",
        success:function(req){
            $('#content-wrapper').html(req)
        },
        complete:function(){
        }
    });
}

//注销
function logout(){
    localStorage.clear();
    location.replace(root+"/");
}
