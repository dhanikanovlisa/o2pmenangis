package com.o2pjualan.GUI;

import com.o2pjualan.Classes.Customers;
import com.o2pjualan.Classes.updatingData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import static com.o2pjualan.Main.controller;

public class signUp extends Tab implements updatingData {
    private ComboBox<String> idDropDown;
    private TextField nameField;
    private TextField phoneField;
    private Button register;
    private Customers customers;
    private Label message;
    private AlertGUI alertGUI;
    public signUp() throws IOException, ParseException {
        this.setText("Sign Up");
        customers = controller.getCustomers();
        alertGUI = new AlertGUI();
        this.idDropDown = new ComboBox<String>();
        this.idDropDown.setId("idDropDown");
        this.idDropDown.setPromptText("Pick Customer ID...");

        updateData();
        VBox formLayout = new VBox();
        HBox nameLayout = new HBox();
        Label nameLabel = new Label("Name");
        nameLabel.setId("signUpLabel");
        this.nameField = new TextField();
        nameLayout.getChildren().addAll(nameLabel, this.nameField);
        nameLayout.setSpacing(30);
        nameField.setId("textFieldSignUp");

        HBox phoneLayout = new HBox();
        Label phoneLabel = new Label("Phone");
        phoneLabel.setId("signUpLabel");
        this.phoneField = new TextField();

        phoneLayout.getChildren().addAll(phoneLabel, this.phoneField);
        phoneLayout.setSpacing(30);
        phoneField.setId("textFieldSignUp");

        this.register = new Button("Register");
        register.setOnAction(e -> {
            try {
                registCust();
            } catch (IOException | ParseException ex) {
                throw new RuntimeException(ex);
            }
        });

        this.message = new Label();

        formLayout.getChildren().addAll(nameLayout, phoneLayout);
        formLayout.setSpacing(20);
        formLayout.setAlignment(Pos.BASELINE_LEFT);
        formLayout.setId("signUpLayout");
        formLayout.setFillWidth(true);
        formLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

        HBox wrapperLayout = new HBox();
        wrapperLayout.getChildren().add(formLayout);
        wrapperLayout.setAlignment(Pos.CENTER);

        VBox wholeLayout = new VBox();
        wholeLayout.getChildren().addAll(this.idDropDown, wrapperLayout, this.register, this.message);
        wholeLayout.setId("wholeLayout");
        wholeLayout.setAlignment(Pos.CENTER);
        wholeLayout.setSpacing(40);
        Pane base = new Pane();
        base.getChildren().add(wholeLayout);
        this.setContent(base);
        wholeLayout.setFillWidth(true);
        wholeLayout.prefWidthProperty().bind(base.widthProperty());
        wholeLayout.prefHeightProperty().bind(base.heightProperty());

        this.setOnSelectionChanged(event -> {
            if (this.isSelected()) {
                updateData();
            }
        });
    }

    private void registCust() throws IOException, ParseException {
        if (idDropDown.getValue() == null || nameField.getText().equals("") || phoneField.getText().equals("")) {
            if (idDropDown.getValue() == null) {
                idDropDown.setPromptText("Pick customer id");
                this.message.setText("Please insert the required data id");
            }

            if (nameField.getText().equals("")) {
                nameField.setPromptText("*Name required");
                nameField.setStyle("-fx-prompt-text-fill: #d0342c;");
                this.message.setText("Please insert the required data");
            }

            if (phoneField.getText().equals("")) {
                phoneField.setPromptText(("*Phone number required"));
                phoneField.setStyle("-fx-prompt-text-fill: #d0342c;");
                this.message.setText("Please insert the required data");
            }
        } else {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String id = idDropDown.getValue();
            if(!inputValidater(phone)){
                alertGUI.alertWarning("Invalid phone number format. Please put number");
            } else {
                Customers customers = controller.getCustomers();
                customers.registerMember(Integer.parseInt(id), name, phone);
                controller.saveDataCustomer(customers);

                this.message.setText("successfully registered!");
                nameField.setText("");
                nameField.setPromptText("");
                phoneField.clear();
                idDropDown.getItems().remove(id);
            }
        }
    }

    public void updateData() {
        ArrayList<Integer> customersId = customers.getCustomerForSignUp();
        ArrayList<String> optionsList = new ArrayList<String>();
        for (Integer i : customersId) {
            optionsList.add(Integer.toString(i));
        }
        ObservableList<String> options = FXCollections.observableArrayList(optionsList);
        this.idDropDown.setItems(options);
    }

    public boolean inputValidater(String input){
        boolean isDigit = true;
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                isDigit = false;
                break;
            }
        }
        return isDigit;
    }
}
