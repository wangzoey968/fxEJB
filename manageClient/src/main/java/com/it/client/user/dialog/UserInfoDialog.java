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
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzy on 2018/8/15.
 */
public class UserInfoDialog extends Dialog {

    @FXML
    private TableView<Tb_Role> tvRole;
    @FXML
    private TableColumn<Tb_Role, String> tcRolename, tcRoleNote;
    @FXML
    private TableColumn<Tb_Role, Boolean> tcRoleCheck;

    @FXML
    private TableView<Tb_Auth> tvAuth;
    @FXML
    private TableColumn<Tb_Auth, String> tcAuthname, tcAuthNote;
    @FXML
    private TableColumn<Tb_Auth, Boolean> tcInclude;
    @FXML
    private TableColumn<Tb_Auth, Boolean> tcAuthCheck;

    private SimpleLongProperty userId = new SimpleLongProperty();

    private ContextMenu menu = new ContextMenu();

    private MenuItem refreshRole = new MenuItem("刷新");
    private MenuItem addRole = new MenuItem("新建角色");
    private MenuItem updateRole = new MenuItem("修改角色");
    private MenuItem deleteRole = new MenuItem("修改角色");

    private MenuItem refreshAuth = new MenuItem("刷新");
    private MenuItem addAuth = new MenuItem("新建权限");
    private MenuItem updateAuth = new MenuItem("修改权限");
    private MenuItem deleteAuth = new MenuItem("删除权限");

    private List<String> ownRole = new ArrayList<>();
    private List<String> ownAuth = new ArrayList<>();

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
                    menu.getItems().addAll(refreshRole, new SeparatorMenuItem(), addRole);
                    Tb_Role item = row.getItem();
                    if (item != null) {
                        menu.getItems().addAll(updateRole, deleteRole);
                    }
                    menu.show(row, req.getScreenX(), req.getScreenY());
                });
                row.setContextMenu(menu);
                return row;
            }
        });
        tcRoleCheck.setCellFactory(new Callback<TableColumn<Tb_Role, Boolean>, TableCell<Tb_Role, Boolean>>() {
            @Override
            public TableCell<Tb_Role, Boolean> call(TableColumn<Tb_Role, Boolean> param) {
                return new TableCell<Tb_Role, Boolean>() {
                    ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            Tb_Role role = (Tb_Role) getTableRow().getItem();
                            try {
                                if (newValue) {
                                    EJB.getUserService().addUserRole(EJB.getSessionId(), userId.get(), role.getId());
                                } else {
                                    EJB.getUserService().deleteUserRole(EJB.getSessionId(), userId.get(), role.getId());
                                }
                            } catch (Exception e) {
                                FxmlUtil.showException(e, null);
                            }
                        }
                    };

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Role role = (Tb_Role) getTableRow().getItem();
                        try {
                            if (empty) {
                                this.setGraphic(null);
                            } else {
                                if (role != null) {
                                    CheckBox box = new CheckBox();
                                    box.selectedProperty().removeListener(listener);
                                    box.setSelected(ownRole.contains(role.getRolename()));
                                    box.selectedProperty().addListener(listener);
                                    this.setGraphic(box);
                                    this.setAlignment(Pos.CENTER);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
            }
        });
        tcRolename.setCellFactory(factory -> new TableCell<Tb_Role, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Tb_Role role = (Tb_Role) this.getTableRow().getItem();
                if (!empty && role != null) {
                    this.setText(role.getRolename());
                }
            }
        });
        tcRoleNote.setCellFactory(factory -> new TableCell<Tb_Role, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Tb_Role role = (Tb_Role) this.getTableRow().getItem();
                if (!empty && role != null) {
                    this.setText(role.getNote());
                }
            }
        });
        tvAuth.setRowFactory(new Callback<TableView<Tb_Auth>, TableRow<Tb_Auth>>() {
            @Override
            public TableRow<Tb_Auth> call(TableView<Tb_Auth> param) {
                TableRow<Tb_Auth> row = new TableRow<>();
                row.setOnContextMenuRequested(req -> {
                    menu.getItems().clear();
                    if (tvRole.getSelectionModel().getSelectedItem() != null) {
                        menu.getItems().addAll(refreshAuth, new SeparatorMenuItem(), addAuth);
                    }
                    Tb_Auth item = row.getItem();
                    if (item != null) {
                        menu.getItems().addAll(updateAuth, deleteAuth);
                    }
                    menu.show(row, req.getScreenX(), req.getScreenY());
                });
                row.setContextMenu(menu);
                return row;
            }
        });
        tcAuthCheck.setCellFactory(new Callback<TableColumn<Tb_Auth, Boolean>, TableCell<Tb_Auth, Boolean>>() {
            @Override
            public TableCell<Tb_Auth, Boolean> call(TableColumn<Tb_Auth, Boolean> param) {
                return new TableCell<Tb_Auth, Boolean>() {
                    ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                        }
                    };

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Auth auth = (Tb_Auth) this.getTableRow().getItem();
                        if (empty) {
                            this.setGraphic(null);
                        } else {
                            if (auth != null) {
                                CheckBox box = new CheckBox();
                                box.selectedProperty().removeListener(listener);
                                //todo
                                box.selectedProperty().addListener(listener);
                                this.setGraphic(box);
                                this.setAlignment(Pos.CENTER);
                            }
                        }
                    }
                };
            }
        });
        tcAuthname.setCellFactory(factory -> new TableCell<Tb_Auth, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Tb_Auth auth = (Tb_Auth) this.getTableRow().getItem();
                if (!empty && auth != null) {
                    this.setText(auth.getAuthname());
                }
            }
        });
        tcAuthNote.setCellFactory(factory -> new TableCell<Tb_Auth, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Tb_Auth auth = (Tb_Auth) this.getTableRow().getItem();
                if (!empty && auth != null) {
                    this.setText(auth.getNote());
                }
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
                            //TODO
                        }
                    }
                };
            }
        });
        tvRole.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tb_Role>() {
            @Override
            public void changed(ObservableValue<? extends Tb_Role> observable, Tb_Role oldValue, Tb_Role newValue) {
                if (newValue != null) {
                    listRoleAuth();
                }
            }
        });
        refreshRole.setOnAction(action -> {
            listAllRole();
        });
        addRole.setOnAction(action -> {
            Tb_Role role = new RoleEditorDialog().createRole();
            if (role != null) {
                new Alert(Alert.AlertType.INFORMATION, "新建角色成功").showAndWait();
                tvRole.getItems().add(0, role);
                tvRole.getSelectionModel().select(0);
            }
        });
        updateRole.setOnAction(action -> {

        });
        deleteRole.setOnAction(action -> {

        });

        addAuth.setOnAction(action -> {
            Tb_Auth auth = new AuthEditorDialog().createAuth();
            if (auth != null) {
                new Alert(Alert.AlertType.INFORMATION, "新建权限成功").showAndWait();
            }
        });
        updateAuth.setOnAction(action -> {

        });
        deleteAuth.setOnAction(action -> {

        });
        setOnShowing(action -> {
            ownRole.clear();
            for (Tb_Role role : listUserRole()) {
                ownRole.add(role.getRolename());
            }
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

    private void listRoleAuth() {
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

    private void listAllRole() {
        try {
            tvRole.getItems().clear();
            List<Tb_Role> list = EJB.getUserService().listAllRole(EJB.getSessionId());
            System.out.println(list.size());
            System.out.println(list.toString());
            tvRole.getItems().addAll(list);
        } catch (Exception e) {
            FxmlUtil.showException(e, null);
        }
    }

    private List<Tb_Auth> listUserAuth() {

        return null;
    }

}
