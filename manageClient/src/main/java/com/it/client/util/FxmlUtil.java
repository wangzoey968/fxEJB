package com.it.client.util;

import com.it.client.WebContainer.WebTab;
import com.it.client.mainFrame.MainFrame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;

public class FxmlUtil {

    public static String regMac;

    public static String regComputer;

    /**
     * 如果fxml中配置了控制器,再使用此方法会报错;
     * 如果fxml中没有配置控制器,在对应的类中就要使用FxmlUtil.loadFxml(this),否则无法加载出页面
     */
    public static Node loadFXML(Object obj) {
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(obj.getClass().getResource(obj.getClass().getSimpleName() + ".fxml"));
            loader.setCharset(Charset.forName("UTF-8"));
            if (loader.getController() == null) {
                loader.setController(obj);
            }
            parent = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parent;
    }

    public static void showException(Exception e, Stage stage) {
        e.printStackTrace();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        if (exception.matches("[\\s\\S]*serialVersionUID[\\s\\S]*")) {
            e = new Exception("序列化ID不一致，此部分程序在服务器上已更新，请重启客户端。");
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (stage != null) {
            alert.initOwner(stage);
        } else {
            alert.initOwner(MainFrame.getInstance());
        }
        alert.setTitle("错误");
        alert.setResizable(false);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public static void showObject(String str) {
        try {
            if (str.startsWith("返工订单")) {
                WebTab wt = new WebTab();
                wt.webView.getEngine().load(ConfigUtil.getServerUrl() + "/manageServer/finance/listIncomeLog.do?orderId=" + str.substring("查看账单".length()));
                MainFrame.getInstance().addTab(wt);
            } else {
                new Alert(Alert.AlertType.INFORMATION, str).showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
