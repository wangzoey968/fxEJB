package com.it.client.fxUtil;

import com.sun.javafx.scene.control.skin.DatePickerContent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDate;

public class DateTimePicker extends HBox {

    private DatePicker picker = new DatePicker();

    private ListView<String> lvHours = new ListView<>();

    private ListView<String> lvMinutes = new ListView<>();

    private TextField node = null;

    public DateTimePicker() throws Exception {
        Class<DatePickerContent> cl = DatePickerContent.class;
        Constructor<DatePickerContent> constructor = cl.getDeclaredConstructor(DatePicker.class);
        constructor.setAccessible(true);
        DatePickerContent content = constructor.newInstance(picker);
        Method updateValues = cl.getDeclaredMethod("updateValues");
        updateValues.setAccessible(true);

        for (int i = 1; i <= 23; i++) {
            lvHours.getItems().add(String.format("%02d", i));
        }
        lvHours.setPrefSize(70, 214);
        lvHours.getSelectionModel().selectFirst();

        for (int i = 1; i <= 59; i++) {
            lvMinutes.getItems().add(String.format("%02d", i));
        }
        lvMinutes.setPrefSize(70, 214);
        lvMinutes.getSelectionModel().selectFirst();

        VBox date = new VBox(new Label("日期"), content);
        date.setSpacing(5);

        VBox hour = new VBox(new Label("时"), lvHours);
        hour.setSpacing(5);

        VBox min = new VBox(new Label("分"), lvMinutes);
        min.setSpacing(5);

        setStyle("-fx-background-color: rgb(244,244,244)");
        getChildren().addAll(date, hour, min);

        picker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                try {
                    updateValues.invoke(content);
                    updateTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ChangeListener<String> listener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateTime();
            }
        };

        lvHours.getSelectionModel().selectedItemProperty().addListener(listener);
        lvMinutes.getSelectionModel().selectedItemProperty().addListener(listener);

    }

    public void setOwnerNode(TextField n) {
        this.node = n;
        node.setEditable(false);
    }

    private void updateTime() {
        LocalDate date = picker.getValue();
        String hour = lvHours.getSelectionModel().getSelectedItem();
        String minute = lvMinutes.getSelectionModel().getSelectedItem();
        if (date == null || hour == null || minute == null) return;
        Timestamp selected = Timestamp.valueOf(date + " " + hour + ":" + minute + ":00");
        node.setText(selected.toString().substring(0, 19));
    }

    public void pop() {
        Popup popup = new Popup();
        popup.getContent().add(this);
        popup.setAutoHide(true);
        Window window = node.getScene().getWindow();
        double x = window.getX() + node.getBoundsInParent().getMinX() - 3;
        double y = window.getY() + node.getBoundsInParent().getMinY() + 57;
        popup.show(node, x, y);
    }

}