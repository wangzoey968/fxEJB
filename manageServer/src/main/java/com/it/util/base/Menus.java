package com.it.util.base;

import com.google.gson.reflect.TypeToken;
import com.it.api.MenuData;
import com.it.api.table.user.Tb_Auth;
import com.it.api.table.user.Tb_User;
import com.it.util.GsonUtil;
import com.it.web.user.service.CoreService;

import java.util.ArrayList;
import java.util.List;

public class Menus {

    public static List<MenuData> menuDatas = new ArrayList<>();

    static {
        List<MenuData> subMenu = new ArrayList<MenuData>();

        //财务
        subMenu.clear();
        subMenu.add(new MenuData("订单价格", "FUN", "打开订单价格", "超管", null));
        subMenu.add(new MenuData("现金流", "FUN", "打开现金流", "超管", null));
        subMenu.add(new MenuData("应付款", "FUN", "打开应付款", "显示应付款", null));
        MenuData finance = new MenuData("财务", "MENU", "", "超管", subMenu);
        menuDatas.add(finance);

        //统计
        subMenu.clear();
        subMenu.add(new MenuData("加工统计", "URL", "/manageServer/statistics/statistics.html", "打开加工统计", null));
        subMenu.add(new MenuData("利用率", "URL", "/manageServer/statistics/staPaperUse.html", "打开利用率", null));
        MenuData statistic = new MenuData("统计", "MENU", "", "超管", subMenu);
        menuDatas.add(statistic);

        subMenu.clear();
        subMenu.add(new MenuData("部门设置", "FUN", "打开部门设置", "事故设置", null));
        subMenu.add(new MenuData("事故类型设置", "FUN", "打开事故类型设置", "事故设置", null));
        MenuData accidentSet = new MenuData("损失设置", "MENU", "", "事故设置", subMenu);
        menuDatas.add(accidentSet);

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
        //临时存放user没有的权限
        ArrayList<MenuData> notAuth= new ArrayList<>();
        //先提取出所有权限的名称;
        List<String> auths = CoreService.getUserAllAuths(user);
        //判断list中是否含有某项权限,如果该用户没有此项权限,从list中移除;
        for (MenuData md : list) {
            if (!md.getAuth().isEmpty() && !auths.contains(md.getAuth())) {
                notAuth.add(md);
            }
        }
        list.removeAll(notAuth);
        //递归,如果是menu那么就继续遍历,获取子菜单;
        for (MenuData md : list) {
            if (md.getKey().equals("MENU")) {
                generateMenu(md.getSubMenu(), user);
            }
        }
    }

}
