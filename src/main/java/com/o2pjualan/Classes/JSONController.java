package com.o2pjualan.Classes;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.o2pjualan.Main.folderName;


public class JSONController implements FileController{
    private Customers customers;
    private Products products;
    private Bills bills;
    private FixedBills fixedBills;
    private Plugins plugins;

    public JSONController() throws IOException {
        loadDataCustomer();
        loadDataProduct();
        loadDataBill();
        loadDataFixedBill();
        loadDataPlugin();
    }

    public void saveDataCustomer (Customers customers) {
        this.customers = customers;
        saveData(customers.getCustomers(), "customer.json");
    }
    public void saveDataProduct (Products products) {
        this.products = products;
        saveData(products.getProducts(), "product.json");
    }
    public void saveDataFixedBill (FixedBills fixedBills) {
        this.fixedBills = fixedBills;
        saveData(fixedBills.getFixedBills(), "fixedBill.json");
    }
    public void saveDataBill (Bills bills) {
        this.bills = bills;
        saveData(bills.getBills(), "bill.json") ;}

    @Override
    public void saveDataPlugins(Plugins plugins) {
        this.plugins = plugins;
        saveData(plugins.getPlugins(), "plugin.json");
    }


    public Customers getCustomers() {
        return customers;
    }

    public Products getProducts() {
        return products;
    }

    public Bills getBills() {
        return bills;
    }

    public FixedBills getFixedBills() {
        return fixedBills;
    }

    @Override
    public Plugins getPlugins() {
        return plugins;
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

    public void loadDataBill() throws IOException {
        this.bills = new Bills();
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = folderName + "bill.json";
        File file = new File(fileName);
        List<Object> objects = objectMapper.readValue(file, new TypeReference<List<Object>>(){});
        for (Object object: objects) {
            Bill bill = objectMapper.convertValue(object, Bill.class);
            bills.addBill(bill);
        }
    }
    public void loadDataFixedBill() throws  IOException{
        this.fixedBills = new FixedBills();
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = folderName + "fixedBill.json";
        File file = new File(fileName);
        List<Object> objects = objectMapper.readValue(file, new TypeReference<List<Object>>(){});
        for (Object object: objects) {
            FixedBill fixedBill = objectMapper.convertValue(object, FixedBill.class);
            fixedBills.addFixedBill(fixedBill);
        }
    }

    public void loadDataPlugin() throws IOException {
        this.plugins = new Plugins();
        ObjectMapper objectMapper = new ObjectMapper();
        String fileName = folderName + "plugin.json";
        File file = new File(fileName);
        List<Object> objects = objectMapper.readValue(file, new TypeReference<List<Object>>(){});
        for (Object object: objects) {
            Plugin plugin = objectMapper.convertValue(object, Plugin.class);
            plugins.addPlugin(plugin);
        }
    }

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

    public int getTotalCustomers()  {
        return customers.getCustomers().size();
    }
    public  int getTotalProducts() {
        return products.getProducts().size();
    }
    public int getTotalFixedBills() {
        return fixedBills.getFixedBills().size();
    }

}
