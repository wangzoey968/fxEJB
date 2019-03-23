package com.it.client.order.dialog;

import com.it.api.common.constant.ORDER_TYPE;
import com.it.api.table.order.Tb_Order;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.ExceptionUtil;
import com.it.client.util.FxmlUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.UUID;

public class OrderEditor extends Dialog {

    @FXML
    private TextField tfTitle;
    @FXML
    private ComboBox<String> cmbOrderType;

    public OrderEditor() {
        initOwner(MainFrame.getInstance());
        getDialogPane().setContent(FxmlUtil.loadFXML(this));
        cmbOrderType.getItems().addAll(ORDER_TYPE.getAllTypes());
        cmbOrderType.getSelectionModel().selectFirst();
    }

    public Tb_Order addOrder() {
        setTitle("订单创建");
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        final Tb_Order[] order = {null};
        getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ANY, event -> {
            event.consume();
            try {
                Tb_Order o = new Tb_Order();
                o.setOrderId(UUID.randomUUID().toString());
                o.setOrderTitle(tfTitle.getText());
                o.setOrderType(cmbOrderType.getValue());
                o.setCreatorName(EJB.getUsername());
                o.setPresetDeadlineTime(System.currentTimeMillis());
                o.setCustomerId(EJB.getUserId());
                o.setCustomerName(EJB.getUsername());
                o.setIncomeId(10L);
                order[0] = EJB.getOrderService().makeOrder(EJB.getSessionId(), o);
                close();
            } catch (Exception e) {
                ExceptionUtil.showException(e);
            }
        });
        showAndWait();
        return order[0];
    }

    public Tb_Order editOrder(Tb_Order o) {
        setTitle("订单编辑");
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        applyForm(o);
        getDialogPane().lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ANY, event -> {
            try {
                o.setOrderTitle(tfTitle.getText());
                o.setOrderType(cmbOrderType.getValue());
                EJB.getOrderService().editOrder(EJB.getSessionId(), o);
            } catch (Exception e) {
                ExceptionUtil.showException(e);
            }
        });
        showAndWait();
        return o;
    }

    private void applyForm(Tb_Order o) {
        tfTitle.setText(o.getOrderTitle());
        cmbOrderType.setValue(o.getOrderType());
    }

}
