package com.o2pjualan.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VIP extends Customer implements Serializable, Discount {
    @XmlElement
    private String name;
    private String phoneNumber;
    private double point;
    private Boolean statusMembership;

    public VIP(Customer customer, String name, String phoneNumber)  {
        super();
        this.idCustomer = customer.getIdCustomer();
        this.idFixedBill = customer.getIdFixedBill();
        this.idBill = customer.getIdBill();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = 0;
        this.membership = "VIP";
        this.statusMembership = true;
    }

    public VIP(@JsonProperty("idCustomer")int idCustomer, @JsonProperty("idFixedBill")ArrayList<Integer> idFixedBill, @JsonProperty("idBill")int idBill, @JsonProperty("membership")String membership, @JsonProperty("name")String name, @JsonProperty("phoneNumber")String phoneNumber, @JsonProperty("point")double point, @JsonProperty("statusMembership")boolean statusMembership) {
        super(idCustomer, idFixedBill, idBill, membership);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = point;
        this.statusMembership = statusMembership;
    }

    @Override
    public String toString() {
        return "VIP{" +
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

    public void addPoint(int point){
        this.point += point;
    }

    public void reducePoint(int point){
        this.point -= point;
    }
}
