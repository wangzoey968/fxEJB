$(function () {
    //转化时间格式
    function formatDateTime(value) {
        var date = new Date(value);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
    }

    //统计详情表格
    $('#table-staDetail').bootstrapTable({
        striped: true,
        height: 600,
        idField: 'id',
        columns: [
            {
                field: "createPoint",
                title: "创建点",
                align: "center",
                formatter: function (value) {
                    return '<a href="###" class="showObj">' + value + '</a>'
                }
            }, {
                field: "productType",
                title: "产品类型",
                align: "center"
            }, {
                field: "jobType",
                title: "工作类型",
                align: "center"
            }, {
                field: "amount",
                title: "任务要求成品数量",
                align: "center"
            }, {
                field: "jobFinishTime",
                title: "任务完成时间",
                align: "center"
            }, {
                field: "desc",
                title: "描述",
                align: "center",
                formatter: function (value) {
                    return '<div  style="overflow: hidden;text-overflow:ellipsis;white-space: nowrap;text-align: center;max-width: 600px;margin:  auto;"  title="' + value + '">' + value + '</div>'
                }
            }]
    });
    var url = decodeURI(window.location.search);
    var urlPath = url.split("=");
    var posId = urlPath[1].split('&')[0];
    var pageNum = urlPath[2].split('&')[0];
    var pageSize = urlPath[3].split('&')[0];
    var workPositionName = urlPath[4].split('&')[0];
    var jobStartTime = urlPath[5].split('&')[0];
    var jobFinishTime = urlPath[6];
    $('#workPositionName').text(workPositionName);
    //定义最大页数
    var maxPage;
    var staDetailSelect = function () {
        $.ajax({
            url: _root + '/jobStatistic/jobDetailPage.do',
            type: 'POST',
            dataType: _json,
            data: {'posId': posId, 'pageNum': pageNum, 'pageSize': pageSize,
                "jobStartTime":jobStartTime,"jobFinishTime":jobFinishTime},
            success: function (data) {
                if (data.result) {
                    $.each(data.rows, function (index, ele) {
                        ele.jobFinishTime = formatDateTime(ele.jobFinishTime)
                    });
                    $('#table-staDetail').bootstrapTable('load', data.rows);
                } else {
                    _control.alert(data.message, "错误", null);
                }
            }
        });
    };
    //每页数据下拉框
    _control.textComboBox($('#pageSize'), [10, 25, 50], function (text) {
        pageSize = text;
        pageCount();
        if (maxPage < $('#pageNum').val()) {
            pageNum = maxPage;
            $('#pageNum').val(maxPage)
        }
        staDetailSelect();
    });
    //总页数下拉框
    var totalItem = $('#totalItem').text();
    var pageCount = function () {
        maxPage = Math.ceil(totalItem / pageSize);
        var pageNumArr = [];
        for (i = 1; i <= maxPage; i++) {
            pageNumArr.push(i);
        }
        var pageStr = '';
        $.each(pageNumArr, function (index, ele) {
            pageStr += '<li style="padding-left: 10px">' + ele + '</li>'
        });
        $('#pageNumSel').html(pageStr);
        $('#pageNumSel li').click(function () {
            $('#pageNum').val($(this).text());
            pageNum = $(this).text();
            staDetailSelect();
        });
        $('#pageNumSel li').hover(
            function () {
                $(this).css('background-color', 'rgb(30, 166, 218)');
            },
            function () {
                $(this).css('background-color', '#fff');
            }
        );
    };
    $('#pageNum').change(function () {
        var pageIpt = $(this).val();
        if (pageIpt <= 0 || pageIpt === "") {
            _control.alert('页数输入有误', "提示", null);
            $('#pageNum').val(pageNum);
            return;
        }
        pageNum = pageIpt;
        if (pageNum > maxPage) {
            pageNum = maxPage;
            $('#pageNum').val(maxPage);
        }
        staDetailSelect();
    });
    //初始化
    $('#pageSize').val(pageSize);
    $('#pageNum').val(pageNum);
    pageCount();

    //上一页
    $("#prePage").click(function () {
        if (pageNum <= 1) {
            _control.alert('已到第一页', "提示", null);
        } else {
            pageNum--;
            staDetailSelect();
            $('#pageNum').val(pageNum);
        }
    });

    //下一页
    $("#nextPage").click(function () {
        if (pageNum >= maxPage) {
            _control.alert('已到最后页', "提示", null);
        } else {
            pageNum++;
            staDetailSelect();
            $('#pageNum').val(pageNum)
        }
    });

    $('tbody').on('click','a.showObj',function () {
        $.showObject('查看任务' + $(this).text());
    })
});