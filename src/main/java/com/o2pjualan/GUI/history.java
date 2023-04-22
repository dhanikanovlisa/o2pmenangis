package com.o2pjualan.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class history extends Tab {
    public ComboBox<String> name;
    public Label idTransaction;
    public Label firstItem;
    public Label totalPrice;

    public ArrayList<Label> listOfBill;
    public history(){

        this.setText("History");
        Label historyTitle = new Label("History");
        historyTitle.setId("h1");

        this.name = new ComboBox<>();
        this.name.setPromptText("Pick Customer Name...");
        this.name.setId("nameDropDown");

        HBox upperLayout = new HBox();
        upperLayout.getChildren().addAll(historyTitle, this.name);
        upperLayout.setSpacing(900);

        this.idTransaction = new Label("194129471");
        this.idTransaction.setId("historyItemLabel");
        this.firstItem = new Label("Tabung Elemenyer...");
        this.firstItem.setId("historyItemLabel");
        this.totalPrice = new Label("Total Pesanan: 1024124091");
        this.totalPrice.setId("historyItemLabel");

        VBox historyItemLayout = new VBox();
        historyItemLayout.setId("historyItemLayout");
        historyItemLayout.getChildren().addAll(this.idTransaction, this.firstItem, this.totalPrice);

        VBox historyLayout = new VBox();
        historyLayout.getChildren().addAll(historyItemLayout);
        historyLayout.setSpacing(10);


        VBox wholeLayout = new VBox();
        wholeLayout.getChildren().addAll(upperLayout, historyLayout);
        wholeLayout.setMargin(upperLayout, new Insets(10,0,0,0));
        wholeLayout.setMargin(historyLayout, new Insets(10, 0, 0, 0));
        wholeLayout.setSpacing(40);
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
