package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;

import static com.o2pjualan.Main.controller;

public class clickedHistory extends Tab {
    private Button back;
    private Button printBill;
    private HBox buttonLayout;
    private GridPane wholePriceLayout;
    private VBox wholeLayout;
    private FixedBills fixedBills;
    private Customers customers;
    private Products listProducts;

    private Products products;
    private Label productGetName;
    private Label productGetTotal;
    private Label productGetPrice;
    private HBox priceBillLayout;
    private HBox priceOnlyLayout;
    private ScrollPane scrollPane;
    private HBox totalLayout;
    private Label totalPrice;
    public clickedHistory(int idBill){
        this.setText("#" + idBill + " [name]'s");
        this.printBill = new Button("Print Bill");
        this.printBill.setId("buttonClickedHistory");

        fixedBills = controller.getFixedBills();
        customers = controller.getCustomers();
        listProducts = controller.getProducts();


        buttonLayout = new HBox();
        buttonLayout.setId("buttonLayout");
        buttonLayout.setSpacing(900);
        buttonLayout.getChildren().addAll(this.printBill);
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

        int rowCount= 0;
        FixedBill fixBillCustomer = fixedBills.getFixedBillByID(idBill);
        HashMap<Integer, Integer> listProd = fixBillCustomer.getListOfProduct();
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
        totalLayout = new HBox();
        Label total = new Label("Total");
        totalPrice = new Label(Integer.toString(fixBillCustomer.getTotalFixedBill()));
        totalLayout.getChildren().addAll(total, totalPrice);
        totalLayout.setSpacing(900);
        totalLayout.setPadding(new Insets(10));

        wholePriceLayout.add(totalLayout, 0, fixBillCustomer.getListOfProduct().size()+1);

        scrollPane.setContent(wholePriceLayout);

        wholeLayout = new VBox();
        wholeLayout.getChildren().addAll(buttonLayout, scrollPane);
        wholeLayout.setMargin(buttonLayout, new Insets(10,0,0,0));
        wholeLayout.setMargin(scrollPane, new Insets(20, 0, 0, 0));
        wholeLayout.setId("wholeLayout");

        Pane base = new Pane();
        base.getChildren().add(wholeLayout);
        this.setContent(base);
        wholeLayout.setFillWidth(true);
        wholeLayout.prefWidthProperty().bind(base.widthProperty());
        wholeLayout.prefHeightProperty().bind(base.heightProperty());
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
    }
}
