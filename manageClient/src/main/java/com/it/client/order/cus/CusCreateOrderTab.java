package com.it.client.order.cus;

import com.it.api.table.order.Tb_Order;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.FxmlUtil;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.util.Callback;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by wangzy on 2018/9/5.
 */
public class CusCreateOrderTab extends Tab {

    @FXML
    public DatePicker dpFrom, dpTo;
    @FXML
    public Button btnSearch, btnMingPianOrder, btnDanYeOrder;
    @FXML
    public TableView<MakeOrder> tvOrder;
    @FXML
    public TableColumn<MakeOrder, String> tcCreateTime, tcFileName, tcOrderType, tcParams, tcOperate;
    @FXML
    public TableColumn<MakeOrder, Integer> tcAmount, tcStatus, tcMoney;
    @FXML
    public Label lbText;

    public CusCreateOrderTab() {
        this.setText("下单");
        this.setContent(FxmlUtil.loadFXML(this));
        dpFrom.setValue(LocalDate.now());
        dpTo.setValue(LocalDate.now());
        btnMingPianOrder.setOnAction(action -> {
            chooseFile("名片");
        });
        btnDanYeOrder.setOnAction(action -> {
            chooseFile("单页");
        });
        tvOrder.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tvOrder.setOnDragOver(event -> {
            Dragboard dragboard = event.getDragboard();
            if (!dragboard.getFiles().isEmpty()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });
        tvOrder.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (!dragboard.getFiles().isEmpty()) {
                List<File> files = dragboard.getFiles();
                files.forEach(file -> addFileToTab(file, null));
            }
            event.setDropCompleted(true);
            event.consume();
        });
        tcCreateTime.setCellValueFactory((factory) ->
                new SimpleStringProperty(formatTime(factory.getValue().order.getCreateTime()))
        );
        tcFileName.setCellValueFactory(factory ->
                new SimpleStringProperty(factory.getValue().file.getAbsolutePath())
        );
        tcOrderType.setCellFactory(new Callback<TableColumn<MakeOrder, String>, TableCell<MakeOrder, String>>() {
            @Override
            public TableCell<MakeOrder, String> call(TableColumn<MakeOrder, String> param) {
                TableCell<MakeOrder, String> cell = new TableCell<MakeOrder, String>() {
                    @Override
                    public void startEdit() {
                        super.startEdit();
                        ListView<String> view = new ListView<>();
                        view.getItems().addAll("单页", "名片");
                        StackPane pane = new StackPane(view);
                        pane.setPrefWidth(80);
                        pane.setPrefHeight(80);
                        Popup popup = new Popup();
                        popup.getContent().add(pane);
                        popup.setAutoHide(true);
                        popup.show(this, this.localToScreen(0, 0).getX(), this.localToScreen(0, 0).getY());
                        popup.setOnHidden(event -> {
                            event.consume();
                            commitEdit(view.getSelectionModel().getSelectedItem());
                        });
                        view.setOnMouseClicked(event -> {
                            event.consume();
                            popup.hide();
                        });
                    }

                    @Override
                    public void commitEdit(String newValue) {
                        super.commitEdit(newValue);
                        MakeOrder bean = tvOrder.getSelectionModel().getSelectedItem();
                        if (bean == null) return;
                        if (bean.order == null) return;
                        if (newValue == null) return;
                        if (newValue.equals(bean.order.getOrderType())) return;
                        bean.order.setOrderType(newValue);
                        updateItem(newValue, false);
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        MakeOrder bean = (MakeOrder) this.getTableRow().getItem();
                        if (empty || bean == null) {
                            this.setText(null);
                        } else {
                            HBox box = new HBox();
                            box.setSpacing(10);
                            box.getChildren().addAll(new Text(bean.order.getOrderType()), new Text("修改"));
                            box.setAlignment(Pos.CENTER);
                            this.setAlignment(Pos.CENTER);
                            this.setGraphic(box);
                        }
                    }
                };
                cell.setOnMouseClicked(event -> {
                    event.consume();
                    if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                        cell.startEdit();
                    }
                });
                return cell;
            }
        });
        tcParams.setCellFactory(new Callback<TableColumn<MakeOrder, String>, TableCell<MakeOrder, String>>() {
            @Override
            public TableCell<MakeOrder, String> call(TableColumn<MakeOrder, String> param) {
                TableCell<MakeOrder, String> cell = new TableCell<MakeOrder, String>() {
                    @Override
                    public void startEdit() {
                        super.startEdit();
                    }

                    @Override
                    public void commitEdit(String newValue) {
                        super.commitEdit(newValue);
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                    }
                };
                cell.setOnMouseClicked(event -> {
                    event.consume();
                    if (cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                        cell.startEdit();
                    }
                });
                return cell;
            }
        });
        tcStatus.setCellFactory(new Callback<TableColumn<MakeOrder, Integer>, TableCell<MakeOrder, Integer>>() {
            @Override
            public TableCell<MakeOrder, Integer> call(TableColumn<MakeOrder, Integer> param) {
                TableCell<MakeOrder, Integer> cell = new TableCell<MakeOrder, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText(null);
                        } else {
                            MakeOrder order = (MakeOrder) this.getTableRow().getItem();
                            if (order != null) {
                                ProgressBar bar = new ProgressBar();
                                bar.progressProperty().unbind();
                                //NumberBinding divide = Bindings.divide(order.uploadedSize, order.totalSize);
                                bar.progressProperty().bind(order.task.progressProperty());
                                setGraphic(bar);
                            }
                        }
                    }
                };
                return cell;
            }
        });
        tcAmount.setCellFactory(new Callback<TableColumn<MakeOrder, Integer>, TableCell<MakeOrder, Integer>>() {
            @Override
            public TableCell<MakeOrder, Integer> call(TableColumn<MakeOrder, Integer> param) {
                return new TableCell<MakeOrder, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText(null);
                        } else {
                            MakeOrder bean = (MakeOrder) this.getTableRow().getItem();
                            if (bean != null) {
                                this.setText(bean.order.getPackCount().toString());
                            }
                        }
                    }
                };
            }
        });
        tcMoney.setCellFactory(new Callback<TableColumn<MakeOrder, Integer>, TableCell<MakeOrder, Integer>>() {
            @Override
            public TableCell<MakeOrder, Integer> call(TableColumn<MakeOrder, Integer> param) {
                return new TableCell<MakeOrder, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText(null);
                        } else {
                            MakeOrder bean = (MakeOrder) this.getTableRow().getItem();
                        }
                    }
                };
            }
        });
        tcOperate.setCellFactory(new Callback<TableColumn<MakeOrder, String>, TableCell<MakeOrder, String>>() {
            @Override
            public TableCell<MakeOrder, String> call(TableColumn<MakeOrder, String> param) {
                return new TableCell<MakeOrder, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText(null);
                        } else {
                            MakeOrder bean = (MakeOrder) this.getTableRow().getItem();
                            if (bean != null) {
                                HBox box = new HBox();
                                box.setSpacing(10);
                                Button btnUpload = new Button("上传");
                                btnUpload.setStyle("-fx-background-color: royalblue");
                                Button btnCancel = new Button("取消");
                                btnCancel.setStyle("-fx-background-color: gray");
                                btnUpload.setOnAction(action -> {
                                    bean.uploadFile();
                                    tvOrder.refresh();
                                });
                                btnCancel.setOnAction(action -> {
                                    cancelTask(bean);
                                    tvOrder.refresh();
                                });
                                box.setAlignment(Pos.CENTER);
                                box.getChildren().addAll(btnUpload, btnCancel);
                                this.setAlignment(Pos.CENTER);
                                this.setGraphic(box);
                            }
                        }
                    }
                };
            }
        });
    }

    //打开上传文件的选择对话框
    public Path lastOpenPath = null;

    public void chooseFile(String orderType) {
        FileChooser fc = new FileChooser();
        if (lastOpenPath == null) {
            FileSystemView fsv = FileSystemView.getFileSystemView();
            File com = fsv.getHomeDirectory();
            fc.setInitialDirectory(com);
        } else {
            fc.setInitialDirectory(lastOpenPath.toFile());
        }
        List<File> files = fc.showOpenMultipleDialog(MainFrame.getInstance());
        if (files != null && !files.isEmpty()) {
            lastOpenPath = files.get(0).toPath().getParent();
            for (File file : files) {
                String filename = file.getName();
                if (filename.contains("单页")) {
                    orderType = "合版单页";
                } else if (filename.contains("名片")) {
                    orderType = "合版名片";
                }
                addFileToTab(file, orderType);
            }
        }
    }

    //添加文件到表格中
    public void addFileToTab(File file, String orderType) {
        try {
            ObservableList<MakeOrder> items = tvOrder.getItems();
            for (MakeOrder item : items) {
                boolean equal = item.file.getAbsolutePath().equals(file.getAbsolutePath());
                if (equal) {
                    throw new Exception("文件已经被选中,请选择其他文件");
                }
            }
            MakeOrder bean = new MakeOrder();
            bean.file = file;
            bean.orderStatus = 0;
            bean.order.setCreateTime(System.currentTimeMillis());
            bean.order.setOrderType(orderType);
            tvOrder.getItems().add(bean);
            tvOrder.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消下单
     */
    public void cancelTask(MakeOrder bean) {
        tvOrder.getItems().remove(bean);
        tvOrder.refresh();
    }

    //格式化时间
    public static SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");

    public String formatTime(Long source) {
        return format.format(source);
    }


    //内部类
    public class MakeOrder {

        public Tb_Order order = null;

        public MakeOrder() {
            //创建订单
            order = new Tb_Order();
        }

        //设置所属的文件
        public File file = null;

        //标记该订单的状态
        public Integer orderStatus = null;

        //标记订单的上传进度
        public SimpleDoubleProperty uploadedSize = new SimpleDoubleProperty();
        public SimpleDoubleProperty totalSize = new SimpleDoubleProperty();

        public Task task = null;

        public void uploadFile() {
            //totalSize.set(file.length());
            task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    FileInputStream ins = null;
                    FileOutputStream outs = null;
                    try {
                        if (file != null) {
                            Path path = Paths.get("E:/testFile/");
                            if (!Files.exists(path)) {
                                Files.createDirectories(path);
                            }
                            ins = new FileInputStream(file);
                            outs = new FileOutputStream(path + File.separator + file.getName());
                            byte[] b = new byte[1024 * 1024];//大小为1M
                            int len;
                            Long uploaded = 0L;
                            while ((len = ins.read(b)) > 0) {
                                orderStatus = Bean_MakeOrderStatus.UPLOADING;
                                outs.write(b, 0, len);
                                uploaded += len;
                                //uploadedSize.set(uploaded / 1024 / 1024D);
                                updateProgress(uploaded, file.length());
                                Thread.sleep(20);
                            }
                            EJB.getOrderService().makeOrder(EJB.getSessionId(), order);
                            orderStatus = Bean_MakeOrderStatus.UPLOADED;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (ins != null) {
                                ins.close();
                            }
                            if (outs != null) {
                                outs.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }
            };
            new Thread(task).run();
        }
    }
}
