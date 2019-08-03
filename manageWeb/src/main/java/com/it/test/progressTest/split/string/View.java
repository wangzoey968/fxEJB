package com.it.test.progressTest.split.string;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Created by wangzy on 2019/3/14.
 */
public class View {

    private ProgressBar progressBar;
    private Label title;
    private Label message;
    private Label running;
    private Label state;
    private Label totalWork;
    private Label workDone;
    private Label progress;
    private Label value;
    private Label exception;
    private Button startButton;
    private Button cancelButton;
    private Button exceptionButton;
    private Scene scene;

    public View(final Model model) {
        progressBar = new ProgressBar();
        progressBar.setMinWidth(250);
        title = new Label();
        message = new Label();
        running = new Label();
        state = new Label();
        totalWork = new Label();
        workDone = new Label();
        progress = new Label();
        value = new Label();
        exception = new Label();
        startButton = new Button("Start");
        cancelButton = new Button("Cancel");
        exceptionButton = new Button("Exception");
        final ReadOnlyObjectProperty<Worker.State> stateProperty = model.getWorker().stateProperty();
        progressBar.progressProperty().bind(model.getWorker().progressProperty());
        title.textProperty().bind(model.getWorker().titleProperty());
        message.textProperty().bind(model.getWorker().messageProperty());
        running.textProperty().bind(Bindings.format("%s", model.getWorker().runningProperty()));
        state.textProperty().bind(Bindings.format("%s", stateProperty));
        totalWork.textProperty().bind(model.getWorker().totalWorkProperty().asString());
        workDone.textProperty().bind(model.getWorker().workDoneProperty().asString());
        progress.textProperty().bind(Bindings.format("%5.2f%%", model.getWorker().progressProperty().multiply(100)));
        value.textProperty().bind(model.getWorker().valueProperty());
        exception.textProperty().bind(
                Bindings.createStringBinding(() -> {
                    final Throwable exception = model.getWorker().getException();
                    if (exception == null) return "";
                    return exception.getMessage();
                }, model.getWorker().exceptionProperty()));
        startButton.disableProperty().bind(stateProperty.isNotEqualTo(Worker.State.READY));
        cancelButton.disableProperty().bind(stateProperty.isNotEqualTo(Worker.State.RUNNING));
        exceptionButton.disableProperty().bind(stateProperty.isNotEqualTo(Worker.State.RUNNING));
        HBox topPane = new HBox(10, progressBar);
        topPane.setAlignment(Pos.CENTER);
        topPane.setPadding(new Insets(10, 10, 10, 10));
        ColumnConstraints constraints1 = new ColumnConstraints();
        constraints1.setHalignment(HPos.CENTER);
        constraints1.setMinWidth(65);
        ColumnConstraints constraints2 = new ColumnConstraints();
        constraints2.setHalignment(HPos.LEFT);
        constraints2.setMinWidth(200);
        GridPane centerPane = new GridPane();
        centerPane.setHgap(10);
        centerPane.setVgap(10);
        centerPane.setPadding(new Insets(10, 10, 10, 10));
        centerPane.getColumnConstraints().addAll(constraints1, constraints2);
        centerPane.add(new Label("Title:"), 0, 0);
        centerPane.add(new Label("Message:"), 0, 1);
        centerPane.add(new Label("Running:"), 0, 2);
        centerPane.add(new Label("State:"), 0, 3);
        centerPane.add(new Label("Total Work:"), 0, 4);
        centerPane.add(new Label("Work Done:"), 0, 5);
        centerPane.add(new Label("Progress:"), 0, 6);
        centerPane.add(new Label("Value:"), 0, 7);
        centerPane.add(new Label("Exception:"), 0, 8);
        centerPane.add(title, 1, 0);
        centerPane.add(message, 1, 1);
        centerPane.add(running, 1, 2);
        centerPane.add(state, 1, 3);
        centerPane.add(totalWork, 1, 4);
        centerPane.add(workDone, 1, 5);
        centerPane.add(progress, 1, 6);
        centerPane.add(value, 1, 7);
        centerPane.add(exception, 1, 8);
        HBox buttonPane = new HBox(10, startButton, cancelButton, exceptionButton);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));
        buttonPane.setAlignment(Pos.CENTER);
        BorderPane root = new BorderPane(centerPane, topPane, null, buttonPane, null);
        scene = new Scene(root);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public Label getTitle() {
        return title;
    }

    public void setTitle(Label title) {
        this.title = title;
    }

    public Label getMessage() {
        return message;
    }

    public void setMessage(Label message) {
        this.message = message;
    }

    public Label getRunning() {
        return running;
    }

    public void setRunning(Label running) {
        this.running = running;
    }

    public Label getState() {
        return state;
    }

    public void setState(Label state) {
        this.state = state;
    }

    public Label getTotalWork() {
        return totalWork;
    }

    public void setTotalWork(Label totalWork) {
        this.totalWork = totalWork;
    }

    public Label getWorkDone() {
        return workDone;
    }

    public void setWorkDone(Label workDone) {
        this.workDone = workDone;
    }

    public Label getProgress() {
        return progress;
    }

    public void setProgress(Label progress) {
        this.progress = progress;
    }

    public Label getValue() {
        return value;
    }

    public void setValue(Label value) {
        this.value = value;
    }

    public Label getException() {
        return exception;
    }

    public void setException(Label exception) {
        this.exception = exception;
    }

    public Button getStartButton() {
        return startButton;
    }

    public void setStartButton(Button startButton) {
        this.startButton = startButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(Button cancelButton) {
        this.cancelButton = cancelButton;
    }

    public Button getExceptionButton() {
        return exceptionButton;
    }

    public void setExceptionButton(Button exceptionButton) {
        this.exceptionButton = exceptionButton;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
