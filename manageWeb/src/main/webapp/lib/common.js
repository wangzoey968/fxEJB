// TabPage类
function TabPage(param) {
    //字段
    this.tabWrap = $(param.tabWrap || "#tabWrap");
    this.pageWrap = $(param.tabWrap || "#pageWrap");
    this.fileWrap = $(param.fileWrap || "#fileWrap");
    this.initPage = param.initPage;
}
TabPage.prototype = {
    //构造
    constructor: "TabPage",
    //函数
    init: function () {
        this.initTab();//先要初始化tab
        this.initEvent();//tab的相关事件
    },
    initTab: function () {//初始化tab
        this.comAdd(this.initPage);
        this.resetTab(this.initPage.name);
        this.resetPage(this.initPage.name);
    },
    initEvent: function () {//tab的相关事件
        this.addTab();//添加tab
        this.clickTab();//点击头部tab切换
        this.closeTab();//关闭头部tab
        this.refreshTab();//刷新tab
        this.closeOths();//关闭其他标签页
        this.resize();//窗口大小改变
    },
    resize: function () {//窗口大小改变
        var g = this;
        window.onresize = function () {
            g.adjustTab();
            $(".iframeStyle").each(function () {
                $(this).attr("height", document.body.scrollHeight - 30);
            });
        };
    },
    closeOths: function () {//关闭其他标签页
        var g = this;
        $(".jCloOth").click(function () {
            g.tabWrap.find("li").each(function () {
                if (!$(this).hasClass("jTabCurr")) {
                    $(this).remove();
                }
            });
            g.pageWrap.find(".page-item").each(function () {
                if (!$(this).hasClass("jPageCurr")) {
                    $(this).remove();
                }
            });
            g.adjustTab();
        })
    },
    refreshTab: function () {//刷新tab
        var g = this;
        $(".jRefresh").click(function () {
            var name = g.tabWrap.find(".jTabCurr").data("name");
            window.frames[name].location.reload();
        })
    },
    closeTab: function () {//关闭头部tab
        var g = this;
        this.tabWrap.on("click", ".jTabClose", function (event) {
            var tab = $(this).parent();
            var name = tab.data("name");
            tab.remove();//移除该tab
            g.pageWrap.find(".page-item[data-name=" + name + "]").remove();//移除该tab对应的页面
            if (tab.hasClass("jTabCurr")) {//如果关闭的tab是当前页的tab 需要重置选中页
                if (g.tabWrap.find("li").length > 0) {//如果还有tab 选中第一个;将tab选中状态转移
                    name = g.tabWrap.find("li").eq(0).data("name");
                    g.resetTab(name);
                    g.resetPage(name);
                } else {//如果tab删完了  显示初始化tab
                    g.initTab();
                }
            }
            g.adjustTab();
            return false;
        })
    },
    closeTabByName: function (name) {//根据名称关闭tab
        this.tabWrap.find("li").each(function () {
            if ($(this).data("name") == name) {
                $(this).find(".jTabClose").click();
            }
        });
    },
    clickTab: function () {//点击头部tab切换
        var g = this;
        this.tabWrap.on("click", "li", function (event) {
            if ($(this).hasClass("jTabCurr")) return;//点中的是选中的tab
            var name = $(this).data("name");
            var title = $(this).find(".jTabName").text();
            g.resetTab(name);
            g.resetPage(name);
        })
    },
    addTab: function () {//添加tab
        var g = this;
        $(".index-l").on("click", ".jNav", function (event) {
            var name = $(this).data("name");
            var title = $(this).data("title");
            var url = $(this).data("url");
            g.comJump(name, title, url);
        })
    },
    comAdd: function (obj) {//添加tab公用方法
        var tabHtml = "<li class=\"tab-item\" data-name=\"" + obj.name + "\"><span class=\"jTabName\">" + obj.title + "</span><span class=\"jTabClose\"><i class=\"iconfont\">&#xe61b;</i></span></li>";
        this.tabWrap.append(tabHtml);
        var pageHtml = "<div class=\"page-item\" data-name=\"" + obj.name + "\"><iframe src=\"" + obj.url + "\" class=\"iframeStyle\" name=\"" + obj.name + "\" onload=\"javascript:this.height=document.body.scrollHeight-30\"></iframe></div>";
        this.pageWrap.append(pageHtml);
    },
    resetTab: function (name) {//重置tab选中项
        this.tabWrap.find("li").each(function () {
            if ($(this).data("name") == name) {
                $(this).addClass("jTabCurr");
            } else {
                $(this).removeClass("jTabCurr");
            }
        });
        var title = this.tabWrap.find(".jTabCurr .jTabName").text();
        $(".jNav").each(function () {//左侧需要同步
            if ($(this).data("title") == title) {
                $(this).addClass("jNavCurr");
            } else {
                $(this).removeClass("jNavCurr");
            }
        });
        this.listenTab();
    },
    resetPage: function (name) {//重置当前显示的page
        this.pageWrap.find(".page-item").each(function () {
            if ($(this).data("name") == name) {
                $(this).addClass("jPageCurr");
            } else {
                $(this).removeClass("jPageCurr");
            }
        });
    },
    adjustTab: function () {//调整tab宽度
        var oTabs = this.tabWrap.find("li");
        var oWrapW = this.tabWrap.width();//容器宽度
        var availW = accSub(oWrapW, accMul(2, oTabs.length));//可用的tab总宽度;总宽度减去'刷新按钮'和'关闭其它标签页'的宽度
        var perW = Math.min(accDiv(availW, oTabs.length), 180);//宽度最大180
        oTabs.innerWidth(perW + 'px');
    },
    linkTo: function (obj) {//内页调用的页面跳转方法
        var name = obj.name;
        var title = obj.title;
        var url = obj.url;
        this.comJump(name, title, url);
    },
    comJump: function (name, title, url) {//共通的跳转方法
        var nav = $(".tab-item[data-name=" + name + "]");
        if (nav.length > 0) {//如果点击的tab 已经存在 让该页面重载
            nav.find(".jTabName").html(title);
            if (name != 'indexClient') {
                $("iframe[name='" + name + "']").attr("src", url);
            }
        } else {//不存在 新加一个tab
            this.comAdd({name: name, title: title, url: url});
            this.adjustTab();
        }
        this.resetTab(name);
        this.resetPage(name);
    },
    listenTab: function () {//监听当前tab
        var name = this.tabWrap.find(".jTabCurr").data("name");
        if (name == "product") {//产品下单页面 需要显示右侧文件下载列表
            this.fileWrap.addClass("jShow");
            index.refreshScroll();
        } else {
            this.fileWrap.removeClass("jShow");
        }
    }
};