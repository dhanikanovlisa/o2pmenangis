package com.o2pjualan.Classes;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Member extends Customer implements Serializable, Discount {
    private String name;
    private String phoneNumber;
    private double point;
    private boolean statusMembership;

    public Member(Customer customer, String name, String phoneNumber) throws IOException {
        this.idCustomer = customer.getIdCustomer();
        this.idFixedBill = customer.getIdFixedBill();
        this.idBill = customer.getIdBill();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = 0;
        this.membership = "Member";
        this.statusMembership = true;
        numberOfCustomer = numberOfCustomer - 1;

    }

    public Member(@JsonProperty("idCustomer")int idCustomer, @JsonProperty("idFixedBill")ArrayList<Integer> idFixedBill, @JsonProperty("idBill")int idBill, @JsonProperty("membership")String membership, @JsonProperty("name")String name, @JsonProperty("phoneNumber")String phoneNumber, @JsonProperty("point")double point, @JsonProperty("statusMembership")boolean statusMembership) {
        super(idCustomer, idFixedBill, idBill, membership);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = point;
        this.statusMembership = statusMembership;
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

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", point=" + point +
                ", statusMembership=" + statusMembership +
                ", idCustomer=" + idCustomer +
                ", idFixedBill=" + idFixedBill +
                ", idBill=" + idBill +
                ", membership='" + membership + '\'' +
                '}';
    }
}
