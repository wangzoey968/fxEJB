String.prototype.format = function() {
    if (arguments.length == 0)
        return this;
    if(arguments[0] instanceof Array){
        arguments = arguments[0];
    }
    for (var s = this, i = 0; i < arguments.length; i++)
        s = s.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
    return s;
};

var _control = {
    alert: function (msg, title, callBack) {
        title = title ? title : "提示";
        var alertHtml = '<div class="modal fade" id="bootstrap-alert" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">'
            + '<div class="modal-dialog" style="width: 250px">'
            + '<div class="modal-content">'
            + '<div class="modal-header">'
            + '<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="mydialog-close">  &times'
            + '</button>'
            + '<h4 class="modal-title">' + title + '</h4>'
            + '</div>'
            + '<div class="modal-body">' + msg + '</div>'
            + '<div class="modal-footer"><button id="btn-close" type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div>'
            + '</div>'
            + '</div>'
            + '</div>';
        $("body").append($(alertHtml));
        var bootstrapAlert = $('#bootstrap-alert');
        bootstrapAlert.modal('show');
        bootstrapAlert.find('#btn-close, #mydialog-close').on('click', function () {
            bootstrapAlert.remove();
            if ($(".modal-backdrop, .modal-backdrop.fade.in").length > 1){
                $(".modal-backdrop, .modal-backdrop.fade.in").eq(1).remove()
            } else {
                $(".modal-backdrop, .modal-backdrop.fade.in").remove()
            }
        });
        $('#bootstrap-alert .modal-dialog').css({
            position:"absolute",
            left:($(window).width()-$("#bootstrap-alert .modal-dialog").outerWidth())/2,
            top:($(window).height()-$("#bootstrap-alert .modal-dialog").outerHeight())/5
        });
    },
    confirm: function (msg, title, callBack) {
        title = title ? title : "确认";
        var confirmHtml = '<div class="modal fade" id="bootstrap-confirm" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">'
            + '<div class="modal-dialog" style="width: 250px">'
            + '<div class="modal-content">'
            + '<div class="modal-header">'
            + '<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="mydialog-close">  &times'
            + '</button>'
            + '<h4 class="modal-title">'+title+'</h4>'
            + '</div>'
            + '<div class="modal-body">'+msg+'</div>'
            + '<div class="modal-footer">' +
            '<button id="btn-ok" type="button" class="btn btn-primary" data-dismiss="modal">确定</button>'+
            '<button id="btn-close" type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div>'
            + '</div>'
            + '</div>'
            + '</div>';
        $("body").append($(confirmHtml));
        var bootstrapConfirm = $('#bootstrap-confirm');
        bootstrapConfirm.modal('show');
        bootstrapConfirm.find('#btn-close, #mydialog-close').on('click', function () {
            if (callBack) { callBack(false) }
            bootstrapConfirm.remove();
            if ($(".modal-backdrop, .modal-backdrop.fade.in").length > 1){
                $(".modal-backdrop, .modal-backdrop.fade.in").eq(1).remove()
            } else {
                $(".modal-backdrop, .modal-backdrop.fade.in").remove()
            }
        });
        bootstrapConfirm.find('#btn-ok').on('click', function () {
            if (callBack) { callBack(true) }
            bootstrapConfirm.remove();
            if ($(".modal-backdrop, .modal-backdrop.fade.in").length > 1){
                $(".modal-backdrop, .modal-backdrop.fade.in").eq(1).remove()
            } else {
                $(".modal-backdrop, .modal-backdrop.fade.in").remove()
            }
        });
        $('#bootstrap-confirm .modal-dialog').css({
            position:"absolute",
            left:($(window).width()-$("#bootstrap-confirm .modal-dialog").outerWidth())/2,
            top:($(window).height()-$("#bootstrap-confirm .modal-dialog").outerHeight())/5
        });
    },
    priceDialog: function (bodyDiv, title, callBack) {
        title = title ? title : "算价";
        var priceHtml = '<div class="modal fade" id="modal-price" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">'
            + '<div class="modal-dialog">'
                + '<div class="modal-content">'
                    + '<div class="modal-header">'
                        + '<button type="button" class="close" data-dismiss="modal" aria-hidden="true" id="mydialog-close">  &times</button>'
                        + '<h4 class="modal-title"></h4>'
                    + '</div>'
                    + '<div class="modal-body" id="modalBody-price"></div>'
                    + '<div class="modal-footer"><button id="btn-close" type="button" class="btn btn-default" data-dismiss="modal">关闭</button></div>'
                 + '</div>'
            + '</div>';
        $("body").append($(priceHtml));
        $('#modal-price').find('h4').text(title);
        var modalBodyPrice = $('#modalBody-price');
        modalBodyPrice.children().remove();
        modalBodyPrice.load(bodyDiv);
        var priceDialog = $('#modal-price');
        priceDialog.modal('show');
        priceDialog.find('#btn-close, #mydialog-close').on('click', function () {
            if (callBack) { callBack(false)}
            priceDialog.remove();
            if ($(".modal-backdrop, .modal-backdrop.fade.in").length > 1){
                $(".modal-backdrop, .modal-backdrop.fade.in").eq(1).remove()
            } else {
                $(".modal-backdrop, .modal-backdrop.fade.in").remove()
            }
        });
    },
    textGroupButton: function (parentDiv, texts, selectIndex, callBack) {
        var button = '<a class="btn btn-default btnCell" style="margin-left: 8px">{0}</a>';
        parentDiv.empty();
        var array = [];
        $.each(texts, function (index, value) {
            if (value) {
                array.push(button.format(value))
            }
        });
        parentDiv.html(array.join(""));
        parentDiv.find(".btnCell").each(function (index) {
            if (selectIndex == index) { $(this).addClass('btnCell-selected') }
        });
        var oldPaper = null;
        parentDiv.find(".btnCell").on("click", function () {
            var cell = $(this);
            var paper = cell.context.innerText;
            cell.addClass('btnCell-selected').siblings('.btnCell').removeClass('btnCell-selected');
            if (callBack && oldPaper != paper) {
                oldPaper = paper;
                callBack(paper);
            }
        });
    },
    sizeGroupButton: function (parentDiv, texts, selectIndex, callBack) {
        var button = '<a class="btn btn-default btnCell" style="margin-left: 8px">{0}</a>';
        parentDiv.empty();
        var array = [];
        $.each(texts, function (index, value) {
            if (value) {
                array.push(button.format(value.text))
            }
        });
        parentDiv.html(array.join(""));
        parentDiv.find(".btnCell").each(function (index) {
            if (selectIndex == index) { $(this).addClass('btnCell-selected') }
        });
        var oldSize = null;
        parentDiv.find(".btnCell").on("click", function () {
            var cell = $(this);
            var size = cell.context.innerText;
            cell.addClass('btnCell-selected').siblings('.btnCell').removeClass('btnCell-selected');
            if (callBack && oldSize != size) {
                oldSize = size;
                $.each(texts, function (index, value) {
                    if (value.text == size) { callBack(size, value.width, value.height, value.thickness); }
                });
            }
        });
    },
    textComboBox: function (parentDiv, texts, callBack) {
        parentDiv.empty();
        $.each(texts, function (index, value) {
            if (value) {
                parentDiv.append('<option value="'+ value +'">'+ value +'</option>');
            }
        });
        parentDiv.change(function () {
            if (callBack) {
                callBack(parentDiv[0].value)
            }
        })
    },
    sizeComboBox: function (parentDiv, texts, callBack) {
        parentDiv.empty();
        $.each(texts, function (index, value) {
            if (value) {
                parentDiv.append('<option value="'+ value.text +'">'+ value.text +'</option>');
            }
        });
        parentDiv.change(function () {
            if (callBack) {
                var size = parentDiv[0].value;
                $.each(texts, function (index, value) {
                    if (value.text == size) { callBack(size, value.width, value.height, value.thickness) }
                });
            }
        })
    },
    numberSpinner: function (parentDiv, initNum, stepNum, callBack) {
        parentDiv.empty();
        var spinner = '<input id="numberSpinner" type="number" class="form-control" onKeyUp="myKeyUp(this, this.value, '+ callBack +')" value="'+ initNum +'" min="0" style="text-align: left;height: 31px;">' +
                '<div class="input-group-btn-vertical">' +
                    '<button class="btn btn-default" type="button"><i class="fa fa-caret-up caret-up"></i></button>' +
                    '<button class="btn btn-default" type="button"><i class="fa fa-caret-down caret-down"></i></button>' +
                '</div>';
        parentDiv.append(spinner);
        parentDiv.find('.input-group-btn-vertical .btn:first-child').on('click', function() {
            var _that=parentDiv.find('input');
            if (_that.val() == "") {
                _that.val(stepNum);
            } else {
                _that.val(parseInt(_that.val(), 0) + stepNum);
            }
            if (callBack) { callBack(_that.val()) }
        });
        parentDiv.find('.input-group-btn-vertical .btn:last-child').on('click', function() {
            var _that=parentDiv.find('input');
            if (_that.val() == "") {
                _that.val(stepNum);
            } else {
                _that.val(parseInt(_that.val(), 0) - stepNum);
                if (parseInt(_that.val()) < initNum) {
                    _that.val(initNum);
                } else {
                    _that.val(_that.val());
                }
            }
            if (callBack) { callBack(_that.val()) }
        });
    }
};

function myKeyUp(spinner, event, callBack) {
    spinner.value = spinner.value.replace("\D/g", '');
    // if (event > 0 && callBack) { callBack(event) }
}