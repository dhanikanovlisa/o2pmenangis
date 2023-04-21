package com.o2pjualan;

import com.o2pjualan.GUI.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Toko Sinar 02P");
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:src/img/logo.png"));
        primaryStage.setHeight(800);
        primaryStage.setWidth(1450);
        primaryStage.show();

        Menu menu1 = new Menu("Menu");
        MenuItem mainMenuItem = new MenuItem("Main Menu");
        MenuItem catalog = new MenuItem("Catalog");
        MenuItem editCatalog = new Menu("Edit Catalog");
        MenuItem report = new MenuItem("Report");
        menu1.getItems().addAll(mainMenuItem, catalog, editCatalog, report);

        Menu menu2 = new Menu("Profile");
        MenuItem signUp = new MenuItem("Sign Up");
        MenuItem updateMembership = new MenuItem("Update Membership");
        MenuItem deactivate = new MenuItem("Deactivate Membership");
        MenuItem history = new MenuItem("History");
        menu2.getItems().addAll(signUp, updateMembership, deactivate, history);

        Menu menu3 = new Menu("Settings");
        MenuItem dataStorage = new MenuItem("Change Data Storage");
        MenuItem loadPlugin = new MenuItem("Load Plugin");
        menu3.getItems().addAll(dataStorage, loadPlugin);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3);


        BorderPane holderTab = new BorderPane();
        TabPane mainTabPane = new TabPane();
        holderTab.setTop(menuBar);
        holderTab.setCenter(mainTabPane);
        holderTab.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
        Scene tabPane = new Scene(holderTab, 800, 700);
        tabPane.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

        primaryStage.setScene(tabPane);
        primaryStage.show();

        /*Create Instances Here*/
        catalog.setOnAction(event -> {
            catalogMenu catalogTab = new catalogMenu();
            mainTabPane.getTabs().add(catalogTab);
        });

        /*Sementara buat testing edit per item*/
        editCatalog.setOnAction(event -> {
            editCatalogMenu editCatalogTab = new editCatalogMenu();
            mainTabPane.getTabs().add(editCatalogTab);
        });

        report.setOnAction(event -> {
            Tab reportTab = new Tab("Report");
            reportTab.setContent(new Label("tes"));
            mainTabPane.getTabs().add(reportTab);
        });

        signUp.setOnAction(event -> {
            Tab signUpTab = new Tab("Sign Up");
            signUpTab.setContent(new Label("tes"));
            mainTabPane.getTabs().add(signUpTab);
        });

        updateMembership.setOnAction(event -> {
            Tab updateMembershipTab = new Tab("Update Membership");
            updateMembershipTab.setContent(new Label("Update"));
            mainTabPane.getTabs().add(updateMembershipTab);
        });

        deactivate.setOnAction(event -> {
            Tab deactivateTab = new Tab("Deactivate Membership");
            deactivateTab.setContent(new Label("Deactivate"));
            mainTabPane.getTabs().add(deactivateTab);
        });

        history.setOnAction(event -> {
            Tab historyTab = new Tab("History");
            historyTab.setContent(new Label("History"));
            mainTabPane.getTabs().add(historyTab);
        });

        dataStorage.setOnAction(event -> {
            dataStoreSettings dataStoreTab = new dataStoreSettings();
            mainTabPane.getTabs().add(dataStoreTab);
        });

        loadPlugin.setOnAction(event -> {
            pluginSettings pluginTab = new pluginSettings();
            mainTabPane.getTabs().add(pluginTab);
        });


        mainMenuItem.setOnAction(event -> {
            mainMenu mainMenuTab = new mainMenu();
            mainTabPane.getTabs().add(mainMenuTab);
        });

        mainMenu mainMenuTab = new mainMenu();
        mainTabPane.getTabs().add(mainMenuTab);


        // stop all thread clock when closing the app
        primaryStage.setOnCloseRequest(event -> {
            for (Tab tab : mainTabPane.getTabs()) {
                if (tab instanceof mainMenu) {
                    ((mainMenu) tab).stopClock();
                }
            }
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}