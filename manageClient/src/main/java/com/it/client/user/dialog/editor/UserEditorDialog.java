package com.it.client.user.dialog.editor;

import com.it.api.table.user.Tb_User;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.FxmlUtil;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by wangzy on 2018/8/14.
 */
public class UserEditorDialog extends Dialog {

    @FXML
    private TextField tfLoginname, tfUsername, tfPassword, tfSuperPassword, tfEmail;
    @FXML
    private RadioButton rbEnable, rbNotEnable;

    public UserEditorDialog() {
        this.initOwner(MainFrame.getInstance());
        this.getDialogPane().setContent(FxmlUtil.loadFXML(this));
    }

    /**
     * 添加tb_user
     */
    public Tb_User addUser() {
        this.setTitle("添加用户");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        final Tb_User[] user = new Tb_User[1];
        this.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try {
                event.consume();
                user[0] = EJB.getUserService().addUser(EJB.getSessionId(), setNewValue());
                this.close();
            } catch (Exception e) {
                FxmlUtil.showException(e, MainFrame.getInstance());
                e.printStackTrace();
            }
        });
        this.showAndWait();
        return user[0];
    }

    /**
     * 修改tb_user
     */
    public Tb_User updateUser(Tb_User user) {
        this.setTitle("修改用户");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        setTextValue(user);
        final Tb_User[] ret = new Tb_User[1];
        this.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try {
                event.consume();
                ret[0] = EJB.getUserService().updateUser(EJB.getSessionId(), setUpdateValue(user));
                this.close();
            } catch (Exception e) {
                FxmlUtil.showException(e, MainFrame.getInstance());
                e.printStackTrace();
            }
        });
        this.showAndWait();
        return ret[0];
    }

    /**
     * 为对象赋值
     */
    private Tb_User setNewValue() {
        Tb_User user = new Tb_User();
        user.setLoginname(tfLoginname.getText());
        user.setUsername(tfUsername.getText());
        user.setPassword(DigestUtils.md5Hex(tfPassword.getText()));
        user.setSuperPassword(DigestUtils.md5Hex(tfSuperPassword.getText()));
        user.setEnable(rbEnable.isSelected());
        user.setEmail(tfEmail.getText());
        return user;
    }

    /**
     * 修改的时候为对象赋值
     */
    private Tb_User setUpdateValue(Tb_User b) throws Exception {
        b.setLoginname(tfLoginname.getText());
        b.setUsername(tfUsername.getText());
        b.setPassword(DigestUtils.md5Hex(tfPassword.getText()));
        b.setSuperPassword(DigestUtils.md5Hex(tfSuperPassword.getText()));
        b.setEnable(rbEnable.isSelected());
        b.setEmail(tfEmail.getText());
        return b;
    }

    /**
     * 把传递过来的对象各个属性设置到fxml的node中
     */
    private void setTextValue(Tb_User user) {
        tfLoginname.setText(user.getLoginname());
        tfUsername.setText(user.getUsername());
        tfPassword.setText("123");
        tfSuperPassword.setText("123");
        if (user.getEnable()) {
            rbEnable.setSelected(true);
        } else {
            rbNotEnable.setSelected(true);
        }
        tfEmail.setText(user.getEmail());
    }

}
