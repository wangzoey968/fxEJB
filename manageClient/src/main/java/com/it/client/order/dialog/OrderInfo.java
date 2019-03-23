package com.it.client.order.dialog;

import com.it.api.table.order.Tb_Order;
import com.it.client.util.FxmlUtil;
import com.it.client.util.ImgUtil;
import com.sun.imageio.plugins.common.ImageUtil;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;

public class OrderInfo extends Dialog {

    public OrderInfo(Tb_Order order) {
        setTitle("订单详情");
        getDialogPane().setContent(FxmlUtil.loadFXML(this));
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        try {
            //Image image = new Image("file:C:/download/wzyDesktop/微信图片_20180728185358.png");

            String s = Paths.get(System.getProperty("user.dir") + "/manageClient/skin/icon/locked.png").toUri().toURL().toString();
            Image image = new Image(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
