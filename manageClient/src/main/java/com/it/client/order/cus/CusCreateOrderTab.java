package com.it.client.order.cus;

import com.it.client.mainFrame.MainFrame;
import com.it.client.util.FxmlUtil;
import javafx.application.Preloader;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.util.Callback;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by wangzy on 2018/9/5.
 */
public class CusCreateOrderTab extends Tab {

    @FXML
    private DatePicker dpFrom, dpTo;
    @FXML
    private Button btnSearch, btnMingPianOrder, btnDanYeOrder;

    @FXML
    private TableView<Bean_MakeOrder> tvOrder;
    @FXML
    private TableColumn<Bean_MakeOrder, String> tcCreateTime, tcFileName, tcOrderType, tcParams, tcOperate;
    @FXML
    private TableColumn<Bean_MakeOrder, Integer> tcAmount, tcStatus, tcMoney;

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
                new SimpleStringProperty(formatTime(factory.getValue().getOrder().getCreateTime()))
        );
        tcFileName.setCellValueFactory(factory ->
                new SimpleStringProperty(factory.getValue().getFile().getAbsolutePath())
        );
        tcOrderType.setCellFactory(new Callback<TableColumn<Bean_MakeOrder, String>, TableCell<Bean_MakeOrder, String>>() {
            @Override
            public TableCell<Bean_MakeOrder, String> call(TableColumn<Bean_MakeOrder, String> param) {
                TableCell<Bean_MakeOrder, String> cell = new TableCell<Bean_MakeOrder, String>() {
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
                        Bean_MakeOrder bean = tvOrder.getSelectionModel().getSelectedItem();
                        if (bean == null) return;
                        if (bean.getOrder() == null) return;
                        if (newValue == null) return;
                        if (newValue.equals(bean.getOrder().getOrderType())) return;
                        bean.getOrder().setOrderType(newValue);
                        updateItem(newValue, false);
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Bean_MakeOrder bean = (Bean_MakeOrder) this.getTableRow().getItem();
                        if (empty || bean == null) {
                            this.setText(null);
                        } else {
                            HBox box = new HBox();
                            box.setSpacing(10);
                            box.getChildren().addAll(new Text(bean.getOrder().getOrderType()), new Text("修改"));
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
        tcParams.setCellFactory(new Callback<TableColumn<Bean_MakeOrder, String>, TableCell<Bean_MakeOrder, String>>() {
            @Override
            public TableCell<Bean_MakeOrder, String> call(TableColumn<Bean_MakeOrder, String> param) {
                TableCell<Bean_MakeOrder, String> cell = new TableCell<Bean_MakeOrder, String>() {
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
        tcStatus.setCellFactory(new Callback<TableColumn<Bean_MakeOrder, Integer>, TableCell<Bean_MakeOrder, Integer>>() {
            @Override
            public TableCell<Bean_MakeOrder, Integer> call(TableColumn<Bean_MakeOrder, Integer> param) {
                TableCell<Bean_MakeOrder, Integer> cell = new TableCell<Bean_MakeOrder, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText(null);
                        } else {
                            Bean_MakeOrder bean = (Bean_MakeOrder) this.getTableRow().getItem();
                            if (bean == null) return;

                            //todo show progressBar progressing
                            Label label = new Label();
                            label.setTextAlignment(TextAlignment.CENTER);
                            label.textProperty().unbind();
                            label.textProperty().bind(Bindings.concat(bean.getUploadedSize() + "/" + bean.getTotalSize()));

                            ProgressBar bar = new ProgressBar();
                            Bindings.divide(bean.uploadedSizeProperty(), bean.totalSizeProperty()).addListener(new ChangeListener<Number>() {
                                @Override
                                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                                    double percent = bean.getUploadedSize() / bean.getTotalSize();
                                    bar.setProgress(Double.parseDouble(String.format("%.1f", percent)));
                                }
                            });
                            bar.progressProperty().unbind();
                            bar.progressProperty().bind(Bindings.divide(bean.uploadedSizeProperty(), bean.totalSizeProperty()));

                            Group group = new Group(bar, label);
                            switch (bean.getOrderStatus()) {
                                case 0:
                                    this.setGraphic(bar);
                                    break;
                                case 1:
                                    this.setGraphic(bar);
                                    break;
                                case 2:
                                    this.setText("已取消");
                                    break;
                                case 3:
                                    this.setText("已删除");
                                    break;
                                case 4:
                                    this.setText("上传完成");
                                    break;
                            }

                        }
                    }
                };
                return cell;
            }
        });
        tcAmount.setCellFactory(new Callback<TableColumn<Bean_MakeOrder, Integer>, TableCell<Bean_MakeOrder, Integer>>() {
            @Override
            public TableCell<Bean_MakeOrder, Integer> call(TableColumn<Bean_MakeOrder, Integer> param) {
                return new TableCell<Bean_MakeOrder, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText(null);
                        } else {
                            Bean_MakeOrder bean = (Bean_MakeOrder) this.getTableRow().getItem();
                            if (bean != null) {
                                this.setText(bean.getOrder().getPackCount().toString());
                            }
                        }
                    }
                };
            }
        });
        tcMoney.setCellFactory(new Callback<TableColumn<Bean_MakeOrder, Integer>, TableCell<Bean_MakeOrder, Integer>>() {
            @Override
            public TableCell<Bean_MakeOrder, Integer> call(TableColumn<Bean_MakeOrder, Integer> param) {
                return new TableCell<Bean_MakeOrder, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText(null);
                        } else {
                            Bean_MakeOrder bean = (Bean_MakeOrder) this.getTableRow().getItem();
                        }
                    }
                };
            }
        });
        tcOperate.setCellFactory(new Callback<TableColumn<Bean_MakeOrder, String>, TableCell<Bean_MakeOrder, String>>() {
            @Override
            public TableCell<Bean_MakeOrder, String> call(TableColumn<Bean_MakeOrder, String> param) {
                return new TableCell<Bean_MakeOrder, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText(null);
                        } else {
                            Bean_MakeOrder bean = (Bean_MakeOrder) this.getTableRow().getItem();
                            if (bean != null) {
                                HBox box = new HBox();
                                box.setSpacing(10);
                                Button hlUpload = new Button("上传");
                                hlUpload.setStyle("-fx-background-color: royalblue");
                                Button hlCancel = new Button("取消");
                                hlCancel.setStyle("-fx-background-color: gray");
                                hlUpload.setOnAction(action -> {
                                    bean.uploadFile();
                                    tvOrder.refresh();
                                });
                                hlCancel.setOnAction(action -> {
                                    cancelTask(bean);
                                });
                                box.setAlignment(Pos.CENTER);
                                box.getChildren().addAll(hlUpload, hlCancel);
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
    private Path lastOpenPath = null;

    private void chooseFile(String orderType) {
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
    private void addFileToTab(File file, String orderType) {
        try {
            ObservableList<Bean_MakeOrder> items = tvOrder.getItems();
            for (Bean_MakeOrder item : items) {
                boolean equal = item.getFile().getAbsolutePath().equals(file.getAbsolutePath());
                if (equal) {
                    throw new Exception("文件已经被选中,请选择其他文件");
                }
            }
            Bean_MakeOrder bean = new Bean_MakeOrder();
            bean.setFile(file);
            bean.setOrderStatus(0);
            bean.getOrder().setCreateTime(System.currentTimeMillis());
            bean.getOrder().setOrderType(orderType);
            tvOrder.getItems().add(bean);
            tvOrder.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消下单
     */
    private void cancelTask(Bean_MakeOrder bean) {
        tvOrder.getItems().remove(bean);
        tvOrder.refresh();
    }

    //格式化时间
    private static SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");

    private String formatTime(Long source) {
        return format.format(source);
    }

}
