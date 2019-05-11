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
import javafx.scene.layout.VBox;
import org.hibernate.engine.jdbc.internal.DDLFormatterImpl;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;

public class OrderInfo extends Dialog {

    @FXML
    private VBox vbLeft, vbRight;

    public OrderInfo(Tb_Order order) {
        setTitle("订单详情");
        getDialogPane().setContent(FxmlUtil.loadFXML(this));
        OrderEditor editor = new OrderEditor();
        vbLeft.getChildren().add(FxmlUtil.loadFXML(editor));
        editor.viewOrder(order);

        try {
            //Image image = new Image("file:C:/download/wzyDesktop/微信图片_20180728185358.png");
            String s = Paths.get(System.getProperty("user.dir") + "/manageClient/skin/icon/locked.png").toUri().toURL().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OrderInfo view() {
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        return this;
    }
}
