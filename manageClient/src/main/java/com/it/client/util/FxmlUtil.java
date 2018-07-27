package com.it.client.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class FxmlUtil {

    public static Node loadFXML(Object obj) {
        Parent parent = null;
        try {
            FXMLLoader loader = new FXMLLoader(obj.getClass().getResource(obj.getClass().getSimpleName()+".fxml"));
            loader.setCharset(Charset.forName("UTF-8"));
            loader.setController(obj);
            parent = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parent;
    }

}
