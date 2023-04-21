package com.example.tokosinaro2p.classes;
import java.io.Serializable;
import java.util.*;

public class VIP extends Customer implements Serializable, Discount {
    private String name;
    private String phoneNumber;
    private double point;
    private boolean statusMembership;

    public VIP(Customer customer, String name, String phoneNumber) {
        super(customer.getIdBill());
        this.idCustomer = customer.getIdCustomer();
        this.idFixedBill = customer.getIdFixedBill();

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = 0;
        this.membership = "VIP";
        this.statusMembership = true;

        numberOfCustomer = numberOfCustomer - 1;
    }

    public int getIdFixedBill(int idx) {
        return (this.getIdFixedBill().get(idx));
    }

    public String getName() {
        return (this.name);
    }

    public String getPhoneNumber() {
        return (this.phoneNumber);
    }

    public double getPoint() {
        return (this.point);
    }

    public boolean getStatusMembership() {
        return (this.statusMembership);
    }
}
