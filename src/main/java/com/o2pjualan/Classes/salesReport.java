package com.o2pjualan.Classes;

import java.util.HashMap;
import java.util.Map;

public class salesReport {
    private FixedBills fixedBills;
    protected HashMap<Integer, Integer> listofAllProductSales;
    protected HashMap<Integer, Double> listofAllProductPrices;
    protected HashMap<Integer, String> listofAllProductNames;

    public salesReport(FixedBills fixedBills){
        this.fixedBills = fixedBills;
        this.listofAllProductNames = new HashMap<>();
        this.listofAllProductPrices = new HashMap<>();
        this.listofAllProductSales = new HashMap<>();
    }

    public void displaySalesReport(){
        for(FixedBill fixedBill: fixedBills.getFixedBills()){
            for(Map.Entry<Integer, Integer> product : fixedBill.ListOfProduct.entrySet()){
                int getProductCode = product.getKey();
                int getProductQuantity = product.getValue();



            }
        }
    }
}
