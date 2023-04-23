package com.o2pjualan.GUI;

import com.o2pjualan.Classes.Customer;
import com.o2pjualan.Classes.Customers;
import com.o2pjualan.Classes.JSONController;
import com.o2pjualan.Classes.Member;
import com.o2pjualan.Main;
import com.sun.javafx.scene.control.FakeFocusTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

import static com.o2pjualan.Main.fileName;

public class signUp extends Tab {
    private ComboBox<Integer> idDropDown;
    private TextField nameField;
    private TextField phoneField;
    private Button register;
    private Customers customers;
    private Label message;
    public signUp() throws IOException, ParseException {
        JSONController cont = new JSONController();
        System.out.println("filename:  "+ fileName);
        customers = new Customers();
        customers = cont.getCustomers();
        ArrayList<Customer> test = customers.getCustomers();
        System.out.println("customer size" + test.size());
        ArrayList<Integer> optionsList = customers.getCustomersId();
        System.out.println(optionsList.size() );
        ObservableList<Integer> options = FXCollections.observableArrayList(optionsList);


        this.setText("Sign Up");

        this.idDropDown = new ComboBox<Integer>(options);
        this.idDropDown.setId("idDropDown");
        this.idDropDown.setPromptText("Pick Customer ID...");

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
    }

    private void registCust() throws IOException, ParseException {
        if (idDropDown.getValue() == null || nameField.getText().equals("") || phoneField.getText().equals("")) {
            if (idDropDown.getValue() == null) {
                idDropDown.setPromptText("Must pick customer id!!");
                this.message.setText("Masukkan data");
            }

            if (nameField.getText().equals("")) {
                nameField.setPromptText("Masukkan nama!");
                nameField.setStyle("-fx-prompt-text-fill: red;");
                this.message.setText("Masukkan data");
            }

            if (phoneField.getText().equals("")) {
                phoneField.setPromptText(("Masukkan nomor telepon!"));
                phoneField.setStyle("-fx-prompt-text-fill: red;");
                this.message.setText("Masukkan data");
            }
        } else {

            int id = idDropDown.getValue();
            String name = nameField.getText();
            String phone = phoneField.getText();

            JSONController controller = new JSONController();
            Customers customers = controller.getCustomers();
            customers.registerMember(id, name, phone);
            controller.saveDataCustomer(customers);

            this.message.setText("Berhasil register!");
            nameField.setText("");
            phoneField.setText("");
            idDropDown.getItems().remove(id);
        }
    }
}
