package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.HashMap;
import java.util.Map;

import static com.o2pjualan.Main.controller;

public class clickedHistory extends Tab {
    private Button printBill;
    private HBox buttonLayout;
    private GridPane wholePriceLayout;
    private VBox wholeLayout;
    private FixedBills fixedBills;
    private Customers customers;
    private Products listProducts;

    private Label productGetName;
    private Label productGetTotal;
    private Label productGetPrice;
    private HBox priceBillLayout;
    private HBox priceOnlyLayout;
    private ScrollPane scrollPane;
    private HBox totalLayout;
    private Label totalPrice;
    public clickedHistory(int idFixedBill, String name){
        Pane base = new Pane();
        VBox wrapper = new VBox();
        wrapper.prefWidthProperty().bind(base.widthProperty());
        wrapper.prefHeightProperty().bind(base.heightProperty());
        this.setText("#" + idFixedBill + ": " + name);
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
        wholePriceLayout.setMaxWidth(600);
        wholePriceLayout.setHgap(15);
        wholePriceLayout.setVgap(15);
        wholePriceLayout.addColumn(0);

        int rowCount= 0;
        FixedBill fixBillCustomer = fixedBills.getFixedBillByID(idFixedBill);
        HashMap<Integer, Integer> listProd = fixBillCustomer.getListOfProduct();
        for(Map.Entry<Integer, Integer> entry : listProd.entrySet()){
            for(Product a: listProducts.getProducts()){
                if(a.getProductCode() == entry.getKey()){
                    HBox productNameLayout = new HBox();
                    productGetName = new Label(a.productName);
                    productGetTotal = new Label("x" + entry.getValue().toString());
                    productGetPrice = new Label(Double.toString(entry.getValue() * a.getBuyPrice()));
                    /*Layout Bill
                     * Labu Erlenmeyer           x2      150000
                     * */
                    priceBillLayout = new HBox();
                    priceOnlyLayout = new HBox();
                    priceOnlyLayout.getChildren().addAll(productGetTotal, productGetPrice);
                    /*Whole customers products
                     * Labu Erlenmeyer           x2      150000
                     * Labu Erlenmeyer           x2      150000
                     * Labu Erlenmeyer           x2      150000
                     * */
                    productNameLayout.getChildren().add(productGetName);
                    productNameLayout.setMinWidth(500);
                    priceOnlyLayout.setMinWidth(500);
                    priceBillLayout.getChildren().addAll(productNameLayout, priceOnlyLayout);
                    priceBillLayout.setStyle("-fx-padding: 10, 0, 0, 10");
                    wholePriceLayout.add(priceBillLayout, 0, rowCount);
                    rowCount++;
                }
            }
        }

//        wholeLayout.setId("reportLayout");

        this.printBill.setOnAction(err -> {
            FixedBill printFixedBill = fixedBills.getFixedBillByID(idFixedBill);
            try{
                printFixedBill.printPDF(listProducts);

            } catch (Exception e){
                System.out.println(e);
            }
        });
        totalLayout = new HBox();
        Label total = new Label("Total:      ");
        totalPrice = new Label(Integer.toString(fixBillCustomer.countTotalFixedBill()));
        totalLayout.getChildren().addAll(total, totalPrice);
//        totalLayout.setSpacing(900);
        totalLayout.setPadding(new Insets(10));

        wholePriceLayout.add(totalLayout, 0, fixBillCustomer.getListOfProduct().size()+1);

        scrollPane.setContent(wholePriceLayout);

        wholeLayout = new VBox();
        wholeLayout.setMaxWidth(900);
        wholeLayout.getChildren().addAll(scrollPane, buttonLayout);
        wholeLayout.setMargin(buttonLayout, new Insets(10,0,0,0));
        wholeLayout.setMargin(scrollPane, new Insets(20, 0, 0, 0));
        wholeLayout.setId("wholeLayout");

        wrapper.getChildren().add(wholeLayout);
        wrapper.setAlignment(Pos.CENTER);
        base.getChildren().add(wrapper);
        this.setContent(base);
        wholeLayout.setFillWidth(true);
        wholeLayout.prefWidthProperty().bind(base.widthProperty());
        wholeLayout.prefHeightProperty().bind(base.heightProperty());
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
    }
}
