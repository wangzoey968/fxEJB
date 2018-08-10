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

    /**
     * @param webView 需要添加附属功能的WebView对象；
     * @param owner   弹框等功能依附的Owner;
     */
    public WebContainer(WebView webView,Window owner) {
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
                WebTab tab = new WebTab();
                new MainFrame().addTab(tab);
                return tab.webView.getEngine();
            }
        });
        //为页面上的webcontainer赋值
        webView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    ((JSObject) webView.getEngine().executeScript("window")).setMember("WebContainer", WebContainer.this);
                }
            }
        });
    }

    public static void sout(String s){
        System.out.println(s);
    }

    public static void Alert(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, s, ButtonType.OK);
        //alert.initOwner(owner);
        alert.showAndWait();
    }

    public static String selectFile() throws Exception {
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(MainFrame.class.newInstance());
        return file.getPath();
    }

    /**
     * 设置客户端菜单；
     */
    public static void configMainFrameMenu(String json) {
        System.out.println(json + "json");
        try {
            MainFrame.class.newInstance().setMenu(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置客户端sessionId
     */
    public static void configClientSid(String sid) {
        try {
            EJB.sidProperty.set(sid);
            EJB.userIdProperty.set(Long.parseLong(EJB.getUserService().getUserId(sid)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户ID，获取用户名称；
     */
    public static String getUserName(Long userId) {
        return CacheUsername.getUserName(userId);
    }

    /**
     * 获取登陆列表
     */
    public static String loadLoginHistory() {
        File file = Paths.get(System.getProperty("user.dir") + "/user.cfg").toFile();
        if (!file.exists()) return null;
        //return file.getText('UTF-8');
        return null;
    }
    /**
     * 保存登陆列表
     */
    public static void saveLoginHistory(String list) {
        File file = Paths.get(System.getProperty("user.dir") + "/user.cfg").toFile();
        //file.setText(list, 'UTF-8');
    }

    /**
     * 调用{@link FxmlUtil#showObject}
     * eg:objStr=订单号A01  任务号123
     */
    public static void showObject(String objStr) {
        FxmlUtil.showObject(objStr);
    }

}
