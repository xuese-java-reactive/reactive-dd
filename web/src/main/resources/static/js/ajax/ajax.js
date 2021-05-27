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
    console.log(xhr)
    if(xhr.responseText.indexOf("logout") != -1){
        localStorage.clear();
        location.replace("/");
    }
});
