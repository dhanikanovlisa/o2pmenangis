package com.o2pjualan.GUI;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class dataStoreSettings extends Tab {
    private ComboBox<String> dataDropDown;

    public dataStoreSettings(){
        this.setText("Change Data Store");

        VBox dataLayout = new VBox();
        Label titleDataStore = new Label("Change Data Store");
        titleDataStore.setId("h1");
        this.dataDropDown = new ComboBox<>();
        this.dataDropDown.setItems(
                FXCollections.observableArrayList("JSON", "XML", "OBJ"));
        this.dataDropDown.setId("settingsDropDown");
        dataLayout.getChildren().addAll(titleDataStore, this.dataDropDown);
        dataLayout.setSpacing(20);



        HBox wholeLayout = new HBox();
        wholeLayout.setId("wholeLayout");
        wholeLayout.getChildren().addAll(dataLayout);
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");


        Pane base = new Pane();
        Image illus = new Image("file:src/img/illustration1.png");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);
        base.setBackground(new Background(new BackgroundImage(illus, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));
        base.getChildren().add(wholeLayout);
        this.setContent(base);

    }

}
