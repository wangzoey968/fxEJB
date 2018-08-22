package com.it.client.user.dialog;

import com.it.api.table.user.Tb_Auth;
import com.it.api.table.user.Tb_Role;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.user.dialog.editor.AuthEditorDialog;
import com.it.client.user.dialog.editor.RoleEditorDialog;
import com.it.client.util.FxmlUtil;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by wangzy on 2018/8/15.
 */
public class UserInfoDialog extends Dialog {

    @FXML
    Button btnCreateRole;
    @FXML
    TableView<Tb_Role> tvRole;
    @FXML
    TableColumn<Tb_Role, String> tcRolename, tcRoleNote;
    @FXML
    TableColumn<Tb_Role, CheckBox> tcRoleCheck;

    @FXML
    Button btnCreateAuth;
    @FXML
    TableView<Tb_Auth> tvAuth;
    @FXML
    TableColumn<Tb_Auth, String> tcAuthname, tcAuthNote;
    @FXML
    TableColumn<Tb_Auth, Boolean> tcInclude;
    @FXML
    TableColumn<Tb_Auth, CheckBox> tcAuthCheck;

    private SimpleLongProperty userId = new SimpleLongProperty();

    ContextMenu menu = new ContextMenu();
    MenuItem extendRole = new MenuItem("分配角色");
    MenuItem excludeRole = new MenuItem("取消角色");

    MenuItem extendAuth = new MenuItem("扩展权限");
    MenuItem excludeAuth = new MenuItem("取消扩展");

    public UserInfoDialog() {
        this.initOwner(MainFrame.getInstance());
        this.getDialogPane().setContent(FxmlUtil.loadFXML(this));
        tvRole.setRowFactory(new Callback<TableView<Tb_Role>, TableRow<Tb_Role>>() {
            @Override
            public TableRow<Tb_Role> call(TableView<Tb_Role> param) {
                TableRow<Tb_Role> row = new TableRow<>();
                row.setOnContextMenuRequested(req -> {
                    req.consume();
                    menu.getItems().clear();
                    menu.getItems().addAll(extendRole);
                    Tb_Role item = row.getItem();
                    if (item != null) {
                        menu.getItems().addAll(excludeRole);
                    }
                    menu.show(row, req.getScreenX(), req.getScreenY());
                });
                row.setContextMenu(menu);
                return row;
            }
        });
        tcRoleCheck.setCellFactory(new Callback<TableColumn<Tb_Role, CheckBox>, TableCell<Tb_Role, CheckBox>>() {
            @Override
            public TableCell<Tb_Role, CheckBox> call(TableColumn<Tb_Role, CheckBox> param) {
                return new TableCell<Tb_Role, CheckBox>() {
                    @Override
                    protected void updateItem(CheckBox item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Role role = (Tb_Role) this.getTableRow().getItem();
                        if (!empty && role != null) {
                            CheckBox checkBox = new CheckBox();
                            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                    List<Tb_Role> roles = listUserRole();
                                    List<Tb_Role> items = tvRole.getItems();
                                    System.out.println(roles.toString());
                                    System.out.println(items.toString());
                                    for (Tb_Role r : roles) {
                                        for (Tb_Role i : items) {
                                            if (r.getRolename().equals(i.getRolename())) {
                                                checkBox.setSelected(true);
                                            }
                                        }
                                    }
                                    if (newValue != null) {
                                        try {
                                            EJB.getUserService().assignRole(EJB.getSessionId(), newValue, userId.get(), role.getId());
                                        } catch (Exception e) {
                                            FxmlUtil.showException(e, null);
                                        }
                                    }
                                }
                            });
                            this.setGraphic(checkBox);
                            this.setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        });
        tcRolename.setCellFactory(new Callback<TableColumn<Tb_Role, String>, TableCell<Tb_Role, String>>() {
            @Override
            public TableCell<Tb_Role, String> call(TableColumn<Tb_Role, String> param) {
                return new TableCell<Tb_Role, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Role role = (Tb_Role) this.getTableRow().getItem();
                        if (!empty && role != null) {
                            this.setText(role.getRolename());
                        }
                    }
                };
            }
        });
        tcRoleNote.setCellFactory(new Callback<TableColumn<Tb_Role, String>, TableCell<Tb_Role, String>>() {
            @Override
            public TableCell<Tb_Role, String> call(TableColumn<Tb_Role, String> param) {
                return new TableCell<Tb_Role, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Role role = (Tb_Role) this.getTableRow().getItem();
                        if (!empty && role != null) {
                            this.setText(role.getNote());
                        }
                    }
                };
            }
        });
        tvAuth.setRowFactory(new Callback<TableView<Tb_Auth>, TableRow<Tb_Auth>>() {
            @Override
            public TableRow<Tb_Auth> call(TableView<Tb_Auth> param) {
                TableRow<Tb_Auth> row = new TableRow<>();
                row.setOnContextMenuRequested(req -> {
                    menu.getItems().clear();
                    menu.getItems().addAll(extendAuth);
                    Tb_Auth item = row.getItem();
                    if (item != null) {
                        menu.getItems().addAll(excludeAuth);
                    }
                    menu.show(row, req.getScreenX(), req.getScreenY());
                });
                row.setContextMenu(menu);
                return row;
            }
        });
        tcAuthCheck.setCellFactory(new Callback<TableColumn<Tb_Auth, CheckBox>, TableCell<Tb_Auth, CheckBox>>() {
            @Override
            public TableCell<Tb_Auth, CheckBox> call(TableColumn<Tb_Auth, CheckBox> param) {
                return new TableCell<Tb_Auth, CheckBox>() {
                    @Override
                    protected void updateItem(CheckBox item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Auth auth = (Tb_Auth) this.getTableRow().getItem();
                        if (!empty && auth != null) {
                            CheckBox checkBox = new CheckBox();
                            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                    //todo
                                }
                            });
                            this.setGraphic(checkBox);
                        }
                    }
                };
            }
        });
        tcAuthname.setCellFactory(new Callback<TableColumn<Tb_Auth, String>, TableCell<Tb_Auth, String>>() {
            @Override
            public TableCell<Tb_Auth, String> call(TableColumn<Tb_Auth, String> param) {
                return new TableCell<Tb_Auth, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Auth auth = (Tb_Auth) this.getTableRow().getItem();
                        if (!empty && auth != null) {
                            this.setText(auth.getAuthname());
                        }
                    }
                };
            }
        });
        tcAuthNote.setCellFactory(new Callback<TableColumn<Tb_Auth, String>, TableCell<Tb_Auth, String>>() {
            @Override
            public TableCell<Tb_Auth, String> call(TableColumn<Tb_Auth, String> param) {
                return new TableCell<Tb_Auth, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Auth auth = (Tb_Auth) this.getTableRow().getItem();
                        if (!empty && auth != null) {
                            this.setText(auth.getNote());
                        }
                    }
                };
            }
        });
        tcInclude.setCellFactory(new Callback<TableColumn<Tb_Auth, Boolean>, TableCell<Tb_Auth, Boolean>>() {
            @Override
            public TableCell<Tb_Auth, Boolean> call(TableColumn<Tb_Auth, Boolean> param) {
                return new TableCell<Tb_Auth, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Auth auth = (Tb_Auth) this.getTableRow().getItem();
                        if (!empty && auth != null) {
                            //todo error
                            /*if (auth.getInclude()) {
                                this.setText("包含");
                                this.setTextFill(Color.GREEN);
                            } else {
                                this.setText("排除");
                                this.setTextFill(Color.RED);
                            }*/
                        }
                    }
                };
            }
        });
        tvRole.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tb_Role>() {
            @Override
            public void changed(ObservableValue<? extends Tb_Role> observable, Tb_Role oldValue, Tb_Role newValue) {
                if (newValue != null) {
                    searchRoleAuth();
                }
            }
        });
        btnCreateRole.setOnAction(action -> {
            Tb_Role role = new RoleEditorDialog().createRole();
            if (role != null) {
                new Alert(Alert.AlertType.INFORMATION, "新建角色成功").showAndWait();
            }
        });
        btnCreateAuth.setOnAction(action -> {
            Tb_Auth auth = new AuthEditorDialog().createAuth();
            if (auth != null) {
                new Alert(Alert.AlertType.INFORMATION, "新建权限成功").showAndWait();
            }
        });
        extendRole.setOnAction(action -> {

        });
        excludeRole.setOnAction(action -> {

        });
        extendAuth.setOnAction(action -> {

        });
        excludeAuth.setOnAction(action -> {

        });
    }

    public UserInfoDialog setUserId(Long uId) {
        userId.set(uId);
        return this;
    }

    public void showUserRole() {
        this.setTitle("用户权限管理");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        try {
            List<Tb_Role> roles = EJB.getUserService().listAllRole(EJB.getSessionId());
            tvRole.getItems().addAll(roles);
        } catch (Exception e) {
            FxmlUtil.showException(e, null);
        }
        this.showAndWait();
    }

    private void searchRoleAuth() {
        try {
            Tb_Role item = tvRole.getSelectionModel().getSelectedItem();
            List<Tb_Auth> list = EJB.getUserService().listRoleAuth(EJB.getSessionId(), item.getId());
            tvAuth.getItems().clear();
            tvAuth.getItems().addAll(list);
        } catch (Exception e) {
            FxmlUtil.showException(e, null);
        }
    }

    private List<Tb_Role> listUserRole() {
        List<Tb_Role> list = null;
        try {
            list = EJB.getUserService().listUserRole(EJB.getSessionId(), userId.get());
        } catch (Exception e) {
            FxmlUtil.showException(e, null);
        }
        return list;
    }

}
