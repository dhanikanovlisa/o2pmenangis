package com.o2pjualan;

import com.o2pjualan.Classes.Customer;
import com.o2pjualan.Classes.Customers;
import com.o2pjualan.Classes.JSONController;
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
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;


public class Main extends Application {
    public static String fileName = "src/dataStore/dataStore1/customer.json" ; //ini harusnya folder name tapi buat testing dulu aja

    @Override
    public void start(Stage primaryStage) {

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
        MenuItem updateMembership = new MenuItem("Upgrade Membership");
        MenuItem deactivate = new MenuItem("Deactivate Membership");
        MenuItem history = new MenuItem("History");
        MenuItem clickHistory = new MenuItem("Clicked History");
        menu2.getItems().addAll(signUp, updateMembership, deactivate, history, clickHistory);

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

        history.setOnAction(event -> {
            history historyTab = new history();
            mainTabPane.getTabs().add(historyTab);
        });

        /*Buat keperluan testing clicked history item*/
        clickHistory.setOnAction(event -> {
            clickedHistory clickTab = new clickedHistory();
            mainTabPane.getTabs().add(clickTab);
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

//        JSONController controller = new JSONController(fileName);
//        ArrayList<Customer> newCusts = new ArrayList<Customer>();
//        System.out.println("hi");
//        for (int i = 0; i<5; i++) {
//            System.out.println("hi");
//            Customer cust = new Customer(i+1);
//            newCusts.add(cust);
//            System.out.println("hi");
//            System.out.println(cust.toString());
//        }
//        Customers customers = new Customers(newCusts);
//        controller.saveDataCustomer(customers);

    }


    public static void main(String[] args) {
        fileName = "src/dataStore/dataStore1/customer.json";
        Application.launch(args);
    }

    public static String getFileName() {
        return fileName;
    }

    public static void setFileName(String fileName) {
        Main.fileName = fileName;
    }
}