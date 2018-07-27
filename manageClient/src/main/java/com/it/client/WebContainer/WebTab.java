package com.it.client.WebContainer;

import com.it.client.util.FxmlUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.web.WebView;

public class WebTab extends Tab {

    @FXML
    public WebView webView;

    public WebTab() {
        this.setText("首页");
        this.setContent(FxmlUtil.loadFXML(this));
        //WebContainer container = new WebContainer(webView, new MainFrame());
        //textProperty().bind(container.titleProperty);
    }

}
