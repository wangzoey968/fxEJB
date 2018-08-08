package com.it.client.mainFrame;

import com.it.api.MenuData;
import com.it.client.WebContainer.WebTab;
import com.it.client.order.OrderTab;
import com.it.client.util.ConfigUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class MainMenu extends MenuItem {

    private MenuData menuData;

    public MainMenu() {

    }

    public MainMenu(String text) {
        super(text);
    }

    public MainMenu(MenuData menuData) {
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
                            MainFrame.class.newInstance().addTab(wt);
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
                        CallFun(menuData.getValue());
                        System.out.println("调用函数");
                    }
                });
                break;
        }
    }

    static void CallFun(String funName) {
        switch (funName) {
            case "超管":
                new MainFrame().addTab(new OrderTab());
                break;
            case "超管1":
                new MainFrame().addTab(new OrderTab());
                break;
        }
    }

}
