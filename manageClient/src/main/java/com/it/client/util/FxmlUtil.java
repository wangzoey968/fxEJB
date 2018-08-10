package com.it.client.util;

import com.it.client.WebContainer.WebTab;
import com.it.client.mainFrame.MainFrame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.nio.charset.Charset;

public class FxmlUtil {

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

    public static void showObject(String str) {
        try {
            if (str.startsWith("返工订单")) {
                WebTab wt = new WebTab();
                wt.webView.getEngine().load(ConfigUtil.getServerUrl() + "/manageServer/finance/listIncomeLog.do?orderId=" + str.substring("查看账单".length()));
                MainFrame.class.newInstance().addTab(wt);
            } else {
                new Alert(Alert.AlertType.INFORMATION, str).showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
