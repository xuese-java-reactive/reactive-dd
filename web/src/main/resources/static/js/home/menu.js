$(function(){
    getMenus()
});

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
                setMenus(req.data)
            }
        }
    });
}

//<li class="nav-item">
//    <a href="#" class="nav-link ">
//        <i class="nav-icon fas fa-tachometer-alt"></i>
//        <p>
//            Starter Pages
//            <i class="right fas fa-angle-left"></i>
//        </p>
//    </a>
//    <ul class="nav nav-treeview" style="display: none;">
//        <li class="nav-item">
//            <a href="#" class="nav-link ">
//                <i class="far fa-circle nav-icon"></i>
//                <p> Page</p>
//            </a>
//        </li>
//        <li class="nav-item">
//            <a href="#" class="nav-link">
//                <i class="far fa-circle nav-icon"></i>
//                <p>In Page</p>
//            </a>
//        </li>
//    </ul>
//</li>

function setMenus(json){

    $(json).each(function(i,e){
        if(e.pid == '0'){
            $('#menus').append(getLis(e))
        }
    })
}

function getLis(data){
    let h_li = $("<li class=\"nav-item\"></li>")
    let h_a = $("<a href=\"#\" class=\"nav-link\"></a>")
    let h_i = $("<i class=\"nav-icon fas fa"+data.ico+"\"></i>")
    let h_p = $("<p>"+data.name+"</p>")
    let h_i2 = $("<i class=\"right fas fa-angle-left\"></i>")
    let h_ul = $("<ul class=\"nav nav-treeview\" style=\"display: none;margin-left:5px;width:auto !important;\"></ul>")

    $(h_li).append(h_a)
    $(h_a).append(h_i)
    $(h_a).append(h_p)

    if(data.children){
        $(h_p).append(h_i2)
        $(h_li).append(h_ul)
        $(data.children).each(function(i2,e2){
            $(h_ul).append(getLis(e2))
        })
    }

    if(data.p){
        $(h_a).attr("onclick","toHtml('"+data.p+"')")
    }

    return $(h_li);
}