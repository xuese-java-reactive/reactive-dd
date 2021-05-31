var setting = {
    view:{
        selectedMulti: false
    },
    callback:{
        onClick: getItem
    }
};

// 2.页面加载完毕后发起异步请求获得json格式的数据
$(document).ready(function(){

//    获取所有的菜单
    findMenus()

//    菜单新增
    $("#form-add").on("submit", function (ev) {
        ev.preventDefault();
        let formData = $('#form-add').serializeObject();
        $.ajax({
            url:"/api/menu/menu",
            contentType : "application/json;charset=UTF-8",
            dataType:"json",
            data:JSON.stringify(formData),
            type:"POST",
            beforeSend:function(){
            },
            success:function(req){
                if(req.state){
                    $('#form-add')[0].reset()
                    $("#modal-add-close").click();
                    findMenus()
                }else{
                    alert(req.msg)
                }
            },
            complete:function(){
            },
            error:function(e){
                console.log("error",e.responseText);
            }
        });
    })
});

function findMenus(){
    $.ajax({
        url:"/api/menu/menu/0",
        contentType : "application/json;charset=UTF-8",
        dataType:"json",
        type:"GET",
        beforeSend:function(){
        },
        success:function(req){
            if(req.state){
                initTree(req.data)
            }
        }
    });
}

// 3.定义zTree树
var zTreeObj;
// 4.根据获取到的json数据展示ztree树
function initTree(data) {
    //第一个参数：树显示的位置，第二个参数：树的配置信息，第三个参数：要展示的数据
    zTreeObj = $.fn.zTree.init($("#tree"), setting, data);
    //true：展开所有节点
    zTreeObj.expandAll(false);
}

function getItem(event, treeId, treeNode, clickFlag) {
    $("#menu-name").text(treeNode.name)
}