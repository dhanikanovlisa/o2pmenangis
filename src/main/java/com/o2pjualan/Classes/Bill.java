package com.o2pjualan.Classes;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
@XmlRootElement(name = "bill")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bill implements Serializable {
    protected int idCustomer;
    protected HashMap<Integer, Integer> ListOfProduct;
    protected HashMap<Integer, Integer> ListPriceOfProduct;

    public Bill(int idCustomer) {
        this.idCustomer = idCustomer;
        this.ListOfProduct = new HashMap<Integer, Integer>();
        this.ListPriceOfProduct = new HashMap<Integer, Integer>();
    }

    public Bill(@JsonProperty("idCustomer")int idCustomer, @JsonProperty("listOfProduct")HashMap<Integer, Integer> listOfProduct, @JsonProperty("listPriceOfProduct")HashMap<Integer, Integer> listPriceOfProduct) {
        this.idCustomer = idCustomer;
        ListOfProduct = listOfProduct;
        ListPriceOfProduct = listPriceOfProduct;
    }

    public void AddProduct(int productCode, int quantity, int price) {
        this.ListOfProduct.put(productCode, quantity);
        this.ListPriceOfProduct.put(productCode, quantity * price);
    }

    public void RemoveProduct(int productCode) {
        this.ListOfProduct.remove(productCode);
    }

    public void print() {
        System.out.println("idCustomer: " + this.idCustomer);
        System.out.println("ListOfProducts: ");
        ListOfProduct.forEach((key, value) -> System.out.println("   " + key + ":" + value));
        System.out.println("ListPriceOfProdcuts: ");
        ListPriceOfProduct.forEach((key, value) -> System.out.println("   " + key + ":" + value));
    }
    @Override
    public String toString() {
        String result = new String();
        int i = 0;
        for (Map.Entry<Integer, Integer> product : this.ListOfProduct.entrySet()) {
            i++;
            result = String.format("%d. productCode: %d - quantity: %d - price: %d \n", i, product.getKey(), product.getValue(), ListPriceOfProduct.get(product.getKey()));
        }
        return result;
    }
}
    /*
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
        HashMap<Integer,Integer> newListOfPrice = newBill.getListPriceOfProduct();
        for (Map.Entry<Integer,Integer> product:newListOfProduct.entrySet()){
            if (!alreadyFound(product.getKey())){
                AddProduct(product.getKey(), product.getValue(), newListOfPrice.get(product.getKey()));
            } else{
                int key = product.getKey();
                int quantity = this.getListOfProduct().get(key) + product.getValue();
                int price = this.getListPriceOfProduct().get(key) + newListOfPrice.get(key);
                AddProduct(key, quantity, price);
            }
        }
    }


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
//        yow.printBill();
        yowas.printBill();
//    }*/
