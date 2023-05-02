package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

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
    private Customers customers;
    private Bills bills;
    private ScrollPane scrollPane;
    public ArrayList<Label> previousLabel;
    private HBox upperLayout;
    private HBox priceBillLayout;
    private HBox priceOnlyLayout;
    private Label productGetName;
    private Label productGetPrice;
    private Label productGetTotal;
    private Button addNewCustomer;
    private Products listProducts;
    private GridPane wholePriceLayout;
    private ArrayList<String> optionsList;
    ObservableList<String> options;
    private TextField enterName;
    private ArrayList<String> allSugestions;
    private ArrayList<Integer> customersId;
    private ArrayList<String> custName;
    private ArrayList<String> customerNames;

    public BillGUI(){
        this.setText("Bills");
        customers = new Customers();
        customers = controller.getCustomers();

        listProducts = new Products();
        listProducts = controller.getProducts();
        bills = controller.getBills();

        /*Layout Dropdown + Button add New Customer*/
        upperLayout = new HBox();

        /*Text Field Input Name*/
        enterName = new TextField();
        enterName.setPromptText("Pick Customer...");

        allSugestions = new ArrayList<>();

        customersId = customers.getIdsByMembership("Customer");
        custName = customers.getCustomerName();
        customerNames = new ArrayList<>();

        for(Integer i : customersId){
            customerNames.add(Integer.toString(i));
        }

        customerNames.addAll(custName);
        for(String s: customerNames){
            System.out.println(s);
        }

        ObservableList<String> sugg = FXCollections.observableArrayList(customerNames);
        AutoCompletionBinding<String> autoCompleteText = TextFields.bindAutoCompletion(enterName, sugg);
        autoCompleteText.setVisibleRowCount(5);


        /*Adding New Customer*/
        addNewCustomer = new Button("+ Add Customer");
        this.addNewCustomer.setOnAction( e-> {
            Customer addCustomer = new Customer(0);
            customers.addCustomer(addCustomer);
            Bill b1 = new Bill(addCustomer.getIdCustomer());
            bills.addBill(b1);
            controller.saveDataCustomer(customers);
            controller.saveDataBill(bills);
        });

        this.enterName.setOnAction(e-> {
            String textValue = this.enterName.getText();
            int idCust = checkIdCustomer(textValue);
            wholePriceLayout.getChildren().clear();
            System.out.println(idCust);
            if(idCust != -1){
                displayBill(idCust);
            }
        });


        /*Pay Button
        * TODO:
        *  1. Move bill to fixed bill
        * 2. Update the bill to empty
        * 3. Pay with point too
        * */

        this.payButton = new Button("Check Out");
        upperLayout.getChildren().addAll(enterName, addNewCustomer);

        /*Button layout save button + pay button displaying side by side*/
        buttonLayout = new HBox();
        buttonLayout.getChildren().addAll(this.payButton);


        /*Layout to display shopping cart*/
        invoiceLayout = new VBox();
        invoiceLayout.setId("invoiceLayout");


        /*Set Scroll Bar*/
        scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVmax(1);
        scrollPane.setVvalue(0);
        scrollPane.setPrefViewportHeight(500);
        scrollPane.setPrefViewportWidth(400);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setId("scrollCatalog");
        wholePriceLayout = new GridPane();
        wholePriceLayout.setHgap(15);
        wholePriceLayout.setVgap(15);
        wholePriceLayout.addColumn(0);



        scrollPane.setContent(wholePriceLayout);

        /*Left layout containing dropdown id, list of printed items, button*/
        //invoiceLayout.getChildren().addAll(scrollPane);
        leftLayout = new VBox();
        leftLayout.setId("leftLayout");
        leftLayout.getChildren().addAll(upperLayout, scrollPane, buttonLayout);
        leftLayout.setPrefWidth(500);
        leftLayout.setPrefHeight(600);
        leftLayout.setSpacing(10);

        Pane base = new Pane();
        base.getChildren().add(leftLayout);
        this.setContent(base);
    }

    public void displayBill(int idInt){
        int rowCount = 0;
        Bill b = bills.getBillByID(idInt);
        HashMap<Integer, Integer> listProd = b.getListOfProduct();
        wholePriceLayout.addRow(listProd.size());

        for(Map.Entry<Integer, Integer> entry : listProd.entrySet()){
            for(Product a: listProducts.getProducts()){
                if(a.getProductCode() == entry.getKey()){
                    productGetName = new Label(a.productName);
                    productGetTotal = new Label("x" + entry.getValue().toString());
                    productGetPrice = new Label(Double.toString(entry.getValue() * a.getBuyPrice()));
                    /*Layout Bill
                     * Labu Erlenmeyer           x2      150000
                     * */
                    priceBillLayout = new HBox();
                    if(a.getProductName().length() > 30){
                        priceBillLayout.setSpacing(50);
                    } else {
                        priceBillLayout.setSpacing(70);
                    }
                    priceBillLayout.setPadding(new Insets(10));
                    priceOnlyLayout = new HBox();
                    priceOnlyLayout.setSpacing(10);
                    priceOnlyLayout.setPadding(new Insets(10));
                    priceOnlyLayout.getChildren().addAll(productGetTotal, productGetPrice);

                    /*Whole customers products
                     * Labu Erlenmeyer           x2      150000
                     * Labu Erlenmeyer           x2      150000
                     * Labu Erlenmeyer           x2      150000
                     * */
                    priceBillLayout.getChildren().addAll(productGetName, priceOnlyLayout);
                    wholePriceLayout.add(priceBillLayout, 0, rowCount);
                    rowCount++;
                }
            }
        }

    }
    public int checkIdCustomer(String textValue){
        int idCust = -1;
        if(textValue.matches("\\d+")) {
            idCust = Integer.parseInt(textValue);
            if (customersId.contains(idCust)) {
                return idCust;
            }
        } else {
            idCust = customers.getCustomerIdByName(textValue);
            if (idCust != -1) {
                return idCust;
            }
        }
        return -1;
    }

}
