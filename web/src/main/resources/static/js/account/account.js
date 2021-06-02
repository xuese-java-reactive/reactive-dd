$(function(){

    //提示信息
    var lang = {
        "sProcessing": "处理中...",
        "sLengthMenu": "每页 _MENU_ 项",
        "sZeroRecords": "没有匹配结果",
        "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
        "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
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

    var tableSetting = {
//        关闭排序
        "ordering":false,
//        关闭更改每页条数的下拉框
        "lengthChange": false,
//        显示等待提示语
        "processing": true,
//        延迟渲染，提交初始加载速度
        "deferRender": true,
//        设置分页按钮
        "pagingType": "first_last_numbers",
//        相关提示信息
        "language": lang,
        //为奇偶行加上样式，兼容不支持CSS伪类的场合
        "stripeClasses": ["odd", "even"],
//        禁用原生搜索
        "searching": false,
//        启用服务端分页
        "serverSide": true,
        "ajax": function (data, callback, settings) {
                    //封装请求参数
                    console.log(data);
                    //ajax请求数据
                    $.ajax({
                        type: "GET",
                        url: "/api/account/account",
                        cache: false,  //禁用缓存
                        data: {
                            "pageSize": data.length,
                            "pageNum": (data.start / data.length)
                        },  //传入组装的参数
                        dataType: "json",
                        success: function (result) {
                            console.log(result);
                            //setTimeout仅为测试延迟效果
                            //封装返回数据
                            var returnData = {};
                            returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                            returnData.recordsTotal = result.total;//返回数据全部记录
                            returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                            returnData.data = result.data;//返回的数据列表
                            //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                            //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                            callback(returnData);
                        }
                    });
        },
        "columns": [
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
                    let rest = '<button type="button" class="btn btn-danger btn-xs">重置密码</button>';
                    let update = '<button type="button" class="btn btn-warning btn-xs">修改状态</button>';
                    return rest+"&nbsp;&nbsp;&nbsp;"+update;
                }
            }
        ]
    }

    $('#table').dataTable(tableSetting).api();
})