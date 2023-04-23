package com.o2pjualan.Classes;
import java.util.*;

public class FixedBill {
    protected int idBill;
    protected int idCustomer;
    protected static int countBill = 0;
    protected HashMap<Integer,Integer> ListOfProduct;
    protected HashMap<Integer,Integer> ListPriceOfProduct;

    public FixedBill(int idCustomer){
        countBill++;
        this.idBill = countBill;
        this.idCustomer = idCustomer;
        this.ListOfProduct = new HashMap<Integer,Integer>();
        this.ListPriceOfProduct = new HashMap<Integer,Integer>();
    }

    public int getIdBill(){
        return this.idBill;
    }

    public int getIdCustomer(){
        return this.idCustomer;
    }

    public HashMap<Integer,Integer> getListOfProduct() {
        return this.ListOfProduct;
    }

    public HashMap<Integer,Integer> getListOfPrice(){
        return this.ListPriceOfProduct;
    }

    public void AddProduct(int productCode, int quantity, int price){
        //if(ListOfProduct.get(ListOfProduct)==0){
        this.ListOfProduct.put(productCode,quantity);
        this.ListPriceOfProduct.put(productCode,quantity*price);
        //} else {
        //    this.ListOfProduct.put(productCode,ListOfProduct.get(productCode) + quantity);
        //    this.ListPriceOfProduct.put(productCode, ListPriceOfProduct.get(productCode) + quantity*price);
        //}
    }

    public void RemoveProduct(int productCode){
        this.ListOfProduct.remove(productCode);
    }

    public void printBill(){
        int i=0;
        for (Map.Entry<Integer,Integer> product:this.ListOfProduct.entrySet()){
            i++;
            System.out.printf("%d. productCode: %d - quantity: %d - price: %d \n",i,product.getKey(),product.getValue(),ListPriceOfProduct.get(product.getKey()));
        }
    }
}