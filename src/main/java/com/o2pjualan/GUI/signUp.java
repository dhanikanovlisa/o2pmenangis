package com.o2pjualan.GUI;

import com.sun.javafx.scene.control.FakeFocusTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class signUp extends Tab {
    private ComboBox<Integer> idDropDown;
    private TextField nameField;
    private TextField phoneField;
    private Button register;
    public signUp(){
        this.setText("Sign Up");

        this.idDropDown = new ComboBox<>();
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
        wholeLayout.getChildren().addAll(this.idDropDown, wrapperLayout, this.register);
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
}
