package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import static com.o2pjualan.Main.controller;

public class catalogMenu extends Tab {
    private Products listProducts;
    public Customers customers;
    public Bills customerBill;
    private TextField searchBar;
    private ComboBox<String> searchDropDown;
    private ComboBox<String> idDropDown;


    private Button saveButton;
    private Button payButton;
    private VBox itemLayout;
    private GridPane itemsLayout;
    private VBox leftLayout;
    private HBox buttonLayout;
    private VBox invoiceLayout;

    public catalogMenu() throws IOException {
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


        customers = new Customers();
        customers = controller.getCustomers();
        ArrayList<Integer> customersId = customers.getAllCustomersId();
        ArrayList<String> optionsList = new ArrayList<String>();
        for (Integer i : customersId) {
            optionsList.add(Integer.toString(i));
        }
        ObservableList<String> options = FXCollections.observableArrayList(optionsList);
        /*Dropdown to search customer by id*/
        this.idDropDown = new ComboBox<>(options);
        this.idDropDown.setId("idDropDown");
        this.idDropDown.setPromptText("Customer ID");
        /*Button to save bill*/
        this.saveButton = new Button("Save Bill");
        this.payButton = new Button("Pay Bill");



        /*Get Products*/
        listProducts = new Products();
        listProducts = controller.getProducts();

        itemsLayout = new GridPane();
        itemsLayout.addColumn(5);
        itemsLayout.setHgap(15);
        itemsLayout.setVgap(15);
        int rowCount = 0;
        int colCount = 0;

        for(Product a: listProducts.getProducts()){
            itemsLayout.addRow(10);
            String getImageUrl = a.imagePath;
            Image placeHolderImg = new Image(getImageUrl);
            ImageView placeImg = new ImageView();
            placeImg.setImage(placeHolderImg);
            placeImg.setFitHeight(150);
            placeImg.setFitWidth(150);

            String getProductName = a.productName;
            Label textHolder = new Label(getProductName);
            textHolder.setId("textItem");
            /*Per item layout*/
            itemLayout = new VBox();
            itemLayout.getChildren().addAll(placeImg, textHolder);
            itemLayout.setSpacing(10);
            /*Col = 0 Row = 0*/
            itemsLayout.add(itemLayout, colCount, rowCount);
            colCount++;
            if(colCount >= 5){
                colCount = 0;
                rowCount++;
            }
        }

        /*Button layout displaying side by side*/
        buttonLayout = new HBox();
        buttonLayout.getChildren().addAll(this.saveButton, this.payButton);
        buttonLayout.setSpacing(100);

        /*Layout to display shopping cart*/
        invoiceLayout = new VBox();
        invoiceLayout.setId("invoiceLayout");

        /*Left layout containing dropdown id, list of printed items, button*/
        leftLayout = new VBox();
        leftLayout.setId("leftLayout");
        leftLayout.getChildren().addAll(this.idDropDown, invoiceLayout, buttonLayout);
        leftLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
        leftLayout.setPrefWidth(500);
        leftLayout.setPrefHeight(600);
        leftLayout.setSpacing(10);

        final Label[] previousLabel = {null};

        customerBill = new Bills();
        customerBill = controller.getBills();

        idDropDown.setOnAction(event -> {
            String selectedOption = idDropDown.getValue();
            Integer idInt = Integer.parseInt(selectedOption);
            Bill b = customerBill.getBillByID(idInt);

            Label newLabel = new Label("tes");

            if (previousLabel[0] != null) {
                invoiceLayout.getChildren().remove(previousLabel[0]);
            }

            invoiceLayout.getChildren().add(newLabel);
            previousLabel[0] = newLabel;
        });

        /*Whole Layout*/
        HBox wholeLayout = new HBox();
        wholeLayout.setFillHeight(true);


        /*Right Layout containing search bar, dropdown, and list of items*/
        VBox rightLayout = new VBox();
        HBox searchLayout = new HBox();
        searchLayout.getChildren().addAll(this.searchBar, this.searchDropDown);
        searchLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
        rightLayout.getChildren().addAll(searchLayout, itemsLayout);
        rightLayout.setSpacing(15);
        searchLayout.setSpacing(15);
        rightLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");


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
