package com.it.client.order.dialog;

import com.it.api.common.constant.ORDER_TYPE;
import com.it.api.common.constant.POLICY_AREA;
import com.it.api.table.order.Tb_Order;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.ExceptionUtil;
import com.it.client.util.FxmlUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class OrderEditor extends Dialog {

    @FXML
    private TextField tfTitle, tfAmount, tfPackNote, tfNote;
    @FXML
    private ComboBox<String> cmbOrderType, cmbCusname, cmbBizname, cmbPolicyArea;
    @FXML
    private ToggleGroup tgUrgent, tgPack, tgPost;
    @FXML
    private RadioButton rb0, rb1, rb2, rb3, rbNotPack, rbPack, rbFetch, rbPost, rbExpress;

    public OrderEditor() {
        initOwner(MainFrame.getInstance());
        getDialogPane().setContent(FxmlUtil.loadFXML(this));
        cmbOrderType.getItems().addAll(ORDER_TYPE.getAllTypes());
        cmbOrderType.getSelectionModel().selectFirst();
        cmbCusname.getItems().addAll("东方彩印", "上海印刷", "浦东印刷");
        cmbCusname.getSelectionModel().selectFirst();
        cmbBizname.getItems().addAll("徐", "杨", "刘");
        cmbBizname.getSelectionModel().selectFirst();
        cmbPolicyArea.getItems().addAll(POLICY_AREA.getAllArea());
        cmbPolicyArea.getSelectionModel().selectFirst();
        tfPackNote.disableProperty().bind(rbPack.selectedProperty().not());
    }

    public Tb_Order addOrder() {
        setTitle("订单创建");
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        rb0.setSelected(true);
        rbPack.setSelected(true);
        rbPost.setSelected(true);
        final Tb_Order[] order = {null};
        getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ANY, event -> {
            event.consume();
            try {
                Tb_Order o = new Tb_Order();
                applyData(o);
                o.setOrderId(UUID.randomUUID().toString());
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
                applyData(o);
                EJB.getOrderService().editOrder(EJB.getSessionId(), o);
            } catch (Exception e) {
                ExceptionUtil.showException(e);
            }
        });
        showAndWait();
        return o;
    }

    private void applyData(Tb_Order o) {
        o.setOrderType(cmbOrderType.getValue());
        o.setOrderTitle(tfTitle.getText());
        o.setAmount(Integer.parseInt(tfAmount.getText()));
        o.setCustomerId(EJB.getUserId());
        o.setCustomerName(cmbCusname.getValue());
        o.setBizId(10L);
        o.setBizName(cmbBizname.getValue());
        o.setCreateorId(10L);
        o.setCreatorName(EJB.getUsername());
        o.setCreateTime(System.currentTimeMillis());
        o.setPresetDeadlineTime(System.currentTimeMillis());
        o.setUrgentLevel(Integer.parseInt(((ToggleButton) tgUrgent.getSelectedToggle()).getText()));
        o.setPolicyArea(cmbPolicyArea.getValue());
        if (rbPack.isSelected()) {
            o.setDemandPack(rbPack.isSelected());
            o.setPackNote(tfPackNote.getText());
        } else {
            o.setDemandPack(false);
            o.setPackNote(null);
        }
        o.setPostWay(((ToggleButton) tgPost.getSelectedToggle()).getText());
        if (rbPost.isSelected()) {
            o.setPresetPostmanId(EJB.getUserId());
            o.setPresetPostmanName(EJB.getUsername());
            o.setPresetPostTime(Timestamp.valueOf(LocalDateTime.now().plusDays(3)).getTime());
        }
        o.setNote(tfNote.getText());
        o.setIncomeId(10L);
    }

    private void applyForm(Tb_Order o) {
        cmbOrderType.setValue(o.getOrderType());
        tfTitle.setText(o.getOrderTitle());
        tfAmount.setText(o.getAmount().toString());
        cmbCusname.setValue(o.getCustomerName());
        cmbBizname.setValue(o.getBizName());
        tfNote.setText(o.getNote());
        tgUrgent.getToggles().forEach(e -> {
            ToggleButton button = (ToggleButton) e;
            if (button.getText().equals(o.getUrgentLevel().toString())) e.setSelected(true);
        });
        if (o.getDemandPack()) {
            rbPack.setSelected(true);
            tfPackNote.setText(o.getPackNote());
        } else {
            rbNotPack.setSelected(true);
        }
        rbFetch.setSelected(o.getPostWay().equals("自提"));
        rbPost.setSelected(o.getPostWay().equals("送货"));
        rbExpress.setSelected(o.getPostWay().equals("快递"));
    }

}
