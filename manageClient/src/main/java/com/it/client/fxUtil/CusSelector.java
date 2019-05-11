package com.it.client.fxUtil;

import com.it.api.common.util.DateUtil;
import com.it.api.table.customer.Tb_Customer;
import com.it.client.EJB;
import com.it.client.util.ExceptionUtil;
import com.it.client.util.FxmlUtil;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzy on 2019/4/8.
 */
public class CusSelector extends Dialog {

    @FXML
    private TextField tfkey;
    @FXML
    private Button btnSearch, btnAdd;
    @FXML
    private TableView<Tb_Customer> tvCus;
    @FXML
    private TableColumn<Tb_Customer, Long> tcCreateTime;
    @FXML
    private Pagination pagination;

    private ContextMenu rowMenu = new ContextMenu();
    private MenuItem mAdd = new MenuItem("添加");
    private MenuItem mEdit = new MenuItem("修改");
    private MenuItem mdel = new MenuItem("删除");

    public CusSelector() {
        getDialogPane().setContent(FxmlUtil.loadFXML(this));
        getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        tvCus.setRowFactory(new Callback<TableView<Tb_Customer>, TableRow<Tb_Customer>>() {
            @Override
            public TableRow<Tb_Customer> call(TableView<Tb_Customer> param) {
                TableRow<Tb_Customer> row = new TableRow<>();
                row.setOnContextMenuRequested(event -> {
                    event.consume();
                    rowMenu.getItems().clear();
                    rowMenu.getItems().add(mAdd);
                    ObservableList<Tb_Customer> items = tvCus.getSelectionModel().getSelectedItems();
                    if (items.size() == 1) rowMenu.getItems().add(mEdit);
                    if (items.size() > 0) rowMenu.getItems().add(mdel);
                });
                return row;
            }
        });
        tcCreateTime.setCellFactory(new Callback<TableColumn<Tb_Customer, Long>, TableCell<Tb_Customer, Long>>() {
            @Override
            public TableCell<Tb_Customer, Long> call(TableColumn<Tb_Customer, Long> param) {
                TableCell<Tb_Customer, Long> cell = new TableCell<Tb_Customer, Long>() {
                    @Override
                    protected void updateItem(Long item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(DateUtil.format(item));
                        }
                    }
                };
                return cell;
            }
        });
        btnSearch.setOnAction(event -> {
            event.consume();
            doSearch();
        });
        getDialogPane().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE || (event.getCode() == KeyCode.F4 && event.isControlDown())) {
                close();
            }
            if (event.getCode() == KeyCode.A && event.isControlDown()) {
                tvCus.getSelectionModel().selectAll();
            }
            if (event.getCode() == KeyCode.F5) {
                doSearch();
            }
            event.consume();
        });
    }

    public ObservableList<Tb_Customer> getSelectCuzz() {
        final ObservableList[] list = new ObservableList[1];
        getDialogPane().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                list[0] = tvCus.getSelectionModel().getSelectedItems();
                close();
            }
        });
        showAndWait();
        return list[0];
    }

    private void doSearch() {
        try {

        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
    }
}
