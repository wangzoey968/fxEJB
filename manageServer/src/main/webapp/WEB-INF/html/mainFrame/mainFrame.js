$(function () {

    $.ajax({
        url: _root + '/auth/user/listMenus.do',
        type: 'POST',
        dataType: _json,
        success: function (data) {
            // console.log(data.result,"1111111222222222");
            if (data.result) {
                console.log(data.result,"11111113333333333");
                $.configMainFrameMenu(data.menus)
            } else {
                _control.alert(data.message, "错误", null);
            }
        }
    });

});