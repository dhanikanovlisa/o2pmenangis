package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.o2pjualan.Main.controller;

public class BillGUI extends Tab {
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
    private ObservableList<String> sugg;
    private Label productGetName;
    private Label productGetPrice;
    private Label productGetTotal;
    private Button addNewCustomer;
    private Products listProducts;
    private GridPane wholePriceLayout;
    private ArrayList<String> optionsList;
    private AutoCompletionBinding<String> autoCompleteText;
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
    private Label discLabel;
    private Label rewardLabel;
    private VBox detailWrapper;
    private Customer selectedCust;
    private Double discount;
    private Double finalPrice;


    public BillGUI() {
        this.setText("Bills");
        alertGUI = new AlertGUI();
        Pane base = new Pane();
        VBox wrapper = new VBox();
        detailWrapper = new VBox();
        wrapper.prefWidthProperty().bind(base.widthProperty());
        wrapper.prefHeightProperty().bind(base.heightProperty());



        /*Layout Dropdown + Button add New Customer*/
        upperLayout = new HBox();


        /*Text Field Input Name*/
        enterName = new TextField();
        enterName.setPromptText("Pick Customer...");
        enterName.setId("searchBarBill");
        enterName.setMinWidth(300);

        customers = controller.getCustomers();
        listProducts = controller.getProducts();
        bills = controller.getBills();
        fixedBills = controller.getFixedBills();

        allSugestions = new ArrayList<>();

        customersId = customers.getIdsByMembership("Customer");
        custName = customers.getCustomerName();
        customerNames = new ArrayList<>();

        for (Integer i : customersId) {
            customerNames.add(Integer.toString(i));
        }

        invoiceLayout = new VBox();
        invoiceLayout.setId("invoiceLayout");

        totalLayout = new HBox();
        pointLayout = new HBox();
        finalTotalLayout = new HBox();

        totalPrice = new Label();
        discLabel = new Label();
        totalPoint = new Label();
        rewardLabel = new Label();
        finalBillLabel = new Label();
        detailWrapper.getChildren().addAll(totalPrice, discLabel, totalPoint, rewardLabel, finalBillLabel);

        customerNames.addAll(custName);

        sugg = FXCollections.observableArrayList(customerNames);
        autoCompleteText = TextFields.bindAutoCompletion(enterName, sugg);
        autoCompleteText.setVisibleRowCount(5);


        /*Adding New Customer*/
        addNewCustomer = new Button("+ Add Customer");
        addNewCustomer.setStyle("-fx-pref-width: 150; -fx-pref-height: 20;");

        this.addNewCustomer.setOnAction(e -> {
            Customer addCustomer = new Customer(0);
            customers = controller.getCustomers();
            customers.addCustomer(addCustomer);
            Bill b1 = new Bill(addCustomer.getIdCustomer());
            bills = controller.getBills();
            bills.addBill(b1);
            controller.saveDataCustomer(customers);
            controller.saveDataBill(bills);
            alertGUI.alertInformation("Succesfully added new customer " + addCustomer.getIdCustomer());
            updateData();
        });

        this.enterName.setOnAction(e -> {
            String textValue = this.enterName.getText();
            int idCust = checkIdCustomer(textValue);
            wholePriceLayout.getChildren().clear();
            if (idCust != -1) {
                selectedCust = controller.getCustomers().getCustomerByID(idCust);
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
        upperLayout.setSpacing(10);
        this.payButton.setOnAction(e -> {
            String textValue = this.enterName.getText();
            int idCust = checkIdCustomer(textValue);
            if (idCust != -1) {
                checkOut(idCust);
            }
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
        invoiceLayout.getChildren().addAll(scrollPane, detailWrapper);
        invoiceLayout.setSpacing(10);
        leftLayout = new VBox();
        leftLayout.setStyle("-fx-background-color: e6f0d1;");
        leftLayout.setId("leftLayout");
        leftLayout.getChildren().addAll(upperLayout, invoiceLayout, buttonLayout);
        leftLayout.setMaxWidth(500);
        leftLayout.setPrefHeight(600);
        leftLayout.setSpacing(10);

        wrapper.getChildren().add(leftLayout);
        wrapper.setAlignment(Pos.CENTER);
        base.getChildren().add(wrapper);
        this.setContent(base);

        this.setOnSelectionChanged(event -> {
            if (this.isSelected()) {
                updateData();
            }
        });
    }

    public void loadDataCust(Integer id) {

//        if (!isEmpty) {
        totalBill = bills.getBillByID(id).getTotalBill();
        currentPoint = customers.getCurrentPoint(id);
        selectedCust = controller.getCustomers().getCustomerByID(id);
        String membership = selectedCust.getMembership();
        boolean isActive = false;
        if (membership.equals("VIP")) {
            isActive = ((VIP) selectedCust).getStatusMembership();
        } else if (membership.equals("Member")) {
            isActive = ((Member) selectedCust).getStatusMembership();
        }

        pointCount = 0.0;
        discount = 0.0;
        if (isActive) {
            pointCount = totalBill*0.01;
            if (selectedCust.getMembership().equals("VIP")) {
                discount = totalBill * 0.1;
            }
        } else {
            currentPoint = 0.0;
        }

        finalPrice = totalBill - discount;
        totalPrice.setText("Sub Total     : " + Double.toString(totalBill));
        discLabel.setText("Disc          : " + Double.toString(discount));
        totalPoint.setText("Point            : " + Double.toString(currentPoint));
        rewardLabel.setText("RewardPoint   : " + Double.toString(pointCount));
        Double discPoint = currentPoint;
        if (discPoint > finalPrice) {
            finalPrice = 0.0;
        } else {
            finalPrice -= discPoint;
        }
        finalBillLabel.setText("Total           : " + Double.toString(finalPrice ));

    }

    public void displayBill(int idInt) {
        int rowCount = 0;
        Bill b = bills.getBillByID(idInt);
        HashMap<Integer, Integer> listProd = b.getListOfProduct();
        wholePriceLayout.addRow(listProd.size());
        int getRowTotal = 0;

        boolean isEmpty = true;
        for (Map.Entry<Integer, Integer> entry : listProd.entrySet()) {
            for (Product a : listProducts.getProducts()) {
                if (a.getProductCode() == entry.getKey()) {
                    if (entry.getValue() > 0) {
                        isEmpty = false;
                        HBox itemWrapper = new HBox();
                        itemWrapper.setMinWidth(500);
                        HBox nameWrapper = new HBox();
                        nameWrapper.setMinWidth(200);
                        HBox priceWrapper = new HBox();
                        productGetName = new Label(a.productName);
                        productGetTotal = new Label("x" + entry.getValue().toString() + "   ");
                        productGetPrice = new Label(Double.toString(entry.getValue() * a.getSellPrice()));
                        nameWrapper.getChildren().add(productGetName);
                        priceWrapper.getChildren().addAll(productGetTotal, productGetPrice);
                        itemWrapper.getChildren().addAll(nameWrapper, priceWrapper);
                        wholePriceLayout.add(itemWrapper, 0, rowCount);
                        rowCount++;
                    }
                }
            }

        totalPrice = new Label(Double.toString(b.getTotalBill()));
        totalLayout.getChildren().addAll(total, totalPrice);
        totalLayout.setSpacing(20);

        }

        if (!isEmpty) {
            loadDataCust(idInt);
        } else {
            totalPrice.setText("Sub Total     : ");
            discLabel.setText("Disc          : " );
            totalPoint.setText("Point            : ");
            rewardLabel.setText("RewardPoint   : " );
            finalBillLabel.setText("Total           : ");
        }
    }

    public int checkIdCustomer(String textValue) {
        int idCust = -1;
        if (textValue.matches("\\d+")) {
            idCust = Integer.parseInt(textValue);
            customersId = customers.getIdsByMembership("Customer");
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

    public void checkOut(int idCust) {
        b = bills.getBillByID(idCust);
        if (b != null && this.totalBill > 0) {
            boolean isActive = false;
            Customer c = customers.getCustomerByID(idCust);
            if (c instanceof VIP) {
                isActive = ((VIP) c).getStatusMembership();
            } else if (c instanceof Member) {
                isActive = ((Member) c).getStatusMembership();
            }


            pointCount = totalBill*0.01;
            System.out.println("point checkout" + pointCount);
            Double pointDisc = 0.0;
            if (isActive) {
                if (currentPoint <= totalBill) {
                    pointDisc = currentPoint;
                } else {
                    pointDisc = totalBill;
                }

                if (c instanceof Member) {
                    ((Member) c).reducePoint(pointDisc);
                    ((Member) c).addPoint(pointCount);
                } else if (c instanceof VIP) {
                    ((VIP) c).reducePoint(pointDisc);
                    ((VIP) c).addPoint(pointCount);
                }
            }

            newBill = b.checkOutBill(pointDisc, c.getMembership(), isActive);
            fixedBills.addFixedBill(newBill);
            Integer idBill = newBill.getIdBill();
            c.addFixedBill(idBill);
            controller.saveDataBill(bills);
            controller.saveDataFixedBill(fixedBills);
            controller.saveDataCustomer(customers);
            alertGUI.alertInformation("Successfully checkout");
            clearBill();
        }
    }

    public void clearBill() {
        totalPrice.setText("");
        discLabel.setText("");
        totalPoint.setText("");
        rewardLabel.setText("");
        finalBillLabel.setText("");

       wholePriceLayout.getChildren().clear();

        totalBill = 0.0;
        discount = 0.0;
        currentPoint = 0.0;
        pointCount = 0.0;
        finalPrice = 0.0;
    }

    public void updateData() {
        customers = controller.getCustomers();
        listProducts = controller.getProducts();
        bills = controller.getBills();
        fixedBills = controller.getFixedBills();

        allSugestions = new ArrayList<>();

        customersId = customers.getIdsByMembership("Customer");
        custName = customers.getCustomerName();
        customerNames = new ArrayList<>();

        for(Integer i : customersId){
            customerNames.add(Integer.toString(i));
        }

        this.enterName.setOnAction(e -> {
            String textValue = this.enterName.getText();
            int idCust = checkIdCustomer(textValue);
            wholePriceLayout.getChildren().clear();
            if (idCust != -1) {
                displayBill(idCust);
            }
        });

        customerNames.addAll(custName);

        sugg = FXCollections.observableArrayList(customerNames);
        autoCompleteText = TextFields.bindAutoCompletion(enterName, sugg);
        autoCompleteText.setVisibleRowCount(5);
    }
}