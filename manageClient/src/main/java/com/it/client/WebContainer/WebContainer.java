package com.it.client.WebContainer;

import com.google.gson.JsonObject;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.CacheUsername;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import netscape.javascript.JSObject;

import java.io.File;

public class WebContainer {

    private WebView webView;
    private Window owner;

    public ReadOnlyStringProperty titleProperty;    //网页标题；

    /**
     * @param webView 需要添加附属功能的WebView对象；
     * @param owner   弹框等功能依附的Owner;
     */
    public WebContainer(WebView webView, Window owner) {
        this.webView = webView;
        this.owner = owner;
        setup();
    }

    public void setup() {
        this.titleProperty = webView.getEngine().titleProperty();
        webView.getEngine().setOnAlert(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> event) {
                Alert(event.getData());
            }
        });
        webView.getEngine().setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
            @Override
            public WebEngine call(PopupFeatures param) {
                WebTab webTab = null;
                try {
                    webTab = new WebTab();
                    MainFrame.class.newInstance().addTab(webTab);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return webTab.webView.getEngine();
            }
        });
        //为页面上的webcontainer赋值
        webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue== Worker.State.SUCCEEDED){
                    ((JSObject)webView.getEngine().executeScript("window")).setMember("WebContainer",WebContainer.this);
                }
            }
        });
    }

    public void Alert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, s, ButtonType.OK);
        alert.initOwner(owner);
        alert.showAndWait();
    }

    public String selectFile() throws Exception {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(MainFrame.class.newInstance());
        return file.getPath();
    }

    /**
     * Ext.js 设置客户端菜单；
     *
     * @param json 客户端菜单的JSON
     */
    public void configMainFrameMenu(String json) {
        System.out.println(json + "this is json");
        try {
            MainFrame.class.newInstance().setMenu(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ext.js 设置客户端SID
     *
     * @param sid
     */
    public void configClientSid(String sid) {
        try {
            EJB.sidProperty.set(sid);
            EJB.userIdProperty.set(Long.parseLong(EJB.getUserService().getUserId(sid)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户ID，获取用户名称；
     *
     * @param userId
     * @return
     */
    public String getUserName(Long userId) {
        return CacheUsername.getUserName(userId);
    }

}
