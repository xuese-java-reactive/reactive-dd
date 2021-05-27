$(function(){

    var setting = {
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },
        data: {
//        定义使用简单的json数据
            simpleData: {
                enable:true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: ""
            }
        },
    };

//    var zNodes =[
//        {id:1, pId:0, name:"[core] 基本功能 演示", open:false},
//        {id:101, pId:1, name:"最简单的树 --  标准 JSON 数据", file:"core/standardData"},
//        {id:102, pId:1, name:"最简单的树 --  简单 JSON 数据", file:"core/simpleData"},
//        {id:103, pId:1, name:"不显示 连接线", file:"core/noline"},
//        {id:104, pId:1, name:"不显示 节点 图标", file:"core/noicon"},
//        {id:105, pId:1, name:"自定义图标 --  icon 属性", file:"core/custom_icon"},
//        {id:106, pId:1, name:"自定义图标 --  iconSkin 属性", file:"core/custom_iconSkin"},
//        {id:107, pId:1, name:"自定义字体", file:"core/custom_font"},
//        {id:115, pId:1, name:"超链接演示", file:"core/url"},
//        {id:108, pId:1, name:"异步加载 节点数据", file:"core/async"},
//        {id:109, pId:1, name:"用 zTree 方法 异步加载 节点数据", file:"core/async_fun"},
//        {id:110, pId:1, name:"用 zTree 方法 更新 节点数据", file:"core/update_fun"},
//        {id:111, pId:1, name:"单击 节点 控制", file:"core/click"},
//        {id:112, pId:1, name:"展开 / 折叠 父节点 控制", file:"core/expand"},
//        {id:113, pId:1, name:"根据 参数 查找 节点", file:"core/searchNodes"},
//        {id:114, pId:1, name:"其他 鼠标 事件监听", file:"core/otherMouse"},
//
//    ];

    // 2.页面加载完毕后发起异步请求获得json格式的数据
    $(document).ready(function(){
        let url = "/api/menu/menu/0";
        $.get(url,function(data) {
            initTree(data);
        },"json");
    });

    // 3.定义zTree树
    var zTreeObj;
    // 4.根据获取到的json数据展示ztree树
    function initTree(data) {
        //第一个参数：树显示的位置，第二个参数：树的配置信息，第三个参数：要展示的数据
        zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
        zTreeObj.expandAll(true);//true：展开所有节点
    }
})