package com.it.client.user.dialog.editor;

import com.it.api.table.user.Tb_Auth;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.FxmlUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.io.File;
import java.nio.file.Files;

/**
 * Created by wangzy on 2018/8/16.
 */
public class AuthEditorDialog extends Dialog {

    @FXML
    private TextField tfAuthname, tfNote;

    public AuthEditorDialog() {
        this.initOwner(MainFrame.getInstance());
        this.getDialogPane().setContent(FxmlUtil.loadFXML(this));
    }

    public Tb_Auth createAuth(Long roleId) {
        this.setTitle("添加角色下的权限");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        final Tb_Auth[] auths = new Tb_Auth[1];
        this.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try {
                event.consume();
                auths[0] = EJB.getUserService().addRole1Auth(EJB.getSessionId(), roleId, setNewValue());
                this.close();
            } catch (Exception e) {
                FxmlUtil.showException(e, MainFrame.getInstance());
                e.printStackTrace();
            }
        });
        this.showAndWait();
        return auths[0];
    }

    private Tb_Auth setNewValue() {
        Tb_Auth auth = new Tb_Auth();
        auth.setAuthname(tfAuthname.getText());
        auth.setNote(tfNote.getText());
        return auth;
    }

    public Tb_Auth updateAuth(Tb_Auth auth) {
        this.setTitle("修改角色下的权限");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        setTextValue(auth);
        final Tb_Auth[] auths = new Tb_Auth[1];
        this.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try {
                event.consume();
                auths[0] = EJB.getUserService().updateAuth(EJB.getSessionId(), setUpdateValue(auth));
                this.close();
            } catch (Exception e) {
                FxmlUtil.showException(e, null);
            }
        });
        this.showAndWait();
        return auths[0];
    }

    private Tb_Auth setUpdateValue(Tb_Auth auth) {
        auth.setAuthname(tfAuthname.getText());
        auth.setNote(tfNote.getText());
        return auth;
    }

    private void setTextValue(Tb_Auth auth) {
        tfAuthname.setText(auth.getAuthname());
        tfNote.setText(auth.getNote());
    }

}
