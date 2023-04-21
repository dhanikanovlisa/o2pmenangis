package com.o2pjualan.GUI;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class pluginSettings extends Tab {
    private Button loadPlugin;

    public pluginSettings(){
        this.setText("Load Plugin");

        VBox pluginLayout = new VBox();
        Label titlePlugin = new Label("Load Plugin");
        titlePlugin.setId("h1");
        this.loadPlugin = new Button("Load Plugin");
        this.loadPlugin.setId("settingsButton");

        pluginLayout.getChildren().addAll(titlePlugin, this.loadPlugin);
        pluginLayout.setSpacing(20);

        HBox wholeLayout = new HBox();
        wholeLayout.setId("wholeLayout");
        wholeLayout.getChildren().addAll(pluginLayout);
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

        Pane base = new Pane();
        Image illus = new Image("file:src/img/illustration1.png");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);
        base.setBackground(new Background(new BackgroundImage(illus, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));
        base.getChildren().add(wholeLayout);
        this.setContent(base);

        /*To Do: open file dialog to load plugin*/
    }

}
