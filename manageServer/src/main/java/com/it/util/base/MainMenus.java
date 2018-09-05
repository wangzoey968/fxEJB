package com.it.util.base;

import com.google.gson.reflect.TypeToken;
import com.it.api.MenuData;
import com.it.api.table.user.Tb_User;
import com.it.util.GsonUtil;
import com.it.web.user.service.Core;

import java.util.ArrayList;
import java.util.List;

public class MainMenus {

    public static List<MenuData> menuDatas = new ArrayList<>();

    static {
        //下单
        List<MenuData> subOrder = new ArrayList<>();
        subOrder.add(new MenuData("下单", "FUN", "打开下单", "显示下单", null));
        subOrder.add(new MenuData("订单查询", "FUN", "打开查询订单", "显示订单查询", null));
        List<MenuData> childOrder = new ArrayList<>();
        childOrder.add(new MenuData("订单详情","FUN","打开订单详情","显示订单详情",null));
        subOrder.add(new MenuData("详情", "MENU", "", "显示订单详情", childOrder));
        MenuData order = new MenuData("订单", "MENU", "", "订单", subOrder);
        menuDatas.add(order);

        //财务
        List<MenuData> subFinance = new ArrayList<>();
        subFinance.add(new MenuData("订单价格", "FUN", "打开订单价格", "超管", null));
        subFinance.add(new MenuData("现金流", "FUN", "打开现金流", "超管", null));
        subFinance.add(new MenuData("应付款", "FUN", "打开应付款", "显示应付款", null));
        MenuData finance = new MenuData("财务", "MENU", "", "超管", subFinance);
        menuDatas.add(finance);

        //统计
        List<MenuData> subStatistic = new ArrayList<>();
        subStatistic.add(new MenuData("加工统计", "URL", "/manageServer/statistics/statistics.html", "超管", null));
        subStatistic.add(new MenuData("利用率", "URL", "/manageServer/statistics/staPaperUse.html", "打开利用率", null));
        MenuData statistic = new MenuData("统计", "MENU", "", "超管", subStatistic);
        menuDatas.add(statistic);

        //事故单配置
        List<MenuData> subAccidentSet = new ArrayList<>();
        subAccidentSet.add(new MenuData("部门设置", "FUN", "打开部门设置", "事故设置", null));
        subAccidentSet.add(new MenuData("事故类型设置", "FUN", "打开事故类型设置", "超管", null));
        MenuData accidentSet = new MenuData("损失设置", "MENU", "", "事故设置", subAccidentSet);
        menuDatas.add(accidentSet);

        //用户设置
        List<MenuData> subUserSet = new ArrayList<>();
        subUserSet.add(new MenuData("用户设置", "FUN", "打开用户设置", "超管", null));
        subUserSet.add(new MenuData("角色设置", "FUN", "打开角色设置", "超管", null));
        subUserSet.add(new MenuData("权限设置", "FUN", "打开权限设置", "超管", null));
        MenuData userSet = new MenuData("用户设置", "MENU", "", "超管", subUserSet);
        menuDatas.add(userSet);

    }

    private static String menuJson = GsonUtil.gson.toJson(menuDatas);

    public static List<MenuData> getMenus() {
        return GsonUtil.gson.fromJson(menuJson, new TypeToken<List<MenuData>>() {
        }.getType());
    }

    public static List<MenuData> getMenus(Tb_User user) {
        List<MenuData> list = GsonUtil.gson.fromJson(menuJson, new TypeToken<List<MenuData>>() {
        }.getType());
        generateMenu(list, user);
        return list;
    }

    private static void generateMenu(List<MenuData> list, Tb_User user) {
        //先提取出所有权限的名称;
        List<String> auths = Core.getUserAllAuths(user);
        System.out.println(auths.toString() + "/auths");
        ArrayList<MenuData> newMenu = new ArrayList<>();
        newMenu.addAll(list);
        for (MenuData menuData : list) {
            if (!menuData.getAuth().isEmpty() && !auths.contains(menuData.getAuth())) {
                newMenu.remove(menuData);
            }
        }

        //递归,如果是menu那么就继续遍历,获取子菜单;
        for (MenuData md : list) {
            if (md.getKey().equals("MENU")) {
                generateMenu(md.getSubMenu(), user);
            }
        }
        list.clear();
        list.addAll(newMenu);
    }

}
