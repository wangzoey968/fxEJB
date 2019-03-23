package com.it.client.login;

import com.it.api.table.user.Tb_User;
import com.it.client.EJB;
import com.it.client.mainFrame.MainFrame;
import com.it.client.util.FxmlUtil;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangzy on 2019/2/27.
 */
public class LoginStage extends Stage {

    @FXML
    private ComboBox<String> cmbUsername;
    @FXML
    private PasswordField iptPassword;
    @FXML
    Button btnLogin;


    public static LoginStage loginStage = new LoginStage();

    public static LoginStage getInstance() {
        return loginStage;
    }

    private Set<String> set = new HashSet<>();

    public LoginStage() {
        StackPane stack = new StackPane();
        stack.getChildren().add(FxmlUtil.loadFXML(this));
        this.setScene(new Scene(stack));
        this.setOnCloseRequest(event -> {
            event.consume();
            this.close();
            MainFrame.getInstance().init();
            MainFrame.getInstance().show();
        });

        cmbUsername.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> cell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            this.setText(null);
                            this.setGraphic(null);
                        } else {
                            HBox parent = new HBox();

                            HBox box = new HBox();
                            HBox.setHgrow(box, Priority.ALWAYS);
                            Label label = new Label(item);
                            box.getChildren().add(label);

                            Hyperlink link = new Hyperlink("清除");
                            link.setOnMouseClicked(event -> {
                                event.consume();
                                cmbUsername.getItems().remove(item);
                            });

                            parent.getChildren().addAll(box, link);
                        }
                    }
                };
                return cell;
            }
        });
        loadUsers();
        btnLogin.setOnAction(event -> {
            event.consume();
            String pwd = iptPassword.getText();
            String name = cmbUsername.getValue();
            if (pwd == "") return;
            try {
                Tb_User user = EJB.getUserService().login(name, pwd);
                this.close();
                MainFrame.getInstance().init();
                MainFrame.getInstance().show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.show();
    }

    private void loadUsers() {
        File file = new File(System.getProperty("user.dir") + "/users.txt");
        if (file.exists()) {
            try {
                FileReader reader = new FileReader(file);
                BufferedReader buffer = new BufferedReader(reader);
                String line = null;
                while ((line = buffer.readLine()) != null) {
                    cmbUsername.getItems().add(line);
                }
                cmbUsername.getSelectionModel().selectFirst();
                reader.close();
                buffer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
