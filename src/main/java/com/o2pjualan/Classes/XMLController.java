package com.o2pjualan.Classes;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

import static com.o2pjualan.Main.folderName;

public class XMLController implements FileController {
    private Customers customers;
    private Products products;
    private Bills bills;
    private FixedBills fixedBills;
    private  Plugins plugins;


    public XMLController() {
        this.customers = getData(Customers.class, "customer.xml");
        this.products = getData(Products.class, "product.xml");
        this.bills = getData(Bills.class, "bill.xml");
        this.fixedBills = getData(FixedBills.class, "fixedBill.xml");
        this.plugins = getData(Plugins.class, "plugin.xml");
    }

    @Override
    public void saveDataCustomer(Customers customers) {
        try {
            this.customers = customers;
            saveData(customers, Customers.class, "customer.xml");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveDataProduct(Products products) {
        try {
            this.products = products;
            saveData(products, Products.class, "product.xml");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveDataFixedBill(FixedBills fixedBills) {
        try {
            this.fixedBills = fixedBills;
            saveData(fixedBills, FixedBills.class, "fixedBill.xml");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveDataBill(Bills bills) {
        try {
            this.bills = bills;
            saveData(bills, Bills.class, "bill.xml");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveDataPlugins(Plugins plugins) {
        try {
            this.plugins = plugins;
            saveData(plugins, Plugins.class, "plugin.xml");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public <T> void saveData(T data, Class<T> tClass, String fileName) throws JsonProcessingException {
        String path = folderName + fileName;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            jaxbMarshaller.marshal(data, new File(path));
        } catch (JAXBException e) {
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

    @Override
    public Plugins getPlugins() {
        return this.plugins;
    }

    public <T> T getData(Class<T> tClass, String fileName) {
        File file = new File(folderName+fileName);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance((tClass));
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (T) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
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
        return  getFixedBills().getFixedBills().size();
    }
}
