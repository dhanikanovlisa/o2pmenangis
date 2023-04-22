package com.o2pjualan.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class clickedHistory extends Tab {
    public Button back;
    public Button printBill;

    public clickedHistory(){
        this.setText("[name]'s bill [id]");

        this.back = new Button("Back");
        this.back.setId("buttonClickedHistory");
        this.printBill = new Button("Print Bill");
        this.printBill.setId("buttonClickedHistory");

        HBox buttonLayout = new HBox();
        buttonLayout.setId("buttonLayout");
        buttonLayout.setSpacing(900);
        buttonLayout.getChildren().addAll(this.back, this.printBill);

        Label idBill = new Label("#19824794");
        idBill.setId("paragraph");

        VBox billLayout = new VBox();
        billLayout.setId("billLayout");
        billLayout.setSpacing(10);
        billLayout.setAlignment(Pos.CENTER);
        billLayout.getChildren().addAll(idBill);

        VBox wholeLayout = new VBox();
        wholeLayout.getChildren().addAll(buttonLayout, billLayout);
        wholeLayout.setMargin(buttonLayout, new Insets(10,0,0,0));
        wholeLayout.setMargin(billLayout, new Insets(20, 0, 0, 0));
        wholeLayout.setId("wholeLayout");

        Pane base = new Pane();
        base.getChildren().add(wholeLayout);
        this.setContent(base);
        wholeLayout.setFillWidth(true);
        wholeLayout.prefWidthProperty().bind(base.widthProperty());
        wholeLayout.prefHeightProperty().bind(base.heightProperty());
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
    }
}
