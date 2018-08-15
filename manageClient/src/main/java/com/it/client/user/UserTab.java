package com.it.client.user;

import com.it.api.table.user.Tb_User;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.user.dialog.UserDialog;
import com.it.client.util.FxmlUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
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

    ContextMenu menu = new ContextMenu();
    MenuItem addItem = new MenuItem("添加");
    MenuItem updateItem = new MenuItem("修改");
    MenuItem deleteItem = new MenuItem("删除");

    public UserTab() {
        this.setText("用户管理");
        this.setContent(FxmlUtil.loadFXML(this));
        tvUser.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tvUser.setRowFactory(new Callback<TableView<Tb_User>, TableRow<Tb_User>>() {
            @Override
            public TableRow<Tb_User> call(TableView<Tb_User> param) {
                TableRow<Tb_User> row = new TableRow<>();
                row.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent event) {
                        menu.getItems().clear();
                        menu.getItems().add(addItem);
                        if (row.getItem() != null) {
                            menu.getItems().addAll(updateItem, deleteItem);
                        }
                        row.setContextMenu(menu);
                    }
                });
                return row;
            }
        });
        addItem.setOnAction(oa -> {
            Tb_User user = new UserDialog().addUser();
            if (user != null) {
                tvUser.getItems().add(0, user);
                tvUser.getSelectionModel().select(0);
                tvUser.refresh();
            }
        });
        updateItem.setOnAction(oa -> {
            Tb_User item = tvUser.getSelectionModel().getSelectedItem();
            Tb_User user = new UserDialog().updateUser(item);
            if (user != null) {
                tvUser.getItems().set(tvUser.getItems().indexOf(item), user);
                tvUser.getSelectionModel().select(user);
                tvUser.refresh();
            }
        });
        deleteItem.setOnAction(oa -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确认删除");
            alert.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, filter -> {
                try {
                    Tb_User user = tvUser.getSelectionModel().getSelectedItem();
                    EJB.getUserService().deleteUser(EJB.getSessionId(), user.getId());
                    tvUser.getItems().remove(user);
                    tvUser.refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            alert.initOwner(MainFrame.getInstance());
            alert.showAndWait();
        });
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
                return new TableCell<Tb_User, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_User user = (Tb_User) this.getTableRow().getItem();
                        if (!empty && user != null) {
                            this.setText(user.getUsername());
                        }
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
                                this.setText("不可用");
                                this.setTextFill(Color.RED);
                            }
                        }
                    }
                };
            }
        });
        tcEmail.setCellFactory(new Callback<TableColumn<Tb_User, String>, TableCell<Tb_User, String>>() {
            @Override
            public TableCell<Tb_User, String> call(TableColumn<Tb_User, String> param) {
                return new TableCell<Tb_User, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        Tb_User user = (Tb_User) this.getTableRow().getItem();
                        if (!empty && user != null) {
                            this.setText(user.getEmail());
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
