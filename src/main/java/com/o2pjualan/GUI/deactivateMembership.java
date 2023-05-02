package com.o2pjualan.GUI;

import com.o2pjualan.Classes.Customer;
import com.o2pjualan.Classes.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;

import static com.o2pjualan.Main.controller;

public class deactivateMembership extends Tab {
    private ComboBox<String> name;
    private Button deactivate;
    private Label message;
    private Customers customers;

    public deactivateMembership(){
        this.setText("Deactivate Membership");
        customers = controller.getCustomers();

        this.name = new ComboBox<String>();
        this.name.setId("nameDropDown");
        this.name.setPromptText("Pick Customer Name...");
        updateData();

        this.deactivate = new Button("Deactivate");
        this.deactivate.setOnAction(e -> deactivateMember());

        Label vipMembership = new Label("ARE YOU SURE YOU WANT TO DEACTIVATE?");
        vipMembership.setId("h1");

        Label info = new Label("Deactivating your account may lose the experience to various discounts.");
        info.setId("paragraph");

        this.message = new Label("");
        this.message.setId("paragraph");

        VBox labelLayout = new VBox();
        labelLayout.getChildren().addAll(vipMembership, info);
        labelLayout.setSpacing(20);
        labelLayout.setPadding(new Insets(20));
        labelLayout.setAlignment(Pos.CENTER);

        VBox upgradeLayout = new VBox();
        upgradeLayout.getChildren().addAll(this.name, this.deactivate, this.message);
        upgradeLayout.setSpacing(50);
        upgradeLayout.setPadding(new Insets(20));
        upgradeLayout.setAlignment(Pos.CENTER);

        VBox wholeLayout = new VBox();
        wholeLayout.getChildren().addAll(labelLayout, upgradeLayout);
        wholeLayout.setAlignment(Pos.CENTER);
        wholeLayout.setId("wholeLayout");
        wholeLayout.setSpacing(100);
        Pane base = new Pane();
        base.getChildren().add(wholeLayout);
        this.setContent(base);
        wholeLayout.setFillWidth(true);
        wholeLayout.prefWidthProperty().bind(base.widthProperty());
        wholeLayout.prefHeightProperty().bind(base.heightProperty());
        Image backgroundImage = new Image("file:src/img/deactivate.png");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
        base.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));
        this.setOnSelectionChanged(event -> {
            if (this.isSelected()) {
                updateData();
            }
        });
    }

    public void deactivateMember() {
        if (this.name.getValue() == null) {
            this.message.setText("Please pick a customer name");
            this.message.setStyle("-fx-text-fill: #d0342c;");
        } else {
            String str = this.name.getValue();
            int startIndex = str.indexOf("(") + 1;
            int endIndex = str.indexOf(")");
            String digits = str.substring(startIndex, endIndex);
            int selectedId = Integer.parseInt(digits);

            Customers customers = controller.getCustomers();
            customers.deactivate(selectedId);
            controller.saveDataCustomer(customers);

            this.name.getItems().remove(str);
            this.message.setText("Customer chosen has been deactivated");
        }
    }

    public void updateData() {
        customers = controller.getCustomers();
        ArrayList<Customer> activeMembers = customers.getActiveMembers(true);
        ArrayList<String> optionsList = new ArrayList<>();
        for (Customer cust : activeMembers) {
            Integer id = cust.getIdCustomer();
            optionsList.add('(' + id.toString() + ") "  + customers.getCustomerNameById(id));
        }
        ObservableList<String> options = FXCollections.observableArrayList(optionsList);
        this.name.setItems(options);
    }
}
