package com.it.client.user;

import com.it.api.table.user.Tb_User;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.user.dialog.infoDialog.UserInfoDialog;
import com.it.client.user.dialog.editor.UserEditorDialog;
import com.it.client.util.FxmlUtil;
import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by wangzy on 2018/8/13.
 */
public class UserTab extends Tab {

    @FXML
    private TextField tfKey;
    @FXML
    private Button btnSearch, btnAdd, btnUpdate, btnDelete;
    @FXML
    private TableView<Tb_User> tvUser;
    /*此处使用number,不要使用long,double等类型,不然setcellvaluefactory的时候出错*/
    @FXML
    private TableColumn<Tb_User, Number> tcId;
    @FXML
    private TableColumn<Tb_User, String> tcLoginname, tcUsername, tcEmail;
    @FXML
    private TableColumn<Tb_User, Boolean> tcEnable;

    private ContextMenu menu = new ContextMenu();
    private MenuItem addItem = new MenuItem("添加");
    private MenuItem updateItem = new MenuItem("修改");
    private MenuItem deleteItem = new MenuItem("删除");

    public UserTab() {
        this.setText("用户管理");
        this.setContent(FxmlUtil.loadFXML(this));
        tvUser.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tvUser.setRowFactory(new Callback<TableView<Tb_User>, TableRow<Tb_User>>() {
            @Override
            public TableRow<Tb_User> call(TableView<Tb_User> param) {
                TableRow<Tb_User> row = new TableRow<>();
                row.setOnContextMenuRequested(req -> {
                    menu.getItems().clear();
                    menu.getItems().add(addItem);
                    if (row.getItem() != null) {
                        menu.getItems().addAll(updateItem, deleteItem);
                    }
                    row.setContextMenu(menu);
                    menu.show(row, req.getScreenX(), req.getScreenY());
                });
                row.setOnMouseClicked(click -> {
                    if (click.getButton() == MouseButton.PRIMARY && click.getClickCount() == 2) {
                        Tb_User user = tvUser.getSelectionModel().getSelectedItem();
                        new UserInfoDialog().setUserId(user.getId()).showUserRole();
                    }
                });
                return row;
            }
        });
        addItem.setOnAction(action -> {
            Tb_User user = new UserEditorDialog().addUser();
            if (user != null) {
                tvUser.getItems().add(0, user);
                tvUser.getSelectionModel().select(0);
                tvUser.refresh();
            }
        });
        updateItem.setOnAction(action -> {
            Tb_User item = tvUser.getSelectionModel().getSelectedItem();
            Tb_User user = new UserEditorDialog().updateUser(item);
            if (user != null) {
                tvUser.getItems().set(tvUser.getItems().indexOf(item), user);
                tvUser.getSelectionModel().select(user);
                tvUser.refresh();
            }
        });
        deleteItem.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确认删除");
            alert.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, filter -> {
                try {
                    Tb_User user = tvUser.getSelectionModel().getSelectedItem();
                    EJB.getUserService().deleteUser(EJB.getSessionId(), user.getId());
                    tvUser.getItems().remove(user);
                    tvUser.refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            alert.initOwner(MainFrame.getInstance());
            alert.showAndWait();
        });
        tcId.setCellValueFactory(factory ->
                new SimpleLongProperty(factory.getValue().getId())
        );
        tcUsername.setCellFactory(factory -> new TableCell<Tb_User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Tb_User user = (Tb_User) this.getTableRow().getItem();
                if (!empty && user != null) {
                    this.setText(user.getUsername());
                }
            }
        });
        tcLoginname.setCellFactory(factory -> new TableCell<Tb_User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Tb_User user = (Tb_User) this.getTableRow().getItem();
                if (!empty && user != null) {
                    setText(user.getLoginname());
                }
            }
        });
        tcEnable.setCellFactory(factory -> new TableCell<Tb_User, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                Tb_User user = (Tb_User) getTableRow().getItem();
                if (!empty && user != null) {
                    if (user.getEnable()) {
                        this.setText("可用");
                        this.setTextFill(Color.GREEN);
                    } else {
                        this.setText("不可用");
                        this.setTextFill(Color.RED);
                    }
                }
            }
        });
        tcEmail.setCellFactory(factory -> new TableCell<Tb_User, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Tb_User user = (Tb_User) this.getTableRow().getItem();
                if (!empty && user != null) {
                    this.setText(user.getEmail());
                }
            }
        });
        btnSearch.setOnAction(action -> {
            doSearch();
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                doSearch();
            }
        });
    }

    private void doSearch() {
        try {
            tvUser.getItems().clear();
            List<Tb_User> users = EJB.getUserService().listUser(EJB.getSessionId(), tfKey.getText());
            tvUser.getItems().addAll(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
