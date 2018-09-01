package com.it.client.mainFrame;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.it.api.MenuData;
import com.it.client.WebContainer.WebTab;
import com.it.client.util.ConfigUtil;
import com.it.client.util.FxmlUtil;
import com.it.client.util.ImgUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.util.List;

/**
 * 此类设计为单例,获取单例对象的时候直接调用getInstance()方法
 */
public class MainFrame extends Stage {

    @FXML
    private MenuBar menuBar;
    @FXML
    private TabPane tabPane;

    //单例
    private static MainFrame instance = new MainFrame();

    public static MainFrame getInstance() {
        /*if (instance == null) {
            instance = new MainFrame();
        }*/
        return instance;
    }

    private MainFrame() {
        this.setTitle("准点印刷");
        this.getIcons().add(ImgUtil.IMG_ICON);
        this.setScene(new Scene((Parent) FxmlUtil.loadFXML(this)));
        iconifiedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) MainFrame.getInstance().close();
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
                    ToolBarIcon.getInstance().trayIcon.displayMessage("你好", "我在这儿。", TrayIcon.MessageType.INFO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void init() {
        try {
            WebTab webTab = new WebTab();
            webTab.setClosable(false);
            addTab(webTab);
            webTab.webView.getEngine().load(ConfigUtil.getServerUrl() + "/manageServer");

            Platform.setImplicitExit(false);
            ToolBarIcon.getInstance().addIcon();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addTab(Tab tab) {
        if (tab == null) return;
        if (!tabPane.getTabs().contains(tab)) tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

    private boolean canCloseTab(Tab tab) {
        Event event = new Event(tab, tab, Tab.TAB_CLOSE_REQUEST_EVENT);
        Event.fireEvent(tab, event);
        return !event.isConsumed();
    }

    public void closeTab(Tab tab) {
        int index = tabPane.getTabs().indexOf(tab);
        if (index != -1) {
            tabPane.getTabs().remove(index);
        }
        if (tab.getOnClosed() != null) {
            Event.fireEvent(tab, new Event(Tab.CLOSED_EVENT));
        }
    }

    public void setMenu(String json) {
        Gson gson = new Gson();
        List<MenuData> menus = gson.fromJson(json, new TypeToken<List<MenuData>>() {
        }.getType());
        menuBar.getMenus().clear();
        for (MenuData menuData : menus) {
            Menu menu = new Menu(menuData.getName());
            loadMenu(menuData, menu);
            menuBar.getMenus().add(menu);
        }
    }

    private void loadMenu(MenuData data, Menu menu) {
        for (MenuData menuData : data.getSubMenu()) {
            if (menuData.getKey().equals("MENU")) {
                Menu subMenu = new Menu(menuData.getName());
                loadMenu(menuData, subMenu);
                menu.getItems().add(subMenu);
            } else if (menuData.getKey().equals("SEP")) {
                menu.getItems().add(new SeparatorMenuItem());
            } else {
                menu.getItems().add(new MenuHandler(menuData));
            }
        }
    }

}
