package com.o2pjualan.Classes;
import java.util.*;

public class Bill extends FixedBill{
    public Bill(int idCustomer){
        super(idCustomer);
    }

    public boolean alreadyFound(int K){
        boolean found = false;
        for (Map.Entry<Integer,Integer> product:this.ListOfProduct.entrySet()){
            if(product.getKey()==K){
                found = true;
            }
        }
        return found;
    }

    public void addBill(FixedBill newBill) {
        HashMap<Integer,Integer> newListOfProduct = newBill.getListOfProduct();
        HashMap<Integer,Integer> newListOfPrice = newBill.getListOfPrice();
        for (Map.Entry<Integer,Integer> product:newListOfProduct.entrySet()){
            if (!alreadyFound(product.getKey())){
                AddProduct(product.getKey(), product.getValue(), newListOfPrice.get(product.getKey()));
            } else{
                int key = product.getKey();
                int quantity = this.getListOfProduct().get(key) + product.getValue();
                int price = this.getListOfPrice().get(key) + newListOfPrice.get(key);
                AddProduct(key, quantity, price);
            }
        }
    }

    /*
    public static void main(String[] args) {
        FixedBill yow = new FixedBill(3);
        yow.AddProduct(45,3,30000);
        yow.AddProduct(34,3,50000);
        yow.AddProduct(45,4,30000);
        yow.AddProduct(36,1,35000);
        FixedBill yowes = new FixedBill(3);
        yowes.AddProduct(45,3,30000);
        yowes.AddProduct(34,3,50000);
        yowes.AddProduct(45,4,30000);
        yowes.AddProduct(37,1,35000);
        Bill yowas = new Bill(3);
        yowas.addBill(yow);
        yowas.addBill(yowes);
        yow.printBill();
        yowas.printBill();
    } */
}