package com.it.client.supplier;

import com.it.api.table.customer.Tb_Supplier;
import com.it.client.EJB;
import com.it.client.supplier.dialog.SupplierEditor;
import com.it.client.util.FxmlUtil;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.util.List;

public class SupplierTab extends Tab {

    @FXML
    private TextField tfKey;
    @FXML
    private Button btnAdd, btnUpdate, btnRefresh, btnDelete;
    @FXML
    private TableView<Tb_Supplier> tvSupplier;
    @FXML
    private TableColumn<Tb_Supplier, Number> tcId;
    @FXML
    private TableColumn<Tb_Supplier, String> tcSupplierName, tcContact, tcAddress, tcPhone, tcNote;

    public SupplierTab() {
        this.setText("供应商管理");
        this.setContent(FxmlUtil.loadFXML(this));
        tvSupplier.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tcId.setCellValueFactory(factory ->
                new SimpleLongProperty(factory.getValue().getId())
        );
        tcSupplierName.setCellValueFactory(factory ->
                new SimpleStringProperty(factory.getValue().getSupplierName())
        );
        tcContact.setCellValueFactory(factory ->
                new SimpleStringProperty(factory.getValue().getContact())
        );
        tcAddress.setCellValueFactory(factory ->
                new SimpleStringProperty(factory.getValue().getAddress())
        );
        tcPhone.setCellValueFactory(factory ->
                new SimpleStringProperty(factory.getValue().getPhone())
        );
        tcNote.setCellValueFactory(factory ->
                new SimpleStringProperty(factory.getValue().getNote())
        );
        btnAdd.setOnAction(action -> {
            action.consume();
            //TablePosition<Tb_Supplier, ?> cell = tvSupplier.getEditingCell();
            SupplierEditor dialog = new SupplierEditor();
            dialog.insertSupplier();
        });
        btnUpdate.setOnAction(action->{
            SupplierEditor dialog = new SupplierEditor();

        });
    }

    private void doSearch() {
        try {
            tvSupplier.getItems().clear();
            List<Tb_Supplier> list = EJB.getSupplierService().selectSupplier(EJB.getSessionId());
            tvSupplier.getItems().addAll(list);
            tvSupplier.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
