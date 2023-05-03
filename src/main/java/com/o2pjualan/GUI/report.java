package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

import static com.o2pjualan.Main.controller;

public class report extends Tab {
    private GridPane dataSalesLayout;
    private ScrollPane scrollPane;
    private Products listProducts;
    private FixedBills fixedBills;
    private Customers customers;
    private Label productGetName;
    private Label productGetTotal;
    private Label productGetPrice;
    private HBox priceBillLayout;
    private HBox priceOnlyLayout;

    public report() {
        this.setText("Report");
        HBox upperLayout = new HBox();
        Label titleReport = new Label("Data Sales");
        titleReport.setId("h1");
        upperLayout.getChildren().add(titleReport);

        customers = controller.getCustomers();
        fixedBills = controller.getFixedBills();
        listProducts = controller.getProducts();

        dataSalesLayout = new GridPane();
        dataSalesLayout.addColumn(1);
        dataSalesLayout.setHgap(15);
        dataSalesLayout.setVgap(15);

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
        scrollPane.setContent(dataSalesLayout);
        scrollPane.setId("scrollCatalog");

        int rowCount = 0;
        Map<Integer, Integer> sales = fixedBills.salesReport();
        for(Map.Entry<Integer, Integer> entry : sales.entrySet()){
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
                    dataSalesLayout.add(priceBillLayout, 0, rowCount);
                    rowCount++;
                }
            }
        }



        VBox wholeLayout = new VBox();
        wholeLayout.getChildren().addAll(upperLayout, scrollPane);
        wholeLayout.setPadding(new Insets(10));
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

        Pane base = new Pane();
        base.getChildren().add(wholeLayout);
        this.setContent(base);

    }


}
