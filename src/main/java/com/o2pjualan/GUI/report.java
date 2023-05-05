package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
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
    private Double totalPrice;
    private Label totalPriceLabel;

    public report() {
        this.setText("Report");
        Pane base = new Pane();
        VBox wrapper = new VBox();
        wrapper.prefWidthProperty().bind(base.widthProperty());
        wrapper.prefHeightProperty().bind(base.heightProperty());
        totalPrice = 0.0;
        totalPriceLabel = new Label("");
        HBox upperLayout = new HBox();
//        upperLayout.setStyle("-fx-background-color: purple");
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
        scrollPane.setPrefViewportWidth(900);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(dataSalesLayout);
        scrollPane.setId("scrollCatalog");

        int rowCount = 0;
        HashMap<Integer, Integer> sales = fixedBills.salesReport();

        for(Map.Entry<Integer, Integer> entry : sales.entrySet()){
            for(Product a: listProducts.getProducts()){
                if(a.getProductCode() == entry.getKey()){

                    HBox leftBox = new HBox();
                    productGetName = new Label(a.productName);
                    leftBox.getChildren().add(productGetName);
                    leftBox.setAlignment(Pos.CENTER_LEFT);
                    productGetTotal = new Label("x" + entry.getValue().toString());
                    productGetPrice = new Label(Double.toString(entry.getValue() * a.getBuyPrice()));
                    this.totalPrice += entry.getValue() * a.getBuyPrice();
                    /*Layout Bill
                     * Labu Erlenmeyer           x2      150000
                     * */

                    priceBillLayout = new HBox();
//                    priceBillLayout.setStyle("-fx-background-color: green;");
                    priceBillLayout.setMinWidth(900);
                    priceBillLayout.setPadding(new Insets(10));
                    priceOnlyLayout = new HBox();
                    priceOnlyLayout.setSpacing(10);
                    priceOnlyLayout.setPadding(new Insets(10));
                    leftBox.setPrefWidth(450);
                    priceOnlyLayout.setPrefWidth(450);


                    priceOnlyLayout.getChildren().addAll(productGetTotal, productGetPrice);
//                    priceOnlyLayout.setStyle("-fx-background-color: yellow;");

                    /*Whole customers products
                     * Labu Erlenmeyer           x2      150000
                     * Labu Erlenmeyer           x2      150000
                     * Labu Erlenmeyer           x2      150000
                     * */
                    priceBillLayout.getChildren().addAll(leftBox, priceOnlyLayout);
                    dataSalesLayout.add(priceBillLayout, 0, rowCount);
                    rowCount++;
                }
            }
        }

        if (this.totalPrice != 0) {
            HBox wrapperPrice = new HBox();
            wrapperPrice.setStyle("-fx-background-color: purple;");
            totalPriceLabel.setText("Total: "  + totalPrice.toString());
            wrapperPrice.getChildren().add(totalPriceLabel);
            wrapperPrice.setAlignment(Pos.CENTER_RIGHT);
            wrapperPrice.setStyle("-fx-padding: 10;");
            dataSalesLayout.add(wrapperPrice, 0, rowCount);
        }

        VBox wholeLayout = new VBox();
        wholeLayout.setMaxWidth(950);
        wholeLayout.getChildren().addAll(upperLayout, scrollPane);
        wholeLayout.setPadding(new Insets(10));
        wholeLayout.setId("reportLayout");
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
        wrapper.getChildren().add(wholeLayout);
        wrapper.setAlignment(Pos.CENTER);
        base.getChildren().add(wrapper);
        this.setContent(base);
    }


}
