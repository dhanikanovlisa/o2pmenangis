package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
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
    private Customers customers;
    private Label currentQuantity;
    private ToggleButton addItem;
    private ToggleButton deleteItem;
    private HBox currentQuantityLayout;
    private ArrayList<Integer> customersId;
    private AlertGUI alertGUI;
    private Integer productCode;
    private Integer selectedId;

    public itemtoBill(Integer productCode){
        /*Whole Layout*/
        currentQuantity = new Label("");
        VBox wholeLayout = new VBox();
        wholeLayout.setId("layoutCatalog");
        alertGUI = new AlertGUI();
        this.productCode = productCode;

        /*Dropdown to search customer by id*/
        this.idDropDown = new ComboBox<String>();
        this.idDropDown.setId("idDropDown");
        this.idDropDown.setPromptText("Customer ID");

        updateData();


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

        currentQuantityLayout = new HBox();
        Label current = new Label("Current Quantity");

        current.setId("catalogLabel");
        currentQuantityLayout.getChildren().addAll(current, currentQuantity);
        currentQuantityLayout.setSpacing(15);


        this.addItem = new ToggleButton("Add Item");
        this.deleteItem = new ToggleButton("Delete Item");
        HBox toggleLayout = new HBox();
        toggleLayout.getChildren().addAll(addItem, deleteItem);
        toggleLayout.setSpacing(15);

        editValueLayout.getChildren().addAll(nameLayout, categoryLayout, sellPriceLayout, currentQuantityLayout);
        editValueLayout.setSpacing(20);

        editLayout.getChildren().addAll(editImageLayout, editValueLayout);
        editLayout.setSpacing(20);
        /*Bottom Button Layout*/
        HBox bottomButtonLayout = new HBox();
        this.itemTotal = new TextField();
        itemTotal.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d+")) {
                return change;
            }
            return null;
        }));

        this.saveButton = new Button("Save");
        this.saveButton.setId("buttonCatalog");



        HBox itemDelete = new HBox();
        itemDelete.getChildren().addAll(this.itemTotal, toggleLayout);
        itemDelete.setSpacing(20);

        bottomButtonLayout.getChildren().addAll(itemDelete, this.saveButton);
        bottomButtonLayout.setSpacing(450);
        wholeLayout.getChildren().addAll(this.idDropDown, editLayout, bottomButtonLayout);
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");


        Pane base = new Pane();
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);

        base.getChildren().add(wholeLayout);
        this.setContent(base);

        this.setOnSelectionChanged(event ->  {
            if (this.isSelected()) {
                updateData();
            }
        });

        this.saveButton.setOnAction(e -> {
            try {
                if (selectedId != null) {
                    addToBill(productCode, selectedId.toString());

                }
            } catch (IOException | ParseException err) {
                throw new RuntimeException(err);
            }
        });

        this.idDropDown.setOnAction(event ->{
            updateData();
        });
    }

    public void addToBill(int productCode, String customerId) throws  IOException, ParseException{
        if(this.idDropDown.getValue() == null){
            alertGUI.alertWarning("You haven't chose the customer");
        } else {
            Integer custId = Integer.parseInt(customerId);
            bills = controller.getBills();
            Bill customerBill = bills.getBillByID(custId);
            if(itemTotal.getText().equals("") || itemTotal.getText().equals(null)){
                alertGUI.alertWarning("You haven't specified how many item");
            } else {
                String quantity = itemTotal.getText();
                Integer stock = Integer.parseInt(quantity);
                Product getterProduct = new Product();
                for(Product a: listProducts.getProducts()){
                    if(a.getProductCode() == productCode)
                        getterProduct = a;
                }

                if(addItem.isSelected() && !deleteItem.isSelected()){
                    customerBill.addProduct(productCode, stock, getterProduct.getSellPrice() * stock);
                    if(getterProduct.reduceStock(stock)){
                        controller.saveDataBill(bills);
                        controller.saveDataProduct(listProducts);
                        alertGUI.alertInformation("Successfully added " + stock + " " +  getterProduct.getProductName() +" to customer " + custId);
                    } else {
                        alertGUI.alertWarning("Not enough stock");
                    }
                } else if (deleteItem.isSelected() && !addItem.isSelected()){
                    if(customerBill.validateDeleteProduct(productCode, stock)){
                        customerBill.deleteProduct(productCode, stock, getterProduct.getSellPrice() * stock);
                        getterProduct.setStock(getterProduct.getStock() + stock);
                        controller.saveDataBill(bills);
                        controller.saveDataProduct(listProducts);
                        alertGUI.alertInformation("Successfully deleted " + stock + " " + getterProduct.getProductName() +" to customer " + custId);
                    } else {
                        alertGUI.alertWarning("Invalid");
                    }
                } else if (!addItem.isSelected() && !deleteItem.isSelected()){
                    alertGUI.alertWarning("You haven't chose whether to add or delete the item");
                } else if(addItem.isSelected() && deleteItem.isSelected()){
                    alertGUI.alertWarning("You cannot choose both");
                }
                System.out.println(("DONE"));
                setTextField(customerBill, productCode);
            }
        }
    }

    public void setTextField(Bill bill, Integer productCode){
        boolean found = false;
        for(Map.Entry<Integer, Integer> product : bill.getListOfProduct().entrySet()){
            if(product.getKey() == productCode){
                currentQuantity.setText(Integer.toString(product.getValue()));
                found = true;
            }
        }
        if (!found) {
            currentQuantity.setText("0");
        }
    }

    public void updateData () {
        listProducts = new Products();
        listProducts = controller.getProducts();
        bills = controller.getBills();
        customers = controller.getCustomers();
        customersId = customers.getCustomersId();
        optionsList = new ArrayList<>();
        for (Integer i : customersId) {
            optionsList.add('(' + i.toString() + ") "  + customers.getCustomerNameById(i));
        }
        ObservableList<String> options = FXCollections.observableArrayList(optionsList);
        this.idDropDown.setItems(options);

        getProd = new Product();
        getProd = listProducts.getProductById(productCode);
        this.setText("Add " + getProd.getProductName());


        if (this.idDropDown.getValue() != null) {
            String str = this.idDropDown.getValue().toString();
            int startIndex = str.indexOf("(") + 1;
            int endIndex = str.indexOf(")");
            String digits = str.substring(startIndex, endIndex);
            selectedId = Integer.parseInt(digits);
            Bill custBill = bills.getBillByID(selectedId);
            setTextField(custBill, productCode);
        } else {
            currentQuantity.setText("");
        }

    }
}

//this.idDropDown.setOnAction(event ->{
//        if (this.idDropDown.getValue() != null) {
//        String str = this.idDropDown.getValue().toString();
//        int startIndex = str.indexOf("(") + 1;
//        int endIndex = str.indexOf(")");
//        String digits = str.substring(startIndex, endIndex);
//        selectedId = Integer.parseInt(digits);
//        Bill custBill = bills.getBillByID(selectedId);
//        currentQuantity.setText("0");
//        setTextField(custBill, productCode);
//
//        }
//        })
