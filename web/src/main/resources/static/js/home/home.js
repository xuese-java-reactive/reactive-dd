$(function(){
    //默认首页
    toHtml('home/right');
//    加载菜单
    getMenus()
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

//加载菜单
function getMenus(){
    $.ajax({
        url:'/api/menu/menu/0',
        dataType:"json",
        type:"GET",
        beforeSend:function(){
            $('#menus').find("li").remove();
        },
        success:function(req){
            if(req.state){
                serverArray(req.data)
            }
        }
    });
}

function serverArray(arr){
    for(var item = 0;item < arr.length;item++){
        if(arr[item].children){
            serverArray(arr[item].children)
        }else{
            //        arr1.push(arr[item])
            let h = '<li class="nav-item">'
            +'     <a href="#" class="nav-link">'
            +'         <i class="fas fa-circle nav-icon"></i>'
            +'         <p>Level 1</p>'
            +'     </a>'
            +'</li>'
            $('#menus').append(h)
        }
    }
}