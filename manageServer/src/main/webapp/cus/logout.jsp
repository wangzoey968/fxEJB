<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //进入此页面的时候执行,从客户端删除sessionId
    request.getSession().removeAttribute("cusUserSid");
%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1 , maximum-scale=1,user-scalable=no">
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/config.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap.min.css">
    <title>退出</title>
</head>
<body>
<script>
    $(function () {
        window.location = 'login.jsp'
    });
</script>
</body>
</html>
