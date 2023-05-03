package com.o2pjualan.GUI;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import com.o2pjualan.Classes.pluginController;

import java.io.File;
import java.io.IOException;

public class pluginSettings extends Tab {
    private Button loadPlugin;
    private VBox pluginLayout;
    private HBox wholeLayout;
    private pluginController pluginController;
    private AlertGUI alertGUI;


    public pluginSettings(){
        this.setText("Plugin");

        pluginLayout = new VBox();
        Label titlePlugin = new Label("Plugin");
        titlePlugin.setId("h1");
        this.loadPlugin = new Button("Upload Plugin");
        this.loadPlugin.setId("settingsButton");
        pluginController = new pluginController();

        alertGUI = new AlertGUI();

        loadPlugin.setOnAction(e-> {
            openFileDialog();
        });

        pluginLayout.getChildren().addAll(titlePlugin, this.loadPlugin);
        pluginLayout.setSpacing(20);

        wholeLayout = new HBox();
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
    }

    public void openFileDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open JAR File");

        FileChooser.ExtensionFilter jarFilter = new FileChooser.ExtensionFilter("JAR Files", "*.jar");
        fileChooser.getExtensionFilters().add(jarFilter);

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            int crop = path.indexOf("src/");
            if(crop == -1){
                alertGUI.alertWarning("Cannot find image in src file");
            } else {
                String pathFile = path.substring(crop);
                try{
                    pluginController.loadPlugin(pathFile);
                    alertGUI.alertInformation("Succesfully loaded plugin");
                    System.out.println(pluginController.getLoadPlugin().size());
                } catch (IOException | ClassNotFoundException e){
                    System.out.println(e);
                }

            }
        }
    }
}
