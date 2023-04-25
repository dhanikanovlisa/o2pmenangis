package com.o2pjualan;

import com.o2pjualan.Classes.*;
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
    public static String folderName = "src/dataStore/dataStore1/" ;
    public static Controller controller;

    @Override
    public void start(Stage primaryStage) throws IOException {


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
            catalogMenu catalogTab;
            try{
                catalogTab = new catalogMenu();
            } catch (IOException e){
                throw new RuntimeException(e);
            }
            mainTabPane.getTabs().add(catalogTab);
        });

        /*Sementara buat testing edit per item*/
        editCatalog.setOnAction(event -> {
            editCatalogMenu editCatalogTab = new editCatalogMenu();
            mainTabPane.getTabs().add(editCatalogTab);
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
    }


    public static void main(String[] args) throws IOException {
        folderName = "src/dataStore/dataStore1/";
        controller = new Controller("json"); // sementara
        Application.launch(args);



        // sebelum CRUD jgn lupa load, terus tiap create instance langsung save
        // biar id nya gak duplicate

/*
        Products products = controller.getProducts();
        Product A = new Product("produk A", "etc", 100000, 120000, 100,"file:src/img/placeholderimg.png");
        products.addProduct(A);
        controller.saveDataProduct(products);
        Product B = new Product("produk B", "etc", 100000, 120000, 100,"file:src/img/placeholderimg.png");
        products.addProduct(B);
        controller.saveDataProduct(products);
        Product C = new Product("produk C", "etc", 100000, 120000, 100,"file:src/img/placeholderimg.png");
        products.addProduct(C);
        controller.saveDataProduct(products);
        Product D = new Product("produk D", "etc", 100000, 120000, 100,"file:src/img/placeholderimg.png");
        products.addProduct(D);
        controller.saveDataProduct(products);


        Customers customers = controller.getCustomers();
        Bills bills = controller.getBills();

        Customer c1 = new Customer();
        customers.addCustomer(c1);
        controller.saveDataCustomer(customers);
        Bill b1 = new Bill(c1.getIdCustomer());
        b1.AddProduct(1,2,120000);
        b1.AddProduct(2, 1,12000);
        bills.addBill(b1);
        controller.saveDataBill(bills);

        Customer c2 = new Customer();
        customers.addCustomer(c2);
        controller.saveDataCustomer(customers);
        Bill b2 = new Bill(c2.getIdCustomer());
        b2.AddProduct(1,10,10000);
        bills.addBill(b2);
        controller.saveDataBill(bills);

        Customer c3 = new Customer();
        customers.addCustomer(c3);
        controller.saveDataCustomer(customers);
        Bill b3 = new Bill(c3.getIdCustomer());
        bills.addBill(b3);
        controller.saveDataBill(bills);

        Customer c4 = new Customer();
        customers.addCustomer(c4);
        controller.saveDataCustomer(customers);
        Bill b4 = new Bill(c4.getIdCustomer());
        bills.addBill(b4);
        controller.saveDataBill(bills);


        FixedBills fixedBills = controller.getFixedBills();
        fixedBills.printFixedBills();

        FixedBill f1 = new FixedBill(c1.getIdCustomer());
        f1.AddProduct(3, 10,30000);
        f1.AddProduct(5, 90,30000);
        f1.AddProduct(1, 20,30000);
        fixedBills.addFixedBill(f1);
        controller.saveDataFixedBill(fixedBills);

        FixedBill f2 = new FixedBill(c1.getIdCustomer());
        f2.AddProduct(6,5,10000);
        fixedBills.addFixedBill(f2);
        controller.saveDataFixedBill(fixedBills);
*/
        /*
        Bills bills = controller.getBills();
        bills.print();
        System.out.println("========\n\n");

        FixedBills fixedBills = controller.getFixedBills();
        fixedBills.print();
        System.out.println("========\n\n");

        Customers customers = controller.getCustomers();
        customers.print();
        System.out.println("========\n\n");

        Products products = controller.getProducts();
        products.print();
        System.out.println("========\n\n");

        System.out.println("DONE");
        */

    }

}