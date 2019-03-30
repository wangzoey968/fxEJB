package com.it.client.order.process;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.it.api.common.constant.ORDER_PROCESS;
import com.it.api.common.constant.ORDER_TYPE;
import com.it.api.table.order.Tb_OrderProcess;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.ExceptionUtil;
import com.it.client.util.FxmlUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.effect.Shadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.util.Callback;

import java.util.*;
import java.util.stream.Collectors;

public class ProcessDialog extends Dialog {

    @FXML
    private ListView<String> lvOrderType, lvProcess;

    private ContextMenu rowMenu = new ContextMenu();
    private MenuItem mTop = new MenuItem("置顶");
    private MenuItem mUp = new MenuItem("上移");
    private MenuItem mDown = new MenuItem("下移");
    private MenuItem mBottom = new MenuItem("置底");

    public ProcessDialog() {
        initOwner(MainFrame.getInstance());
        getDialogPane().setContent(FxmlUtil.loadFXML(this));
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CLOSE);
        lvOrderType.getItems().addAll(ORDER_TYPE.getAllTypes());
        lvOrderType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                doSearch();
            }
        });
        lvProcess.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> cell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            CheckBox box = new CheckBox();
                            setGraphic(box);
                            setText(item);
                        }
                    }
                };
                cell.setOnContextMenuRequested(event -> {
                    event.consume();
                    rowMenu.getItems().clear();
                    int i = lvProcess.getSelectionModel().getSelectedIndex();
                    if (i != 0) rowMenu.getItems().addAll(mTop, mUp);
                    if (i != (lvProcess.getItems().size() - 1)) rowMenu.getItems().addAll(mDown, mBottom);
                    rowMenu.show(cell, event.getScreenX(), event.getScreenY());
                });
                return cell;
            }
        });
        getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            event.consume();
            Set<Node> nodes = lvProcess.lookupAll("CheckBox");
            List<Node> list = new ArrayList<>(nodes);
            ArrayList<CheckBox> boxes = new ArrayList<>();
            list.forEach(e -> {
                CheckBox box = (CheckBox) e;
                boxes.add(box);
            });
            ArrayList<String> checkedItems = new ArrayList<>();
            boxes.forEach(e -> {
                if (e.isSelected()) {
                    int i = boxes.indexOf(e);
                    String item = lvProcess.getItems().get(i);
                    checkedItems.add(item);
                }
            });
            for (String checkedItem : checkedItems) {
                System.out.println(checkedItem);
            }
        });
        mTop.setOnAction(event -> {
            event.consume();
            String item = lvProcess.getSelectionModel().getSelectedItem();
            lvProcess.getItems().remove(item);
            lvProcess.getItems().add(0, item);
            lvProcess.refresh();
        });
        mUp.setOnAction(event -> {
            int i = lvProcess.getSelectionModel().getSelectedIndex();
            String item = lvProcess.getSelectionModel().getSelectedItem();
            lvProcess.getItems().remove(i);
            lvProcess.getItems().add(i - 1, item);
            lvProcess.refresh();
        });
        mDown.setOnAction(event -> {
            event.consume();
            int i = lvProcess.getSelectionModel().getSelectedIndex();
            String item = lvProcess.getSelectionModel().getSelectedItem();
            lvProcess.getItems().remove(i);
            lvProcess.getItems().add(i + 1, item);
            lvProcess.refresh();
        });
        mBottom.setOnAction(event -> {
            event.consume();
            int size = lvProcess.getItems().size();
            String item = lvProcess.getSelectionModel().getSelectedItem();
            lvProcess.getItems().remove(item);
            lvProcess.getItems().add(size - 1, item);
            lvProcess.refresh();
        });
    }

    private void doSearch() {
        try {
            lvProcess.getItems().clear();
            Tb_OrderProcess process = new Tb_OrderProcess();
            process.setProcess("['模切','圆角','打码']");
            List<String> list = new Gson().fromJson(process.getProcess(), new TypeToken<List<String>>() {
            }.getType());
            lvProcess.getItems().addAll(list);
            lvProcess.refresh();
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
    }

    private void saveUpdate(){
        try {
            //EJB.getOrderService().editOrder()
        }catch (Exception e){
            ExceptionUtil.showException(e);
        }
    }

}
