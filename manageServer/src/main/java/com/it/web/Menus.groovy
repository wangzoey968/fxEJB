package com.it.web;

import com.google.gson.reflect.TypeToken;
import com.it.api.MenuData;
import com.it.api.table.Tb_User;
import com.it.util.GsonUtil;

import java.util.List;

public class Menus {

    private static List<MenuData> datas = [
            new MenuData(name: '订单', key: 'MENU', auth: '显示订单菜单', subMenuDatas: [
                    new MenuData(name: '工作安排', key: 'FUN', value: '打开工作安排', auth: '显示工作安排'),
                    new MenuData(name: '合版工具', key: 'FUN', value: '打开合版工具', auth: '显示零件合版'),
                    new MenuData(name: '成品打包', key: 'FUN', value: '打开成品打包', auth: '显示成品打包')
            ]),
            //备料
            new MenuData(name: '备料', key: 'MENU', auth: '显示备料菜单', subMenuDatas: [
                    new MenuData(name: '开纸', key: 'FUN', value: '打开机台开纸任务', auth: '显示开纸'),
                    new MenuData(name: 'CTP', key: 'FUN', value: '打开机台CTP任务', auth: '显示CTP制版')
            ]),
            new MenuData(name: '统计', key: 'MENU', auth: '显示统计菜单', subMenuDatas: [
                    new MenuData(name: '机台加工统计', key: 'URL', value: '/manageServer/statistics/statistics.html', auth: ''),
                    new MenuData(name: '拼版数量差', key: 'URL', value: '/manageServer/statistics/staPinBan.html', auth: '特殊统计'),
                    new MenuData(name: '纸张利用率', key: 'URL', value: '/manageServer/statistics/staPaperUse.html', auth: '特殊统计')
            ]),
            //损失
            new MenuData(name: '损失', key: 'MENU', auth: '显示损失菜单', subMenuDatas: [
                    new MenuData(name: '罚单处理', key: 'FUN', value: '打开罚单处理', auth: '罚单处理'),
                    new MenuData(name: '事故处理', key: 'FUN', value: '打开事故处理', auth: '事故处理'),
                    new MenuData(name: '损失设置', key: 'MENU', auth: '事故设置', subMenuDatas: [
                            new MenuData(name: '部门设置', key: 'FUN', value: '打开部门设置', auth: '事故设置'),
                            new MenuData(name: '事故类型设置', key: 'FUN', value: '打开事故类型设置', auth: '事故设置')
                    ])
            ]),
            // 仓库管理
            new MenuData(name: '仓库', key: 'MENU', auth: '', subMenuDatas: [
                    new MenuData(name: "仓库首页", key: 'FUN', value: "打开仓库首页", auth: '')
            ]),
            new MenuData(name: '财务', key: 'MENU', auth: '显示财务菜单', subMenuDatas: [
                    new MenuData(name: '订单价格', key: 'FUN', value: '打开订单价格', auth: '订单价格修改'),
                    new MenuData(name: '现金流', key: 'FUN', value: '打开现金流', auth: '收付款'),
                    new MenuData(name: '应付款', key: 'FUN', value: '打开应付款', auth: '收付款')
            ]),
            //设置
            new MenuData(name: '设置', key: 'MENU', auth: '', subMenuDatas: [
                    new MenuData(name: "工作岗位管理", key: 'URL', value: "/manageServer/workPosition/workPositionManage/workPositionManage.html", auth: '工作岗位管理'),
                    new MenuData(name: "客户管理", key: 'URL', value: "/manageServer/cus/customerManage/customerManage.html", auth: '客户资料管理'),
                    new MenuData(name: "供应商管理", key: 'URL', value: "/manageServer/supplier/supplierManage/supplierManage.html", auth: '供应商资料管理'),
                    new MenuData(name: "电脑注册管理", key: 'URL', value: "/manageServer/uac/computerManage/computerManage.html", auth: ''),
                    new MenuData(name: "角色管理", key: 'URL', value: "/manageServer/uac/roleManage/roleManage.html", auth: '用户管理'),
                    new MenuData(name: "用户管理", key: 'URL', value: "/manageServer/uac/userManage/userManage.html", auth: '用户管理'),
                    new MenuData(name: "修改密码", key: "FUN", value: '打开修改密码', auth: ''),
                    new MenuData(name: "算价", key: 'MENU', auth: '价格设置', subMenuDatas: [
                            new MenuData(name: '产品类型', key: 'URL', value: "/manageServer/order/priceScheme/productType.html", auth: ''),
                            new MenuData(name: '价格方案', key: 'URL', value: "/manageServer/order/priceScheme/priceScheme.html", auth: ''),
                            new MenuData(name: '--------', key: 'SEP', value: "", auth: ''),
                            new MenuData(name: '印刷设置', key: 'URL', value: "/manageServer/order/priceScheme/yinShuaSet/yinShuaSet.html", auth: ''),
                            new MenuData(name: '覆膜设置', key: 'URL', value: "/manageServer/order/priceScheme/fuMoSet/fuMoSet.html", auth: ''),
                            new MenuData(name: 'uv设置', key: 'URL', value: "/manageServer/order/priceScheme/uvSet/uvSet.html", auth: ''),
                            new MenuData(name: '凹凸设置', key: 'URL', value: "/manageServer/order/priceScheme/aoTuSet/aoTuSet.html", auth: ''),
                            new MenuData(name: '烫金设置', key: 'URL', value: "/manageServer/order/priceScheme/tangSet/tangSet.html", auth: ''),
                            new MenuData(name: '压纹设置', key: 'URL', value: "/manageServer/order/priceScheme/yaWenSet/yaWenSet.html", auth: ''),
                            new MenuData(name: '压线设置', key: 'URL', value: "/manageServer/order/priceScheme/yaXianSet/yaXianSet.html", auth: ''),
                            new MenuData(name: '打码设置', key: 'URL', value: "/manageServer/order/priceScheme/daMaSet/daMaSet.html", auth: ''),
                            new MenuData(name: '刮刮卡设置', key: 'URL', value: "/manageServer/order/priceScheme/guaGuaKaSet/guaGuaKaSet.html", auth: ''),
                            new MenuData(name: '名片打孔设置', key: 'URL', value: "/manageServer/order/priceScheme/mPDaKongSet/mPDaKongSet.html", auth: ''),
                            new MenuData(name: '名片圆角设置', key: 'URL', value: "/manageServer/order/priceScheme/mPYuanJiaoSet/mPYuanJiaoSet.html", auth: ''),
                            new MenuData(name: '划线设置', key: 'URL', value: "/manageServer/order/priceScheme/huaXianSet/huaXianSet.html", auth: ''),
                            new MenuData(name: '模切设置', key: 'URL', value: "/manageServer/order/priceScheme/moQieSet/moQieSet.html", auth: ''),
                            new MenuData(name: '成品折页设置', key: 'URL', value: "/manageServer/order/priceScheme/chengPinZheYeSet/chengPinZheYeSet.html", auth: ''),
                            new MenuData(name: '手提袋设置', key: 'URL', value: "/manageServer/order/priceScheme/shouTiDaiSet/shouTiDaiSet.html", auth: ''),
                            new MenuData(name: '信封设置', key: 'URL', value: "/manageServer/order/priceScheme/xinFengSet/xinFengSet.html", auth: ''),
                            new MenuData(name: '装订设置', key: 'URL', value: "/manageServer/order/priceScheme/zhuangDingSet/zhuangDingSet.html", auth: '')
                    ])
            ])
    ]

    private static String menuJson = GsonUtil.gson.toJson(datas)

    public static List<MenuData> getMenus() {
        return GsonUtil.gson.fromJson(menuJson, new TypeToken<List<MenuData>>() {}.getType())
    }

    public static List<MenuData> getMenus(Tb_User user) {
        List<MenuData> list = GsonUtil.gson.fromJson(menuJson, new TypeToken<List<MenuData>>() {}.getType())
        generateMenu(list, user)
        return list
    }

    private static void generateMenu(List<MenuData> list, Tb_User user) {
        //判断list中是否含有某项权限,如果该用户没有此项权限,从list中移除
        for (MenuData md : list) {
            if (!md.getAuth().isEmpty() && !user.getAuthList().contains(md.getAuth())) {
                list.remove(md.getAuth())
            }
        }
        //递归,如果是menu那么就给继续遍历,获取子菜单
        for (MenuData md : list) {
            if (md.getKey().equals("MENU")) {
                generateMenu(md.getSubMenu(), user)
            }
        }
    }

}
