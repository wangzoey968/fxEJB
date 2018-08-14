package com.it.client.user.dialog;

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
public class UserDialog extends Dialog {

    @FXML
    private TextField tfLoginname, tfUsername, tfPassword, tfSuperPassword, tfEmail;
    @FXML
    private RadioButton rbEnable, rbNotEnable;

    public UserDialog() {
        this.getDialogPane().setContent(FxmlUtil.loadFXML(this));
    }

    /**
     * 添加tb_user
     */
    public Tb_User addUser() {
        this.setTitle("添加用户");
        this.initOwner(MainFrame.getInstance());
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        final Tb_User[] user = new Tb_User[1];
        this.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, filter -> {
            try {
                user[0] = EJB.getUserService().addUser(EJB.getSessionId(), setObjectValue());
            } catch (Exception e) {
                FxmlUtil.showException(e, MainFrame.getInstance());
                e.printStackTrace();
            }
        });
        showAndWait();
        return user[0];
    }

    /**
     * 修改tb_user
     */
    public Tb_User updateUser(Tb_User user) {
        this.setTitle("修改用户");
        this.initOwner(MainFrame.getInstance());
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        setTextValue(user);
        //todo,更新时获取的值不正确
        final Tb_User[] ret = new Tb_User[1];
        this.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, event -> {
            try {
                ret[0] = setObjectValue();
                ret[0].setId(user.getId());
                EJB.getUserService().updateUser(EJB.getSessionId(), ret[0]);
            } catch (Exception e) {
                FxmlUtil.showException(e, MainFrame.getInstance());
                e.printStackTrace();
            }
        });
        showAndWait();
        return ret[0];
    }

    /**
     * 为对象赋值
     */
    private Tb_User setObjectValue() {
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
     * 把传递过来的对象各个属性设置到node中
     */
    private void setTextValue(Tb_User user) {
        tfLoginname.setText(user.getLoginname());
        tfUsername.setText(user.getUsername());
        tfPassword.setText("******");
        tfSuperPassword.setText("******");
        if (user.getEnable()) {
            rbEnable.setSelected(true);
        } else {
            rbNotEnable.setSelected(true);
        }
        tfEmail.setText(user.getEmail());
    }

}
