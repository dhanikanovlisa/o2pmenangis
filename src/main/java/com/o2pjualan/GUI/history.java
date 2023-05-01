package com.o2pjualan.GUI;

import com.o2pjualan.Classes.Customer;
import com.o2pjualan.Classes.Customers;
import com.o2pjualan.Classes.FixedBill;
import com.o2pjualan.Classes.FixedBills;
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

public class history extends Tab {
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
    public HBox upperLayout;
    private ScrollPane scrollPane;
    private ArrayList<String> optionsList;
    private ObservableList<String> options;
    public history(TabPane mainTabPane) throws IOException{

        this.setText("History");
        this.mainTabPane = mainTabPane;
        historyTitle = new Label("History");
        historyTitle.setId("h1");

        fixedBills = controller.getFixedBills();
        customers = controller.getCustomers();

        ArrayList<Integer> customersId = customers.getCustomersId();
        optionsList = new ArrayList<String>();
        for (Integer i : customersId) {
            optionsList.add(Integer.toString(i));
        }
        options = FXCollections.observableArrayList(optionsList);

        this.name = new ComboBox<>(options);
        this.name.setPromptText("Pick Customer Name...");
        this.name.setId("idDropDown");
        this.name.setValue("ID");


        historyLayout = new GridPane();
        historyLayout.addColumn(1);
//        historyLayout.addRow();
        historyLayout.setHgap(15);
        historyLayout.setVgap(15);

        try {
            displayFixedBill(name.getValue());
        } catch (IOException err){
            throw new RuntimeException(err);
        }

        name.setOnAction(e -> {
            try {
                historyLayout.getChildren().clear();
                displayFixedBill(name.getValue());
            } catch (IOException err){
                throw new RuntimeException(err);
            }
        });


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
        scrollPane.setContent(historyLayout);
        scrollPane.setId("scrollCatalog");

        upperLayout = new HBox();
        upperLayout.getChildren().addAll(historyTitle, this.name);
        upperLayout.setSpacing(900);


        this.idTransaction = new Label("");
        this.firstItem = new Label("");
        this.totalPrice = new Label("");

        this.idTransaction.setId("historyItemLabel");
        this.firstItem.setId("historyItemLabel");
        this.totalPrice.setId("historyItemLabel");

        VBox wholeLayout = new VBox();
        wholeLayout.getChildren().addAll(upperLayout, scrollPane);
        wholeLayout.setMargin(upperLayout, new Insets(10,0,0,0));
        wholeLayout.setMargin(scrollPane, new Insets(10, 0, 0, 0));
        wholeLayout.setSpacing(40);
        wholeLayout.setId("wholeLayout");

        Pane base = new Pane();
        base.getChildren().add(wholeLayout);
        this.setContent(base);
        wholeLayout.setFillWidth(true);
        wholeLayout.prefWidthProperty().bind(base.widthProperty());
        wholeLayout.prefHeightProperty().bind(base.heightProperty());
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

    }

    public void displayFixedBill(String idCust) throws IOException  {
        int rowCount = 0;

        fixedBill = fixedBills.getFixedBills();

        for(FixedBill fix: fixedBill){
            String idFixedBill = Integer.toString(fix.getIdBill());
            String totalPrice = Integer.toString(fix.getTotalFixedBill());
            if(idCust.equals("ID")){
                displayPerItem(idFixedBill, totalPrice, rowCount);
                rowCount++;
            } else if(Integer.parseInt(idCust) == fix.getIdBill()) {
                displayPerItem(idFixedBill, totalPrice, rowCount);
                rowCount++;
            }
        }
    }

    public void displayPerItem(String idFixedBill, String totalPrice, int rowCount){
        this.idTransaction = new Label(idFixedBill);
        this.totalPrice = new Label(totalPrice);
        historyItemLayout = new VBox();
        historyItemLayout.setId("historyItemLayout");
        historyItemLayout.getChildren().addAll(this.idTransaction, this.totalPrice);
        historyItemLayout.setOnMouseClicked(e -> {
            clickedHistory clickedHistoryTab = new clickedHistory(Integer.parseInt(idFixedBill));
            mainTabPane.getTabs().add(clickedHistoryTab);
        });
        historyLayout.add(historyItemLayout, 0, rowCount);
    }
}
