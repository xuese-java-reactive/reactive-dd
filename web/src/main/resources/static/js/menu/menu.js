var setting = {
    view: {
        selectedMulti: false
    },
};

// 2.页面加载完毕后发起异步请求获得json格式的数据
$(document).ready(function(){
    $.ajax({
        url:"/api/menu/menu/0",
        contentType : "application/json;charset=UTF-8",
        dataType:"json",
        type:"GET",
        beforeSend:function(){
        },
        success:function(req){
            console.log(req)
            if(req.state){
                initTree(req.data)
            }
        }
    });
});

// 3.定义zTree树
var zTreeObj;
// 4.根据获取到的json数据展示ztree树
function initTree(data) {
    //第一个参数：树显示的位置，第二个参数：树的配置信息，第三个参数：要展示的数据
    zTreeObj = $.fn.zTree.init($("#tree"), setting, data);
    //true：展开所有节点
    zTreeObj.expandAll(false);
}