package com.it.client.order;

import com.it.api.common.util.DateUtil;
import com.it.api.common.util.StrUtil;
import com.it.api.table.customer.Tb_Customer;
import com.it.api.table.order.Tb_Order;
import com.it.client.EJB;
import com.it.client.fxUtil.CusSelector;
import com.it.client.fxUtil.OrderTypeSelector;
import com.it.client.mainFrame.MainFrame;
import com.it.client.order.dialog.OrderEditor;
import com.it.client.order.dialog.OrderInfo;
import com.it.client.order.process.ProcessDialog;
import com.it.client.util.ExceptionUtil;
import com.it.client.util.FxmlUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OrderTab extends Tab {

    @FXML
    private ToolBar tbRoot;
    @FXML
    private DatePicker dpFrom, dpTo;
    @FXML
    private TextField tfOrderType, tfKey;
    @FXML
    private Button btnSearch, btnProcess;
    @FXML
    private TableView<Tb_Order> tvOrder;
    @FXML
    private TableColumn<Tb_Order, Timestamp> tcCreateTime;
    @FXML
    private TableColumn<Tb_Order, String> tcOrderType, tcTitle, tcParam;
    @FXML
    private TableColumn<Tb_Order, Integer> tcAmount;
    @FXML
    private TableColumn<Tb_Order, Double> tcMoney;
    @FXML
    private TableColumn<Tb_Order, Integer> tcStatus;
    @FXML
    private TableColumn<Tb_Order, String> tcOperate;

    private ContextMenu menu = new ContextMenu();
    private MenuItem mAdd = new MenuItem("添加");
    private MenuItem mEdit = new MenuItem("编辑");
    private MenuItem mDel = new MenuItem("删除");

    public OrderTab() {
        this.setText("订单");
        this.setContent(FxmlUtil.loadFXML(this));
        dpFrom.setValue(LocalDate.now().minusDays(7));
        dpTo.setValue(LocalDate.now());
        tfOrderType.setOnMouseClicked(event -> {
            event.consume();
            try {
                OrderTypeSelector selector = OrderTypeSelector.class.newInstance();
                List<String> list = selector.itemClick();
                if (list == null) return;
                tfOrderType.setText(list.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tvOrder.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tvOrder.setRowFactory(new Callback<TableView<Tb_Order>, TableRow<Tb_Order>>() {
            @Override
            public TableRow<Tb_Order> call(TableView<Tb_Order> param) {
                TableRow<Tb_Order> row = new TableRow<Tb_Order>();
                row.setOnContextMenuRequested(event -> {
                    event.consume();
                    if (row.getItem() != null) {
                        menu.getItems().clear();
                        menu.getItems().addAll(mAdd, mDel);
                        if (tvOrder.getSelectionModel().getSelectedItems().size() == 1) {
                            menu.getItems().add(1, mEdit);
                        }
                        menu.show(row, event.getScreenX(), event.getScreenY());
                    }
                });
                row.setOnMouseClicked(event -> {
                    event.consume();
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                        new OrderInfo(row.getItem()).view().showAndWait();
                    }
                });
                return row;
            }
        });
        tcCreateTime.setCellFactory(new Callback<TableColumn<Tb_Order, Timestamp>, TableCell<Tb_Order, Timestamp>>() {
            @Override
            public TableCell<Tb_Order, Timestamp> call(TableColumn<Tb_Order, Timestamp> param) {
                return new TableCell<Tb_Order, Timestamp>() {
                    @Override
                    protected void updateItem(Timestamp item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(DateUtil.format.format(item));
                        }
                    }
                };
            }
        });
        tcCreateTime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tb_Order, Timestamp>, ObservableValue<Timestamp>>() {
            @Override
            public ObservableValue<Timestamp> call(TableColumn.CellDataFeatures<Tb_Order, Timestamp> param) {
                return new SimpleObjectProperty<>(new Timestamp(param.getValue().getCreateTime()));
            }
        });
        tcParam.setCellFactory(new Callback<TableColumn<Tb_Order, String>, TableCell<Tb_Order, String>>() {
            @Override
            public TableCell<Tb_Order, String> call(TableColumn<Tb_Order, String> param) {
                return new TableCell<Tb_Order, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            this.setText(null);
                        } else {
                            Tb_Order order = (Tb_Order) getTableRow().getItem();
                            if (order != null) {
                                this.setText(order.getParam());
                            }
                        }
                    }
                };
            }
        });
        tcParam.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tb_Order, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tb_Order, String> param) {
                return new SimpleStringProperty(param.getValue().getParam());
            }
        });
        tcOperate.setCellFactory(new Callback<TableColumn<Tb_Order, String>, TableCell<Tb_Order, String>>() {
            @Override
            public TableCell<Tb_Order, String> call(TableColumn<Tb_Order, String> param) {
                TableCell<Tb_Order, String> cell = new TableCell<Tb_Order, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            //设置graphic的时候此处不能判断item是否为null
                            setText(null);
                        } else {
                            Tb_Order order = (Tb_Order) getTableRow().getItem();
                            if (order != null) {
                                HBox box = new HBox();
                                box.setAlignment(Pos.CENTER);
                                Button btn = new Button("添加");
                                btn.setOnAction(event -> {
                                    event.consume();
                                    addOrder();
                                });
                                box.getChildren().add(btn);
                                setGraphic(box);
                            }
                        }
                    }
                };
                return cell;
            }
        });
        btnSearch.setOnAction(e -> {
            e.consume();
            doSearch();
        });
        mAdd.setOnAction(event -> {
            event.consume();
            addOrder();
        });
        mDel.setOnAction(event -> {
            event.consume();
            delOrder();
            ObservableList<Tb_Order> selects = tvOrder.getSelectionModel().getSelectedItems();
            tvOrder.getItems().removeAll(selects);
            tvOrder.refresh();
        });
        mEdit.setOnAction(event -> {
            event.consume();
            editOrder();
        });
        btnProcess.setOnAction(event -> {
            event.consume();
            processSort();
        });
    }

    private void processSort() {
        //new ProcessDialog().showAndWait();
        List<Tb_Customer> list = new CusSelector().getSelectCuzz();
        if (list == null) {
            System.out.println("----s");
        } else {
            System.out.println(list.size());
        }
    }

    private void doSearch() {
        try {
            btnSearch.setDisable(true);
            tvOrder.getItems().clear();
            List<String> list = StrUtil.toList(tfOrderType.getText());
            if (list == null) return;
            String from = dpFrom.getEditor().getText();
            String to = dpTo.getEditor().getText();
            List<Tb_Order> orders = EJB.getOrderService().listOrders(EJB.getSessionId(), list, from + " 00:00:00", to + " 23:59:59", tfKey.getText());
            tvOrder.getItems().addAll(orders);
            tvOrder.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.showException(e);
        } finally {
            btnSearch.setDisable(false);
        }
    }

    private void addOrder() {
        Tb_Order order = new OrderEditor().addOrder();
        if (order != null) {
            tvOrder.getItems().add(0, order);
            tvOrder.getSelectionModel().select(order);
            tvOrder.refresh();
        }
    }

    private void delOrder() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确认删除");
        alert.initOwner(MainFrame.getInstance());
        alert.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ANY, event -> {
            try {
                ObservableList<Tb_Order> items = tvOrder.getSelectionModel().getSelectedItems();
                List<Long> ids = items.stream().map(m -> m.getId()).collect(Collectors.toList());
                EJB.getOrderService().delOrders(EJB.getSessionId(), ids);
            } catch (Exception e) {
                ExceptionUtil.showException(e);
            }
        });
        alert.showAndWait();
    }

    private void editOrder() {
        Tb_Order item = tvOrder.getSelectionModel().getSelectedItem();
        Tb_Order order = new OrderEditor().editOrder(item);
        tvOrder.refresh();
    }

}
