package com.it.client.util;

import com.it.client.mainFrame.MainFrame;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by wangzy on 2019/3/19.
 */
public class ExceptionUtil {

    public static void showException(Exception e) {
        e.printStackTrace();
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
        alert.initOwner(MainFrame.getInstance());
        alert.showAndWait();
    }

}
