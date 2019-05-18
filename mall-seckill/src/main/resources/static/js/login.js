if (parent.window.location != window.location){
    parent.window.location = window.location;
}

window.onload = function(){
    document.getElementById("loginName").focus();
    document.onkeydown = function(){
        // firefox没有window.event对象
        var event = arguments[0] ? arguments[0] : window.event;
        if (event.keyCode === 13){
            onLogin();
        }
    };
};
var onLogin = function(){
    //验证用户名和密码是否输入
    var loginname = document.getElementById("loginName").value;
    if(loginname == null || loginname == "" || loginname.length == 0){
        alert("请输入用户名!");
        document.getElementById("loginName").focus;
        return false;
    }
    var password = document.getElementById("password").value;
    if(password == null || password == "" || password.length == 0){
        alert("请输入密码!");
        document.getElementById("password").focus;
        return false;
    }
    document.getElementById("loginform").submit();
};