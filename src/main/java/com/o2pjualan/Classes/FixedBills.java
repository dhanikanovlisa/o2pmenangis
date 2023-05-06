package com.o2pjualan.Classes;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
@XmlRootElement(name = "fixedBills")
@XmlAccessorType(XmlAccessType.FIELD)

public class FixedBills implements Serializable {
    private ArrayList<FixedBill> fixedBills;
    public  FixedBills() {
        this.fixedBills = new ArrayList<FixedBill>();
    }

    public FixedBills(ArrayList<FixedBill> fixedBills) {
        this.fixedBills = fixedBills;
    }

    public void addFixedBill(FixedBill bill) {
        this.fixedBills.add(bill);
    }

    public FixedBill getFixedBillByID(int ID){
        for (FixedBill b: fixedBills){
            if (b.getIdBill() == ID) {
                return b;
            }
        }
        return null;
    }

    public void print () {
        for (FixedBill f: this.fixedBills) {
            f.print();
        }
    }
}
