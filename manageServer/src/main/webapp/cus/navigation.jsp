<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/config.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/mybootstrap.js"></script>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">

        <div class="navbar-header">
            <a class="navbar-brand" href="#">准典印刷</a>
        </div>

        <ul class="nav navbar-nav">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">在线下单<span class="caret"></span></a>
                <div class="dropdown-menu ">
                    <ul class="nav" style="width: 400px;">
                        <li class="col-xs-4 text-center"><a href="${pageContext.request.contextPath}/cus/webOrders/mPOrders.jsp" target="_blank">合版名片</a></li>
                        <li class="col-xs-4 text-center"><a href="javascript:void(0)">合版单页</a></li>
                        <li class="col-xs-4 text-center"><a href="javascript:void(0)">专版单页</a></li>
                        <li class="col-xs-4 text-center"><a href="javascript:void(0)">合版不干胶</a></li>
                        <li class="col-xs-4 text-center"><a href="javascript:void(0)">专版不干胶</a></li>
                        <li class="col-xs-4 text-center"><a href="javascript:void(0)">合版单页</a></li>
                    </ul>
                </div>
            </li>
            <li><a href="${pageContext.request.contextPath}/cus/listOrders/listOrders.jsp" target="_blank">订单查询</a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">账户<b class="caret"></b></a>
                <div class="dropdown-menu">
                    <ul class="nav">
                        <li class="col-xs-12 text-center"><a href="${pageContext.request.contextPath}/cus/finance/financial.jsp" target="_blank">账户信息</a></li>
                        <li class="col-xs-12 text-center"><a href="${pageContext.request.contextPath}/cus/finance/recharge.jsp" target="_blank">账户充值</a></li>
                        <li class="col-xs-12 text-center"><a href="${pageContext.request.contextPath}/cus/uac/modifyPassword.jsp" target="_blank">修改密码</a></li>
                        <li class="col-xs-12 text-center"><a href="${pageContext.request.contextPath}/cus/logout.jsp">退出</a></li>
                    </ul>
                </div>
            </li>
        </ul>

    </div>
</nav>
<style>
    body {
        padding-top: 70px;
    }
</style>
<script>
    $(function () {
        $("nav").find("li.dropdown").hover(
            function () {
                $(this).addClass("open");
            },
            function () {
                $(this).removeClass("open");
            });
    });

    function navActive(navName, navMenu) {
        if (navMenu === null) navMenu = $("#navRoot");
        var s1, s2;
        var spIndex = navName.indexOf('.');
        if (spIndex === -1) {
            s1 = navName;
            s2 = null;
        } else {
            s1 = navName.substr(0, spIndex);
            s2 = navName.substr(spIndex + 1);
        }
        var item = navMenu.children("[navName=\"" + s1 + "\"]");
        item.addClass("active");
        if (s2 != null) {
            var subMenu = item.children("ul");
            if (subMenu != null) navActive(s2, subMenu)
        }
    }
</script>