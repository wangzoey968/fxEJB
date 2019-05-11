package com.it.client.order.process;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.it.api.common.constant.ORDER_PROCESS;
import com.it.api.common.util.GsonUtil;
import com.it.api.table.order.Tb_OrderProcess;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.ExceptionUtil;
import com.it.client.util.FxmlUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class ProcessDialog extends Dialog {

    @FXML
    private TableView<Tb_OrderProcess> tvOrderType;
    @FXML
    private TableColumn<Tb_OrderProcess, String> tcOrderType;
    @FXML
    private ListView<String> lvProcess;

    private ContextMenu rowMenu = new ContextMenu();

    private MenuItem mAdd = new MenuItem("添加");
    private MenuItem mEdit = new MenuItem("修改");
    private MenuItem mDel = new MenuItem("删除");
    private MenuItem mFresh = new MenuItem("刷新");

    private MenuItem mTop = new MenuItem("置顶");
    private MenuItem mUp = new MenuItem("上移");
    private MenuItem mDown = new MenuItem("下移");
    private MenuItem mBottom = new MenuItem("置底");

    public ProcessDialog() {
        setTitle("处理步骤设置");
        initOwner(MainFrame.getInstance());
        getDialogPane().setContent(FxmlUtil.loadFXML(this));
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CLOSE);
        lvProcess.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tvOrderType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tb_OrderProcess>() {
            @Override
            public void changed(ObservableValue<? extends Tb_OrderProcess> observable, Tb_OrderProcess oldValue, Tb_OrderProcess newValue) {
                getProcess();
            }
        });
        tvOrderType.setOnContextMenuRequested(event -> {
            event.consume();
            rowMenu.getItems().clear();
            Tb_OrderProcess item = tvOrderType.getSelectionModel().getSelectedItem();
            rowMenu.getItems().addAll(mAdd, mFresh);
            if (item != null) {
                rowMenu.getItems().addAll(mEdit, mDel);
            }
            rowMenu.show(tvOrderType, event.getScreenX(), event.getScreenY());
        });
        mAdd.setOnAction(event -> {
            event.consume();
            addOrderType();
        });
        mFresh.setOnAction(event -> {
            event.consume();
            listTabData();
        });
        mEdit.setOnAction(event -> {
            event.consume();
            editProcess();
        });
        mDel.setOnAction(event -> {
            event.consume();
            delOrderType();
        });
        tcOrderType.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tb_OrderProcess, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tb_OrderProcess, String> param) {
                SimpleStringProperty property = new SimpleStringProperty();
                Tb_OrderProcess value = param.getValue();
                property.set(value == null ? null : value.getOrderType());
                return property;
            }
        });
        tcOrderType.setCellFactory(new Callback<TableColumn<Tb_OrderProcess, String>, TableCell<Tb_OrderProcess, String>>() {
            @Override
            public TableCell<Tb_OrderProcess, String> call(TableColumn<Tb_OrderProcess, String> param) {
                TableCell<Tb_OrderProcess, String> cell = new TableCell<Tb_OrderProcess, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            Tb_OrderProcess i = (Tb_OrderProcess) getTableRow().getItem();
                            if (i != null) {
                                setText(i.getOrderType());
                            }
                        }
                    }
                };
                return cell;
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
            saveUpdate();
            close();
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

    private void addOrderType() {
        TextInputDialog input = new TextInputDialog();
        Node button = input.getDialogPane().lookupButton(ButtonType.OK);
        button.addEventFilter(ActionEvent.ACTION, event -> {
            event.consume();
            try {
                Tb_OrderProcess process = new Tb_OrderProcess();
                process.setOrderType(input.getEditor().getText());
                process.setProcess("");
                Tb_OrderProcess p = EJB.getOrderService().makeOrderProcess(EJB.getSessionId(), process);
                tvOrderType.getItems().add(p);
                tvOrderType.refresh();
                input.close();
            } catch (Exception e) {
                ExceptionUtil.showException(e);
            }
        });
        input.showAndWait();
    }

    private void delOrderType() {
        try {
            Tb_OrderProcess item = tvOrderType.getSelectionModel().getSelectedItem();
            EJB.getOrderService().delProcess(EJB.getSessionId(), item);
            lvProcess.refresh();
            tvOrderType.getItems().remove(item);
            tvOrderType.refresh();
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
    }

    private void listTabData() {
        try {
            tvOrderType.getItems().clear();
            List<Tb_OrderProcess> list = EJB.getOrderService().listProcess(EJB.getSessionId(), null);
            tvOrderType.getItems().addAll(list);
            tvOrderType.refresh();
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void getProcess() {
        try {
            lvProcess.getItems().clear();
            Tb_OrderProcess item = tvOrderType.getSelectionModel().getSelectedItem();
            if (item != null) {
                List<String> list = (List<String>) GsonUtil.gson.fromJson(item.getProcess(), new TypeToken<List<String>>() {
                }.getType());
                List<String> all = ORDER_PROCESS.getAllProcess();
                if (list != null) {
                    lvProcess.getItems().addAll(list);
                    lvProcess.getSelectionModel().selectAll();
                    for (String s : all) {
                        if (!list.contains(s)) lvProcess.getItems().add(s);
                    }
                } else {
                    lvProcess.getItems().addAll(all);
                }
                lvProcess.refresh();
            }
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
    }

    private void editProcess() {
        Tb_OrderProcess item = tvOrderType.getSelectionModel().getSelectedItem();
        if (item != null) {
            TextInputDialog dialog = new TextInputDialog();
            Node button = dialog.getDialogPane().lookupButton(ButtonType.OK);
            button.addEventFilter(ActionEvent.ACTION, event -> {
                event.consume();
                try {
                    item.setOrderType(dialog.getEditor().getText());
                    EJB.getOrderService().editProcess(EJB.getSessionId(), item);
                    dialog.close();
                    tvOrderType.refresh();
                } catch (Exception e) {
                    ExceptionUtil.showException(e);
                }
            });
            dialog.showAndWait();
        }
    }

    private void saveUpdate() {
        try {
            Tb_OrderProcess process = tvOrderType.getSelectionModel().getSelectedItem();
            if (process == null) throw new Exception("请选中订单类型");
            ObservableList<String> items = lvProcess.getSelectionModel().getSelectedItems();
            if (items.isEmpty()) throw new Exception("请选中多个步骤");
            ArrayList<String> list = new ArrayList<>();
            list.addAll(items);
            process.setProcess(GsonUtil.gson.toJson(list));
            Tb_OrderProcess p = EJB.getOrderService().editProcess(EJB.getSessionId(), process);
            tvOrderType.refresh();
            lvProcess.refresh();
        } catch (Exception e) {
            ExceptionUtil.showException(e);
        }
    }

}
