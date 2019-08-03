package com.it.test.progressTest.split.string;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by wangzy on 2019/3/14.
 */
public class ProgressTest extends Application {

    private Model model;
    private View view;

    public static void main(String[] args) {
        launch(args);
    }

    public ProgressTest() {
        model = new Model();
    }

    @Override
    public void start(Stage stage) throws Exception {
        view = new View(model);
        hookupEvents();
        stage.setTitle("Task Example");
        stage.setScene(view.getScene());
        stage.show();
    }

    private void hookupEvents() {
        view.getStartButton().setOnAction(actionEvent -> {
            new Thread((Runnable) model.getWorker()).start();
        });
        view.getCancelButton().setOnAction(actionEvent -> {
            model.getWorker().cancel();
        });
        view.getExceptionButton().setOnAction(actionEvent -> {
            model.getShouldThrow().getAndSet(true);
        });
    }

}