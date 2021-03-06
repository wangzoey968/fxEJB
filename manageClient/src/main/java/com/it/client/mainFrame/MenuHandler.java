package com.it.client.mainFrame;

import com.it.api.MenuData;
import com.it.client.WebContainer.WebTab;
import com.it.client.order.OrderTab;
import com.it.client.order.cus.CusCreateOrderTab;
import com.it.client.supplier.SupplierTab;
import com.it.client.user.AuthTab;
import com.it.client.user.RoleTab;
import com.it.client.user.UserTab;
import com.it.client.util.ConfigUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import sun.applet.Main;

public class MenuHandler extends MenuItem {

    private MenuData menuData;

    public MenuHandler() {

    }

    public MenuHandler(String text) {
        super(text);
    }

    public MenuHandler(MenuData menuData) {
        super(menuData.getName());
        this.menuData = menuData;
        init();
    }

    private void init() {
        switch (menuData.getKey()) {
            case "URL":
                setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            WebTab wt = new WebTab();
                            wt.webView.getEngine().load(ConfigUtil.getServerUrl() + menuData.getValue());
                            MainFrame.getInstance().addTab(wt);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case "FUN":
                setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        callFun(menuData.getValue());
                    }
                });
                break;
        }
    }

    private static void callFun(String funName) {
        switch (funName) {
            //订单
            case "打开下单":
                MainFrame.getInstance().addTab(new CusCreateOrderTab());
                break;
            case "打开查询订单":
                MainFrame.getInstance().addTab(new OrderTab());
                break;
            case "打开订单详情":
                MainFrame.getInstance().addTab(new OrderTab());
                break;

            //财务
            case "打开现金流":
                MainFrame.getInstance().addTab(new OrderTab());
                break;

            //供应商管理
            case "打开供应商设置":
                MainFrame.getInstance().addTab(new SupplierTab());
                break;

            //用户,角色,权限管理
            case "打开用户设置":
                MainFrame.getInstance().addTab(new UserTab());
                break;
            case "打开角色设置":
                MainFrame.getInstance().addTab(new RoleTab());
                break;
            case "打开权限设置":
                MainFrame.getInstance().addTab(new AuthTab());
        }
    }

}
