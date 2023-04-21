package com.example.tokosinaro2p.classes;
import java.io.Serializable;
import java.util.*;

public class Customer implements Serializable {
    protected static int numberOfCustomer = 0;
    protected int idCustomer;
    protected ArrayList<Integer> idFixedBill;
    protected int idBill;
    protected String membership;

    public Customer(int idBill) {
        numberOfCustomer = numberOfCustomer + 1;
        this.idCustomer = numberOfCustomer;

        this.idFixedBill = new ArrayList<Integer>();
        this.idBill = idBill;
        this.membership = "Customer";
    }

    public int getIdCustomer() {
        return (this.idCustomer);
    }

    public ArrayList<Integer> getIdFixedBill() {
        return (this.idFixedBill);
    }

    public void addFixedBill(int id) {
        this.idFixedBill.add(id);
    }

    public String getMembership() {
        return (this.membership);
    }

    public int getIdBill() {
        return (this.idBill);
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }
}