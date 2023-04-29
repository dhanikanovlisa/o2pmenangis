package com.o2pjualan.Classes;

import lombok.Data;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@Data
@XmlRootElement(name = "bills")
@XmlAccessorType(XmlAccessType.FIELD)

public class Bills implements Serializable {
    private ArrayList<Bill> bills;
    public Bills() {
        this.bills = new ArrayList<Bill>();
    }
    public void addBill(Bill bill) {
        this.bills.add(bill);
    }

    public Bill getBillByID(int ID){
        for (Bill b: bills){
            if (b.getIdCustomer() == ID) {
                return b;
            }
        }
        return null;
    }
    public void print() {
        if (this.bills.size() != 0) {
            for (Bill b : this.bills) {
                b.print();
                System.out.println(" ");
            }
        } else {
            System.out.println("Empty Bills");
        }
    }

}
