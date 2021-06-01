$(document).ajaxSend(function(event, jqxhr, settings) {
    if(localStorage.getItem("auth") != null){
        jqxhr.setRequestHeader('auth', localStorage.getItem("auth"))
    }
})

$(document).ajaxSuccess(function(event,xhr,options){
    let t = xhr.getResponseHeader('auth');
    if(t != null){
        localStorage.setItem("auth",t);
    }
});

$(document).ajaxError(function(event,xhr,options,exc){
    if(xhr.responseText){
        var result = xhr.responseText;
        var json=eval("("+result+")");
        if(json.message.indexOf("logout") != -1){
            localStorage.clear();
            location.replace("/");
        }else if(json.message.indexOf("NOT_FOUND") != -1){
            alert("资源不存在")
        }else{
            alert(json.message)
        }
    }
});
