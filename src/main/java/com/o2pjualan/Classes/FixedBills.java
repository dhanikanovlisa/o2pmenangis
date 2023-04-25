package com.o2pjualan.Classes;

import java.util.ArrayList;

public class FixedBills {
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
    public ArrayList<FixedBill> getFixedBills() {
        return this.fixedBills;
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
