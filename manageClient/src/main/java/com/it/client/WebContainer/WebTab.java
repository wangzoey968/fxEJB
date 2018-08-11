package com.it.client.WebContainer;

import com.it.client.mainFrame.MainFrame;
import com.it.client.util.FxmlUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.web.WebView;

public class WebTab extends Tab {

    @FXML
    public WebView webView;

    public WebTab() {
        this.setText("首页");
        this.setContent(FxmlUtil.loadFXML(this));
        WebContainer container = new WebContainer(webView, MainFrame.getInstance());
        textProperty().bind(container.titleProperty);

        //为刷新按钮添加事件
        /*btnFresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                webView.getEngine().load(webView.getEngine().getLocation());
            }
        });*/
    }

}
