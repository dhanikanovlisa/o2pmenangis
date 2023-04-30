package com.o2pjualan.Classes;

import lombok.Data;
import java.io.IOException;

@Data
public class Controller implements FileController {
    private FileController fileController;
    private String type;

    public Controller(String fileFormat) throws IOException {
        this.type = fileFormat;
        switch (fileFormat) {
            case "json":
                fileController = new JSONController();
                break;
            case "xml" :
                fileController = new XMLController();
                break;
            case "obj" :
            case "ser" :
                fileController = new OBJController();
                break;

            default:
                throw new IllegalArgumentException("Unsupported file format: " + fileFormat);
        }
    }
    @Override
    public void saveDataCustomer(Customers customers) {
        fileController.saveDataCustomer(customers);
    }

    @Override
    public void saveDataProduct(Products products) {
        fileController.saveDataProduct(products);
    }

    @Override
    public void saveDataFixedBill(FixedBills fixedBills) {
        fileController.saveDataFixedBill(fixedBills);
    }

    @Override
    public void saveDataBill(Bills bills) {
        fileController.saveDataBill(bills);
    }

    @Override
    public Customers getCustomers() {
        return fileController.getCustomers();
    }

    @Override
    public Products getProducts() {
        return fileController.getProducts();
    }

    @Override
    public Bills getBills() {
        return fileController.getBills();
    }

    @Override
    public FixedBills getFixedBills() {
        return fileController.getFixedBills();
    }

    @Override
    public int getTotalCustomers() {
        return fileController.getTotalCustomers();
    }

    @Override
    public int getTotalProducts() {
        return fileController.getTotalProducts();
    }

    @Override
    public int getTotalFixedBills() {
        return fileController.getTotalFixedBills();
    }
}
