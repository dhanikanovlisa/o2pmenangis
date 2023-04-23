package com.o2pjualan.GUI;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class report extends Tab {
    public report() {
        this.setText("Report");
        HBox upperLayout = new HBox();
        Label titleReport = new Label("Data Sales");
        titleReport.setId("h1");
        upperLayout.getChildren().add(titleReport);

        VBox wholeLayout = new VBox();
        wholeLayout.getChildren().add(upperLayout);
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

        Pane base = new Pane();
        base.getChildren().add(wholeLayout);
        this.setContent(base);

    }
}
