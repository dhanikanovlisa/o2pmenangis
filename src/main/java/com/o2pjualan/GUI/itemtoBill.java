package com.o2pjualan.GUI;

import com.o2pjualan.Classes.Bill;
import com.o2pjualan.Classes.Bills;
import com.o2pjualan.Classes.Product;
import com.o2pjualan.Classes.Products;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.o2pjualan.Main.controller;

public class itemtoBill extends Tab {
    private Bills bills;
    private Button saveButton;
    private Image imageItem;
    private ImageView imageView;
    private Label nameTextField;
    private Label categoryTextField;
    private Label sellPriceTextField;
    private Products listProducts;
    private Product getProd;
    private TextField itemTotal;
    private ComboBox idDropDown;
    private ObservableList<String> options;
    private ArrayList<String> optionsList;
    private Label warning;
    public itemtoBill(Integer productCode){
        /*Whole Layout*/
        VBox wholeLayout = new VBox();
        wholeLayout.setId("layoutCatalog");
        listProducts = new Products();
        listProducts = controller.getProducts();
        bills = controller.getBills();
        ArrayList<Integer> customersId = bills.getBillCustomerID();
        optionsList = new ArrayList<String>();
        for (Integer i : customersId) {
            optionsList.add(Integer.toString(i));
        }
        options = FXCollections.observableArrayList(optionsList);

        /*Dropdown to search customer by id*/
        this.idDropDown = new ComboBox<>(options);
        this.idDropDown.setId("idDropDown");
        this.idDropDown.setPromptText("Customer ID");

        getProd = new Product();
        getProd = listProducts.getProductById(productCode);
        this.setText("Add " + getProd.getProductName());

        /*Edit Whole Item Layout*/
        HBox editLayout = new HBox();

        /*Edit Image Layout*/
        VBox editImageLayout = new VBox();
        this.imageItem = new Image(getProd.getImagePath());

        imageView = new ImageView();
        imageView.setImage(imageItem);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);


        editImageLayout.getChildren().addAll(imageView);
        editImageLayout.setAlignment(Pos.CENTER);
        editImageLayout.setSpacing(20);

        /*Edit Value Layout*/
        VBox editValueLayout = new VBox();
        HBox nameLayout = new HBox();
        Label nameText = new Label("Name");
        nameText.setId("catalogLabel");
        this.nameTextField = new Label(getProd.getProductName());
        nameLayout.getChildren().addAll(nameText, this.nameTextField);
        nameLayout.setSpacing(15);

        HBox categoryLayout = new HBox();
        Label categoryText = new Label("Category");
        categoryText.setId("catalogLabel");
        this.categoryTextField = new Label(getProd.getProductCategory());
        categoryLayout.getChildren().addAll(categoryText, this.categoryTextField);
        categoryLayout.setSpacing(15);

        HBox sellPriceLayout = new HBox();
        Label sellPriceText = new Label("Sell Price");
        sellPriceText.setId("catalogLabel");
        this.sellPriceTextField = new Label(Double.toString(getProd.getSellPrice()));
        sellPriceLayout.getChildren().addAll(sellPriceText, this.sellPriceTextField);
        sellPriceLayout.setSpacing(15);

        editValueLayout.getChildren().addAll(nameLayout, categoryLayout, sellPriceLayout);
        editValueLayout.setSpacing(20);

        editLayout.getChildren().addAll(editImageLayout, editValueLayout);
        editLayout.setSpacing(20);
        /*Bottom Button Layout*/
        HBox bottomButtonLayout = new HBox();
        this.itemTotal = new TextField();
        itemTotal.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));

        this.saveButton = new Button("Save");
        this.saveButton.setId("buttonCatalog");


        this.idDropDown.setOnAction(event ->{
            String getCustomerId = this.idDropDown.getValue().toString();
            this.saveButton.setOnAction(e -> {
                try{
                    addToBill(productCode, getCustomerId);
                }catch (IOException | ParseException err){
                    throw new RuntimeException(err);
                }
            });
        });
//
        warning = new Label("");
        bottomButtonLayout.getChildren().addAll(this.itemTotal, this.saveButton, warning);
        bottomButtonLayout.setSpacing(450);
        wholeLayout.getChildren().addAll(this.itemTotal, editLayout, bottomButtonLayout);
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");


        Pane base = new Pane();
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);

        base.getChildren().add(wholeLayout);
        this.setContent(base);
    }

    public void addToBill(int productCode, String customerId) throws  IOException, ParseException{
        if(customerId.equals("") || customerId.equals(null)){
            warning = new Label("You have not yet chosen the customer");
            warning.setStyle("-fx-prompt-text-fill: red;");
        } else {
            Integer custId = Integer.parseInt(customerId);
            Bill customerBill = bills.getBillByID(custId);
            if(itemTotal.getText().equals("") || itemTotal.getText().equals(null)){
                warning = new Label("You have not yet specifiy how much item");
                warning.setStyle("-fx-prompt-text-fill: red;");
            } else {
                String quantity = itemTotal.getText();
                Integer stock = Integer.parseInt(quantity);
                Product getterProduct = new Product();
                for(Product a: listProducts.getProducts()){
                    if(a.getProductCode() == productCode)
                        getterProduct = a;
                }
                customerBill.AddProduct(productCode, stock, getterProduct.getSellPrice() * stock);
                getterProduct.setStock(getterProduct.getStock() - stock);
                bills.addBill(customerBill);
                controller.saveDataBill(bills);
                controller.saveDataProduct(listProducts);
            }
        }
    }
}
