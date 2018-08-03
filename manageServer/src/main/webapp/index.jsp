<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="lib/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="lib/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="lib/config.js"></script>
    <script type="text/javascript" src="lib/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="lib/bootstrap/js/mybootstrap.js"></script>
    <script type="text/javascript" src="lib/Ext.js"></script>
    <title>登录</title>
    <style>
        #userName {
            width: 266px !important;
        }

        #number_list li {
            position: relative;
            margin: auto;
            overflow: hidden;
            float: left;
            width: 90px;
            height: 45px;
        }

        #number_list li button {
            position: relative;
            float: left;
            padding: 0px;
            cursor: pointer;
            background: rgb(255, 115, 14);
            text-align: center;
            width: 80px;
            height: 40px;
            border-radius: 2px;
            border: 0.5px solid rgb(255, 115, 14);
            line-height: 40px;
            margin: 0px 0px 0px 5px;
            font-family: 微软雅黑, arial;
            font-size: 16px;
            color: rgb(255, 255, 255);
        }
    </style>
</head>
<body>
<div class="container" style="margin-top: 140px;">
    <div class="form-signin" style="width: 300px;margin: 0 auto;">
        <h2 class="form-signin-heading">请登录</h2>
        <div class="input-group" style="width: 300px;">
            <input type="text" id="userName" value="18012345678" class="form-control" placeholder="用户名" required="">
            <div class="input-group-btn" style="display: inline-block;width: 34px;">
                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                        style="height: 34px">
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu pull-right" id="userNameSel"
                    style="margin-top: 0;height: 100px;overflow: auto;width: 302px;right: -1px;"></ul>
            </div>
        </div>
        <input type="password" id="passWord" value="123" class="form-control" placeholder="密码" required=""
               style="margin-bottom: 20px;">
        <button class="btn btn-lg btn-primary btn-block" id="submit">登录</button>
    </div>
</div>
<div style="width: 300px; height: 250px; border-radius: 5px;margin: 40px auto auto;">
    <ul class="number_list" id="number_list"
        style="position: relative; margin: 15px auto auto; width: 310px; height: 185px;margin-left: -26px;">
        <li>
            <button type="button">7</button>
        </li>
        <li>
            <button type="button">8</button>
        </li>
        <li>
            <button type="button">9</button>
        </li>
        <li>
            <button type="button">4</button>
        </li>
        <li>
            <button type="button">5</button>
        </li>
        <li>
            <button type="button">6</button>
        </li>
        <li>
            <button type="button">1</button>
        </li>
        <li>
            <button type="button">2</button>
        </li>
        <li>
            <button type="button">3</button>
        </li>
        <li>
            <button type="button">0</button>
        </li>
        <li>
            <button type="submit">清除</button>
        </li>
        <li>
            <button type="submit">退格</button>
        </li>
    </ul>
</div>
</body>
<script>
    $(function () {
        if ($.loadLoginHistory() !== null && $.loadLoginHistory() !== undefined) {
            var userArr = $.loadLoginHistory().split(',');
            $("#userName").val(userArr[0]);
        }
        $(document).keyup(function (event) {
            if (event.keyCode === 13) {
                $("#submit").trigger("click");
            }
        });
        function checkMobile(str, obj) {
            var re = /^[1][3-8]\d{9}/;
            if (re.test(str)) {
                /*发送请求*/
                $.ajax({
                    url: _root + '/auth/user/login.do',
                    data: obj,//{"userName":userName,"passWord":passWord}
                    type: "POST",
                    dataType: _json,
                    success: function (data) {
                        if (data.result) {
                            $.configClientSid(data.sid);
                            window.location.href = "/html/mainFrame/mainFrame.html";
                            if ($.loadLoginHistory() == null || $.loadLoginHistory() == undefined) {
                                $.saveLoginHistory(obj.userName);
                            } else {
                                if (userArr.indexOf(obj.userName) === -1) {
                                    $.saveLoginHistory(obj.userName + ',' + $.loadLoginHistory())
                                } else {
                                    userArr.splice(userArr.indexOf(obj.userName), 1);
                                    $.saveLoginHistory(obj.userName + ',' + userArr.join(','))
                                }
                            }
                        } else {
                            _control.alert(data.message, "错误", null);
                        }
                    }
                })
            } else {
                _control.alert("请填写正确的用户名称", "提示", null);
            }
        }

        $("#submit").click(function () {
            var obj = {};
            obj.username = trimStr($("#userName").val());
            obj.password = trimStr($("#passWord").val());
            switch (true) {
                case obj.username === "":
                    _control.alert("请填写用户名称", "提示", null);
                    return;
                case obj.password === "":
                    _control.alert("请填写密码", "提示", null);
                    return;
            }
            checkMobile(obj.username, obj)
        });

        //数字键盘
        var activeinputele;
        $('input').focus(function () {
            activeinputele = $(this);
        });
        $('#number_list li button').click(function () {
            numbuttonclick($(this).text());
        });
        function numbuttonclick(buttontext) {
            if (buttontext / 1) {
                clickkey(buttontext / 1);
            } else {
                if (buttontext === "0") {
                    clickkey(0);
                }
                if (buttontext === "退格") {
                    backspace();
                }
                if (buttontext === "清除") {
                    keyclear();
                }
            }
        }

        function keyclear() {
            if (!activeinputele) return;
            activeinputele.val("");
        }

        function backspace() {
            if (!activeinputele) return;
            var inputtext = activeinputele.val();
            if (inputtext.length > 0) {
                inputtext = inputtext.substring(0, inputtext.length - 1);
                activeinputele.val(inputtext);
            }
        }

        function clickkey(num) {
            if (!activeinputele) return;
            var inputtext = activeinputele.val();
            inputtext = inputtext + num;
            activeinputele.val(inputtext);
        }

        //登录列表
        var str = '';
        $.each(userArr, function (index, ele) {
            str += '<li style="padding-left: 10px">' + ele + '</li>'
        });
        $('#userNameSel').html(str);
        $('#userNameSel li').click(function () {
            $('#userName').val($(this).text())
        });
        $('#userNameSel li').hover(
            function () {
                $(this).css('background-color', 'rgb(30, 166, 218)');
            },
            function () {
                $(this).css('background-color', '#fff');
            }
        );
    });

</script>
</html>
