
var setting = {
    view:{
        selectedMulti: false
    },
    callback:{
        onClick: getItem
    },
    edit:{
        enable:false,
        drag: {
            isCopy: false,//所有操作都是move
            isMove: false,
            prev: false,
            next: false,
            inner: false
        }
    }
};
// 3.定义zTree树
var zTreeObj;
// 4.根据获取到的json数据展示ztree树
function initTree(data) {
    //第一个参数：树显示的位置，第二个参数：树的配置信息，第三个参数：要展示的数据
    zTreeObj = $.fn.zTree.init($("#tree"), setting, data);
    //true：展开所有节点
    zTreeObj.expandAll(true);
}
function getItem(event, treeId, treeNode, clickFlag) {
    $('#org-edit').val(treeNode.uuid)
    $('#org-name-edit').val(treeNode.name)
    zTreeObj.selectNode(treeNode);
}
function findOrg(){
    $.ajax({
        url:"/api/org/org/0",
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
    },
    {
       "title": "组织机构",
       "data": "orgModel.name"
    },
    {
        "title": "状态",
        "data": null,
        "render":function(data,type,row,meta) {
            let green = '<span class="badge bg-success">正常</span>';
            let red = '<span class="badge bg-warning">离职</span>';
            return data.state === 1 ? green : red;
        }
    },
    {
       "title": "操作",
       "data": null,
       "render":function(data,type,row,meta) {
            let rest = '<button type="button" class="btn btn-danger btn-xs" onclick="restPwd(\''+data.uuid+'\')">重置密码</button>';
            let update = '<button type="button" class="btn btn-warning btn-xs" onclick="toUpdate(\''+data.uuid+'\')">修改</button>';
            return rest+"&nbsp;&nbsp;&nbsp;"+update;
       }
    }
]);
$("div.toolbar").prepend('<button type="button" class="btn btn-success btn-xs" data-toggle="modal" data-target="#add-model" id="add-model-open-button">新增</button>');

$(function(){

//获取组织机构
    findOrg()

    $("#add-form").on("submit", function (ev) {
        ev.preventDefault();
        let formData = $('#add-form').serializeObject();
        $.ajax({
            url:"/api/account/account",
            contentType : "application/json;charset=UTF-8",
            dataType:"json",
            data:JSON.stringify(formData),
            type:"POST",
            success:function(req){
                if(req.state){
                    success_swal(null)
                    $('#add-form')[0].reset()
                    $("#add-model-open-button").click();
                    tablesReload(tb)
                }else{
                    error_swal(req.msg)
                }
            }
        });
    })

    $("#edit-form").on("submit", function (ev) {
        ev.preventDefault();
        let formData = $('#edit-form').serializeObject();
        $.ajax({
            url:"/api/account/account/"+$("#id-edit").val(),
            contentType : "application/json;charset=UTF-8",
            dataType:"json",
            data:JSON.stringify(formData),
            type:"PUT",
            beforeSend:function(){
            },
            success:function(req){
                if(req.state){
                    success_swal(null)
                    $('#edit-form')[0].reset()
                    $("#edit-model-open-button").click();
                    tablesReload(tb)
                }else{
                    error_swal(req.msg)
                }
            },
            complete:function(){
            },
            error:function(e){
                console.log("error",e.responseText);
            }
        });
    })
})

function restPwd(data){
    confirmations("确定重置密码吗?",function(){
        $.ajax({
            url:"/api/account/account/rest/"+data,
            contentType : "application/json;charset=UTF-8",
            dataType:"json",
            type:"PUT",
            beforeSend:function(){
            },
            success:function(req){
                if(req.state){
                    success_swal(null)
                    tablesReload(tb)
                }else{
                    error_swal(req.msg)
                }
            }
        });
    })
}

function toUpdate(data){
    $.ajax({
        url:"/api/account/account/"+data,
        contentType : "application/json;charset=UTF-8",
        dataType:"json",
        type:"GET",
        beforeSend:function(){
        },
        success:function(req){
            if(req.state){
                let data = req.data
                $("#edit-model-open-button").click();
                $("#id-edit").val(data.uuid)
                $("#account-edit").val(data.account)
                $("#state-edit").val(data.state)
                $('#org-edit').val(data.orgModel.uuid)
                $('#org-name-edit').val(data.orgModel.name)
            }else{
                error_swal(req.msg)
            }
        }
    });
}
