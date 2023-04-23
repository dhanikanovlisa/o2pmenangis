package com.o2pjualan.Classes;
import java.util.*;

public class FixedBill extends Bill {
    protected int idBill;
    protected static int countBill = 0;

    public FixedBill(int idCustomer){
        super(idCustomer);
        countBill++;
        this.idBill = countBill;
    }

    public int getIdBill(){
        return this.idBill;
    }

    /*
    public void printBill(){
        int i=0;
        for (Map.Entry<Integer,Integer> product:this.ListOfProduct.entrySet()){
            i++;
            System.out.printf("%d. productCode: %d - quantity: %d - price: %d \n",i,product.getKey(),product.getValue(),ListPriceOfProduct.get(product.getKey()));
        }
    }
    */
}