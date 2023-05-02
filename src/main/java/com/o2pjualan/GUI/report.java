package com.o2pjualan.GUI;

import com.o2pjualan.Classes.Customers;
import com.o2pjualan.Classes.FixedBill;
import com.o2pjualan.Classes.FixedBills;
import com.o2pjualan.Classes.Products;
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

import static com.o2pjualan.Main.controller;

public class report extends Tab {
    private GridPane dataSalesLayout;
    private ScrollPane scrollPane;
    private Products products;
    private FixedBills fixedBills;
    private Customers customers;
    public report() {
        this.setText("Report");
        HBox upperLayout = new HBox();
        Label titleReport = new Label("Data Sales");
        titleReport.setId("h1");
        upperLayout.getChildren().add(titleReport);

        customers = controller.getCustomers();
        products = controller.getProducts();
        fixedBills = controller.getFixedBills();

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



        VBox wholeLayout = new VBox();
        wholeLayout.getChildren().addAll(upperLayout, scrollPane);
        wholeLayout.setPadding(new Insets(10));
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

        Pane base = new Pane();
        base.getChildren().add(wholeLayout);
        this.setContent(base);

    }
}
