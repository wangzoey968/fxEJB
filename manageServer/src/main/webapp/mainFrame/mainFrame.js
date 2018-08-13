$(function () {

    $.ajax({
        url: _root + '/core/user/listMenus.do',
        type: 'POST',
        dataType: _json,
        success: function (data) {
            if (data.result) {
                $.configMainFrameMenu(data.menus)
            } else {
                _control.alert(data.message, "错误", null);
            }
        }
    });

});