package com.o2pjualan.Classes;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Data
@XmlRootElement(name = "customers")
@XmlSeeAlso({Member.class, VIP.class})
@XmlAccessorType(XmlAccessType.FIELD)
public class Customers implements Serializable {
    private ArrayList<Customer> customers;
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


    public void registerMember (int id, String name, String phone)  {
        Customer cust = getCustomerByID(id);
        Member newMember = new Member(cust, name, phone);
        customers.remove(cust);
        customers.add((newMember));
    }

    public void upgradeVIP(int id) {
        Member cust =(Member) getCustomerByID(id);
        VIP newVIP = new VIP(cust, cust.getName(), cust.getPhoneNumber());
        customers.remove(cust);
        customers.add(newVIP);
    }

    public ArrayList<Integer> getIdsByMembership(String membership) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (Customer cust : customers){
            if (cust.getMembership().equals(membership)) {
                ret.add(cust.getIdCustomer());
            }
        }
        return ret;
    }

    public String getCustomerNameById(int id) {
        for (Customer cust : customers) {
            if (cust.getIdCustomer() == id) {

                return ((Member) cust).getName();
            }
        }
        return "";
    }

    public ArrayList<Integer> getCustomersId() {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for (Customer cust : customers){
            ret.add(cust.getIdCustomer());
        }
        return ret;
    }

    public void print() {
        for (Customer c : this.customers) {
            System.out.println(c.toString());
        }
    }

}