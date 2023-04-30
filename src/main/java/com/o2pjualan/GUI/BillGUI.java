package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.o2pjualan.Main.controller;

public class BillGUI extends Tab{
    private VBox leftLayout;
    private HBox buttonLayout;
    private VBox invoiceLayout;
    private Button saveButton;
    private Button payButton;
    private ComboBox<String> idDropDown;
    public Customers customers;
    public Bills customerBill;
    public ArrayList<Label> previousLabel;
    private HBox priceBillLayout;
    private Label productGetName;
    private Label productGetPrice;
    private Button addNewCustomer;
    private Products listProducts;
    public BillGUI(){
        this.setText("Bills");
        customers = new Customers();
        customers = controller.getCustomers();
        HBox upperLayout = new HBox();

        addNewCustomer = new Button("+ Add Customer");

        Bills bills = controller.getBills();
        ArrayList<Integer> customersId = customers.getCustomersId();
        ArrayList<Bill> listBills = bills.getBills();
        ArrayList<Integer> validId = new ArrayList<>();
        ArrayList<String> optionsList = new ArrayList<String>();
        for (Integer i : validId) {
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
        upperLayout.getChildren().addAll(this.idDropDown, addNewCustomer);
        /*Button layout displaying side by side*/
        buttonLayout = new HBox();
        buttonLayout.getChildren().addAll(this.saveButton, this.payButton);


        /*Layout to display shopping cart*/
        invoiceLayout = new VBox();
        invoiceLayout.setId("invoiceLayout");

        priceBillLayout = new HBox();
        priceBillLayout.setSpacing(15);
        priceBillLayout.setPadding(new Insets(10));
        listProducts = new Products();
        listProducts = controller.getProducts();
        idDropDown.setOnAction(event -> {
            String selectedOption = idDropDown.getValue();
            Integer idInt = Integer.parseInt(selectedOption);
            Bill b = bills.getBillByID(idInt);
            HashMap<Integer, Integer> listProd = b.getListOfProduct();
            HashMap<Integer, Integer> listPriceProd = b.getListPriceOfProduct();
            for(Map.Entry<Integer, Integer> entry: listProd.entrySet()){
                for(Product a: listProducts.getProducts()){
                    if(a.getProductCode() == entry.getKey()){
                        productGetName = new Label(a.productName);
                        productGetPrice = new Label(entry.getValue().toString());
                        priceBillLayout.getChildren().addAll(productGetName, productGetPrice);
                    }
                }
            }

        });

        /*Left layout containing dropdown id, list of printed items, button*/
        leftLayout = new VBox();
        leftLayout.setId("leftLayout");
        leftLayout.getChildren().addAll(upperLayout,invoiceLayout, buttonLayout);
        leftLayout.setPrefWidth(500);
        leftLayout.setPrefHeight(600);
        leftLayout.setSpacing(10);

        Pane base = new Pane();
        base.getChildren().add(leftLayout);
        this.setContent(base);
    }

}
