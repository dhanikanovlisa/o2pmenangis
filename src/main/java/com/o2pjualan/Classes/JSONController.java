package com.o2pjualan.Classes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.o2pjualan.Main.fileName;


public class JSONController {
    private Customers customers;
    public JSONController(){}

//    public static void main (String [] Args) throws IOException, ParseException {
//        Customers cs = new Customers();
//        for (int i = 0; i < 5; i++) {
//            Customer c = new Customer(i+1);
//            cs.addCustomer(c);
//        }

//
//        JSONController cont = new JSONController();
//        cs = cont.getCustomers();
//        ArrayList<Integer> ids = cs.getCustomersId();
//        System.out.println(ids.size());
//    }
    public void loadDataCustomer() throws IOException {
        customers = new Customers();
        ObjectMapper objectMapper = new ObjectMapper();
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

    public void saveDataCustomer(Customers customers) {
        ArrayList<Customer> data = customers.getCustomers();
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        try {
            String json = writer.writeValueAsString(data);

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


}
