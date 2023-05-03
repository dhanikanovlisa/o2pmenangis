package com.o2pjualan;

import com.o2pjualan.Classes.*;
import com.o2pjualan.GUI.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class Main extends Application {
    public static String folderName = "src/dataStore/dataStore1/" ;
    public static Controller controller;
    public Menu menu1;
    public Menu menu2;
    public Menu menu3;
    public MenuBar menuBar;

    public BorderPane holderTab;
    public TabPane mainTabPane;
    public Scene tabPane;



    @Override
    public void start(Stage primaryStage)  {


        primaryStage.setTitle("Toko Sinar 02P");
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:src/img/logo.png"));
        primaryStage.setHeight(800);
        primaryStage.setWidth(1450);
        primaryStage.show();

        menu1 = new Menu("Menu");
        MenuItem mainMenuItem = new MenuItem("Main Menu");
        MenuItem catalog = new MenuItem("Catalog");
        MenuItem report = new MenuItem("Report");
        menu1.getItems().addAll(mainMenuItem, catalog, report);

        menu2 = new Menu("Profile");
        MenuItem signUp = new MenuItem("Sign Up");
        MenuItem updateMembership = new MenuItem("Upgrade Membership");
        MenuItem deactivate = new MenuItem("Deactivate Membership");
        MenuItem history = new MenuItem("History");
        MenuItem billCustomer = new MenuItem("Bills");
        MenuItem activate = new MenuItem("Activate Membership");
        menu2.getItems().addAll(signUp, updateMembership, activate, deactivate, billCustomer, history);

        menu3 = new Menu("Settings");
        MenuItem dataStorage = new MenuItem("Change Data Storage");
        MenuItem loadPlugin = new MenuItem("Load Plugin");
        menu3.getItems().addAll(dataStorage, loadPlugin);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3);


        holderTab = new BorderPane();
        mainTabPane = new TabPane();
        mainTabPane.setId("tabPane");
        holderTab.setTop(menuBar);
        holderTab.setCenter(mainTabPane);
        holderTab.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
        tabPane = new Scene(holderTab, 800, 700);
        tabPane.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

        primaryStage.setScene(tabPane);
        primaryStage.show();

        /*Create Instances Here*/
        catalog.setOnAction(event -> {
            catalogMenu catalogTab;
            try{
                catalogTab = new catalogMenu(mainTabPane);
                Button a = catalogTab.addNewItem();
                a.setOnAction(e-> {
                    addItemCatalog addCatalogItem = new addItemCatalog();
                    mainTabPane.getTabs().add(addCatalogItem);
                });

            } catch (IOException e){
                throw new RuntimeException(e);
            }
            mainTabPane.getTabs().add(catalogTab);
        });

        report.setOnAction(event -> {
            report reportTab = new report();
            mainTabPane.getTabs().add(reportTab);
        });

        signUp.setOnAction(event -> {
            signUp signUpTab;
            try {
                signUpTab = new signUp();
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
            mainTabPane.getTabs().add(signUpTab);
        });

        updateMembership.setOnAction(event -> {
            upgradeMembership upgradeMembershipTab = new upgradeMembership();
            mainTabPane.getTabs().add(upgradeMembershipTab);
        });

        deactivate.setOnAction(event -> {
            deactivateMembership deactivateTab = new deactivateMembership();
            mainTabPane.getTabs().add(deactivateTab);
        });

        activate.setOnAction(event -> {
            activateMembership acvitaeTab = new activateMembership();
            mainTabPane.getTabs().add(acvitaeTab);
        });

        history.setOnAction(event -> {
            try {
                history historyTab = new history(mainTabPane);
                mainTabPane.getTabs().add(historyTab);
            } catch (IOException err){
                throw new RuntimeException(err);
            }
        });

        dataStorage.setOnAction(event -> {

            dataStoreSettings dataStoreTab = new dataStoreSettings();
            mainTabPane.getTabs().add(dataStoreTab);
        });

        loadPlugin.setOnAction(event -> {
            pluginSettings pluginTab = new pluginSettings(mainTabPane);
            mainTabPane.getTabs().add(pluginTab);
        });

        billCustomer.setOnAction(event -> {
            BillGUI billTab = new BillGUI();
            mainTabPane.getTabs().add(billTab);
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


    public static void main(String[] args) throws IOException {
        folderName = "src/dataStore/dataStore1/";
        FileManager.initiateDataStore();
        Application.launch(args);
        System.out.println("DONE");

    }
}