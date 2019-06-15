package com.it.test;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TreeTableDrag extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    private TreeTableView<Tb_User> tree = new TreeTableView<>();
    private TreeTableColumn<Tb_User, String> clmName = new TreeTableColumn<>();
    private TreeTableColumn<Tb_User, String> clmPwd = new TreeTableColumn<>();

    private MenuItem mAdd = new MenuItem("添加");
    private MenuItem mUpdate = new MenuItem("修改");
    private MenuItem mDel = new MenuItem("删除");
    private MenuItem mUp = new MenuItem("上移");
    private MenuItem mDown = new MenuItem("下移");
    private ContextMenu menu = new ContextMenu(mAdd, mUpdate, mDel, mUp, mDown);

    @Override
    public void start(Stage stage) throws Exception {

        clmName.setText("名称");
        clmPwd.setText("密码");
        clmName.setPrefWidth(150);
        tree.getColumns().addAll(clmName, clmPwd);
        tree.setPrefHeight(400);
        tree.setPrefWidth(600);
        tree.setShowRoot(false);
        tree.setRowFactory(new Callback<TreeTableView<Tb_User>, TreeTableRow<Tb_User>>() {
            @Override
            public TreeTableRow<Tb_User> call(TreeTableView<Tb_User> param) {
                TreeTableRow<Tb_User> row = new TreeTableRow<Tb_User>();
                row.setOnContextMenuRequested(event -> {
                    event.consume();
                    if (row.isEmpty()) return;
                    menu.show(row, event.getScreenX(), event.getScreenY());
                    TreeItem<Tb_User> item = row.getTreeItem();
                    mAdd.setOnAction(e -> {
                        e.consume();
                        addRow(item);
                    });
                    mUpdate.setOnAction(e -> {
                        e.consume();
                        updateRow(item);
                    });
                    mDel.setOnAction(e -> {
                        e.consume();
                        delRow(item);
                    });
                    int i = item.getParent().getChildren().indexOf(item);
                    mUp.setDisable(i == 0);
                    mUp.setOnAction(e -> {
                        e.consume();
                        moveUp(item);
                    });
                    int j = item.getParent().getChildren().size() - 1;
                    int k = item.getParent().getChildren().indexOf(item);
                    mDown.setDisable(j == k);
                    mDown.setOnAction(e -> {
                        e.consume();
                        moveDown(item);
                    });
                });
                //拖拽开始
                row.setOnDragDetected(event -> {
                    Dragboard board = row.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    //把row的index传递到剪切板中
                    content.putString(String.valueOf(row.getIndex()));
                    board.setContent(content);

                    //把要拖拽的item折叠
                    TreeItem<Tb_User> item = tree.getSelectionModel().getSelectedItem();
                    item.setExpanded(false);
                    tree.refresh();
                });
                //拖拽悬停setOnDragOver
                row.setOnDragOver(event -> {
                    event.consume();
                    event.acceptTransferModes(TransferMode.ANY);
                });
                //拖拽进入
                row.setOnDragEntered(event -> {
                    event.consume();
                    TreeItem<Tb_User> item = row.getTreeItem();
                    item.setExpanded(!item.isLeaf());
                    tree.refresh();
                });
                //拖拽放开
                row.setOnDragDropped(event -> {
                    event.consume();
                    if (!row.isEmpty()) {
                        TreeItem<Tb_User> source = tree.getSelectionModel().getSelectedItem();
                        TreeItem<Tb_User> target = row.getTreeItem();

                        //判断是不是原来的row
                        if (source == target) return;

                        //移除选中的item
                        TreeItem<Tb_User> parent = source.getParent();
                        parent.getChildren().remove(source);

                        //添加选中的item到row
                        target.getChildren().add(source);
                        tree.getSelectionModel().clearSelection();
                        tree.getSelectionModel().select(source);
                    }
                    tree.refresh();
                });
                return row;
            }
        });
        clmName.setCellFactory(new Callback<TreeTableColumn<Tb_User, String>, TreeTableCell<Tb_User, String>>() {
            @Override
            public TreeTableCell<Tb_User, String> call(TreeTableColumn<Tb_User, String> param) {
                TreeTableCell<Tb_User, String> cell = new TreeTableCell<Tb_User, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            this.setText(null);
                        } else {
                            this.setText(item);
                        }
                    }
                };
                return cell;
            }
        });
        clmName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Tb_User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Tb_User, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getName());
            }
        });
        clmPwd.setCellFactory(new Callback<TreeTableColumn<Tb_User, String>, TreeTableCell<Tb_User, String>>() {
            @Override
            public TreeTableCell<Tb_User, String> call(TreeTableColumn<Tb_User, String> param) {
                return new TreeTableCell<Tb_User, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            this.setText(null);
                        } else {
                            this.setText(item);
                        }
                    }
                };
            }
        });
        clmPwd.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Tb_User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Tb_User, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getPwd());
            }
        });

        doSearch();

        VBox box = new VBox();
        Button button = new Button("刷新");

        button.setOnAction(event -> {
            event.consume();
            doSearch();
        });

        box.getChildren().addAll(button, tree);

        stage.setScene(new Scene(box));
        stage.show();
    }

    private void addRow(TreeItem<Tb_User> row) {
        Tb_User nu = new Tb_User();
        nu.setId(50L);
        nu.setName("new user");
        nu.setPwd("new user");
        nu.setParentId(row.getValue().getId());
        TreeItem<Tb_User> item = new TreeItem<>(nu);
        row.getChildren().add(item);

        if (!row.isExpanded()) row.setExpanded(true);

        tree.refresh();
    }

    private void updateRow(TreeItem<Tb_User> row) {
        row.getValue().setName("2233334455");
        tree.refresh();
    }

    private void delRow(TreeItem<Tb_User> row) {
        row.getParent().getChildren().remove(row);
        tree.refresh();
    }

    private void moveUp(TreeItem<Tb_User> item) {
        ObservableList<TreeItem<Tb_User>> items = item.getParent().getChildren();
        int i = items.indexOf(item);
        TreeItem<Tb_User> pre = item.previousSibling();
        items.set(i - 1, item);
        items.set(i, pre);
        tree.getSelectionModel().select(item);
        tree.refresh();
    }

    private void moveDown(TreeItem<Tb_User> item) {
        ObservableList<TreeItem<Tb_User>> items = item.getParent().getChildren();
        int i = items.indexOf(item);
        TreeItem<Tb_User> next = item.nextSibling();
        items.set(i, next);
        items.set(i + 1, item);
        tree.getSelectionModel().select(item);
        tree.refresh();
    }

    private void doSearch() {
        Tb_User root = new Tb_User();
        root.setId(1L);
        root.setName("1111");
        root.setPwd("1111");
        root.setParentId(null);

        Tb_User c1 = new Tb_User();
        c1.setId(20L);
        c1.setName("2222333");
        c1.setPwd("55556666");
        c1.setParentId(root.getId());
        root.getChild().add(c1);

        Tb_User c2 = new Tb_User();
        c2.setId(30L);
        c2.setName("2222333");
        c2.setPwd("336666");
        c2.setParentId(c1.getId());
        c1.getChild().add(c2);

        Tb_User c3 = new Tb_User();
        c3.setId(40L);
        c3.setName("444555");
        c3.setPwd("222555");
        c3.setParentId(c2.getId());
        c2.getChild().add(c3);

        for (long i = 2; i < 5; i++) {
            Tb_User user = new Tb_User();
            user.setId(i);
            user.setName(String.valueOf(i));
            user.setPwd(String.valueOf(i));
            user.setParentId(1L);
            root.getChild().add(user);
            for (long j = 5; j < 8; j++) {
                Tb_User u = new Tb_User();
                u.setId(j);
                u.setName(String.valueOf(j));
                u.setPwd(String.valueOf(j));
                u.setParentId(i);
                user.getChild().add(u);
                for (long k = 8; k < 12; k++) {
                    Tb_User r = new Tb_User();
                    r.setId(k);
                    r.setName(String.valueOf(k));
                    r.setPwd(String.valueOf(k));
                    r.setParentId(j);
                    u.getChild().add(r);
                }
            }
        }

        TreeItem<Tb_User> container = new TreeItem<>(root);
        container.setExpanded(true);
        tree.setRoot(container);
        makeTree(container);
    }

    private void makeTree(TreeItem<Tb_User> user) {
        for (Tb_User child : user.getValue().getChild()) {
            TreeItem<Tb_User> childItem = new TreeItem<>(child);
            user.getChildren().add(childItem);
            if (child.getId() == 40) {
                setParentExpand(childItem);
            }
            makeTree(childItem);
        }
    }

    private void setParentExpand(TreeItem<Tb_User> u) {
        TreeItem<Tb_User> parent = u.getParent();
        if (parent != null) {
            parent.setExpanded(true);
            setParentExpand(parent);
        }
    }

    private class Tb_User {
        private Long id;
        private String name;
        private String pwd;
        private Long parentId;
        private List<Tb_User> child = new ArrayList<>();

        public List<Tb_User> getChild() {
            return child;
        }

        @Override
        public String toString() {
            return "Tb_User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", pwd='" + pwd + '\'' +
                    ", parentId=" + parentId +
                    ", child=" + child +
                    '}';
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public Long getParentId() {
            return parentId;
        }

        public void setParentId(Long parentId) {
            this.parentId = parentId;
        }
    }

}
