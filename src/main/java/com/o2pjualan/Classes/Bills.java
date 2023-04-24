package com.o2pjualan.Classes;

import java.util.ArrayList;

public class Bills {
    private ArrayList<Bill> bills;
    public Bills() {
        this.bills = new ArrayList<Bill>();
    }
    public void addBill(Bill bill) {
        this.bills.add(bill);
    }
    public ArrayList<Bill> getBills () {
        return  this.bills;
    }
    public void print() {
        for (Bill b: this.bills) {
            b.print();
        }
    }

}
