package com.it.client.user;

import com.it.api.table.user.Tb_User;
import com.it.client.EJB;
import com.it.client.util.FxmlUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import javax.persistence.Table;
import java.util.List;

/**
 * Created by wangzy on 2018/8/13.
 */
public class UserTab extends Tab {

    @FXML
    private TextField tfKey;
    @FXML
    private Button btnSearch, btnAdd, btnUpdate, btnDelete;
    @FXML
    private TableView<Tb_User> tvUser;
    @FXML
    private TableColumn<Tb_User, Long> tcId;
    @FXML
    private TableColumn<Tb_User, String> tcLoginname, tcUsername, tcEmail;
    @FXML
    private TableColumn<Tb_User, Boolean> tcEnable;

    public UserTab() {
        this.setText("用户管理");
        this.setContent(FxmlUtil.loadFXML(this));
        tcId.setCellFactory(new Callback<TableColumn<Tb_User, Long>, TableCell<Tb_User, Long>>() {
            @Override
            public TableCell<Tb_User, Long> call(TableColumn<Tb_User, Long> param) {
                return new TableCell<Tb_User, Long>() {
                    @Override
                    protected void updateItem(Long item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty && item != null) {
                            setText(item.toString());
                        }
                    }
                };
            }
        });
        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                doSearch();
            }
        });
        doSearch();
    }

    private void doSearch() {
        try {
            List<Tb_User> users = EJB.getUserService().listUser(EJB.getSessionId(), tfKey.getText());
            tvUser.getItems().addAll(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
