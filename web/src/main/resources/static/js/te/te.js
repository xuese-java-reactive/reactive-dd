var tb = table('table','te','te',[
    {
        "title": "序号",
        "data": null,
        "render":function(data,type,row,meta) {
            return meta.row + 1;
        }
    },
    {
        "title": "测试a",
        "data": "a"
    },
    {
        "title": "操作",
        "data": null,
        "render":function(data,type,row,meta) {
            let rest = '<button type="button" class="btn btn-danger btn-xs" onclick="remove(\''+data.uuid+'\')">删除</button>';
            let update = '<button type="button" class="btn btn-warning btn-xs" onclick="toUpdate(\''+data.uuid+'\')">修改</button>';
            return rest+"&nbsp;&nbsp;&nbsp;"+update;
        }
    }
]);
$("div.toolbar").prepend('<button type="button" class="btn btn-success btn-xs" data-toggle="modal" data-target="#add-model" id="add-model-open-button">新增</button>');


$(function(){
    $("#add-form").on("submit", function (ev) {
        ev.preventDefault();
        let formData = $('#add-form').serializeObject();
        $.ajax({
            url:"/api/te/te",
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
            url:"/api/te/te/"+$("#id-edit").val(),
            contentType : "application/json;charset=UTF-8",
            dataType:"json",
            data:JSON.stringify(formData),
            type:"PUT",
            success:function(req){
                if(req.state){
                    success_swal(null)
                    $('#edit-form')[0].reset()
                    $("#edit-model-open-button").click();
                    tablesReload(tb)
                }else{
                    error_swal(req.msg)
                }
            }
        });
    })
})

function toUpdate(data){
    $.ajax({
        url:"/api/te/te/"+data,
        contentType : "application/json;charset=UTF-8",
        dataType:"json",
        type:"GET",
        success:function(req){
            if(req.state){
                let data = req.data
                $("#edit-model-open-button").click();
                $("#id-edit").val(data.uuid)
            }else{
                error_swal(req.msg)
            }
        }
    });
}

function remove(data){
    confirmations("确定删除吗?",function(){
        $.ajax({
            url:"/api/te/te/"+data,
            contentType : "application/json;charset=UTF-8",
            dataType:"json",
            type:"DELETE",
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