$(function () {
    /*head_active(1);*/
    var browserVersion = window.navigator.userAgent.toUpperCase();  //获取浏览器版本
    if(browserVersion.indexOf("MSIE") > -1){  //IE
        var version = browserVersion.split("; ")[1].split(" ")[1];
        if(version != null && version < 10){
            $('.webOrder').text("注意：IE10以下的浏览器暂不支持上传文件，请使用其它浏览器（谷歌、火狐等）或者升级到更高版本").css("color","#f20303")
        }
    }
    var _zhanBanParam = {
        'paperType': null, 'width': null, 'height': null,'amount': 1, 'typeAmount': 1,'fuMo':false,'fuMoType':'哑膜','jingXiManPen':false,'daKou':false,
        'daKouType':'','baoBian':false,'baoBianType':'','printFace':''
    };
    //$("#orderDesc").text(getDesc(_zhanBanParam));
    $(".tr-price:last-child").hide();
    //品种
    var papers = [];
    var paperMap = {
        'KT板(冷板)':'KT板（冷板）','PVC板3mm': 'PVC板3mm（不能包边）','PVC板5mm':'PVC板5mm（可以包边）',
    };

    for (var key in paperMap) {
        papers.push(key)
    }
    var paper = $('.zhanBanCalPrice #paper-group');
    var paperTipDiv = paper.parents('tr').find('td:first-child');
    _control.orderTextGroup(paper, papers, function (text) {
        if (text === null) {
            paperTipDiv.addClass('notSelectColor');
            _zhanBanParam.paperType = null;
            $("#orderDesc").text(getDesc(_zhanBanParam));
            $('div#result').html('<span style="font-size: 16px;margin-right:4px;">____</span>元');
            return;
        }
        if(paperTipDiv.hasClass('notSelectColor')){
            paperTipDiv.removeClass('notSelectColor');
        }
        _zhanBanParam.paperType = paperMap[text];
        if(text.search(/PVC板3mm/i) !== -1){
            if(_zhanBanParam.baoBian){
                $.each($('#baoBian-group a'),function (index,ele) {
                    if($(ele).text() ===_zhanBanParam.baoBianType){
                        $(ele).trigger('click');
                    }
                });
                $('#baoBian-group a').attr('disabled',true);
                return;
            }else{
                $('#baoBian-group a').attr('disabled',true);
            }
        }else{
            $('#baoBian-group a').attr('disabled',false)
        }
        $("#orderDesc").text(getDesc(_zhanBanParam));
        sendRequest(_zhanBanParam);
    });

    //规格
    var whChange = function () {
        _zhanBanParam.width = parseFloat($(".zhanBanCalPrice .sizeSet .widthSet").val());
        _zhanBanParam.height = parseFloat($(".zhanBanCalPrice .sizeSet .heightSet").val());
        if (_zhanBanParam.width > 0 && _zhanBanParam.height > 0) {
            sendRequest(_zhanBanParam);
        } else {
            $('div#result').html('<span style="font-size: 16px;margin-right:4px;">____</span>元');
        }
        $("#orderDesc").text(getDesc(_zhanBanParam));
    };
    $(".zhanBanCalPrice .sizeSet input").keyup(function () {
        this.value = this.value.replace(/[^(\d|.)]/g, "");
        whChange();
    });
    //单双面
    var printFace = $('.zhanBanCalPrice #printFace-group');
    var printFaceTipDiv = printFace.parents('tr').find('td:first-child');
    _control.orderTextGroup(printFace, ['单面裱','双面裱'], function (text) {
        text == null?printFaceTipDiv.addClass('notSelectColor'):(printFaceTipDiv.hasClass('notSelectColor')?printFaceTipDiv.removeClass('notSelectColor'):'');
        _zhanBanParam.printFace = text;
        $("#orderDesc").text(getDesc(_zhanBanParam));
        sendRequest(_zhanBanParam);
    });

    //覆膜
    _control.orderTextGroup($('.zhanBanCalPrice #fuMo-group'), ['光膜','哑膜'], function (text) {
        _zhanBanParam.fuMo = text == null?false:true;
        _zhanBanParam.fuMoType = text;
        $("#orderDesc").text(getDesc(_zhanBanParam));
        sendRequest(_zhanBanParam);
    });
    //超精细慢喷
    _control.orderTextGroup($('.zhanBanCalPrice #pen-group'), ['超精细慢喷'], function (text) {
        _zhanBanParam.jingXiManPen = text == null?false:true;
        $("#orderDesc").text(getDesc(_zhanBanParam));
        sendRequest(_zhanBanParam);
    });
    //打扣
    /*_control.orderTextGroup($('.zhanBanCalPrice #daKou-group'), ['打扣'], function (text) {
        _zhanBanParam.daKou = text == null?false:true;
        $("#orderDesc").text(getDesc(_zhanBanParam));
        sendRequest(_zhanBanParam);
    });*/
    $('#daKouTip,#daKouOptions').on({
        mouseover: function () {
            $('#daKouOptions').show();
        },
        mouseout: function () {
            $('#daKouOptions').hide();
        }
    });
    $('#daKouOptions').find('a').click(function () {
        $('#daKouSelect').text($(this).text()).show();
        $('#daKouOptions').hide();
        _zhanBanParam.daKou = true;
        _zhanBanParam.daKouType = $.trim($(this).text());
        $("#orderDesc").text(getDesc(_zhanBanParam));
        sendRequest(_zhanBanParam)
    });
    $('#daKouSelect').click(function () {
        $('#daKouSelect').hide();
        _zhanBanParam.daKou = false;
        $("#orderDesc").text(getDesc(_zhanBanParam));
        sendRequest(_zhanBanParam)
    });
    //包边
    var baoBianArr = [
        '小灰边45度角','小蓝边45度角','小红边45度角','小金边45度角','小银边45度角',
        '大灰边45度角','大蓝边45度角','大金边45度角','大银边45度角'
    ];
    _control.orderTextGroup($('.zhanBanCalPrice #baoBian-group'), baoBianArr, function (text) {
        _zhanBanParam.baoBian = text == null?false:true;
        _zhanBanParam.baoBianType = text;
        $("#orderDesc").text(getDesc(_zhanBanParam));
        sendRequest(_zhanBanParam);
    });

    //款数
    _control.amountSpinner($('.zhanBanCalPrice #type'), 1, 1, function (number) {
        _zhanBanParam.typeAmount = number;
        $("#orderDesc").text(getDesc(_zhanBanParam));
        sendRequest(_zhanBanParam);
    });
    $(".zhanBanCalPrice #type input").keyup(function () {
        _zhanBanParam.typeAmount = $(".zhanBanCalPrice #type input").val();
        $("#orderDesc").text(getDesc(_zhanBanParam));
        if(_zhanBanParam.typeAmount > 0){
            sendRequest(_zhanBanParam);
        } else{
            $('div#result').html('<span style="font-size: 16px;margin-right:4px;">____</span>元');
        }
    });
    //数量
    _control.amountSpinner($('.zhanBanCalPrice .spinner'), 1, 1, function (number) {
        _zhanBanParam.amount = number;
        $("#orderDesc").text(getDesc(_zhanBanParam));
        sendRequest(_zhanBanParam);
    });
    //印刷数量实时响应
    $(".zhanBanCalPrice .spinner input").keyup(function () {
        _zhanBanParam.amount = $(".zhanBanCalPrice .spinner input").val();
        $("#orderDesc").text(getDesc(_zhanBanParam));
        if (_zhanBanParam.amount > 0) {
            sendRequest(_zhanBanParam);
        } else {
            $('div#result').html('<span style="font-size: 16px;margin-right:4px;">____</span>元');
        }
    });

    /*户内展板描述*/
    function getDesc(value) {
        var valueObj=value;
        if (Object.prototype.toString.call(value)==='[object String]') valueObj=JSON.parse(value);
        var postDesc = '';
        valueObj.fuMo ? postDesc += valueObj.fuMoType+ '，' : null;
        valueObj.pen ? postDesc += '超精细慢喷,' : null;
        valueObj.daKou ? postDesc +=valueObj.daKouType + "，" : null;
        valueObj.baoBian ? postDesc += valueObj.baoBianType+'，': null;
        if(postDesc !== '') {
            postDesc = postDesc.substring(0,postDesc.length-1);
            postDesc += '。';
        }
        return valueObj.paperType+'，'+valueObj.width+'*'+valueObj.height+'，'+valueObj.printFace+'，'+valueObj.typeAmount+'款，'+valueObj.amount+'块。'+postDesc;
    }

    /*价格请求函数*/
    var i = 1;
    function sendRequest(request_param) {
        if (!request_param.paperType || !request_param.amount || !request_param.width || !request_param.height || !request_param.printFace) {
            $('div#result').html('<span style="font-size: 16px;margin-right:4px;">____</span>元');
            return;
        }
        if ($(".tr-price:last-child").css("display") === "none") $(".tr-price:last-child").show();
        i++;
        var j = i;
        $.ajax({
            url: _root + "/cus/preOrder/price.do",
            data: {productJson: JSON.stringify(request_param), orderType: "户内展板"},
            dataType: 'json',
            /*async: false,*/
            type: 'POST',
            beforeSend:function () {
                $('div#result').html('');
            },
            error: function (http, text) {
                $('div#result').html(text)
            },
            success: function (data) {
                if (data.result) {
                    if(i === j){
                        $('div#result').html(data.money * request_param.typeAmount + ' 元');
                    }
                } else {
                    /*_control.scrollAlert(data.message, "错误", null);*/
                    $('div#result').html('暂不支持报价');
                }
            }
        });
    }

    /*查验文件*/
    var fileSuffix = ['.cdr', '.ai', '.pdf', '.rar', '.zip'];  //合法文件
    function checkFile(value) {
        if (value.size > 1024 * 1024 * 1024) {
            _control.scrollAlert("文件太大建议使用客户端上传");
            return false
        }
        if ($.inArray(value.name.substring(value.name.lastIndexOf('.')).toLowerCase(), fileSuffix) < 0) {
            _control.scrollAlert("上传文件格式应为 CDR、AI、PDF、RAR、ZIP；JPG或TIF 等文件，请打成压缩包（正反面放在一个压缩包内）");
            return false
        }
        return true
    }

    //定义文件
    var fileValue = null;
    //户内展板文件选择器
    $('#mingPianFiles').change(function () {
        if (this.files) {
            if (!checkFile(this.files[0])) return;
            fileValue = this.files[0];
            $(".webOrder").text(fileValue.name)
        }
    });

    /*拖拽文件上传*/
    $('body').on({
        dragenter:function (e) {
            fileValue = null;
            e.preventDefault();
        },
        dragover:function (e) {
            e.preventDefault();
        },
        dragleave:function (e) {
            e.preventDefault();
        }
    });
    $('body').on('drop', function (e) {
        e.preventDefault();
        var fileSelect = e.originalEvent.dataTransfer.files[0];
        if(!fileSelect) return;
        if (!checkFile(fileSelect)) return;
        fileValue = fileSelect;
        $(".webOrder").text(fileValue.name)
    });

    //获取网络订单上传文件参数函数
    function getWebOrderUploadParams() {
        $.ajax({
            url: _root + "/cus/preOrder/getWebOrderUploadParams.do",
            type: 'POST',
            data: {webOrderId: orderId, randomNumber: randomNumber, fileName: fileValue.name},
            dataType: _json,
            success: function (res) {
                if (res.result) {
                    var filePath = res.filePath,
                        authCode = res.authCode,
                        url = res.url;
                    uploadFile(url, filePath, authCode);
                } else {
                    _control.scrollAlert(res.message, "错误", null)
                }
            },
            error:function (http,text) {
                _control.scrollAlert(text,"错误",null)
            }
        });
    }
    //上传文件函数
    function uploadFile(url,filePath,authCode) {
        var formData = new FormData();
        formData.append('file', fileValue);
        formData.append("filePath",filePath);
        formData.append("authCode",authCode);
        ajaxXhr = $.ajax({
            url: url,
            type: "POST",
            data: formData,
            contentType: false, //必须false才会自动加上正确的Content-Type
            processData: false,  //必须false才会避开jQuery对 formdata 的默认处理
            xhr: function () { //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
                myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) { //检查upload属性是否存在
                    //绑定progress事件的回调函数
                    myXhr.upload.addEventListener('progress', function (e) {
                        if (e.lengthComputable) {
                            var percent = (e.loaded / e.total * 100).toFixed(1);
                            $('.progress .progress-bar').text(percent + '%').css("width", percent + "%");
                        }
                    }, false);
                }
                return myXhr; //xhr对象返回给jQuery使用
            },
            beforeSend: function () {
                $('#modal-mask').modal('show');
            },
            complete: function () {
                $('#modal-mask').modal('hide');
                $('.progress .progress-bar').text(0 + '%').css("width", 0 + "%");
            },
            error: function (http, text) {
                if(text === "abort") return;
                if (text === 'error') {
                    _control.scrollAlert("文件服务器正在更新，请稍等...", '错误', null);
                } else{
                    _control.scrollAlert(text, '错误', null);
                }
            },
            success: function (data) {
                if (JSON.parse(data).result) {
                    finishWebOrderUpload(filePath)
                } else {
                    _control.scrollAlert(JSON.parse(data).message, '错误', null)
                }
            }
        });
    }
    //通知服务器上传完成函数
    function finishWebOrderUpload(filePath) {
        $.ajax({
            url: _root + "/cus/preOrder/finishWebOrderUpload.do",
            type: 'POST',
            data: {webOrderId: orderId, randomNumber: randomNumber,filePath:filePath},
            dataType: _json,
            success: function (dataResult) {
                if (dataResult.result) {
                    //跳转
                    window.location.href = _root + '/cusweb/product/webOrderSuccess.jsp'
                } else {
                    _control.scrollAlert(dataResult.message, "错误", null)
                }
            },
            error:function (http,text) {
                _control.scrollAlert(text, "错误", null)
            }
        });
    }
    var ajaxXhr = null,orderId,randomNumber;
    $("#fileUpload").click(function () {
        if (!parseInt($('#result').text()) || !fileValue) return;
        $.ajax({
            url: _root + "/cus/preOrder/insertWebOrder.do",
            type: 'POST',
            data: {typeAmount:_zhanBanParam.typeAmount,productContent:JSON.stringify(_zhanBanParam),orderTitle:fileValue.name,orderType:'户内展板'},
            dataType: _json,
            success: function (result) {
                if (result.result) {
                    orderId = result.data.id;
                    randomNumber = result.data.randomNumber;
                    getWebOrderUploadParams();
                } else {
                    _control.scrollAlert(result.message, "错误", null)
                }
            },
            error:function (http,text) {
                _control.scrollAlert(text, "错误", null)
            }
        });
    });

    //取消上传
    $('#btn-abort').click(function () {
        if (ajaxXhr != null) ajaxXhr.abort();
        $.ajax({
            url: _root + "/cus/preOrder/delWebOrder.do",
            type: 'POST',
            data: {webOrderId: orderId, randomNumber: randomNumber},
            dataType: _json,
            success: function (delResult) {
                if (delResult.result) {

                } else {
                    _control.scrollAlert(delResult.message, "错误", null)
                }
            },
            error:function (http,text) {
                _control.scrollAlert(text, "错误", null)
            }
        });
    });

    //图片hover事件
    $('#img-list a').hover(
        function () {
            $('#img-list a').removeClass('active');
            $(this).addClass('active');
            $("#s-images")[0].src = $(this).children()[0].src;
        },
        function () {

        }
    );
    //判断用户是否选择确切的纸张
    /*var paperStandList = $('#paper-group a');
     var url = window.location.search;
     if (url !== undefined && url !== null && url !== "") {
     var paperTypeSelectX = url.split('=')[1];
     if (paperTypeSelectX !== undefined && paperTypeSelectX != null) {
     paperStandList.eq(paperTypeSelectX).trigger('click');
     }
     } else {
     paperStandList.eq(0).trigger('click');
     }*/
    /*$('#fuMo-group a').eq(0).trigger('click');*/
});