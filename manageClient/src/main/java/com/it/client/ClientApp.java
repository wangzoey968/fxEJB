package com.it.client;

import com.it.client.mainFrame.MainFrame;
import com.it.client.mainFrame.RegComputer;
import com.it.client.mainFrame.ToolBarIcon;
import com.it.client.util.CacheUsername;
import com.it.client.util.ConfigUtil;
import com.it.client.util.httpClient.core.HttpClient;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            ConfigUtil.init();
            HttpClient.init();
            EJB.startup();
            MainFrame.getInstance().init();
            MainFrame.getInstance().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        try {
            HttpClient.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

}