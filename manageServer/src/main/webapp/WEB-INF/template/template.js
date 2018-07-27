$(function () {
    //请求价格方案
    $.ajax({
        url: _root + '/order/priceScheme/listPriceSchemeByType.do',
        type: "POST",
        async: false,
        dataType: _json,
        data: {'typeStr': '印刷'},
        success: function (data) {
            if (data.result) {
                //定义方案数组
                var schemes = [];
                $.each(data.priceSchemes, function (index, ele) {
                    schemes.push(ele.schemeName)
                });
                //价格方案的下拉框
                _control.textComboBox($('#priceScheme'), schemes, null);
                //印刷计算器的价格方案下拉框
                _control.textComboBox($('#yinShua_priceScheme'), schemes, null);
            } else {
                _control.alert(data.message, "错误", null);
            }
        }
    });
    /*请求纸张*/
    var paper = {};
    var getPaper = function () {
        paper = {};
        $.ajax({
            url: _root + '/order/yinShuaCraft/listPaper.do',
            type: "POST",
            async: false,
            dataType: _json,
            success: function (data) {
                if (data.result) {
                    $.each(data.rows, function (index, ele) {
                        paper[ele.productType] ? paper[ele.productType].push(ele.paperType) : paper[ele.productType] = [ele.paperType]
                    })
                } else {
                    _control.alert(data.message, "错误", null);
                }
            }
        });
    };
    getPaper();
    /*请求产品类型*/
    $.ajax({
        url: _root + '/order/priceScheme/listProductTypes.do',
        type: "POST",
        async: false,
        dataType: _json,
        data: {'productTypeStr': ''},
        success: function (data) {
            if (data.result) {
                //定义方案数组
                var productTypes = [];
                $.each(data.productTypes, function (index, ele) {
                    productTypes.push(ele.productType)
                });
                //零件计算器界面的产品类型下拉框
                _control.textComboBox($('#cal_productType'), productTypes, function (text) {
                    _control.textComboBox($('#paperName'), paper[text], null)
                });
            } else {
                _control.alert(data.message, "错误", null);
            }
        }
    });
    $('#printDiv').find('input[type=radio]').click(function () {
        var printFace = $('#printDiv').find('input:checked').val();
        if (printFace === '单面') {
            $('#doubleColor').hide()
        } else {
            $('#doubleColor').show()
        }
    });
    $('#fmDiv').find('input[type=checkbox]').click(function () {
        $('#fmDiv').find('input[type=checkbox]').not(this).prop("checked", false);
    });


    //*********************************************************向下为js常用的函数**************************************************
    parseInt();
    parseFloat();

    var leftContent = $('.left-content');

    $('.yinShuaPaper').click(function () {
        leftContent.children().remove();
        leftContent.load("tempContent.html");
    });

    function compare(value1, value2) {
        if (value1 >= value2) {
            return value1;
        } else {
            return value2;
        }
    }

    var comp = function (value1, value2) {
        return value2;
    };

    var left = window.screenLeft;
    window.moveTo(0, 0);
    //左移50px
    window.moveBy(-50, 0);
    //重设窗口大小
    window.resizeTo(300, 500);
    //重新打开一个窗口,或者导航的新的url,等同于<a>标签中的href
    window.open("template.html", "_blank");
    window.location = "http://www.baidu.com";
    location.href = "http://www.baidu.com";//常用
    location.reload(true);

    //系统的对话框
    alert();//包含ok按钮
    var trueOrFalse = confirm("是否删除");//ok按钮和cancel()取消按钮
    var infoStr = prompt("输入信息");

    //上条历史
    history.go(-1);

    var div = document.createElement("<div id=\'mydiv\'></div>");
    var body = document.body;
    body.appendChild(div);

    var handler = function () {
        alert("ok");
    };

    var top = document.documentElement.scrollTop;
    top.addEventListener("click", function () {
        alert("ok");
    }, false);
    top.addEventListener("click", handler, false);

    //load事件即是加载,比如window.onload(),对应的是window.unonload(),作用是在加载新的页面的时候,释放内存
    //键盘事件,keyDown,keyUp,keyPress,shiftKey,ctrlKey,altKey,metaKey

    setTimeout(function () {
        alert("超时测试")
    }, 100);

    var event = document.createEventObject("HTMLEvents");
    event.clientX;
    event.clientY;
    event.screenX;
    event.screenY;
    event.altKey = false;
    event.ctrlKey = false;
    evnet.shiftKey = false;
    event.initEvent("focus", true, true);

    var option = new Option("text", "value");
    var selection = document.getElementById("selection");
    selection.appendChild(option);

    var draw = document.getElementById("drawing");
    //确定浏览器支持2d
    if (draw.getContext) {
        var url = draw.toDataURL("image/pic.jpg");
        var img = document.createElement("img");
        img.src = url;
        //使用innerHtml效率较快
        document.body.appendChild(img)

        //矩形是唯一的可以在2d上下文中绘制的图形,相关的方法有
        var context = draw.getContext("2d");
        context.fillStyle = "#ff0000";
        context.fillRect(0, 100, 20, 100);

        context.beginPath();
        context.lineTo(100, 100);
        context.moveTo(105, 105);
        //context.closePath();
        context.stroke();//执行渲染
    }

    //json的序列化和解析
    var book = {
        title: 'this is title',
        edition: 3.0,
        year: 2018,
        authors: [
            "joe",
            "jack"
        ]
    };
    var bookText = JSON.stringify(book);
    console.log(bookText);
    //解析json字符串时,所有的undefined的类型,都会被忽略
    var bookStr = JSON.parse(bookText);
    console.log(bookStr);
    //过滤结果
    var bookFilter = JSON.stringify(book, ["title", "year"]);
    console.log(bookFilter);


    Object.preventExtensions(book);
    //密封对象
    Object.seal(book);
    Object.freeze(book);
    btn.click = function () {
        setTimeout(100);
    };

    //清空session
    sessionStorage.clear();

    //dom开销很大,要注意dom的使用
    navigator.geolocation.getCurrentPosition(function (position) {
    })

});