$(function(){
    $("#login-form").on("submit", function (ev) {
        ev.preventDefault();
        let formData = $('#login-form').serializeObject();
        $.ajax({
            url:"/api/login/login",
            contentType : "application/json;charset=UTF-8",
            dataType:"json",
            data:JSON.stringify(formData),
            type:"POST",
            success:function(req){
                if(req.state){
                    localStorage.setItem("auth",req.data);
                    location.replace("/page/home/home");
                }else{
                    alert(req.msg)
                }
            },
            error:function(err){
                alert(err.responseJSON.message)
            }
        });
    })
})
