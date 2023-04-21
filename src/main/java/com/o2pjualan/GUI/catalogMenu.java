package com.o2pjualan.GUI;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class catalogMenu extends Tab {
    private TextField searchBar;
    private ComboBox<String> searchDropDown;
    private ComboBox<Integer> idDropDown;
    private ArrayList<Label> items;

    private Button saveButton;
    private Button payButton;

    public catalogMenu(){
        /*Set Tab Name*/
        this.setText("Catalog");

        /*Search Bar Text Field*/
        this.searchBar = new TextField();
        this.searchBar.setId("searchBar");
        this.searchBar.setPromptText("Search");
        this.searchBar.setPrefWidth(600);
        this.searchBar.setPrefHeight(40);

        /*Dropdown to search by category*/
        this.searchDropDown = new ComboBox<>();
        searchDropDown.setItems(FXCollections.observableArrayList("All", "Name", "Price", "Category"));
        this.searchDropDown.setId("searchDropDown");
        this.searchDropDown.setPromptText("Search by...");

        /*Dropdown to search customer by id*/
        this.idDropDown = new ComboBox<>();
        idDropDown.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
        this.idDropDown.setId("idDropDown");
        this.idDropDown.setPromptText("Customer ID");

        /*Button to save bill*/
        this.saveButton = new Button("Save Bill");
        this.payButton = new Button("Pay Bill");


        /*Label*/
        Label placeholder = new Label();
        Image placeHolderImg = new Image("file:src/img/placeholderimg.png");
        placeholder.setGraphic(new ImageView(placeHolderImg));

        Label textHolder = new Label("Item 1");
        textHolder.setId("textItem");


        /*Per item layout*/
        VBox itemLayout = new VBox();
        itemLayout.getChildren().addAll(placeholder, textHolder);
        itemLayout.setSpacing(10);
        itemLayout.getStylesheets().add("file:src/main/java/com/example/tokosinaro2p/style/style.css");

        /*All Item Layout*/
        HBox itemsLayout = new HBox();
        itemsLayout.setPrefWidth(500);
        itemsLayout.setPrefHeight(500);
        itemsLayout.getChildren().add(itemLayout);


        /*Button layout displaying side by side*/
        HBox buttonLayout = new HBox();
        buttonLayout.getChildren().addAll(this.saveButton, this.payButton);
        buttonLayout.setSpacing(100);

        /*Whole Layout*/
        HBox wholeLayout = new HBox();
        wholeLayout.setFillHeight(true);

        /*Layout to display shopping cart*/
        VBox invoiceLayout = new VBox();
        invoiceLayout.setId("invoiceLayout");

        /*Right Layout containing search bar, dropdown, and list of items*/
        VBox rightLayout = new VBox();
        HBox searchLayout = new HBox();
        searchLayout.getChildren().addAll(this.searchBar, this.searchDropDown);
        searchLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
        rightLayout.getChildren().addAll(searchLayout, itemsLayout);
        rightLayout.setSpacing(15);
        searchLayout.setSpacing(15);
        rightLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

        /*Left layout containing dropdown id, list of printed items, button*/
        VBox leftLayout = new VBox();
        leftLayout.setId("leftLayout");
        leftLayout.getChildren().addAll(this.idDropDown, invoiceLayout, buttonLayout);
        leftLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
        leftLayout.setPrefWidth(500);
        leftLayout.setPrefHeight(600);
        leftLayout.setSpacing(10);

        wholeLayout.getChildren().addAll(rightLayout, leftLayout);
        wholeLayout.setSpacing(50);
        wholeLayout.setPadding(new Insets(20,20,20,20));

        Pane base = new Pane();
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);

        base.getChildren().add(wholeLayout);
        this.setContent(base);

    }
}
