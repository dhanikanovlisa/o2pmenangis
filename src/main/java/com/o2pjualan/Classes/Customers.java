package com.example.tokosinaro2p.classes;
import java.util.*;

public class Customers {
    private ArrayList<Customer> customers;

    public Customers() {
        this.customers = new ArrayList<Customer>();
    }
    
    public void addProduct(Customer customer) {
        this.customers.add(customer);
    }

    public void deleteCustomer(int id) {
        for (int i = 0; i < this.customers.size(); i++) {
            if (this.customers.get(i).getIdCustomer() == id) {
                this.customers.remove(i);
                break;
            }
        }
    }
}