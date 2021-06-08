var tb = table('table','org','org',[
    {
        "title": "序号",
        "data": null,
        "render":function(data,type,row,meta) {
            return meta.row + 1;
        }
    },
    {
        "title": "",
        "data": "pid"
    },
    {
        "title": "",
        "data": "name"
    },
    {
        "title": "操作",
        "data": null,
        "render":function(data,type,row,meta) {
            let rest = '<button type="button" class="btn btn-danger btn-xs">删除</button>';
            let update = '<button type="button" class="btn btn-warning btn-xs">修改</button>';
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
            url:"/api/org/org",
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
            url:"/api/org/org/"+$("#id-edit").val(),
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

function toUpdate(data){
    $.ajax({
        url:"/api/org/org/"+data,
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
            }else{
                error_swal(req.msg)
            }
        }
    });
}

function remove(data){
    confirmations("确定删除吗?",function(){
        $.ajax({
            url:"/api/org/org/"+data,
            contentType : "application/json;charset=UTF-8",
            dataType:"json",
            type:"DELETE",
            beforeSend:function(){
            },
            success:function(req){
                if(req.state){
                    success_swal(null)
                    tablesReload
                }else{
                    error_swal(req.msg)
                }
            }
        });
    })
}