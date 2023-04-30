package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class dataStoreSettings extends Tab {
    private ComboBox<String> dataDropDown;
    private Button buttonChange;
    private Button buttonFolder;
    private Label message;

    public dataStoreSettings(){
        this.setText("Data Store Setting");

        Pane base = new Pane();
        Image illus = new Image("file:src/img/illustration1.png");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);
        base.setBackground(new Background(new BackgroundImage(illus, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));

        VBox baseWrapper = new VBox();
        baseWrapper.setFillWidth(true);
        baseWrapper.prefWidthProperty().bind(base.widthProperty());
        baseWrapper.prefHeightProperty().bind(base.heightProperty());

        VBox dataLayout = new VBox();
        Label titleDataStore = new Label("Change Data Store");
        titleDataStore.setId("h1");
        this.buttonFolder =  new Button("Choose Folder");
        buttonFolder.setId("settingsButton");
        dataLayout.setPadding(new Insets(0, 0, 50, 0));
        dataLayout.getChildren().addAll(titleDataStore, buttonFolder);
        dataLayout.setSpacing(20);

        VBox dataLayout2 = new VBox();
        HBox buttonLayout = new HBox();
        Label labelExt = new Label("Change Extension");
        labelExt.setId("h1");
        this.dataDropDown = new ComboBox<>();
        this.dataDropDown.setItems(
                FXCollections.observableArrayList("JSON", "XML", "OBJ"));
        this.dataDropDown.setId("settingsDropDown");
        this.buttonChange = new Button("change");
        buttonChange.setId("settingsButton");
        buttonChange.setOnAction(e -> {
            convertData();
        });
        this.message = new Label("");
        this.message.setFont(new Font(14));
        buttonLayout.getChildren().addAll(dataDropDown, buttonChange);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.setSpacing(20);
        dataLayout2.getChildren().addAll(labelExt, buttonLayout, message);
        dataLayout2.setSpacing(20);

        dataLayout.setAlignment(Pos.CENTER);
        dataLayout2.setAlignment(Pos.CENTER);
        baseWrapper.setAlignment(Pos.CENTER);
        baseWrapper.getChildren().addAll(dataLayout, dataLayout2);

        base.getChildren().add(baseWrapper);
        this.setContent(base);
    }

    public void convertData() {
        this.message.setText("Loading...");
        this.message.setTextFill(Paint.valueOf("#878787"));
        String newFormat = dataDropDown.getValue().toLowerCase();
        if (newFormat.equals("obj")) {
            newFormat = "ser";
        }
        Integer retcode = FileManager.changeExt(newFormat);
        if (retcode == 0) {
            this.message.setText("The file extension has been updated.");
            this.message.setTextFill(Paint.valueOf("#599962"));
        } else if (retcode == 1) {
            this.message.setText("Files already have the requested extension.");
            this.message.setTextFill(Paint.valueOf("#599962"));
        } else {
            this.message.setText("An error occurred. Please try again later.");
            this.message.setTextFill(Paint.valueOf("#d0342c"));
        }
    }

}
