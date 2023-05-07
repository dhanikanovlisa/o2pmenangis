package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.util.ArrayList;

import static com.o2pjualan.Main.controller;
import static com.o2pjualan.Main.main;

public class history extends Tab implements updatingData {
    public ComboBox<String> name;
    public Label idTransaction;
    public Label firstItem;
    public Label totalPrice;
    private TabPane mainTabPane;
    private GridPane historyLayout;
    public ArrayList<Label> listOfBill;
    public VBox historyItemLayout;
    public Label historyTitle;
    public FixedBills fixedBills;
    public Customers customers;
    public ArrayList<FixedBill> fixedBill;
    public VBox upperLayout;
    private ScrollPane scrollPane;
    private ArrayList<String> optionsList;
    private ObservableList<String> options;
    public history(TabPane mainTabPane) throws IOException{
        this.setText("History");
        this.name = new ComboBox<>();
        Pane base = new Pane();
        VBox wrapper = new VBox();
        wrapper.prefWidthProperty().bind(base.widthProperty());
        wrapper.prefHeightProperty().bind(base.heightProperty());
        this.mainTabPane = mainTabPane;
        historyTitle = new Label("History");
        historyTitle.setId("h1");

        fixedBills = controller.getFixedBills();
        customers = controller.getCustomers();

        this.name = new ComboBox<>(options);
        this.name.setPromptText("Pick Customer Name...");
        this.name.setId("idDropDown");

        historyLayout = new GridPane();
        historyLayout.setStyle("-fx-padding: 0, 0, 0, 10;");
        historyLayout.setMinWidth(570);
        historyLayout.addColumn(1);
        historyLayout.setHgap(15);
        historyLayout.setVgap(15);

        scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVmax(1);
        scrollPane.setVvalue(0);
        scrollPane.setPrefViewportHeight(500);
        scrollPane.setPrefViewportWidth(570);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setContent(historyLayout);
        scrollPane.setId("scrollCatalog");

        upperLayout = new VBox();
//        upperLayout.setStyle("-fx-background-color: blue;");
        upperLayout.getChildren().addAll(historyTitle, this.name);
        upperLayout.setAlignment(Pos.CENTER);
        upperLayout.setSpacing(10);


        this.idTransaction = new Label("");
        this.firstItem = new Label("");
        this.totalPrice = new Label("");

        this.idTransaction.setId("historyItemLabel");
        this.firstItem.setId("historyItemLabel");
        this.totalPrice.setId("historyItemLabel");

        VBox wholeLayout = new VBox();
        wholeLayout.setId("historyLayout");
        wholeLayout.setMaxWidth(600);
        wholeLayout.getChildren().addAll(upperLayout, scrollPane);
        wholeLayout.setMargin(upperLayout, new Insets(10,0,0,0));
        wholeLayout.setMargin(scrollPane, new Insets(10, 0, 0, 0));
        wholeLayout.setSpacing(20);

        wrapper.getChildren().add(wholeLayout);
        wrapper.setAlignment(Pos.CENTER);
        base.getChildren().add(wrapper);
        this.setContent(base);
        wholeLayout.setFillWidth(true);
        wholeLayout.prefWidthProperty().bind(base.widthProperty());
        wholeLayout.prefHeightProperty().bind(base.heightProperty());
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

        this.setOnSelectionChanged(event -> {
            if (this.isSelected()) {
                updateData();
            }
        });

    }

    public void displayFixedBill() throws IOException  {
        if (this.name.getValue() != null) {
            String str = this.name.getValue();
            int startIndex = str.indexOf("(") + 1;
            int endIndex = str.indexOf(")");
            String digits = str.substring(startIndex, endIndex);
            int selectedId = Integer.parseInt(digits);

            int rowCount = 0;

            fixedBill = fixedBills.getFixedBills();

            for (FixedBill fix : fixedBill) {
                String idFixedBill = Integer.toString(fix.getIdBill());
                String totalPrice = Double.toString(fix.getTotalFixedBill());
                System.out.println(idFixedBill + " " + totalPrice);
                if (selectedId == fix.getIdCustomer()) {
                    displayPerItem(idFixedBill, totalPrice, rowCount);
                    rowCount++;
                }
            }
        }
    }

    public void displayPerItem(String idFixedBill, String totalPrice, int rowCount){
        this.idTransaction = new Label(idFixedBill);
        this.totalPrice = new Label(totalPrice);
        historyItemLayout = new VBox();
        historyItemLayout.setId("historyItemLayout");
        historyItemLayout.setFillWidth(true);
        historyItemLayout.getChildren().addAll(this.idTransaction, this.totalPrice);
        historyItemLayout.setOnMouseClicked(e -> {
            clickedHistory clickedHistoryTab = new clickedHistory(Integer.parseInt(idFixedBill), name.getValue().toString());
            mainTabPane.getTabs().add(clickedHistoryTab);
        });
        historyLayout.add(historyItemLayout, 0, rowCount);
        historyLayout.setAlignment(Pos.CENTER);
    }

    public void updateData() {
        fixedBills = controller.getFixedBills();
        customers = controller.getCustomers();
        Customers customers = controller.getCustomers();
        ArrayList<Integer> customersId = customers.getAllCustomersId();
        ArrayList<String> optionsList = new ArrayList<String>();
        for (Integer i : customersId) {
            optionsList.add('(' + i.toString() + ") "  + customers.getCustomerNameById(i));
        }
        ObservableList<String> options = FXCollections.observableArrayList(optionsList);
        this.name.setItems(options);

        try {
            displayFixedBill();
        } catch (IOException err){
            throw new RuntimeException(err);
        }
        name.setOnAction(e -> {
            try {
                historyLayout.getChildren().clear();
                displayFixedBill();
            } catch (IOException err){
                throw new RuntimeException(err);
            }
        });
    }
}
