package com.o2pjualan.Classes;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;

import static com.o2pjualan.Main.controller;

@Data
@NoArgsConstructor
@XmlRootElement(name = "bill")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bill implements Serializable {
    protected int idCustomer;
    protected HashMap<Integer, Integer> ListOfProduct;
    protected HashMap<Integer, Double> ListPriceOfProduct;
    protected double totalBill;

    public Bill(int idCustomer) {
        this.idCustomer = idCustomer;
        this.ListOfProduct = new HashMap<Integer, Integer>();
        this.ListPriceOfProduct = new HashMap<Integer, Double>();
        this.totalBill = 0;
    }

    public Bill(@JsonProperty("idCustomer")int idCustomer,
                @JsonProperty("listOfProduct")HashMap<Integer, Integer> listOfProduct,
                @JsonProperty("listPriceOfProduct")HashMap<Integer, Double> listPriceOfProduct,
                @JsonProperty("totalBill")double totalBill) {
        this.idCustomer = idCustomer;
        ListOfProduct = listOfProduct;
        ListPriceOfProduct = listPriceOfProduct;
        this.totalBill = totalBill;
    }

    public double getTotalBill(){
        return this.totalBill;
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

    public FixedBill checkOutBill(double point, String membership, boolean isActive){

        Products p = controller.getProducts();
        HashMap<Integer, String> listNameofProduct = new HashMap<>();
        FixedBill moveFromBill = new FixedBill(this.idCustomer);
        moveFromBill.setListOfProduct(new HashMap<>(this.ListOfProduct));
        moveFromBill.setListPriceOfProduct(new HashMap<>(this.ListPriceOfProduct));
        for(Map.Entry<Integer, Integer> product : this.ListOfProduct.entrySet()){
            for(Product prod: p.getProducts()){
                if(prod.getProductCode() == product.getKey()){
                    listNameofProduct.put(prod.getProductCode(), prod.getProductName());
                }
            }
        }

        if (isActive) {
            if (membership.equals("VIP")) {
                moveFromBill.setDicsount(this.totalBill * 0.1);
            }
            moveFromBill.setPaidPoint(point);
        }

        moveFromBill.setListNameOfProduct(listNameofProduct);
        moveFromBill.setTotalFixedBill(this.totalBill);
        this.ListOfProduct.clear();
        this.ListPriceOfProduct.clear();
        this.totalBill = 0;


        return moveFromBill;
    }
}