var setting = {
    view:{
        selectedMulti: false
    },
    callback:{
        onClick: getItem,
        onRename: renameItem,
        beforeRemove: removeItem
    },
    edit:{
        enable:true
    }
};

// 2.页面加载完毕后发起异步请求获得json格式的数据
$(document).ready(function(){

//    获取所有的菜单
    findOrg()

//    菜单新增
    $("#form-add").on("submit", function (ev) {
        ev.preventDefault();
        let formData = $('#form-add').serializeObject();
        $.ajax({
            url:"/api/org/org",
            contentType : "application/json;charset=UTF-8",
            dataType:"json",
            data:JSON.stringify(formData),
            type:"POST",
            beforeSend:function(){
            },
            success:function(req){
                if(req.state){
                    $('#form-add')[0].reset()
                    findOrg()
                }else{
                    alert(req.msg)
                }
            }
        });
    })

    $('#set-pid').on('click',function(){
        $('#pid-add').val(0)
        $('#pid-name-add').val('顶级')
    })
});

function findOrg(){
    $.ajax({
        url:"/api/org/org/0",
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
    $('#pid-add').val(treeNode.uuid)
    $('#pid-name-add').val(treeNode.name)
    zTreeObj.selectNode(treeNode);
}

function renameItem(event, treeId, treeNode, clickFlag) {
    $.ajax({
        url:"/api/org/org/"+treeNode.uuid,
        contentType : "application/json;charset=UTF-8",
        dataType:"json",
        data:JSON.stringify({
            "name":treeNode.name
        }),
        type:"PUT",
        beforeSend:function(){
        },
        success:function(req){
            if(!req.state){
                alert(req.msg)
                findOrg()
            }
        }
    });
}

function removeItem(treeId, treeNode) {
   $.ajax({
       url:"/api/org/org/"+treeNode.uuid,
       contentType : "application/json;charset=UTF-8",
       dataType:"json",
       type:"DELETE",
       beforeSend:function(){
       },
       success:function(req){
           if(!req.state){
               alert(req.msg)
               findOrg()
           }
       }
   });
}