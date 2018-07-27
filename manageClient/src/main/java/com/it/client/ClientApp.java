package com.it.client;

import com.it.client.mainFrame.MainFrame;
import com.it.client.util.ConfigUtil;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            ConfigUtil.init();
            try {
                EJB.startup();
            } catch (Exception e) {
                e.printStackTrace();
            }
            MainFrame.init();
            MainFrame mainFrame = new MainFrame();
            mainFrame.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}