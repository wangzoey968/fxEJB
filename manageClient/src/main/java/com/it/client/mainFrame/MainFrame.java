package com.it.client.mainFrame;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.it.api.MenuData;
import com.it.client.EJB;
import com.it.client.WebContainer.WebTab;
import com.it.client.util.FxmlUtil;
import com.it.client.util.ConfigUtil;
import com.it.client.util.ImgUtil;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;

public class MainFrame extends Stage {

    public static boolean firstHidden = true;

    public static List authList;    //权限列表；

    @FXML
    private MenuBar menuBar;
    @FXML
    private TabPane tabPane;

    public MainFrame(){
        this.getIcons().add(ImgUtil.IMG_ICON);
        this.setScene(new Scene((Parent) FxmlUtil.loadFXML(this)));
        titleProperty().bind(Bindings.format("%s:%s", EJB.factoryNameProperty, EJB.userNameProperty));
        iconifiedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) MainFrame.this.close();
            }
        });
        this.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getEventType().equals(KeyEvent.KEY_PRESSED) && event.isControlDown() && event.getCode() == KeyCode.W) {
                    closeTab(tabPane.getSelectionModel().getSelectedItem());
                } else {
                    try {
                        tabPane.getSelectionModel().getSelectedItem();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        this.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    if (firstHidden) {
                        ToolBarIcon.class.newInstance().trayIcon.displayMessage("你好", "我在这儿。", TrayIcon.MessageType.INFO);
                        firstHidden = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        WebTab webTab = new WebTab();
        webTab.setClosable(false);
        addTab(webTab);
        webTab.webView.getEngine().load(ConfigUtil.getServerUrl() + "/manageServer");
    }

    public static void init() throws Exception {
        Platform.setImplicitExit(false);
        ToolBarIcon.class.newInstance().addIcon();
    }

    public void addTab(Tab tab) {
        if (tab == null) return;
        if (!tabPane.getTabs().contains(tab)) tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private boolean canCloseTab(Tab tab) {
        javafx.event.Event event = new javafx.event.Event(tab, tab, Tab.TAB_CLOSE_REQUEST_EVENT);
        javafx.event.Event.fireEvent(tab, event);
        return !event.isConsumed();
    }

    public void closeTab(Tab tab) {
        //if (canCloseTab(tab) && tab.isClosable()) {
            int index = tabPane.getTabs().indexOf(tab);
            if (index != -1) {
                tabPane.getTabs().remove(index);
            }
            if (tab.getOnClosed() != null) {
                javafx.event.Event.fireEvent(tab, new javafx.event.Event(Tab.CLOSED_EVENT));
            }
        //}
    }

    public void setMenu(String json) {
        Gson gson = new Gson();
        java.util.List<MenuData> menus = gson.fromJson(json, new TypeToken<java.util.List<MenuData>>() {
        }.getType());
        for (MenuData menuData : menus) {
            Menu menu = new Menu(menuData.getName());
            loadMenu(menuData, menu);
            menuBar.getMenus().add(menu);
        }
    }

    public void loadMenu(MenuData data, Menu menu) {
        for (MenuData menuData : data.getSubMenu()) {
            if (menuData.getKey().equals("MENU")) {
                Menu subMenu = new Menu(menuData.getName());
                loadMenu(menuData, subMenu);
                menu.getItems().add(subMenu);
            } else if (menuData.getKey().equals("SEP")) {
                menu.getItems().add(new SeparatorMenuItem());
            } else {
                menu.getItems().add(new MainMenu(menuData));
            }
        }
    }

}
