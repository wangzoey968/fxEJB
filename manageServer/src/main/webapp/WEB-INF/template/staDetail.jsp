<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1 , maximum-scale=1,user-scalable=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap-table.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap-datetimepicker.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/config.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap-table-reorder-rows.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/dateChinese.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/mybootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/lib/Ext.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/WEB-INF/template/staDetail.js"></script>
    <title>统计详情</title>
</head>
<body>
<!--统计详情 -->
<div style="margin-top: 40px;" class="col-xs-10 col-xs-offset-1">
    <div class="panel panel-primary" style="margin-top: 10px;">
        <div class="panel-heading">工作类型：${rows[0].jobType}
            <div style="display:inline-block;margin-left: 20px;">
                岗位名称：<span id="workPositionName"></span>
            </div>
            <div style="display:inline-block;margin-left: 20px;">总条数：<span id="totalItem">${rows[0].totalItem}</span>
            </div>
        </div>

        <div class="panel-body">
            <table class="table table-hover" id="table-staDetail">
                <thead>
                <tr>
                    <th style="text-align: center;">创建点</th>
                    <th style="text-align: center;">产品类型</th>
                    <th style="text-align: center;">工作类型</th>
                    <th style="text-align: center;">任务要求成品数量</th>
                    <th style="text-align: center;">任务最迟完成时间</th>
                    <th style="text-align: center;">描述</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${rows}" var="sp">
                    <tr>
                        <td style="text-align: center;">${sp.createPoint}</td>
                        <td style="text-align: center;">${sp.productType}</td>
                        <td style="text-align: center;">${sp.jobType}</td>
                        <td style="text-align: center;">${sp.amount}</td>
                        <td style="text-align: center;"><fmt:formatDate value="${sp.jobFinishTime}" pattern="yyyy-MM-dd HH:mm:ss" type="both"/></td>
                        <td style="text-align: center;">${sp.desc}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div style="margin-top: 20px;">
                <span>
                    <label>每页显示
                        <select id="pageSize" class="form-control" style="display: inline-block;width: 66px;"></select>
                    条数据；</label>
                </span>
                <div style="display: inline-block;">
                    <label>第
                        <div class="input-group" style="display: inline-block;width: 100px;">
                            <input type="text" class="form-control" style="width: 60px;" id="pageNum">
                            <div class="input-group-btn" style="display: inline-block;width: 34px;">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                        style="height: 34px">
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu pull-right" id="pageNumSel"
                                    style="margin-top: 0;height: 100px;overflow: auto;min-width:94px"></ul>
                            </div>
                        </div>
                        页 ;
                    </label>
                </div>
                <button id="prePage" class="btn btn-default">上一页</button>
                <button id="nextPage" class="btn btn-default">下一页</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>