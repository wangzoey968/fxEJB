package com.it.client.fxUtil;

import com.it.api.table.order.Tb_OrderProcess;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.ExceptionUtil;
import com.it.client.util.FxmlUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wangzy on 2019/4/3.
 */
public class OrderTypeSelector extends Dialog {

    @FXML
    private ListView<String> lvType;

    public OrderTypeSelector() {
        initOwner(MainFrame.getInstance());
        getDialogPane().setContent(FxmlUtil.loadFXML(this));
        lvType.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void doSearch() {
        try {
            lvType.getItems().clear();
            List<Tb_OrderProcess> list = EJB.getOrderService().listProcess(EJB.getSessionId(), null);
            List<String> collect = list.stream().map(e -> e.getOrderType()).collect(Collectors.toList());
            lvType.getItems().addAll(collect);
            lvType.refresh();
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
    }

    public List<String> itemClick() {
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CLOSE);
        Node button = getDialogPane().lookupButton(ButtonType.OK);
        final ObservableList[] items = new ObservableList[1];
        lvType.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> cell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item);
                        }
                    }
                };
                cell.setOnMouseClicked(event -> {
                    event.consume();
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                        items[0] = lvType.getSelectionModel().getSelectedItems();
                        close();
                    }
                });
                return cell;
            }
        });
        button.addEventFilter(ActionEvent.ACTION, event -> {
            event.consume();
            items[0] = lvType.getSelectionModel().getSelectedItems();
            close();
        });
        doSearch();
        showAndWait();
        return items[0];
    }

}
