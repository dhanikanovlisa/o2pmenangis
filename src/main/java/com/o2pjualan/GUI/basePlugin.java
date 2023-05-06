package com.o2pjualan.GUI;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class basePlugin extends Tab {
    protected TabPane mainTabPane;
    public basePlugin(TabPane mainTabPane){
        this.mainTabPane = mainTabPane;
    }
    public void addChart(Node chart) {
        this.setText("Data Sales Report Chart");
        this.setContent(chart);
        mainTabPane.getTabs().add(this);
    }
}