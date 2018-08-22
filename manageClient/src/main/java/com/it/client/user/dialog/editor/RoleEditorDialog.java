package com.it.client.user.dialog.editor;

import com.it.api.table.user.Tb_Role;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.FxmlUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

/**
 * Created by wangzy on 2018/8/16.
 */
public class RoleEditorDialog extends Dialog {

    @FXML
    private TextField tfRolename, tfNote;

    public RoleEditorDialog() {
        this.initOwner(MainFrame.getInstance());
        this.getDialogPane().setContent(FxmlUtil.loadFXML(this));
    }

    public Tb_Role createRole() {
        this.setTitle("添加角色");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        final Tb_Role[] roles = new Tb_Role[1];
        this.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try {
                event.consume();
                roles[0] = EJB.getUserService().addRole(EJB.getSessionId(), setNewValue());
                this.close();
            } catch (Exception e) {
                FxmlUtil.showException(e, MainFrame.getInstance());
                e.printStackTrace();
            }
        });
        this.showAndWait();
        return roles[0];
    }

    private Tb_Role setNewValue() {
        Tb_Role role = new Tb_Role();
        role.setRolename(tfRolename.getText());
        role.setNote(tfNote.getText());
        return role;
    }

    public Tb_Role updateRole(Tb_Role role) {
        this.setTitle("修改角色");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        setTextValue(role);
        final Tb_Role[] roles = new Tb_Role[1];
        this.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try {
                event.consume();
                roles[0] = EJB.getUserService().updateRole(EJB.getSessionId(), setUpdateValue(role));
                this.close();
            } catch (Exception e) {
                FxmlUtil.showException(e, null);
            }
        });
        this.showAndWait();
        return roles[0];
    }

    private Tb_Role setUpdateValue(Tb_Role role) {
        role.setRolename(tfRolename.getText());
        role.setNote(tfNote.getText());
        return role;
    }

    private void setTextValue(Tb_Role role) {
        tfRolename.setText(role.getRolename());
        tfNote.setText(role.getNote());
    }

}
