package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.HashMap;

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
    private SalesReport salesReport;
    private Button printToPDF;
    private AlertGUI alertGUI;

    public report() {
        this.setText("Report");
        Pane base = new Pane();
        VBox wrapper = new VBox();
        wrapper.prefWidthProperty().bind(base.widthProperty());
        wrapper.prefHeightProperty().bind(base.heightProperty());
        totalPrice = 0.0;
        totalPriceLabel = new Label("");
        HBox upperLayout = new HBox();
        Label titleReport = new Label("Data Sales");
        titleReport.setId("h1");
        upperLayout.getChildren().add(titleReport);

        customers = controller.getCustomers();
        fixedBills = controller.getFixedBills();
        listProducts = controller.getProducts();
        salesReport = new SalesReport(fixedBills);

        dataSalesLayout = new GridPane();
        dataSalesLayout.addColumn(1);
        dataSalesLayout.setHgap(15);
        dataSalesLayout.setVgap(15);
        alertGUI = new AlertGUI();

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
        HashMap<String, Integer> sales = salesReport.getListOfAllProductSales();
        for(HashMap.Entry<String, Integer> product : sales.entrySet()){
                HBox leftBox = new HBox();
                productGetName = new Label(product.getKey());
                leftBox.getChildren().add(productGetName);
                leftBox.setAlignment(Pos.CENTER_LEFT);
                productGetTotal = new Label("x" + product.getValue());
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


                priceOnlyLayout.getChildren().addAll(productGetTotal);
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

        printToPDF = new Button("Print To PDF");
        printToPDF.setId("buttonClickedHistory");
        printToPDF.setOnAction(e->{
            Thread thread = new Thread(() -> {
                salesReport.printPDF();
                Platform.runLater(() -> {
                    alertGUI.alertInformation("Successfully printed to PDF. Check pdf/report folder");
                });
            });
            thread.start();

        });


        VBox wholeLayout = new VBox();
        wholeLayout.setMaxWidth(950);
        wholeLayout.getChildren().addAll(upperLayout, scrollPane, printToPDF);
        wholeLayout.setPadding(new Insets(10));
        wholeLayout.setId("reportLayout");
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
        wrapper.getChildren().add(wholeLayout);
        wrapper.setAlignment(Pos.CENTER);
        base.getChildren().add(wrapper);
        this.setContent(base);
    }


}
