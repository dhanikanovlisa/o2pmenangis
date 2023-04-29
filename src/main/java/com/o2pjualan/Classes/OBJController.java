package com.o2pjualan.Classes;

import java.io.*;

import static com.o2pjualan.Main.folderName;

public class OBJController implements FileController{
    private Customers customers;
    private Products products;
    private Bills bills;
    private FixedBills fixedBills;
    public OBJController() {
        this.customers = getData("customer.ser");
        this.products = getData("product.ser");
        this.bills = getData("bill.ser");
        this.fixedBills = getData("fixedBill.ser");
    }
    @Override
    public void saveDataCustomer(Customers customers) {
        this.customers = customers;
        saveData(customers, "customer.ser");
    }

    @Override
    public void saveDataProduct(Products products) {
        this.products = products;
        saveData(products, "product.ser");
    }

    @Override
    public void saveDataFixedBill(FixedBills fixedBills) {
        this.fixedBills = fixedBills;
        saveData(fixedBills, "fixedBill.ser");
    }

    @Override
    public void saveDataBill(Bills bills) {
        this.bills = bills;
        saveData(bills, "bill.ser");
    }

    public <T> void saveData(T data, String fileName) {
        String path = folderName + fileName;
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(data);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customers getCustomers() {
        return this.customers;
    }

    @Override
    public Products getProducts() {
        return this.products;
    }

    @Override
    public Bills getBills() {
        return this.bills;
    }
    @Override
    public FixedBills getFixedBills() {
        return this.fixedBills;
    }

    public <T> T getData(String fileName) {
        String path = folderName + fileName;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            T data = (T) in.readObject();
            in.close();
            fileIn.close();
            return data;
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTotalCustomers() {
        return getCustomers().getCustomers().size();
    }

    @Override
    public int getTotalProducts() {
        return getProducts().getProducts().size();
    }

    @Override
    public int getTotalFixedBills() {
        return getFixedBills().getFixedBills().size();
    }
}
