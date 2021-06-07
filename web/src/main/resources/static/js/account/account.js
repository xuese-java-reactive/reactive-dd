let table = null;

$(function(){

    let columns = [
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
            "title": "操作",
            "data": null,
            "render":function(data,type,row,meta) {
                let rest = '<button type="button" class="btn btn-danger btn-xs" onclick="restPwd(\''+data.uuid+'\')">重置密码</button>';
                let update = '<button type="button" class="btn btn-warning btn-xs">修改状态</button>';
                return rest+"&nbsp;&nbsp;&nbsp;"+update;
            }
        }
    ]

    table = tables('table','account','account',columns);
//  配置每页显示条数
//    table.page.len(5).draw();

})

function restPwd(data){
    $.ajax({
        url:"/api/account/account/rest/"+data,
        contentType : "application/json;charset=UTF-8",
        dataType:"json",
        type:"PUT",
        beforeSend:function(){
        },
        success:function(req){
            if(req.state){
                table.ajax.reload();
            }else{
                alert(req.msg)
            }
        }
    });
}