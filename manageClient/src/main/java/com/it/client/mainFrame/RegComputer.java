package com.it.client.mainFrame;

import com.google.gson.JsonObject;
import com.it.client.EJB;
import com.it.client.util.ConfigUtil;
import com.it.client.util.FxmlUtil;
import com.it.client.util.httpClient.task.HttpPostTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class RegComputer extends Dialog {

    @FXML
    ComboBox<NetworkInterface> cmbRegNI;
    @FXML
    TextField tfComputerName, tfTelephone;
    @FXML
    PasswordField pfPassword;

    Node node = null;

    HttpPostTask hpt = null;

    public RegComputer() {
        setTitle("电脑注册");
        initOwner(MainFrame.getInstance());
        node = FxmlUtil.loadFXML(this);

        this.getDialogPane().setContent(new StackPane(node));

        cmbRegNI.setConverter(new StringConverter<NetworkInterface>() {
            @Override
            public String toString(NetworkInterface object) {
                return object.getDisplayName();
            }
            @Override
            public NetworkInterface fromString(String string) {
                return null;
            }
        });
        cmbRegNI.getItems().addAll(getNetworkInterfaces());
        cmbRegNI.getSelectionModel().selectFirst();
    }


    public static List<NetworkInterface> getNetworkInterfaces() {
        List<NetworkInterface> res = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (ni.getHardwareAddress().length == 6) res.add(ni);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public static String toMac(byte[] mac) {
        if (mac == null) return null;
        String str = "";
        for (int i = 0; i < mac.length; i++) {
            str += String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
        }
        return str;
    }

    public void showAndRun() {
        testReg();
        //showAndWait();
        /*if (FxmlUtil.regMac == null || FxmlUtil.regComputer == null) {
            Platform.exit();
            System.exit(0);
        }*/
    }

    public void testReg() {
        MainFrame.getInstance().init();

        /*node.setVisible(true);
        node.setVisible(false);
        this.getDialogPane().getButtonTypes().clear();
        this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        List<String> list = cmbRegNI.getItems().collect { toMac(it.hardwareAddress) }
        new HttpPostTask().setOnSuccess { String str ->
            try {
                JsonObject jsonObject = GsonUtil.jsonParser.parse(str).getAsJsonObject();
                println jsonObject
                if (jsonObject.get("result").getAsBoolean()) {
                    if (jsonObject.get("reg").getAsBoolean()) {//已注册，且已通过；
                        MyUtil.regMac = jsonObject.get("mac").getAsString();
                        MyUtil.regComputer = jsonObject.get("computerName").getAsString();
                        Platform.runLater {
                            close();
                            MainFrame.instance.init();
                            Runtime.getRuntime().exec("NETUSE.BAT");
                        }
                        EJB.startup()
                    } else { //已注册，未通过；
                        Platform.runLater {
                            maskerPane.setText("注册信息尚未通过；请等待通过后重新打开；");
                            maskerPane.setProgressVisible(false)
                            dialogPane.buttonTypes.clear();
                            dialogPane.buttonTypes.addAll(ButtonType.CLOSE);
                        }
                    }
                } else {//未注册；
                    Platform.runLater {
                        maskerPane.setVisible(false);
                        node.setVisible(true);
                        dialogPane.buttonTypes.clear();
                        dialogPane.buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL);
                        dialogPane.lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, {
                            it.consume();
                            try {
                                if (cmbRegNI.selectionModel.selectedItem == null) throw new Exception("请选择注册网卡");
                                if (tfComputerName.text.trim().empty) throw new Exception("请输入计算机名称");

                                new HttpPostTask().setSync(true)
                                        .addStringValue("macId", toMac(cmbRegNI.selectionModel.selectedItem.hardwareAddress))
                                        .addStringValue("computerName", tfComputerName.text.trim())
                                        .addStringValue("loginName", tfTel.text.trim())
                                        .addStringValue("password", DigestUtils.md5Hex(pfPassword.text))
                                        .setOnSuccess { String res ->
                                    try {
                                        JsonObject jo = GsonUtil.jsonParser.parse(res).getAsJsonObject();
                                        if (jo.get("result").getAsBoolean()) {
                                            Platform.runLater { testReg() }
                                        } else throw new Exception(jo.get("message").getAsString())
                                    } catch (e) {
                                        MyUtil.exceptionShow(e)
                                    }
                                }
                                .execute(ConfigUtil.getServerUrl() + "/manageServer/uac/user/regMac.do")
                            } catch (e) {
                                MyUtil.exceptionShow(e);
                            }
                        })
                    }
                }
            } catch (e) {
                e.printStackTrace()
                System.exit(0)
            }
        }.addStringValue("macs", GsonUtil.gson.toJson(list))
                .execute(ConfigUtil.getServerUrl() + "/manageServer/uac/user/findRegMac.do")*/

    }


}
