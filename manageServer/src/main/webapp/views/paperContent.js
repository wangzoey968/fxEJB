$(function () {
    var isAdd = true;

    function AddBtn(value, row, index) {
        return [
            '<a class="TableEdit" style="margin-right: 20px;">修改</a>',
            '<a class="TableDel">删除</a>'
        ].join("")
    };
    window.operateEvents = {
        "click .TableEdit": function (e, value, row, index) {
            $('#table-yinShuaPaper').bootstrapTable('uncheck', index);
            yinShuaPaperUpdate(row);
        },
        "click .TableDel": function (e, value, row, index) {
            $('#table-yinShuaPaper').bootstrapTable('uncheck', index);
            yinShuaPaperDelete(row)
        }
    };

    //零件纸张配置表格
    $('#table-yinShuaPaper').bootstrapTable({
        toolbar: '#toolbar',
        striped: true,
        clickToSelect: true,
        singleSelect: true,
        idField: 'id',
        height: 400,
        columns: [
            {
                title: "选择",
                checkbox: true
            }, {
                field: "productType",
                title: "产品类型",
                align: "center",
                sortable: "true"
            }, {
                field: "paperType",
                title: "纸张类型",
                align: "center",
                sortable: "true"
            }, {
                field: "weight",
                title: "克重",
                align: "center",
                sortable: "true"
            }, {
                field: "pbType",
                title: "排版方式",
                align: "center",
                sortable: "true"
            }, {
                field: "mSize",
                title: "模位数",
                align: "center",
                sortable: "true"
            }, {
                field: "fullSizeName",
                title: "大纸名称",
                align: "center",
                sortable: "true"
            }, {
                field: "cuttingName",
                title: "开纸名称",
                align: "center",
                sortable: "true"
            }, {
                field: "machineType",
                title: "机台名称",
                align: "center",
                sortable: "true"
            }, {
                field: "button",
                title: "操作",
                align: "center",
                events: operateEvents,
                formatter: AddBtn
            }
        ],
        onCheck: function (row) {
            $.ajax({
                url: _root + '/order/yinShuaCraft/listPaperBy.do',
                type: 'POST',
                dataType: _json,
                data: {"productType": row.productType, "paperType": row.paperType},
                success: function (data) {
                    if (data.result) {
                        $.each( data.subtab, function (index,value) {
                            value['id'] = id++
                        });
                        $('#table-yinShuaPaperPrice').bootstrapTable('load', data.subtab);
                    } else {
                        _control.alert(data.message, "错误", null);
                    }
                }
            })
            $.ajax({
                url:  _root + '/order/yinShuaCraft/listPaperSizeMappingByCondition.do',
                type:"POST",
                //async:false,
                dataType:_json,
                data: {"productType": row.productType, "paperType": row.paperType},
                success:function(data){
                    if(data.result){
                        var paperFull = $('#table-yinShuaSizeMapping').bootstrapTable('getData', true);
                        $('#table-yinShuaSizeMapping').bootstrapTable('uncheckAll');
                        $.each(data.rows, function (index, value) {
                            $.each(paperFull, function (index1, value1) {
                                if (value.fullSizeName == value1.fullSizeName) {
                                    $('#table-yinShuaSizeMapping').find('tr').eq(index1+1).addClass('selected');
                                    $('#table-yinShuaSizeMapping').find('tr').eq(index1+1).find('input').prop("checked",true)
                                }
                            });
                        });
                    }else{
                        _control.alert(data.message, "错误", null);
                    }
                }
            })
        },
        onUncheck: function (row) {
            $('#table-yinShuaSizeMapping').bootstrapTable('uncheckAll');
            $('#table-yinShuaPaperPrice').empty();
        }
    });

    //查询零件纸张配置数据函数
    var dataGet = function () {
        $.ajax({
            url: _root + '/order/yinShuaCraft/listPaperWithKey.do',
            type: "POST",
            async: false,
            dataType: _json,
            data:{'key':$('#customerSearch').val()},
            success: function (data) {
                if (data.result) {
                    $('#table-yinShuaPaper').bootstrapTable('load', data.rows);
                } else {
                    _control.alert(data.message, "错误", null);
                }
            }
        })
    };
    dataGet();

    //查询客户
    /*enter键按下，并且搜索输入框获取焦点时，发送查询请求*/
    $(document).keyup(function(event){
        if(event.keyCode ==13&&$('#customerSearch')[0] == document.activeElement){
            $("#btn-search").trigger("click");
        }
    });
    $("#btn-search").click(function(){
        $('#table-yinShuaPaperPrice').empty();
        $('#table-yinShuaSizeMapping').bootstrapTable('uncheckAll');
        dataGet()
    });

    //请求产品类型
    var getProductType = function () {
        $.ajax({
            url: _root + '/order/priceScheme/listProductTypes.do',
            type: "POST",
            async: false,
            dataType: _json,
            success: function (data) {
                if (data.result) {
                    var productType = [];
                    $.each(data.productTypes, function (index, ele) {
                        productType.push(ele.productType);
                    });
                    _control.textComboBox($('#productType'), productType, null);
                } else {
                    _control.alert(data.message, "错误", null);
                }
            }
        });
    };
    getProductType();
    //零件纸张配置表格
    $('#table-yinShuaSizeMapping').bootstrapTable({
        striped: true,
        clickToSelect: false,
        singleSelect: false,
        checkboxHeader:false,
        idField: 'id',
        height: 265,
        columns: [
            {
                title: "选择",
                checkbox: true
            },{
                field: "fullSizeName",
                title: "大纸名称",
                align: "center"
            }
        ],
        onCheck: function (row) {
            var yinShuaPaper = $('#table-yinShuaPaper').bootstrapTable('getSelections')[0];
            if (!yinShuaPaper) {
                _control.alert("请选择上表一行数据", "提示", null);
                return
            }
            $.ajax({
                url: _root + '/order/yinShuaCraft/savePaperSizeMapping.do',
                type: 'POST',
                dataType: _json,
                data: {"productType": yinShuaPaper.productType, "paperType":yinShuaPaper.paperType,"fullSizeName":row.fullSizeName},
                success: function (data) {
                    if (data.result) {
                        //data.row['id']=id++;
                    } else {
                        _control.alert(data.message, "错误", null);
                    }
                }
            });
        },
        onUncheck: function (row) {
            var yinShuaPaper = $('#table-yinShuaPaper').bootstrapTable('getSelections')[0];
            if (!yinShuaPaper) {
                _control.alert("请选择上表一行数据", "提示", null);
                return
            }
            $.ajax({
                url: _root + '/order/yinShuaCraft/deletePaperSizeMapping.do',
                type: 'POST',
                dataType: _json,
                data: {"productType": yinShuaPaper.productType, "paperType":yinShuaPaper.paperType,"fullSizeName":row.fullSizeName},
                success: function (data) {
                    if (data.result) {
                    } else {
                        _control.alert(data.message, "错误", null);
                    }
                }
            });
        }

    });
    //请求规格名称
    var kaiSelectSize = function () {
        $.ajax({
            url: _root + '/order/yinShuaCraft/listPaperFullSize.do',
            type: "POST",
            async: false,
            dataType: _json,
            success: function (data) {
                if (data.result) {
                    $('#table-yinShuaSizeMapping').bootstrapTable('load', data.rows);
                    var fullSizeName = [];
                    $.each(data.rows, function (index, ele) {
                        fullSizeName.push(ele.fullSizeName);
                    });
                    _control.textComboBox($('#fullSizeName'), fullSizeName, function () {
                        kaiSelectPaper();
                    });
                } else {
                    _control.alert(data.message, "错误", null);
                }
            }
        })
    }
    kaiSelectSize();





    //请求零件纸张配置
    var kaiSelectPaper = function () {
        $.ajax({
            url: _root + '/order/yinShuaCraft/listPaperCuttingByFullSizeName.do',
            type: "POST",
            async: false,
            dataType: _json,
            data: {'fullSizeName': $('#fullSizeName').val()},
            success: function (data) {
                if (data.result) {
                    var cuttingName = [];
                    $.each(data.rows, function (index, ele) {
                        cuttingName.push(ele.cuttingName);
                    });
                    _control.textComboBox($('#cuttingName'), cuttingName, null);
                } else {
                    _control.alert(data.message, "错误", null);
                }
            }
        })
    }
    kaiSelectPaper();

    //获取机台名称
    var getMachineType = function () {
        $.ajax({
            url: _root + '/order/yinShuaCraft/listYSMachine.do',
            type: 'POST',
            async: false,
            dataType: _json,
            success: function (data) {
                if (data.result) {
                    var machineType = [];
                    $.each(data.rows, function (index, ele) {
                        machineType.push(ele.machineType)
                    });
                    _control.textComboBox($('#machineType'), machineType, null);
                } else {
                    _control.alert(data.message, "错误", null)
                }
            }
        })
    };
    getMachineType();

    // // 刷新
    // $("#btn-refresh").click(function () {
    //     $('#table-yinShuaPaperPrice').empty();
    //     $('#table-yinShuaSizeMapping').bootstrapTable('uncheckAll');
    //     dataGet();
    // });

    //选中"动态"radio
    $("#pbTypeD").click(function () {
        $('.pbTypeShow').hide();
    });
    //选中"固定"radio
    $("#pbTypeG").click(function () {
        $('.pbTypeShow').show();
    });

    //零件纸张配置添加
    var yinShuaPaperAdd = function () {
        isAdd = true;
        $('#paperType').val('');
        $('#weight').val('');
        $('#mSize').val('');
        $('#modal-yinShuaPaper .modal-title').text("添加零件纸张");
        $('#btn-Ok').text("添加");
        $('#modal-yinShuaPaper').modal('show');
    }
    //添加
    $("#btn-add").click(function () {
        yinShuaPaperAdd();
    });
    //零件纸张配置修改
    var yinShuaPaperUpdate = function (selectRow) {
        isAdd = false;
        $('#productType').val(selectRow.productType);
        $('#paperType').val(selectRow.paperType);
        $('#weight').val(selectRow.weight);
        if (selectRow.pbType == '动态') {
            $('#pbTypeD').trigger('click');
            $('.pbTypeShow').hide();
        } else {
            $('#pbTypeG').trigger('click');
            $('#mSize').val(selectRow.mSize);
            $('#fullSizeName').val(selectRow.fullSizeName);
            $('#cuttingName').val(selectRow.cuttingName);
            $('#machineType').val(selectRow.machineType);
            $('.pbTypeShow').show();
        }
        $('#modal-yinShuaPaper .modal-title').text("修改零件纸张");
        $('#btn-Ok').text("修改");
        $('#modal-yinShuaPaper').modal('show');
    };
    //点击修改按钮
    $('#btn-update').click(function () {
        var selectRow = $('#table-yinShuaPaper').bootstrapTable('getSelections')[0];
        if (!selectRow) {
            _control.alert("请选择要修改的行", "提示", null);
            return
        }
        yinShuaPaperUpdate(selectRow);
    });
    //添加确认
    $('#btn-Ok').click(function () {
        var obj = {};
        obj.productType = $('#productType').val();
        obj.paperType = $('#paperType').val();
        obj.weight = $('#weight').val();
        var pbType = $("input[name='pbType']:checked").val();
        if (pbType == "动态") {
            obj.pbType = $("input[name='pbType']:checked").val();
            obj.mSize = null;
            obj.fullSizeName = null;
            obj.cuttingName = null;
            obj.machineType = null;
        } else {
            obj.pbType = $("input[name='pbType']:checked").val();
            obj.mSize = $('#mSize').val();
            obj.fullSizeName = $('#fullSizeName').val();
            obj.cuttingName = $('#cuttingName').val();
            obj.machineType = $('#machineType').val();
        }
        switch (true) {
            case obj.paperType == "":
                _control.alert("请填写纸张类型", "提示", null);
                return
            case obj.weight == "":
                _control.alert("请填写克重", "提示", null);
                return
        }
        //发送请求
        if (isAdd) {
            $.ajax({
                url: _root + '/order/yinShuaCraft/savePaper.do',
                data: obj,
                type: 'POST',
                dataType: _json,
                success: function (data) {
                    if (data.result) {
                        $('#table-yinShuaPaper').bootstrapTable('insertRow', {index: 0, row: data.row});
                        $('#modal-yinShuaPaper').modal('hide');
                    } else {
                        _control.alert(data.message, "错误", null);
                    }
                }
            });
        } else {
            var selectRow = $('#table-yinShuaPaper').bootstrapTable('getSelections')[0];
            obj.id = selectRow.id
            $.ajax({
                url: _root + '/order/yinShuaCraft/updatePaper.do',
                data: obj,
                type: 'POST',
                dataType: _json,
                success: function (data) {
                    if (data.result) {
                        if (pbType == "动态") data.row.mSize = '';
                        $('#table-yinShuaPaper').bootstrapTable('updateRow', {
                            index: $('#table-yinShuaPaper tr.selected').attr("data-index"),
                            row: data.row
                        });
                        $('#modal-yinShuaPaper').modal('hide');
                    } else {
                        _control.alert(data.message, "错误", null);
                    }
                }
            });
        }
    });
    //删除函数
    var yinShuaPaperDelete = function (deleteRow) {
        _control.confirm("确认删除？", null, function (isOk) {
            if (isOk) {
                $.ajax({
                    url: _root + '/order/yinShuaCraft/deletePaper.do',
                    data: deleteRow,
                    type: "POST",
                    dataType: _json,
                    success: function (data) {
                        if (data.result) {
                            $('#table-yinShuaPaper').bootstrapTable('remove', {field: 'id', values: [deleteRow.id]});
                        } else {
                            _control.alert(data.message, "错误", null);
                        }
                    }
                })
            }
        })
    };
    $('#btn-delete').click(function () {
        var deleteRow = $('#table-yinShuaPaper').bootstrapTable('getSelections')[0];
        if (!deleteRow) {
            _control.alert("请选择要删除的行", "提示", null);
            return
        }
        ;
        yinShuaPaperDelete(deleteRow);
    });
    /*表1js结束*/


    /*表2开始js,印刷价格设置*/
    //查询纸价设置数据函数
    var dataGet2=function () {
        var row = $('#table-yinShuaPaper').bootstrapTable('getSelections')[0];
        if(!row){
            _control.alert("请选择上表一行数据","提示",null);
            return
        }
        $.ajax({
            url: _root + '/order/yinShuaCraft/listPaperBy.do',
            type: 'POST',
            dataType: _json,
            data: {"productType": row.productType, "paperType": row.paperType},
            success: function (data) {
                if (data.result) {
                    $('#table-yinShuaPaperPrice').bootstrapTable('load', data.subtab);
                } else {
                    _control.alert(data.message, "错误", null);
                }
            }
        })
    };
    //定义全局变量，判断是修改还是添加纸价设置
    var isAdd2 = true;
    //纸价设置列表操作事件
    function AddBtn2(value, row, index) {
        return [
            '<a class="TableEdit2" style="margin-right: 10px;">修改</a>',
            '<a class="TableDel2">删除</a>'
        ].join("")
    };
    window.operateEvents2 = {
        "click .TableEdit2": function (e, value, row, index) {
            $('#table-yinShuaPaperPrice').bootstrapTable('uncheck', index);
            stdUnitPriceUpdate(row);
        },
        "click .TableDel2": function (e, value, row, index) {
            $('#table-yinShuaPaperPrice').bootstrapTable('uncheck', index);
            stdUnitPriceDelete(row)
        }
    };

    //纸价设置表格
    $('#table-yinShuaPaperPrice').bootstrapTable({
        toolbar: '#toolbar2',
        striped: true,
        clickToSelect: true,
        singleSelect: true,
        idField: 'id',
        height: 300,
        columns: [
            {
                title: "选择",
                checkbox: true,
                width:40
            }, {
                field: "schemeName",
                title: "方案名称",
                align: "center",
                sortable: "true"
            }, {
                field: "price",
                title: "吨价",
                align: "center",
                sortable: "true"
            }, {
                field: "button",
                title: "操作",
                align: "center",
                width: 100,
                events: operateEvents2,
                formatter: AddBtn2
            }
        ]
    });

    var id = 0;

    //请求价格方案
    $.ajax({
        url:  _root + '/order/priceScheme/listPriceSchemeByType.do',
        type:"POST",
        async:false,
        dataType:_json,
        data:{'typeStr':'印刷'},
        success:function(data){
            if(data.result){
                //定义方案数组
                var schemes=[];
                $.each(data.priceSchemes,function (index,ele) {
                    schemes.push(ele.schemeName)
                });
                //添加修改界面的价格方案下拉框
                _control.textComboBox($('#schemeName'), schemes, null);
            }else{
                _control.alert(data.message, "错误", null);
            }
        }
    });

    //刷新
    $("#btn-refresh2").click(function () {
        dataGet2()
    });

    //纸价设置添加
    var stdUnitPriceAdd = function () {
        var selectRow = $('#table-yinShuaPaper').bootstrapTable('getSelections')[0];
        if (!selectRow){
            _control.alert("请选择上表一行数据","提示",null);
            return;
        }else {
            isAdd2 = true;
            $('#schemeName').prop('disabled', false);
            $('#price').val('');
            $('#modal-yinShuaPaperPrice .modal-title').text("添加纸价设置");
            $('#btn-Ok2').text("添加");
            $('#modal-yinShuaPaperPrice').modal('show');
        }
    };
    //添加
    $("#btn-add2").click(function () {
        stdUnitPriceAdd();
    });
    //纸价设置修改
    var stdUnitPriceUpdate = function (selectRow) {
        isAdd2 = false;
        $('#schemeName').val(selectRow.schemeName);
        $('#schemeName').prop('disabled', true);
        $('#price').val(selectRow.price);
        $('#modal-yinShuaPaperPrice .modal-title').text("修改纸价设置");
        $('#btn-Ok2').text("修改");
        $('#modal-yinShuaPaperPrice').modal('show');
    };
    // 点击修改按钮
    $('#btn-update2').click(function () {
        var selectRow = $('#table-yinShuaPaper').bootstrapTable('getSelections')[0];
        if (!selectRow){
            _control.alert("请选择上表一行数据","提示",null);
            return;
        }else{
            var selectRow = $('#table-yinShuaPaperPrice').bootstrapTable('getSelections')[0];
            if (!selectRow) {
                _control.alert("请选择要修改的行", "提示", null);
                return
            }
            stdUnitPriceUpdate(selectRow);
        }
    });
    //添加确认
    $('#btn-Ok2').click(function () {
        var obj = {};
        obj.productType=$('#table-yinShuaPaper').bootstrapTable('getSelections')[0].productType;
        obj.paperType = $('#table-yinShuaPaper').bootstrapTable('getSelections')[0].paperType;
        obj.schemeName = $('#schemeName').val();
        obj.price = $('#price').val();
        switch (true) {
            case obj.price == "":
                _control.alert("请填写纸张吨价", "提示", null);
                return;
        }
        //发送请求
        if (isAdd2) {
            $.ajax({
                url: _root + '/order/yinShuaCraft/savePaperPrice.do',
                data: obj,
                type: 'POST',
                dataType: _json,
                success: function (data) {
                    if (data.result) {
                        data.rows['id'] = id++;
                        $('#table-yinShuaPaperPrice').bootstrapTable('insertRow', {index: 0, row: data.rows});
                        $('#modal-yinShuaPaperPrice').modal('hide');
                    } else {
                        _control.alert(data.message, "错误", null);
                    }
                }
            });
        } else {
            $.ajax({
                url: _root + '/order/yinShuaCraft/updatePaperPrice.do',
                data: obj,
                type: 'POST',
                dataType: _json,
                success: function (data) {
                    if (data.result) {
                        data.rows['id'] = id++;
                        $('#table-yinShuaPaperPrice').bootstrapTable('updateRow', {
                            index: $('#table-yinShuaPaperPrice tr.selected').attr("data-index"),
                            row: data.rows
                        });
                        $('#modal-yinShuaPaperPrice').modal('hide');
                    } else {
                        _control.alert(data.message, "错误", null);
                    }
                }
            });
        }
    });
    //删除函数
    var stdUnitPriceDelete = function (deleteRow) {
        _control.confirm("确认删除？", null, function (isOk) {
            if (isOk) {
                $.ajax({
                    url: _root + '/order/yinShuaCraft/deletePaperPrice.do',
                    data: deleteRow,
                    type: "POST",
                    dataType: _json,
                    success: function (data) {
                        if (data.result) {
                            $('#table-yinShuaPaperPrice').bootstrapTable('remove', {field: 'id', values: [deleteRow.id]})
                        } else {
                            _control.alert(data.message, "错误", null);
                        }
                    }
                })
            }
        })
    };
    $('#btn-delete2').click(function () {
        var deleteRow = $('#table-yinShuaPaperPrice').bootstrapTable('getSelections')[0];
        if (!deleteRow) {
            _control.alert("请选择要删除的行", "提示", null);
            return
        }
        stdUnitPriceDelete(deleteRow);
    });


});