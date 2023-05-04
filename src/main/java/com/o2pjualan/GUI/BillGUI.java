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
    private FixedBills fixedBills;
    private AlertGUI alertGUI;
    public Bill b;
    public FixedBill newBill;
    private HBox totalLayout;
    private HBox pointLayout;
    private Label totalPrice;
    private Label totalPoint;
    private VBox lowerLayout;
    private double pointCount;
    private double totalBill;
    private HBox finalTotalLayout;
    private double finalBillPrice;
    private Label finalBillLabel;
    private double currentPoint;


    public BillGUI(){
        this.setText("Bills");
        customers = controller.getCustomers();
        alertGUI = new AlertGUI();

        listProducts = controller.getProducts();
        bills = controller.getBills();
        fixedBills = controller.getFixedBills();

        /*Layout Dropdown + Button add New Customer*/
        upperLayout = new HBox();

        /*Text Field Input Name*/
        enterName = new TextField();
        enterName.setPromptText("Pick Customer...");
        enterName.setId("searchBar");


        allSugestions = new ArrayList<>();

        customersId = customers.getIdsByMembership("Customer");
        custName = customers.getCustomerName();
        customerNames = new ArrayList<>();

        for(Integer i : customersId){
            customerNames.add(Integer.toString(i));
        }
        invoiceLayout = new VBox();
        invoiceLayout.setId("invoiceLayout");

        totalLayout = new HBox();
        pointLayout = new HBox();
        finalTotalLayout = new HBox();

        Label finalLabel = new Label("Total");
        finalBillLabel = new Label(Double.toString(totalBill - pointCount));
        finalTotalLayout.getChildren().addAll(finalLabel, finalBillLabel);


        lowerLayout = new VBox();
        lowerLayout.getChildren().addAll(totalLayout, pointLayout);

        customerNames.addAll(custName);

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
            alertGUI.alertInformation("Succesfully added new customer " + addCustomer.getIdCustomer());
        });

        this.enterName.setOnAction(e-> {
            String textValue = this.enterName.getText();
            int idCust = checkIdCustomer(textValue);
            wholePriceLayout.getChildren().clear();
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

        this.payButton.setOnAction(e-> {
            String textValue = this.enterName.getText();
            int idCust = checkIdCustomer(textValue);
            checkOut(idCust);
        });

        /*Button layout save button + pay button displaying side by side*/
        buttonLayout = new HBox();
        buttonLayout.getChildren().addAll(this.payButton);


        /*Layout to display shopping cart*/

        /*Set Scroll Bar*/
        scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVmax(1);
        scrollPane.setVvalue(0);
        scrollPane.setPrefViewportHeight(400);
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
        invoiceLayout.getChildren().addAll(scrollPane, totalLayout, pointLayout, finalTotalLayout);
        invoiceLayout.setSpacing(10);
        leftLayout = new VBox();
        leftLayout.setId("leftLayout");
        leftLayout.getChildren().addAll(upperLayout, invoiceLayout, buttonLayout);
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
        int getRowTotal = 0;

        for(Map.Entry<Integer, Integer> entry : listProd.entrySet()){
            for(Product a: listProducts.getProducts()){
                if(a.getProductCode() == entry.getKey()){
                    productGetName = new Label(a.productName);
                    productGetTotal = new Label("x" + entry.getValue().toString());
                    productGetPrice = new Label(Double.toString(entry.getValue() * a.getSellPrice()));
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
        Label total = new Label("Sub Total");
        Label point = new Label("Poin");
        b = bills.getBillByID(idInt);
        totalBill = b.countTotalBill();
        currentPoint = customers.getCurrentPoint(idInt);


        totalPrice = new Label(Double.toString(b.countTotalBill()));
        totalLayout.getChildren().addAll(total, totalPrice);
        totalLayout.setSpacing(20);


        totalPoint = new Label(Double.toString(currentPoint));
        pointLayout.getChildren().addAll(point, totalPoint);
        pointLayout.setSpacing(20);

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

    public void checkOut(int idCust){
        b = bills.getBillByID(idCust);
        newBill = b.checkOutBill();
        fixedBills.addFixedBill(newBill);
        Customer c = customers.getCustomerByID(idCust);
        pointCount = customers.pointCalculation(idCust, totalBill,currentPoint);
        Integer idBill = newBill.getIdBill();
        if(c instanceof Member){
            ((Member) c).reducePoint(currentPoint);
            ((Member) c).addPoint(pointCount);
        } else if(c instanceof VIP){
            ((VIP) c).reducePoint(currentPoint);
            ((VIP) c).addPoint(pointCount);
        }
        c.addFixedBill(idBill);
        controller.saveDataBill(bills);
        controller.saveDataFixedBill(fixedBills);
        controller.saveDataCustomer(customers);
        alertGUI.alertInformation("Successfully checkout");
    }
//    public void displayTotal(int idCust){
//        b = bills.getBillByID(idCust);
//        double pointCount = customers.pointCalculation(idCust, b.countTotalBill());
//
//        Label total = new Label("Sub Total");
//        totalPrice = new Label(Double.toString(b.countTotalBill()));
//        totalLayout.getChildren().addAll(total, totalPrice);
//        totalLayout.setSpacing(15);
//
//
//        Label point = new Label("Poin");
//        totalPoint = new Label("-" + ((pointCount)));
//        pointLayout.getChildren().addAll(point, totalPoint);
//
//        lowerLayout.getChildren().addAll(totalLayout, pointLayout);
//        lowerLayout.setSpacing(15);
//
//    }
}