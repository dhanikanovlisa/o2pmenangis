package com.o2pjualan.Classes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.o2pjualan.Main.folderName;
import static java.nio.file.Files.*;


public class JSONController {
    private Customers customers;
    private Products products;
    public JSONController(){}

//    public static void main (String [] Args) throws IOException, ParseException {
//
//
////        JSONController cont = new JSONController();
////        Products products = new Products();
////        Product A = new Product("produk A", "etc", 100000, 120000, 100,"null");
////        Product B = new Product("produk B", "etc", 100000, 120000, 100,"null");
////        Product C = new Product("produk C", "etc", 100000, 120000, 100,"null");
////        products.addProduct(A);
////        products.addProduct(B);
////        products.addProduct(C);
////
////        cont.saveDataProduct(products);
////        cont.loadDataProduct();
//
//    }
    public void loadDataCustomer() throws IOException {
        customers = new Customers();
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = folderName + "customer.json";
        File file = new File(fileName);
        List<Object> objects = objectMapper.readValue(file, new TypeReference<List<Object>>(){});

        for (Object object : objects) {
            switch (((Map<String, Object>) object).get("membership").toString()) {
                case "Customer":
                    Customer customer = objectMapper.convertValue(object, Customer.class);
                    customers.addCustomer(customer);
                    break;
                case "Member":
                    Member member = objectMapper.convertValue(object, Member.class);
                    customers.addCustomer(member);
                    break;
                case "VIP":
                    VIP vip = objectMapper.convertValue(object, VIP.class);
                    customers.addCustomer(vip);
                    break;
                default:
                    break;
            }
        }
    }

    public void loadDataProduct() throws IOException {
        this.products = new Products();
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = folderName + "product.json";
        File file = new File(fileName);
        List<Object> objects = objectMapper.readValue(file, new TypeReference<List<Object>>(){});
        for (Object object: objects) {
            Product product = objectMapper.convertValue(object, Product.class);
            this.products.addProduct(product);
        }
    }

    public void saveDataCustomer(Customers customers) {
        ArrayList<Customer> data = customers.getCustomers();
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        try {
            String json = writer.writeValueAsString(data);
            String fileName = folderName + "customer.json";
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDataProduct(Products products) {
        ArrayList<Product> data = products.getProducts();
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        try {
            String json = writer.writeValueAsString(data);
            String fileName = folderName + "product.json";
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Customers getCustomers() throws IOException, ParseException {
        loadDataCustomer();
        return customers;
    }

    public Products getProducts() throws IOException {
        loadDataProduct();
        return products;
    }
    public int getTotalCustomers() throws IOException {
        loadDataCustomer();
        return customers.getCustomers().size();
    }
    public  int getTotalProducts() throws  IOException {
        loadDataProduct();
        return products.getProducts().size();
    }
}
