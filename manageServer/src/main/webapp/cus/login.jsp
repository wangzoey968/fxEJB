<%--<%@ page import="luna.server.forCus.Bean_SCusUser" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1 , maximum-scale=1,user-scalable=no">
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/config.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap.min.css">
    <title>用户登录</title>
</head>
<body>
<%--导航--%>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="http://vip.zhundianyinwu.com">准典印务</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">会员中心</a></li>
            </ul>
        </div>
    </div>
</nav>
<%--登录--%>
<div class="container" style="margin-top: 10%;">
    <div style="width: 300px;margin: 0 auto;">
        <h2 class="form-signin-heading">用户登录</h2>
        <input type="text" id="userName" class="form-control" placeholder="用户名" value="<%= request.getParameter("username")==null?"":request.getParameter("username")%>" autofocus="">
        <input type="password" id="passWord" class="form-control" placeholder="密码"
               style="margin: 20px 0;">
        <div style="text-align: justify;">
            <button class="btn btn-lg btn-primary" id="submit" style="width: 148px;">登录</button>
            <button class="btn btn-lg btn-primary pull-right" id="findPwd" style="width: 148px;">忘记密码</button>
        </div>
        <div style="margin-top: 40px;">
            <strong style="top: -15px; margin-left: 10px; position:relative; font-size: 20px;">客户端：</strong>
            <br/>
            <a href="http://www.zhundian.net:8080/download/zdClientSetup.exe"
               style="cursor:pointer;top: -15px; margin-left: 10px; position:relative; font-size: 20px;color:#666;"
               target="_blank">安装版下载</a>
            <a href="http://www.zhundian.net:8080/download/zdClient.zip"
               style="cursor:pointer;top: -15px; margin-left: 10px; position:relative; font-size: 20px;color:#666;"
               target="_blank">绿色版下载</a>
        </div>
    </div>
</div>


<%@ include file="/cus/copyright.jsp" %>
<script>

    $(function () {

        $("#findPwd").click(function () {
            window.location = "${pageContext.request.contextPath}/cus/uac/FindPassword.jsp?username=" + $("#userName").val()
        });

        $(document).keyup(function (event) {
            if (event.keyCode === 13) {
                $("#submit").trigger("click");
            }
        });
        //点击登录
        $("#submit").click(function () {
            var obj = {};
            obj.userName = $.trim($("#userName").val());
            obj.password = $.trim($("#passWord").val());
            switch (true) {
                case obj.userName === "":
                    alert("请填写用户名");
                    return;
                case obj.password === "":
                    alert("请填写密码");
                    return;
            }
            $.ajax({
                url: _root + '/cus/uac/login.do',
                data: obj,
                type: "POST",
                dataType: _json,
                success: function (data) {
                    if (data.result) {
                        window.location.href = "${pageContext.request.contextPath}/cus/index.jsp";
                    } else {
                        alert(data.message);
                    }
                },
                error: function (http, text) {
                    alert(text)
                }
            })
        });
    });
</script>
</body>
</html>
