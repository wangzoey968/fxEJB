package com.it.client.order.cus;

import com.it.api.table.order.Tb_Order;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.FxmlUtil;
import com.sun.tools.corba.se.idl.constExpr.Times;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Callback;
import org.wildfly.common.annotation.NotNull;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.persistence.Table;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.Certificate;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Created by wangzy on 2018/9/5.
 */
public class CusCreateOrderTab extends Tab {

    @FXML
    private DatePicker dpFrom, dpTo;
    @FXML
    private Button btnSearch, btnMingPianOrder, btnDanYeOrder;

    @FXML
    private TableView<Tb_Order> tvOrder;
    @FXML
    private TableColumn<Tb_Order, String> tcCreateTime, tcOrderType, tcParams, tcStatus, tcOperate;
    @FXML
    private TableColumn<Tb_Order, Integer> tcAmount;

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
                new SimpleStringProperty(formatTime(factory.getValue().getCreateTime()))
        );
        tcCreateTime.setCellFactory(new Callback<TableColumn<Tb_Order, String>, TableCell<Tb_Order, String>>() {
            @Override
            public TableCell<Tb_Order, String> call(TableColumn<Tb_Order, String> param) {
                TableCell<Tb_Order, Long> cell = new TableCell<Tb_Order, Long>() {
                    @Override
                    protected void updateItem(Long item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Order order = (Tb_Order) this.getTableRow().getItem();
                        if (order != null) {

                        }
                    }
                };
                return null;
            }
        });
        tcOrderType.setCellValueFactory(factory ->
                new SimpleStringProperty(factory.getValue().getOrderType())
        );
        tcStatus.setCellFactory(new Callback<TableColumn<Tb_Order, String>, TableCell<Tb_Order, String>>() {
            @Override
            public TableCell<Tb_Order, String> call(TableColumn<Tb_Order, String> param) {
                TableCell<Tb_Order, String> cell = new TableCell<Tb_Order, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Order order = (Tb_Order) this.getTableRow().getItem();
                        if (empty) {
                            this.setText(null);
                        } else {
                            if (order != null) {
                                HBox box = new HBox();
                                Hyperlink hlUpload = new Hyperlink("上传");
                                Hyperlink hlCancel = new Hyperlink("取消");
                                hlUpload.setStyle("-fx-background-color: blue");
                                hlCancel.setStyle("-fx-background-color: gray");
                                hlUpload.setOnAction(action -> {
                                    //todo upload
                                });
                                hlCancel.setOnAction(action -> {
                                    tvOrder.getItems().remove(order);
                                });
                                box.getChildren().addAll(hlUpload, hlCancel);
                                box.setAlignment(Pos.CENTER);
                                this.setAlignment(Pos.CENTER);
                                this.setGraphic(box);
                            }
                        }
                    }
                };
                return cell;
            }
        });

    }

    private void doSearch() {
        try {

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
        Tb_Order order = new Tb_Order();
        order.setCreateTime(System.currentTimeMillis());
        String title = file.getParent() + File.separator + file.getName();
        order.setOrderTitle(title);
        order.setNormalPack(true);

    }

    //上传文件
    private synchronized void uploadFile() {

    }

    //格式化时间
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String formatTime(Long source) {
        return format.format(source);
    }

}
