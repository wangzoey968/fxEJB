package com.it.client.order;

import com.it.client.util.FxmlUtil;
import javafx.scene.control.Tab;

public class OrderTab extends Tab {

    public OrderTab(){
        this.setContent(FxmlUtil.loadFXML(this));
    }

}
