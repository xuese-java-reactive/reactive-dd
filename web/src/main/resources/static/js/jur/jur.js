var setting = {
    check: {
        enable: true,
        chkboxType: {"Y":"","N":""}
    },
    view:{
        selectedMulti: false,
        showIcon: false
    },
    callback:{
        onClick: getItem,
    }
};
// 3.定义zTree树
var zTreeObj;
// 4.根据获取到的json数据展示zTree树
function initTree(data) {
    //第一个参数：树显示的位置，第二个参数：树的配置信息，第三个参数：要展示的数据
    zTreeObj = $.fn.zTree.init($("#tree"), setting, data);
    //true：展开所有节点
    zTreeObj.expandAll(true);
}

function getItem(event, treeId, treeNode, clickFlag) {
    zTreeObj.selectNode(treeNode);
}

var tb = table('table','account','account',[
    {
       "title": "序号",
       "data": null,
       "render":function(data,type,row,meta) {
           return meta.row + 1;
       }
    },
    {
       "title": "账号",
       "data": "account"
    }
]);
$(tb).DataTable( {
   select: {
       style: 'single'
   }
});

$(function(){

//    获取所有的菜单
    findMenus()

//  获取指定账号的权限信息
    $('#table tbody').on('click','tr',function(){
        let d = tb.row(this).data()
        let uuid = d.uuid;
        let account = d.account;
        $.ajax({
            url:"/api/jur/jur/"+uuid,
            contentType : "application/json;charset=UTF-8",
            dataType:"json",
            type:"GET",
            beforeSend:function(xhr) {
                 $("#select-acc-span").text(account)
                 $("#select-acc-span").attr("data-id",uuid)
                 let nodes = zTreeObj.getCheckedNodes();
                 $(nodes).each(function(k,v){
                    zTreeObj.checkNode(v,false,false)
                 })
            },
            success:function(req){
                if(req.state){
                    $(req.data).each(function(k,v){
                        let node = zTreeObj.getNodeByParam('uuid',v.menuId);
                        zTreeObj.checkNode(node,true,false)
                    })
                }else{
                    error_swal(req.msg)
                }
            }
        });
    });

    $(".add-button").on("click", function(){
        $("#add-form").find("input").remove();
        $("#add-form").append('<input type="hidden" name="accId" value="'+$("#select-acc-span").attr("data-id")+'"/>');
        let nodes = zTreeObj.getCheckedNodes();
        let menus = "";
        $(nodes).each(function(i,e){
            menus += e.uuid+",";
        });
        $("#add-form").append('<input type="hidden" name="menuId" value="'+menus+'"/>')
        let formData = $('#add-form').serializeObject();
        $.ajax({
            url:"/api/jur/jur",
            contentType : "application/json;charset=UTF-8",
            dataType:"json",
            data:JSON.stringify(formData),
            type:"POST",
            success:function(req){
                if(req.state){
                    success_swal(null)
                }else{
                    error_swal(req.msg)
                }
            }
        });
    })

})

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
