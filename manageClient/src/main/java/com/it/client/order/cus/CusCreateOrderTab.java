package com.it.client.order.cus;

import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.order.cus.Bean_MakeOrder;
import com.it.client.util.FxmlUtil;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
    private DatePicker dpFrom, dpTo;
    @FXML
    private Button btnSearch, btnMingPianOrder, btnDanYeOrder;

    @FXML
    private TableView<Bean_MakeOrder> tvOrder;
    @FXML
    private TableColumn<Bean_MakeOrder, String> tcCreateTime, tcFileName, tcOrderType, tcParams, tcStatus, tcOperate;
    @FXML
    private TableColumn<Bean_MakeOrder, Integer> tcAmount, tcMoney;

    public CusCreateOrderTab() {
        this.setText("下单");
        this.setContent(FxmlUtil.loadFXML(this));
        dpFrom.setValue(LocalDate.now());
        dpTo.setValue(LocalDate.now());
        btnSearch.setOnAction(action -> {
            doSearch();
        });
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
        tcOrderType.setCellValueFactory(factory ->
                new SimpleStringProperty(factory.getValue().getOrder().getOrderType())
        );
        tcStatus.setCellFactory(new Callback<TableColumn<Bean_MakeOrder, String>, TableCell<Bean_MakeOrder, String>>() {
            @Override
            public TableCell<Bean_MakeOrder, String> call(TableColumn<Bean_MakeOrder, String> param) {
                TableCell<Bean_MakeOrder, String> cell = new TableCell<Bean_MakeOrder, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText(null);
                        } else {
                            Bean_MakeOrder bean = (Bean_MakeOrder) this.getTableRow().getItem();
                            if (bean != null) {
                                switch (bean.getOrderStatus()) {
                                    case 0:
                                        this.setText("未上传");
                                        break;
                                    case 1:
                                        this.setText("正在上传");
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
                                Hyperlink hlUpload = new Hyperlink("上传");
                                Hyperlink hlCancel = new Hyperlink("取消");
                                hlUpload.setOnAction(action -> {
                                    uploadFile(bean);
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

    private void doSearch() {
        try {
            //todo 查询记录
        } catch (Exception e) {
            FxmlUtil.showException(e, null);
        }
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
        if (!files.isEmpty()) {
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
        Bean_MakeOrder bean = new Bean_MakeOrder();
        bean.setFile(file);
        bean.setOrderStatus(0);
        bean.getOrder().setCreateTime(System.currentTimeMillis());
        bean.getOrder().setOrderType(orderType);
        tvOrder.getItems().add(bean);
        tvOrder.refresh();
    }

    /**
     * 上传文件
     */
    private void uploadFile(Bean_MakeOrder bean) {
        FileInputStream ins = null;
        FileOutputStream outs = null;
        try {
            File file = bean.getFile();
            if (file != null) {
                Path path = Paths.get("E:/testFile/");
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                ins = new FileInputStream(file);
                outs = new FileOutputStream(path + File.separator + file.getName());
                byte[] b = new byte[10];
                int len;
                while ((len = ins.read(b)) > 0) {
                    bean.setOrderStatus(Bean_MakeOrderStatus.UPLOADING);
                    tvOrder.refresh();
                    outs.write(b, 0, len);
                }
                EJB.getOrderService().makeOrder(EJB.getSessionId(), bean.getOrder());
                bean.setOrderStatus(Bean_MakeOrderStatus.UPLOADED);
                tvOrder.refresh();
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
    }

    /**
     * 取消下单,同时删除文件
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
