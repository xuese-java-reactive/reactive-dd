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
            "title": "",
            "data": "uuid"
        },
        {
            "title": "",
            "data": "pId"
        },
        {
            "title": "",
            "data": "name"
        },
        {
            "title": "跳转路径 例如：menu/menu",
            "data": "p"
        },
        {
            "title": "图标",
            "data": "ico"
        },
        {
            "title": "权限标识",
            "data": "jur"
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
    ]

    let table = tables('table','test','test',columns);
//  配置每页显示条数
    table.page.len(5).draw();

    $("#table_search_btn").on("click",function(ev){
        table.ajax.reload();
    })

})