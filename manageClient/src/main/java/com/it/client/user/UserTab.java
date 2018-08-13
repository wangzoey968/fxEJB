package com.it.client.user;

import com.it.api.table.user.Tb_User;
import com.it.client.EJB;
import com.it.client.util.FxmlUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
                        Tb_User user = (Tb_User) this.getTableRow().getItem();
                        if (!empty && user != null) {
                            setText(user.getId().toString());
                        }
                    }
                };
            }
        });
        tcUsername.setCellFactory(new Callback<TableColumn<Tb_User, String>, TableCell<Tb_User, String>>() {
            @Override
            public TableCell<Tb_User, String> call(TableColumn<Tb_User, String> param) {
                return new TableCell<Tb_User,String>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                    }
                };
            }
        });
        tcLoginname.setCellFactory(new Callback<TableColumn<Tb_User, String>, TableCell<Tb_User, String>>() {
            @Override
            public TableCell<Tb_User, String> call(TableColumn<Tb_User, String> param) {
                return new TableCell<Tb_User, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_User user = (Tb_User) this.getTableRow().getItem();
                        if (!empty && user != null) {
                            setText(user.getLoginname());
                        }
                    }
                };
            }
        });
        tcEnable.setCellFactory(new Callback<TableColumn<Tb_User, Boolean>, TableCell<Tb_User, Boolean>>() {
            @Override
            public TableCell<Tb_User, Boolean> call(TableColumn<Tb_User, Boolean> param) {
                return new TableCell<Tb_User, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_User user = (Tb_User) getTableRow().getItem();
                        if (!empty && user != null) {
                            if (user.getEnable()) {
                                this.setText("可用");
                                this.setTextFill(Color.GREEN);
                            } else {
                                this.setText("不可以");
                                this.setTextFill(Color.RED);
                            }
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
            tvUser.getItems().clear();
            List<Tb_User> users = EJB.getUserService().listUser(EJB.getSessionId(), tfKey.getText());
            tvUser.getItems().addAll(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
