package com.it.client.mqTest;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by wangzy on 2019/6/15.
 */
public class TestMqApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Client();
        }
    }
}
