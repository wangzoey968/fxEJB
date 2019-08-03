package com.it.test.progressTest.split;

import com.sun.deploy.ui.ProgressDialog;
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by wangzy on 2019/3/16.
 */
public class ProgressSample extends Application {

    Service<Integer> service = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int i = 0;
                    while (i++ < 100) {
                        updateProgress(i, 100);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            };
        }
    };

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.setTitle("下载文件进度条同步更新");

        Label label = new Label("下载进度：");
        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(0);
        progressBar.setPrefWidth(200);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.TOP_CENTER);
        hBox.setPrefHeight(60);
        hBox.getChildren().addAll(label, progressBar);

        Button button = new Button("开始下载");
        button.setOnMouseClicked((e) -> {
            progressBar.progressProperty().unbind();
            progressBar.progressProperty().bind(service.progressProperty());
            if (service.isRunning()){
                service.cancel();
            }else {
                service.start();
            }
        });

        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox, button);

        scene.setRoot(vBox);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
