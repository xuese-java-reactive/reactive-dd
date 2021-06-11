//提示信息
let lang = {
    "sProcessing": "处理中...",
    "sLengthMenu": "每页 _MENU_ 项",
    "sZeroRecords": "没有匹配结果",
    "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
    "sInfoEmpty": "",
    "sInfoFiltered": "",
    "sInfoPostFix": "",
    "sSearch": "搜索:",
    "sUrl": "",
    "sEmptyTable": "表中数据为空",
    "sLoadingRecords": "载入中...",
    "sInfoThousands": ",",
    "oPaginate": {
        "sFirst": "首页",
        "sPrevious": "上页",
        "sNext": "下页",
        "sLast": "末页",
        "sJump": "跳转"
    },
    "oAria": {
        "sSortAscending": ": 以升序排列此列",
        "sSortDescending": ": 以降序排列此列"
    }
};

let tableSetting = function(path1,path2,columns){
    return {
    //        关闭排序
        "ordering":false,
    //        关闭更改每页条数的下拉框
        "lengthChange": false,
    //        显示等待提示语
        "processing": true,
    //        延迟渲染，提交初始加载速度
        "deferRender": true,
    //        相关提示信息
        "language": lang,
        //为奇偶行加上样式，兼容不支持CSS伪类的场合
        "stripeClasses": ["odd", "even"],
    //        禁用原生搜索
        "searching": true,
//        search pin lv
        "searchDelay": 400,
        "dom": '<"toolbar">frtip',
    //        启用服务端分页
        "serverSide": true,
//        每页条数
        "pageLength": 5,
        "ajax": function (data, callback, settings) {
            //ajax请求数据
            $.ajax({
                type: "GET",
                url: "/api/"+path1+"/"+path2+"/"+data.length+"/"+(data.start / data.length),
                cache: false,//禁用缓存
                data: {
                    "search": data.search.value
                },
                dataType: "json",
                success: function (result) {
                    console.log(result)
                    var returnData = {};
                    //这里直接自行返回了draw计数器,应该由后台返回
                    returnData.draw = data.draw;
                    //返回数据全部记录
                    returnData.recordsTotal = result.data.t1;
                    //后台不实现过滤功能，每次查询均视作全部结果
                    returnData.recordsFiltered = result.data.t1;
                    //返回的数据列表
                    returnData.data = result.data.t2;
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                }
            });
        },
//        设置空字段默认值
        "columnDefs": [
            {
                "defaultContent": "",
                "targets": "_all"
            }
        ],
        "columns": columns
    }
}

var table = function(tableId,path1,path2,columns){
    return $('#'+tableId).dataTable(tableSetting(path1,path2,columns)).api();
}

var tablesReload = function(t){
    t.ajax.reload();
}