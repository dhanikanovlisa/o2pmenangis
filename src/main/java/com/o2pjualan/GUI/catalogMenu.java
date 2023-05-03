package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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
    private ArrayList<String> allSugestions;
    private TabPane mainTabPane;
    private ToggleButton toggleButton;


    public catalogMenu(TabPane mainTabPane) throws IOException {
        /*Set Tab Name*/
        this.setText("Catalog");
        Pane base = new Pane();
        this.mainTabPane = mainTabPane;
        /*Get Products*/
        listProducts = new Products();
        listProducts = controller.getProducts();

        /*Search Bar Text Field*/
        this.searchBar = new TextField();
        this.searchBar.setId("searchBar");
        this.searchBar.setPromptText("Search");
        this.searchBar.setPrefWidth(450);
        this.searchBar.setPrefHeight(40);


        /*Dropdown to search by category*/
        this.searchDropDown = new ComboBox<>();

        this.searchDropDown.setId("searchDropDown");
        this.searchDropDown.setPromptText("All");
        this.searchDropDown.setValue("All");

        toggleButton = new ToggleButton("Buy Mode");
        toggleButton.setId("toggle");

        toggleButton.setOnAction(event -> {
            if (toggleButton.isSelected()) {
                toggleButton.setText("Edit Mode");
                toggleButton.setStyle("-fx-background-color: #2f7839;");
            } else {
                toggleButton.setText("Buy Mode");
                toggleButton.setStyle("-fx-background-color: #599962;");
            }
        });



        VBox itemsWholeLayout = new VBox();
//        itemsWholeLayout.setStyle("-fx-background-color: aqua");
        itemsWholeLayout.setAlignment(Pos.TOP_CENTER);
        itemsLayout = new GridPane();
        itemsLayout.addColumn(6);
        itemsLayout.addRow(10);
        itemsLayout.setHgap(50);
        itemsLayout.setVgap(50);
        itemsLayout.setStyle("-fx-background-color: white;");
        itemsLayout.setAlignment(Pos.TOP_CENTER);

        StackPane contentPane = new StackPane();
        contentPane.setAlignment(Pos.TOP_CENTER);
        contentPane.getChildren().add(itemsLayout);


        scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(contentPane);
        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setId("scrollBarCatalog");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVmax(1);
        scrollPane.setVvalue(0);
        scrollPane.setPrefViewportHeight(700);
        scrollPane.setPrefViewportWidth(900);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setId("scrollCatalog");

        contentPane.prefWidthProperty().bind(scrollPane.widthProperty());
        contentPane.prefHeightProperty().bind(scrollPane.heightProperty());


        /*Whole Layout*/
        HBox wholeLayout = new HBox();
        wholeLayout.setStyle("-fx-background-color: #F3FFE7;");
        wholeLayout.setMinHeight(85);
        wholeLayout.setAlignment(Pos.CENTER);
        wholeLayout.setFillHeight(true);

        itemsWholeLayout.getChildren().addAll(scrollPane);
        VBox newFixedLayout = new VBox();
        newFixedLayout.setSpacing(15);
        newFixedLayout.prefHeightProperty().bind(base.heightProperty());
        newFixedLayout.prefWidthProperty().bind(base.widthProperty());

        VBox billLayout = new VBox();
        billLayout.setStyle("-fx-background-color: black");
        billLayout.prefHeightProperty().bind(base.heightProperty());
        billLayout.prefWidthProperty().bind(base.widthProperty().multiply(0.4));

        HBox newLayoutBanget = new HBox();
        newLayoutBanget.prefHeightProperty().bind(base.heightProperty());
        newLayoutBanget.prefWidthProperty().bind(base.widthProperty());

        updateData();

        wholeLayout.getChildren().addAll(this.searchBar, this.searchDropDown, this.addItem, this.toggleButton);
        wholeLayout.setSpacing(30);
        wholeLayout.setPadding(new Insets(20, 20, 20, 20));
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
        newFixedLayout.getChildren().addAll(wholeLayout, itemsWholeLayout);
        newLayoutBanget.getChildren().addAll(newFixedLayout);
        newLayoutBanget.setAlignment(Pos.CENTER);
        base.getChildren().add(newLayoutBanget);
        this.setContent(base);

        this.setOnSelectionChanged(event -> {
            if (this.isSelected()) {
                updateData();
            }
        });

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
            Integer productCode = a.productCode;
            if (value.equals("All") || value.equals("")) {

                if(textValue.equals("") || textValue.equals(null)){
                    addItem(productCode, getProductName, getImageUrl, rowCount, colCount);
                    colCount++;
                    if (colCount >= 6) {
                        colCount = 0;
                        rowCount++;
                    }
                } else if (textValue.contains(a.productName)) {
                    addItem(productCode, getProductName, getImageUrl, rowCount, colCount);
                    colCount++;
                    if (colCount >= 6) {
                        colCount = 0;
                        rowCount++;
                    }
                }
            } else if(value.equals(category)){
                if(textValue.equals("") || textValue.equals(null)){
                    addItem(productCode, getProductName, getImageUrl, rowCount, colCount);
                    colCount++;
                    if (colCount >= 6) {
                        colCount = 0;
                        rowCount++;
                    }
                } else if(textValue.contains(a.productName)){
                    addItem(productCode, getProductName, getImageUrl, rowCount, colCount);
                    colCount++;
                    if (colCount >= 6) {
                        colCount = 0;
                        rowCount++;
                    }
                }
            }
        }
    }
    public void addItem(Integer productCode, String getProductName, String getImageUrl, int rowCount, int colCount){
        Image placeHolderImg = new Image(getImageUrl);
        placeImg = new ImageView();
        placeImg.setImage(placeHolderImg);
        placeImg.setFitHeight(150);
        placeImg.setFitWidth(150);

        textHolder = new Label(getProductName);
        textHolder.setId("textItem");
        /*Per item layout*/
        itemLayout = new VBox();
        itemLayout.getChildren().addAll(placeImg, textHolder);
        itemLayout.setSpacing(25);
        /*Col = 0 Row = 0*/
        itemsLayout.add(itemLayout, colCount, rowCount);
        itemLayout.setOnMouseClicked(e -> {
            if(toggleButton.isSelected()){
                editCatalogMenu editCatalogTab = new editCatalogMenu(productCode);
                mainTabPane.getTabs().add(editCatalogTab);
            } else {
                itemtoBill itemToBillTab = new itemtoBill(productCode);
                mainTabPane.getTabs().add(itemToBillTab);
            }
        });
    }

    public void updateData () {
        listProducts = controller.getProducts();
        ArrayList<String> categoryDrop = listProducts.getAllCategory();
        ObservableList<String> cat = FXCollections.observableArrayList(categoryDrop);
        searchDropDown.setItems(cat);

        try{
            if (searchDropDown.getValue() != null) {
                displayItem(searchDropDown.getValue(), searchBar.getText());
            }
        } catch(IOException error){
            throw new RuntimeException(error);
        }
        searchDropDown.setOnAction(event ->{
            if (searchDropDown.getValue() != null) {
                String value = searchDropDown.getValue();
                itemsLayout.getChildren().clear();
                try {
                    itemsLayout.getChildren().clear();
                    displayItem(value, searchBar.getText());
                } catch (IOException err) {
                    throw new RuntimeException(err);
                }
                searchBar.setOnAction(e -> {
                    try {
                        itemsLayout.getChildren().clear();
                        displayItem(value, searchBar.getText());
                    } catch (IOException err) {
                        throw new RuntimeException(err);
                    }
                });
            }
        });

        customerBill = new Bills();
        customerBill = controller.getBills();
        this.addItem = new Button("+ Add Item");
        this.addItem.setId("buttonItem");
        allSugestions = new ArrayList<>();
        for(Product product: listProducts.getProducts()){
            allSugestions.add(product.getProductName());
        }
        ObservableList<String> sugg = FXCollections.observableArrayList(allSugestions);
        AutoCompletionBinding<String> autoCompleteText = TextFields.bindAutoCompletion(searchBar, sugg);
        autoCompleteText.setVisibleRowCount(6);
    }
}
