package com.o2pjualan.Classes;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

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

    public void deactivate (int id) {
        Customer selected = getCustomerByID(id);
        if (selected.getMembership().equals("Member")) {
            ((Member) selected).setStatusMembership(false);
        } else {
            ((VIP) selected).setStatusMembership(false);
        }
    }



    // get Id by membership, parameter Both buat get VIP sama Member sekaligus
    public ArrayList<Integer> getIdsByMembership(String membership) {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        if (membership.equals("Both")) {
            for (Customer cust : customers) {
                if (cust.getMembership().equals("VIP") || cust.getMembership().equals("Member")) {
                    ret.add(cust.getIdCustomer());
                }
            }
        } else {
            for (Customer cust : customers) {
                if (cust.getMembership().equals(membership)) {
                    ret.add(cust.getIdCustomer());
                }
            }
        }
        return ret;
    }

    public String getMembershipByIds(int id){
        for(Customer cust: customers){
            if(cust.getIdCustomer().equals(id)){
                return cust.getMembership();
            }
        }
        return "";
    }

    // Buat get nama member/vip by id
    public String getCustomerNameById(int id) {
        for (Customer cust : customers) {
            if (cust.getIdCustomer() == id) {
                if (cust.getMembership().equals("Member")) {
                    return ((Member) cust).getName();
                } else if (cust.getMembership().equals("VIP")){
                    return  ((VIP) cust).getName();
                }
            }
        }
        return "";
    }

    public int getCustomerIdByName(String name) {
        for (Customer cust : customers) {
            if (cust.getMembership().equals("Member")) {
                if(((Member) cust).getName().equals(name)){
                    return cust.getIdCustomer();
                }
            } else if (cust.getMembership().equals("VIP")){
                if(((VIP) cust).getName().equals(name)){
                    return cust.getIdCustomer();
                }
            }
        }
        return -1;
    }
    public ArrayList<String> getCustomerName() {
        ArrayList<String> customersName = new ArrayList<>();
        for (Customer cust : customers) {
            if (cust.getMembership().equals("Member")) {
                    customersName.add(((Member) cust).getName());
                } else if (cust.getMembership().equals("VIP")){
                    customersName.add(((VIP) cust).getName());
                }

        }
        return customersName;
    }

    public ArrayList<Customer> getActiveMembers(Boolean active) {
        ArrayList<Customer> ret = new ArrayList<>();
        for (Customer cust: customers) {
            if (cust.getMembership().equals("Member")) {
                if (((Member) cust).getStatusMembership() == active) {
                    ret.add(cust);
                }
            } else if (cust.getMembership().equals("VIP")) {
                if (((VIP) cust).getStatusMembership() == active) {
                    ret.add(cust);
                }
            }
        }
        return ret;
    }

    public ArrayList<Integer> getCustomerForSignUp() {
        ArrayList<Integer> ret = new ArrayList<>();
        for (Customer cust : customers) {
            if (cust.getMembership().equals("Customer") && cust.getIdFixedBill().size() != 0) {
                ret.add(cust.getIdCustomer());
            }
        }
        return ret;
    }

    public double pointCalculation(int idCust, double totalPrice) {
        double poin = 0;
        for (Customer cust : customers) {
            if(cust.getIdCustomer() == idCust){
                if (cust.getMembership().equals("Member")) {
                    poin += totalPrice * 0.01;
                }else if (cust.getMembership().equals("VIP")) {
                    poin += totalPrice * 0.05;
                }
            }
        }
        return poin;
    }


    public ArrayList<Integer> getCustomersId() {
        ArrayList<Integer> custId = new ArrayList<>();
        for (Customer cust : customers) {
            if (cust.getMembership().equals("Member") || cust.getMembership().equals("VIP")) {
                custId.add(cust.getIdCustomer());
            } else{
                if(cust.getIdFixedBill().size() == 0){
                    custId.add(cust.getIdCustomer());
                }
            }
        }
        return custId;
    }

    public ArrayList<Integer> getAllCustomersId() {
        ArrayList<Integer> ret = new ArrayList<>();
        for (Customer cust : customers) {
            ret.add(cust.getIdCustomer());
        }

        return ret;
    }

    public void addFixedBill(int idFixedBill, int idCust) {
        for(Customer c: customers){
            if(c.getIdCustomer() == idCust){
                c.addFixedBill(idFixedBill);
            }
        }
    }


    public void print() {
        for (Customer c : this.customers) {
            System.out.println(c.toString());
        }
    }


}