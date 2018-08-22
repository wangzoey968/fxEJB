package com.it.client.user.dialog.assign;

import com.it.api.table.user.Tb_Role;
import com.it.client.mainFrame.MainFrame;
import com.it.client.user.dialog.editor.AuthEditorDialog;
import com.it.client.user.dialog.editor.RoleEditorDialog;
import com.it.client.util.FxmlUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangzy on 2018/8/16.
 */
public class RoleAssignDialog extends Dialog {

    @FXML
    private Button btnAddRole;
    @FXML
    private TableView<Tb_Role> tvRole;
    @FXML
    private TableColumn<Tb_Role, String> tcCheck, tcRolename, tcRoleNote;

    private List<Tb_Role> include = new ArrayList<>();
    private List<Tb_Role> exclude = new ArrayList<>();

    public RoleAssignDialog() {
        this.initOwner(MainFrame.getInstance());
        this.getDialogPane().setContent(FxmlUtil.loadFXML(this));
        btnAddRole.setOnAction(action -> {
            Tb_Role role = new RoleEditorDialog().createRole();
            if (role != null) {
                tvRole.getItems().add(0, role);
            }
        });
        tcCheck.setCellFactory(new Callback<TableColumn<Tb_Role, String>, TableCell<Tb_Role, String>>() {
            @Override
            public TableCell<Tb_Role, String> call(TableColumn<Tb_Role, String> param) {
                return new TableCell<Tb_Role, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_Role role = (Tb_Role) this.getTableRow().getItem();
                        if (!empty && role != null) {
                            CheckBox checkBox = new CheckBox();
                            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                @Override
                                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                    if (newValue){
                                        include.add(tvRole.getSelectionModel().getSelectedItem());
                                    }else if (oldValue){
                                        exclude.add(tvRole.getSelectionModel().getSelectedItem());
                                    }
                                }
                            });
                            this.setGraphic(checkBox);
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
                            this.setText(role.getRolename());
                        }
                    }
                };
            }
        });
    }

    public List<Tb_Role> assignRole() {
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        this.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, action -> {
            try {

            } catch (Exception e) {
                FxmlUtil.showException(e, null);
            }
        });
        this.showAndWait();
        return tvRole.getSelectionModel().getSelectedItems();
    }

    private void searchRole(){

    }

}
