package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import com.o2pjualan.Main;
import com.sun.rowset.internal.Row;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import static com.o2pjualan.Main.controller;

public class catalogMenu extends Tab {
    private Main mainPage;
    private Products listProducts;
    public Customers customers;
    public Bills customerBill;
    private TextField searchBar;
    private ComboBox<String> searchDropDown;
    private ComboBox<String> idDropDown;
    private ImageView placeImg;
    private Label textHolder;
    private ScrollPane scrollPane;

    private Button saveButton;
    private Button payButton;
    private Button addItem;
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
        this.searchBar.setPrefWidth(450);
        this.searchBar.setPrefHeight(40);

        /*Dropdown to search by category*/
        this.searchDropDown = new ComboBox<>();
        searchDropDown.setItems(FXCollections.observableArrayList("All",
                "Alat Laboratorium", "Seragam Medis", "Masker"));
        this.searchDropDown.setId("searchDropDown");
        this.searchDropDown.setPromptText("All");
        this.searchDropDown.setValue("All");


        customers = new Customers();
        customers = controller.getCustomers();
        ArrayList<Integer> customersId = customers.getCustomersId();
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

        this.addItem = new Button("+ Add Item");
        this.addItem.setId("buttonItem");





        /*Get Products*/
        listProducts = new Products();
        listProducts = controller.getProducts();

        itemsLayout = new GridPane();
//        RowConstraints row = new RowConstraints(50);
//        ColumnConstraints col = new ColumnConstraints(15);
        itemsLayout.addColumn(5);
        itemsLayout.addRow(10);
        itemsLayout.setHgap(15);
        itemsLayout.setVgap(15);
        itemsLayout.addRow(10);

        scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(itemsLayout);
        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVmax(1);
        scrollPane.setVvalue(0);
        scrollPane.setPrefViewportHeight(500);
        scrollPane.setPrefViewportWidth(400);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(itemsLayout);
        scrollPane.setId("scrollCatalog");

        try{
            displayItem(searchDropDown.getValue());
        } catch(IOException error){
            throw new RuntimeException(error);
        }
        searchDropDown.setOnAction(event ->{
            String value = searchDropDown.getValue();
            itemsLayout.getChildren().clear();
            try{
                displayItem(value);
            } catch(IOException error){
                throw new RuntimeException(error);
            }
        });
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
        searchLayout.getChildren().addAll(this.searchBar, this.searchDropDown, this.addItem);
        rightLayout.getChildren().addAll(searchLayout, scrollPane);
        rightLayout.setSpacing(15);
        searchLayout.setSpacing(15);


        wholeLayout.getChildren().addAll(rightLayout, leftLayout);
        wholeLayout.setSpacing(50);
        wholeLayout.setPadding(new Insets(20, 20, 20, 20));
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

        Pane base = new Pane();
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);

        base.getChildren().add(wholeLayout);
        this.setContent(base);

    }

    public Button addNewItem(){
        return addItem;
    }
    public void displayItem(String value) throws IOException{
        int rowCount = 0;
        int colCount = 0;

        for (Product a : listProducts.getProducts()) {
            String category = a.productCategory;
            if (value.equals("All") || value.equals("")) {
                String getImageUrl = a.imagePath;
                Image placeHolderImg = new Image(getImageUrl);
                placeImg = new ImageView();
                placeImg.setImage(placeHolderImg);
                placeImg.setFitHeight(120);
                placeImg.setFitWidth(120);

                String getProductName = a.productName;
                textHolder = new Label(getProductName);
                textHolder.setId("textItem");
                /*Per item layout*/
                itemLayout = new VBox();
                itemLayout.getChildren().addAll(placeImg, textHolder);
                itemLayout.setSpacing(10);
                /*Col = 0 Row = 0*/
                itemsLayout.add(itemLayout, colCount, rowCount);
                colCount++;
                if (colCount >= 5) {
                    colCount = 0;
                    rowCount++;
                }
            } else if(value.equals(category)){
                String getImageUrl = a.imagePath;
                Image placeHolderImg = new Image(getImageUrl);
                placeImg = new ImageView();
                placeImg.setImage(placeHolderImg);
                placeImg.setFitHeight(120);
                placeImg.setFitWidth(120);

                String getProductName = a.productName;
                textHolder = new Label(getProductName);
                textHolder.setId("textItem");
                /*Per item layout*/
                itemLayout = new VBox();
                itemLayout.getChildren().addAll(placeImg, textHolder);
                itemLayout.setSpacing(10);
                /*Col = 0 Row = 0*/
                itemsLayout.add(itemLayout, colCount, rowCount);
                colCount++;
                if (colCount >= 5) {
                    colCount = 0;
                    rowCount++;
                }
            }
        }
    }


}
