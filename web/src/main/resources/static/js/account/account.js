tables('table','account','account',[
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
        "title": "状态",
        "data": null,
        "render":function(data,type,row,meta) {
            let green = '<span class="badge bg-success">正常</span>';
            let red = '<span class="badge bg-warning">离职</span>';
            return data.state === '0' ? green : red;
        }
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
]);

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
                    tablesReload
                }else{
                    error_swal(req.msg)
                }
            }
        });
    })
}