package com.o2pjualan.Classes;
import java.util.*;

public class FixedBills {
    private ArrayList<FixedBill> fixedbills;

    //default constructor
    public FixedBills(){
        this.fixedbills = new ArrayList<FixedBill>();
    }

    public FixedBills(ArrayList<FixedBill> fixedbills){
        this.fixedbills = fixedbills;
    }

    public void addBill(FixedBill fixedbill){
        this.fixedbills.add(fixedbill);
    }

    public void removeFixedBillbyID(int id){
        for(int i=0; i<this.fixedbills.size(); i++){
            if (fixedbills.get(i).getIdBill() == id) {
                fixedbills.remove(i);
                break;
            }
        }
    }

    public FixedBill getFixedBillbyID(int id){
        for(FixedBill el: fixedbills){
            if(el.getIdBill() == id){
                return el;
                break;
            }
        }
        return null;
    }

    public fixedbills getFixedBills(){
        return fixedbills;
    }
}