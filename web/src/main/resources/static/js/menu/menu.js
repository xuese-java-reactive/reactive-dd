var setting = {
    view:{
        showLine: false,
        selectedMulti: false,
        showIcon: false,
        addDiyDom: addDiyDom
    },
    callback:{
        onClick: getItem,
        onRename: renameItem,
        beforeRemove: removeItem,
        beforeDrag: beforeDrag,
        beforeDrop: beforeDrop,
        onDrop:onDrop
    },
    edit:{
        enable:true,
        removeTitle : "删除",
        renameTitle : "编辑名称",
        drag: {
            isCopy: false,//所有操作都是move
            isMove: true,
            prev: true,
            next: true,
            inner: true
        }
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
            success:function(req){
                if(req.state){
                    $('#form-add')[0].reset()
                    findMenus()
                }else{
                    error_swal(req.msg)
                }
            }
        });
    })

    $('#set-pid').on('click',function(){
        $('#pid-add').val(0)
        $('#pid-name-add').val('顶级')
    })
});

function findMenus(){
    $.ajax({
        url:"/api/menu/menu/0",
        contentType : "application/json;charset=UTF-8",
        dataType:"json",
        type:"GET",
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
        url:"/api/menu/menu/"+treeNode.uuid,
        contentType : "application/json;charset=UTF-8",
        dataType:"json",
        type:"PUT",
        data:JSON.stringify({
            "name":treeNode.name
        }),
        success:function(req){
            if(!req.state){
                error_swal(req.msg)
            }
        },
        complete:function(){
            findMenus()
       },
    });
}

function removeItem(treeId, treeNode) {
   $.ajax({
       url:"/api/menu/menu/"+treeNode.uuid,
       contentType : "application/json;charset=UTF-8",
       dataType:"json",
       type:"DELETE",
       success:function(req){
           if(!req.state){
               error_swal(req.msg)
           }
       },
       complete:function(){
            findMenus()
       },
   });
}

function beforeDrag(treeId, treeNodes) {
    return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType) {
    return targetNode ? targetNode.drop !== false : true;
}
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
    let pid = targetNode.pid;
    if(moveType==='inner'){
        pid = targetNode.uuid
    }
    $.ajax({
        url:"/api/menu/menu/"+treeNodes[0].uuid,
        contentType : "application/json;charset=UTF-8",
        dataType:"json",
        type:"PUT",
        data:JSON.stringify({
            "pid":pid
        }),
        success:function(req){
            if(!req.state){
                error_swal(req.msg)
            }
        },
        complete:function(){
            findMenus()
       },
    });
}

/**
 * 自定义DOM节点
 */
function addDiyDom(treeId, treeNode) {
    var spaceWidth = 15;
    var liObj = $("#" + treeNode.tId);
    var aObj = $("#" + treeNode.tId + "_a");
    var switchObj = $("#" + treeNode.tId + "_switch");
    var icoObj = $("#" + treeNode.tId + "_ico");
    var spanObj = $("#" + treeNode.tId + "_span");
    aObj.attr('title', '');
    aObj.append('<div class="divTd swich fnt" style="width:60%"></div>');
    var div = $(liObj).find('div').eq(0);
    //从默认的位置移除
    switchObj.remove();
    spanObj.remove();
    icoObj.remove();
    //在指定的div中添加
    div.append(switchObj);
    div.append(spanObj);
    //隐藏了层次的span
    var spaceStr = "<span style='height:1px;display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
    switchObj.before(spaceStr);
    //图标垂直居中
    icoObj.css("margin-top","9px");
    switchObj.after(icoObj);
    var editStr = '';
    //宽度需要和表头保持一致
    editStr += '<div class="divTd" style="width:20%">' + (treeNode.typecode  == null ? '' : treeNode.typecode ) + '</div>';
    editStr += '<div class="divTd" style="width:10%">' + (treeNode.status == '1' ? '有效' : '无效' ) + '</div>';
    editStr += '<div class="divTd" style="width:10%">' + opt(treeNode) + '</div>';
    aObj.append(editStr);
}