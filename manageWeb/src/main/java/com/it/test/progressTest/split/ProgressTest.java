package com.it.test.progressTest.split;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by wangzy on 2019/3/16.
 */
public class ProgressTest extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                        break;
                    }
                    System.out.println(i + 1);
                    updateProgress(i + 1, 10);
                }
                return null;
            }
        };

        ProgressBar updProg = new ProgressBar();
        updProg.progressProperty().bind(task.progressProperty());

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        layout.getChildren().add(updProg);

        stage.setScene(new Scene(layout));
        stage.show();
    }
}
