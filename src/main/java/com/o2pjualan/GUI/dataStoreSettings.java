package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

import static com.o2pjualan.Main.folderName;
import static com.o2pjualan.Main.mainTabPane;

public class dataStoreSettings extends Tab {
    private ComboBox<String> dataDropDown;
    private Button buttonChange;
    private Button buttonFolder;
    private Label message;
    private Label message2;
    private AlertGUI alertGUI;


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
        alertGUI = new AlertGUI();

        VBox dataLayout = new VBox();
        HBox titleLayout = new HBox();
        Label titleDataStore = new Label("Change Data Store");
        titleDataStore.setId("h1");
        Button informationBtn = new Button();
        informationBtn.setId("infoBtn");
        Tooltip tooltip = new Tooltip("Choose empty folder to create a new data store");
        tooltip.setShowDelay(Duration.millis(100));
        Tooltip.install(informationBtn, tooltip);
        informationBtn.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE));
        this.buttonFolder =  new Button("Choose Folder");
        this.buttonFolder.setId("settingsButton");
        this.buttonFolder.setOnAction(e -> changeFolder());
        this.message2 = new Label("");

        titleLayout.getChildren().addAll(titleDataStore, informationBtn);
        titleLayout.setAlignment(Pos.CENTER);
        dataLayout.setPadding(new Insets(0, 0, 50, 0));
        dataLayout.getChildren().addAll(titleLayout, buttonFolder, message2);
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
        this.buttonChange.setId("settingsButton");
        this.buttonChange.setOnAction(e ->{
            Thread thread = new Thread(() -> {
                Integer retcode = convertData();
                Platform.runLater(() -> {
                    if (retcode == 0) {
                        alertGUI.alertWarning("The file extension has been updated.");
                    } else if (retcode == 1) {
                        alertGUI.alertWarning("Files already in the requested extension.");
                    } else if (retcode == -1){
                        alertGUI.alertWarning("You have not selected the extension");
                    }
                    else {
                        alertGUI.alertWarning("An error occurred. Please try again later.");
                    }
                });
            });
            thread.start();
        }
        );
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

    public Integer convertData() {
        if (dataDropDown.getValue() != null) {
            String newFormat = dataDropDown.getValue().toLowerCase();
            newFormat = newFormat.toLowerCase();
            if (newFormat.equals("obj")) {
                newFormat = "ser";
            }
            Integer retcode = FileManager.changeExt(newFormat);

            return retcode;
        }
        return -1;
    }

    public void changeFolder() {
        File currentFile = new File(".");
        File relativeFolder  = new File(currentFile.getAbsolutePath() + "/src/dataStore");

        if (!relativeFolder.canRead()) {
            this.message2.setText("You do not have permission to access the target folder");
            this.message2.setTextFill(Paint.valueOf("#d0342c"));
        }

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(relativeFolder);
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            String newFolderPath = selectedDirectory.getAbsolutePath();
            if (newFolderPath.contains("dataStore/")) {
                newFolderPath = "src/" + newFolderPath.substring(newFolderPath.indexOf("src/") + 4) + '/';

                if (!newFolderPath.equals(folderName)) {
                    FileManager.changeFolder(newFolderPath);
                    this.message2.setText("The data store directory has been updated");
                    this.message2.setTextFill(Paint.valueOf("#599962"));
                    closeAllTabs();
                } else {
                    this.message2.setText("Currently using this data store");
                    this.message2.setTextFill(Paint.valueOf("#878787"));
                }

            } else {
                this.message2.setText("Please choose directory inside 'dataStore' directory");
                this.message2.setTextFill(Paint.valueOf("#d0342c"));
            }
        } else {
            this.message2.setText("No directory selected");
            this.message2.setTextFill(Paint.valueOf("#878787"));
        }
    }

    public void closeAllTabs() {
        ArrayList<Tab> tabsToRemove = new ArrayList<>();
        for (Tab tab : mainTabPane.getTabs()) {
            if (tab != this) {
                tabsToRemove.add(tab);
            }
        }
        mainTabPane.getTabs().removeAll(tabsToRemove);
    }
}
