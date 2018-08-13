$.extend({
    print: function (str) {
        if (typeof WebContainer === "undefined") return;
        WebContainer.print(str);
    },
    selectFile: function (callback) {
        if (typeof WebContainer === "undefined") return;
        if (typeof callback === 'function') {
            var x = WebContainer.selectFile();
            callback(x); // 给callback赋值，callback是个函数变量
        }
    },
    //设置客户端SID；
    configClientSid: function (str) {
        if (typeof WebContainer === "undefined") return;
        WebContainer.configClientSid(str)
    },
    //根据用户ID，获取用户名称；
    getUsername: function (userId) {
        if (typeof WebContainer === "undefined") return;
        return WebContainer.getUsername(userId)
    },
    //设置客户端菜单；
    configMainFrameMenu: function (str) {
        if (typeof WebContainer === "undefined") return;
        WebContainer.configMainFrameMenu(str)
    },
    // 获取登陆列表
    loadLoginHistory: function () {
        if (typeof WebContainer === "undefined") return;
        return WebContainer.loadLoginHistory()
    },
    // 保存登陆列表
    saveLoginHistory: function (str) {
        if (typeof WebContainer === "undefined") return;
        WebContainer.saveLoginHistory(str)
    },
    //显示某些东西
    showObject: function (str) {
        if (typeof WebContainer === "undefined") return false;
        WebContainer.showObject(str);
        return true
    }
});
