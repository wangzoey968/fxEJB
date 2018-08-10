$(function () {

    $.ajax({
        url: _root + '/auth/user/listMenus.do',
        type: 'POST',
        dataType: _json,
        success: function (data) {
            console.log("data",data.menus);
            if (data.result) {
                $.configMainFrameMenu(data.menus)
            } else {
                _control.alert(data.message, "错误", null);
            }
        }
    });

});