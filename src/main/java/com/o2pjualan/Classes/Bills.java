package com.o2pjualan.Classes;
import java.util.*;

public class Bills {
    private ArrayList<Bill> bills;

    //default constructor
    public Bills(){
        this.bills = new ArrayList<Bill>();
    }

    public Bills(ArrayList<Bill> bills){
        this.bills = bills;
    }

    public void addBill(Bill bill){
        this.bills.add(bill);
    }

    public void removeBillbyID(int id){
        for(int i=0; i<this.bills.size(); i++){
            Bill el = bills.get(i);
            if (el.getIdCustomer() == id) {
                bills.remove(i);
                break;
            }
        }
    }

    public Bill getBillbyID(int id){
        for(Bill el: bills){
            if(el.getIdCustomer() == id){
                return el;
            }
        }
        return null;
    }

    public ArrayList<Bill> getBills(){
        return bills;
    }
}