package com.o2pjualan.Classes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import static com.o2pjualan.Main.controller;

public class Customer implements Serializable {
    protected static int numberOfCustomer;
    protected int idCustomer;
    protected List<Integer> idFixedBill;
    protected int idBill;
    protected String membership;

    public Customer() throws IOException {
        numberOfCustomer = controller.getTotalCustomers() + 1;
        this.idCustomer = numberOfCustomer;

        this.idFixedBill = new ArrayList<Integer>();
        this.idBill = idCustomer;
        this.membership = "Customer";
    }

    public Customer(@JsonProperty("idCustomer")int idCustomer, @JsonProperty("idFixedBill")ArrayList<Integer> idFixedBill, @JsonProperty("idBill")int idBill, @JsonProperty("membership")String membership) {
        this.idCustomer = idCustomer;
        this.idFixedBill = idFixedBill;
        this.idBill = idBill;
        this.membership = membership;
    }

    public int getIdCustomer() {
        return (this.idCustomer);
    }

    public List<Integer> getIdFixedBill() {
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

    @Override
    public String toString() {
        return "Customer{" +
                "idCustomer=" + idCustomer +
                ", idFixedBill=" + idFixedBill +
                ", idBill=" + idBill +
                ", membership='" + membership + '\'' +
                '}';
    }
}