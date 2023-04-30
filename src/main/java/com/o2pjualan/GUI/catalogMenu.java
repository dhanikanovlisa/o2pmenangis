package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
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
import static com.o2pjualan.Main.main;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class catalogMenu extends Tab {
    private Products listProducts;
    public Bills customerBill;
    private TextField searchBar;
    private ComboBox<String> searchDropDown;
    private ImageView placeImg;
    private Label textHolder;
    private ScrollPane scrollPane;

    private VBox itemLayout;
    private GridPane itemsLayout;
    private Button addItem;
    private VBox leftLayout;
    private ArrayList<String> allSugestions;
    private TabPane mainTabPane;

    public catalogMenu(TabPane mainTabPane) throws IOException {
        /*Set Tab Name*/
        this.setText("Catalog");
        this.mainTabPane = mainTabPane;

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



        /*Get Products*/
        listProducts = new Products();
        listProducts = controller.getProducts();
        allSugestions = new ArrayList<>();
        for(Product product: listProducts.getProducts()){
            allSugestions.add(product.getProductName());
        }
        ObservableList<String> sugg = FXCollections.observableArrayList(allSugestions);
        AutoCompletionBinding<String> autoCompleteText = TextFields.bindAutoCompletion(searchBar, sugg);
        autoCompleteText.setVisibleRowCount(5);

        itemsLayout = new GridPane();
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
            displayItem(searchDropDown.getValue(), searchBar.getText());
        } catch(IOException error){
            throw new RuntimeException(error);
        }
        searchDropDown.setOnAction(event ->{
            String value = searchDropDown.getValue();
            itemsLayout.getChildren().clear();
            searchBar.setOnKeyTyped(e->{
                try{
                    itemsLayout.getChildren().clear();
                    displayItem(value, searchBar.getText());
                } catch(IOException err){
                    throw new RuntimeException(err);
                }
            });
        });

        leftLayout = new VBox();
        leftLayout.setId("leftLayout");
        leftLayout.setPrefWidth(500);
        leftLayout.setPrefHeight(600);
        leftLayout.setSpacing(10);

        customerBill = new Bills();
        customerBill = controller.getBills();
        this.addItem = new Button("+ Add Item");
        this.addItem.setId("buttonItem");

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


        wholeLayout.getChildren().addAll(rightLayout);
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
    public void displayItem(String value, String textValue) throws IOException{
        int rowCount = 0;
        int colCount = 0;

        for (Product a : listProducts.getProducts()) {
            String category = a.productCategory;
            String getImageUrl = a.imagePath;
            String getProductName = a.productName;
            if (value.equals("All") || value.equals("")) {
                if(textValue.equals("") || textValue.equals(null)){
                    addItem(getProductName, getImageUrl, rowCount, colCount);
                    colCount++;
                    if (colCount >= 5) {
                        colCount = 0;
                        rowCount++;
                    }
                } else if (textValue.contains(a.productName)) {
                    addItem(getProductName, getImageUrl, rowCount, colCount);
                    colCount++;
                    if (colCount >= 5) {
                        colCount = 0;
                        rowCount++;
                    }
                }
            } else if(value.equals(category)){;
                if(textValue.equals("") || textValue.equals(null)){
                    addItem(getProductName, getImageUrl, rowCount, colCount);
                    colCount++;
                    if (colCount >= 5) {
                        colCount = 0;
                        rowCount++;
                    }
                } else if(textValue.contains(a.productName)){
                    addItem(getProductName, getImageUrl, rowCount, colCount);
                    colCount++;
                    if (colCount >= 5) {
                        colCount = 0;
                        rowCount++;
                    }
                }
            }
        }
    }
    public void addItem(String getProductName, String getImageUrl, int rowCount, int colCount){
        Image placeHolderImg = new Image(getImageUrl);
        placeImg = new ImageView();
        placeImg.setImage(placeHolderImg);
        placeImg.setFitHeight(120);
        placeImg.setFitWidth(120);

        textHolder = new Label(getProductName);
        textHolder.setId("textItem");
        /*Per item layout*/
        itemLayout = new VBox();
        itemLayout.getChildren().addAll(placeImg, textHolder);
        itemLayout.setSpacing(10);
        /*Col = 0 Row = 0*/
        itemsLayout.add(itemLayout, colCount, rowCount);
        itemLayout.setOnMouseClicked(e -> {
            editCatalogMenu editCatalogTab = new editCatalogMenu();
            mainTabPane.getTabs().add(editCatalogTab);
        });
    }

}
