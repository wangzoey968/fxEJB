package com.it.client.WebContainer;

import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.CacheUsername;
import com.it.client.util.FxmlUtil;
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
import java.nio.file.Paths;

public class WebContainer {

    private WebView webView;
    private Window owner;

    public ReadOnlyStringProperty titleProperty;    //网页标题；

    public WebContainer(WebView webView, Window owner) {
        this.webView = webView;
        this.owner = owner;
        setup();
    }

    private void setup() {
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
                WebTab tab = new WebTab();
                MainFrame.getInstance().addTab(tab);
                return tab.webView.getEngine();
            }
        });
        //为页面上的webcontainer赋值,由ext.js中的webcontainer调用
        webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    ((JSObject) webView.getEngine().executeScript("window")).setMember("WebContainer", WebContainer.this);
                }
            }
        });
    }

    /**
     * 下面的多个方法必须是非静态的,不然前端页面识别不出来
     */

    public void sout(String s) {
        System.out.println(s);
    }

    public void Alert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, s, ButtonType.OK);
        alert.initOwner(owner);
        alert.showAndWait();
    }

    public String selectFile() throws Exception {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(MainFrame.getInstance());
        return file.getPath();
    }

    /**
     * 设置客户端菜单；
     */
    public void configMainFrameMenu(String json) {
        try {
            MainFrame.getInstance().setMenu(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置客户端sessionId
     */
    public void configClientSid(String sid) {
        try {
            EJB.setSessionId(sid);
            EJB.setUserId(EJB.getUserService().getUserIdBySession(sid));
            EJB.setUsername(EJB.getUserService().getUsernameBySession(sid));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户ID，获取用户名；
     */
    public String getUsername(Long userId) {
        return CacheUsername.getUsername(userId);
    }

    /**
     * 获取登陆列表
     */
    public String loadLoginHistory() {
        File file = Paths.get(System.getProperty("user.dir") + "/user.cfg").toFile();
        if (!file.exists()) return null;
        //return file.getText('UTF-8');
        return null;
    }

    /**
     * 保存登陆列表
     */
    public void saveLoginHistory(String list) {
        File file = Paths.get(System.getProperty("user.dir") + "/user.cfg").toFile();
        //file.setText(list, 'UTF-8');
    }

    /**
     * 调用{@link FxmlUtil#showObject}
     * eg:objStr=订单号A01  任务号123
     */
    public void showObject(String objStr) {
        FxmlUtil.showObject(objStr);
    }

}
