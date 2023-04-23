package com.o2pjualan.Classes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.o2pjualan.Main.folderName;


public class JSONController {
    private Customers customers;
    private Products products;
//    private FixedBills fixedBills;
    public JSONController(){}

    public static void main (String [] Args) throws IOException, ParseException {
        JSONController cont = new JSONController();
//        Products products = cont.getProducts();
//        Product A = new Product("produk A", "etc", 100000, 120000, 100,"file:src/img/placeholderimg.png");
//        Product B = new Product("produk B", "etc", 100000, 120000, 100,"file:src/img/placeholderimg.png");
//        Product C = new Product("produk C", "etc", 100000, 120000, 100,"file:src/img/placeholderimg.png");
//        Product D = new Product("produk D", "etc", 100000, 120000, 100,"file:src/img/placeholderimg.png");
//        products.addProduct(A);
//        products.addProduct(B);
//        products.addProduct(C);
//        products.addProduct(D);
//

    }
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

//    public void loadDataFixedBill() throws  IOException{
//        this.fixedBills = new FixedBills();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String fileName = folderName + "fixedBill.json";
//        File file = new File(fileName);
//        List<FixedBill> billList = Arrays.asList(objectMapper.readValue(file, FixedBill[].class));
//        List<Object> objects = objectMapper.readValue(file, new TypeReference<List<Object>>(){});
//        for (Object object: objects) {
//            FixedBill fixedBill = objectMapper.<FixedBill>convertValue(object, FixedBill.class);
//            fixedBills.addFixedBill(fixedBill);
//        }
//    }

    public <T> void saveData(ArrayList<T> data, String file) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        try {
            String json = writer.writeValueAsString(data);
            String fileName = folderName + file;
            try (FileWriter fileWriter = new FileWriter(fileName)) {
                fileWriter.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveDataCustomer (Customers customers) {
        saveData(customers.getCustomers(), "customer.json");
    }
    public void saveDataProduct (Products products) {
        saveData(products.getProducts(), "product.json");
    }
    public void saveDataFixedBill (FixedBills fixedBills) {
        saveData(fixedBills.getFixedBills(), "fixedBill.json");
    }
//    public void saveDataBill (Bills bills) {
//
//    }
    public Customers getCustomers() throws IOException {
        loadDataCustomer();
        return customers;
    }
    public Products getProducts() throws IOException {
        loadDataProduct();
        return products;
    }
//    public FixedBills getFixedBills() throws  IOException {
//        loadDataFixedBill();
//        return fixedBills;
//    }
    public int getTotalCustomers() throws IOException {
        loadDataCustomer();
        return customers.getCustomers().size();
    }
    public  int getTotalProducts() throws  IOException {
        loadDataProduct();
        return products.getProducts().size();
    }
}
