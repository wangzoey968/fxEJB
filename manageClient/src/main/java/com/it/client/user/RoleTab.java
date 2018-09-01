package com.it.client.user;

import com.it.api.table.user.Tb_Role;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.user.dialog.infoDialog.UserInfoDialog;
import com.it.client.user.dialog.editor.RoleEditorDialog;
import com.it.client.util.FxmlUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by wangzy on 2018/8/13.
 */
public class RoleTab extends Tab {

    @FXML
    private TextField tfKey;
    @FXML
    private Button btnSearch, btnAdd, btnUpdate, btnDelete;
    @FXML
    private TableView<Tb_Role> tvRole;
    @FXML
    private TableColumn<Tb_Role, Long> tcId;
    @FXML
    private TableColumn<Tb_Role, String> tcRolename, tcNote;

    private ContextMenu menu = new ContextMenu();
    private MenuItem addItem = new MenuItem("添加");
    private MenuItem updateItem = new MenuItem("修改");
    private MenuItem deleteItem = new MenuItem("删除");

    public RoleTab() {
        this.setText("角色管理");
        this.setContent(FxmlUtil.loadFXML(this));
        tvRole.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tvRole.setRowFactory(new Callback<TableView<Tb_Role>, TableRow<Tb_Role>>() {
            @Override
            public TableRow<Tb_Role> call(TableView<Tb_Role> param) {
                TableRow<Tb_Role> row = new TableRow<>();
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
                        Tb_Role role = tvRole.getSelectionModel().getSelectedItem();
                        new UserInfoDialog().setUserId(role.getId()).showUserRole();
                    }
                });
                return row;
            }
        });
        tcRolename.setCellFactory(new Callback<TableColumn<Tb_Role, String>, TableCell<Tb_Role, String>>() {
            @Override
            public TableCell<Tb_Role, String> call(TableColumn<Tb_Role, String> param) {
                return new TableCell<Tb_Role, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText(null);
                        } else {
                            Tb_Role role = (Tb_Role) this.getTableRow().getItem();
                            this.setText(role.getRolename());
                            this.setTextAlignment(TextAlignment.CENTER);
                        }
                    }
                };
            }
        });
        tcNote.setCellFactory(new Callback<TableColumn<Tb_Role, String>, TableCell<Tb_Role, String>>() {
            @Override
            public TableCell<Tb_Role, String> call(TableColumn<Tb_Role, String> param) {
                return new TableCell<Tb_Role, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            this.setText(null);
                        } else {
                            Tb_Role role = (Tb_Role) this.getTableRow().getItem();
                            this.setText(role.getNote());
                            this.setTextAlignment(TextAlignment.CENTER);

                        }
                    }
                };
            }
        });
        addItem.setOnAction(action -> {
            Tb_Role role = new RoleEditorDialog().addRole();
            if (role != null) {
                tvRole.getItems().add(0, role);
                tvRole.getSelectionModel().select(0);
                tvRole.refresh();
            }
        });
        updateItem.setOnAction(action -> {
            Tb_Role item = tvRole.getSelectionModel().getSelectedItem();
            Tb_Role role = new RoleEditorDialog().updateRole(item);
            if (role != null) {
                tvRole.getItems().set(tvRole.getItems().indexOf(item), role);
                tvRole.getSelectionModel().select(role);
                tvRole.refresh();
            }
        });
        deleteItem.setOnAction(action -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确认删除");
            alert.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, filter -> {
                try {
                    Tb_Role role = tvRole.getSelectionModel().getSelectedItem();
                    EJB.getUserService().deleteRole(EJB.getSessionId(), role.getId());
                    tvRole.getItems().remove(role);
                    tvRole.refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            alert.initOwner(MainFrame.getInstance());
            alert.showAndWait();
        });
        tcId.setCellFactory(factory -> new TableCell<Tb_Role, Long>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                Tb_Role role = (Tb_Role) this.getTableRow().getItem();
                if (!empty && role != null) {
                    setText(role.getId().toString());
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
            tvRole.getItems().clear();
            List<Tb_Role> list = EJB.getUserService().listAllRole(EJB.getSessionId());
            tvRole.getItems().addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
