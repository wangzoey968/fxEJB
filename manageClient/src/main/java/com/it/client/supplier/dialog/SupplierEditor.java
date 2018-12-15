package com.it.client.supplier.dialog;

import com.it.api.table.customer.Tb_Supplier;
import com.it.api.table.user.Tb_User;
import com.it.client.EJB;
import com.it.client.util.FxmlUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by wangzy on 2018/11/2.
 */
public class SupplierEditor extends Dialog {

    @FXML
    private TextField tfSuppplierName, tfAddress, tfContact, tfPhone, tfNote, tfSupplyType;

    public SupplierEditor() {
        this.getDialogPane().setContent(FxmlUtil.loadFXML(this));

    }

    public Tb_Supplier editSupplier(Tb_Supplier supplier) {

        return null;
    }

    private void setTextValue(Tb_Supplier supplier) {

    }

    public Tb_Supplier insertSupplier() {
        this.setTitle("新建供应商");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Tb_Supplier supplier = this.getTextValue();
        this.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, action -> {
            action.consume();
            try {
                EJB.getSupplierService().insertSupplier(EJB.getSessionId(), supplier);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.showAndWait();
        return supplier;
    }

    private Tb_Supplier getTextValue() {
        Tb_Supplier supplier = new Tb_Supplier();

        return null;
    }

}
