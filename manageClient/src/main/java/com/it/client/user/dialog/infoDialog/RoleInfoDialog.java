package com.it.client.user.dialog.infoDialog;

import com.it.api.table.user.Tb_Auth;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.FxmlUtil;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by wangzy on 2018/8/31.
 */
public class RoleInfoDialog extends Dialog {

    @FXML
    private TableView<Tb_Auth> tvAuth;
    @FXML
    private TableColumn<Tb_Auth, Boolean> tcAuthChck;
    @FXML
    private TableColumn<Tb_Auth, String> tcAuthname, tcAuthNote;

    private SimpleLongProperty roleId = new SimpleLongProperty();
    private SimpleLongProperty userId = new SimpleLongProperty();

    public RoleInfoDialog() {
        this.setTitle("角色下的权限");
        this.initOwner(MainFrame.getInstance());
        this.getDialogPane().setContent(FxmlUtil.loadFXML(this));
        tcAuthChck.setCellFactory(new Callback<TableColumn<Tb_Auth, Boolean>, TableCell<Tb_Auth, Boolean>>() {
            @Override
            public TableCell<Tb_Auth, Boolean> call(TableColumn<Tb_Auth, Boolean> param) {
                return new TableCell<Tb_Auth, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Auth auth = (Tb_Auth) this.getTableRow().getItem();
                        if (empty) {
                            this.setGraphic(null);
                        } else {
                            if (auth != null) {

                            }
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
                        if (empty) {
                            this.setText(null);
                        } else {
                            Tb_Auth auth = (Tb_Auth) this.getTableRow().getItem();
                            if (auth != null) {
                                this.setText(auth.getAuthname());
                                this.setTextAlignment(TextAlignment.CENTER);
                            }
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
                        if (empty) {
                            this.setText(null);
                        } else {
                            if (auth != null) {
                                this.setText(auth.getNote());
                                this.setTextAlignment(TextAlignment.CENTER);
                            }
                        }
                    }
                };
            }
        });
        this.setOnShowing(action -> {
            listRoleAuth();
        });
    }

    public RoleInfoDialog setUserId(Long userId) {
        this.userId.set(userId);
        return this;
    }

    public RoleInfoDialog setRoleId(Long roleId) {
        this.roleId.set(roleId);
        return this;
    }

    private void listRoleAuth() {
        try {
            tvAuth.getItems().clear();
            List<Tb_Auth> list = EJB.getUserService().listRoleAuth(EJB.getSessionId(), userId.getValue(), roleId.getValue());
            tvAuth.getItems().addAll(list);
        } catch (Exception e) {
            FxmlUtil.showException(e, null);
        }
    }

}
