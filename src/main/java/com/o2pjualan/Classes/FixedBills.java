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

    public HashMap<Integer, Integer> salesReport(){
        HashMap<Integer, Integer> sales = new HashMap<>();
        for(FixedBill b : fixedBills){
            for(Map.Entry<Integer, Integer> product : b.getListOfProduct().entrySet()){
                Integer key = product.getKey();
                Integer value = product.getValue();

                // If the key already exists in the HashMap, add the new value to the existing value
                if(sales.containsKey(key)){
                    value += sales.get(key);
                }

                sales.put(key, value);
            }
        }
        return sales;
    }


}
