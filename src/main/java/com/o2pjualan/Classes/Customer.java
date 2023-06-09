package com.o2pjualan.Classes;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

import static com.o2pjualan.Main.controller;

@Data
@NoArgsConstructor
public  class Customer implements Serializable {
//    protected static int numberOfCustomer;
    protected Integer idCustomer;
    protected ArrayList<Integer> idFixedBill;
    protected Integer idBill;
    protected String membership;

    // kalo mau create new Customer gabisa pake yg no args constructor, soalnya dipake buat library parsing
    // jadi create aja new Customer(0) .... id nya tetep kehandle di custructornya harusnya
    public Customer(Integer idCust)  {
        idCust = controller.getCustomers().getCustomers().size() + 1;
        this.idCustomer = idCust;
        this.idFixedBill = new ArrayList<>();
        this.idBill = idCustomer;
        this.membership = "Customer";
    }

    public Customer(@JsonProperty("idCustomer")int idCustomer, @JsonProperty("idFixedBill")ArrayList<Integer> idFixedBill, @JsonProperty("idBill")int idBill, @JsonProperty("membership")String membership) {
        this.idCustomer = idCustomer;
        this.idFixedBill = idFixedBill;
        this.idBill = idBill;
        this.membership = membership;
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
    public void addFixedBill(Integer idFixedBill) {
        this.idFixedBill.add(idFixedBill);
    }

}