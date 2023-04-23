package com.o2pjualan.Classes;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class Customers {
    private ArrayList<Customer> customers;


//    public  static  void main (String [] Args) throws IOException, ParseException {
//        JSONController controller = new JSONController();
//        Customers customers = controller.getCustomers();
//        Customer A = new Customer();
//        customers.addCustomer(A);
//        controller.saveDataCustomer(customers);
//    }
    public Customers() {
        this.customers = new ArrayList<Customer>();
    }

    public Customers(ArrayList<Customer> customers) {this.customers = customers; }

    public void addCustomer(Customer customer) {
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
    public Customer getCustomerByID (int id) {
        for (Customer cust: customers) {
            if (cust.getIdCustomer() == id) {
                return cust;
            }
        }
        return null;
    }

    public void registerMember (int id, String name, String phone) throws IOException {
        Customer cust = getCustomerByID(id);
        Member newMember = new Member(cust, name, phone);
        customers.remove(cust);
        customers.add((newMember));

    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public ArrayList<Integer> getCustomersId() {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (Customer cust : customers){
            String membership = cust.getMembership();
            if (membership.equals("Customer")) {
                ret.add(cust.getIdCustomer());
                System.out.println(cust.getIdCustomer());
            }
        }
        return ret;
    }


    @Override
    public String toString() {
        return "Customers{" +
                "customers=" + customers +
                '}';
    }
}