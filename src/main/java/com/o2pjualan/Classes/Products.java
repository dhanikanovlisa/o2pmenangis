package com.o2pjualan.Classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class Products implements Serializable {
    private ArrayList<Product> productCodes;

    public Products() {
        this.productCodes = new ArrayList<Product>();
    }
    public void addProduct(Product product) {
        this.productCodes.add(product);
    }

    public boolean addNewProduct(Product product) {
        for(Product prod : this.productCodes){
            if(prod.getProductName().contains(product.getProductName())){
                return false;
            }
        }
        return true;
    }

    public void deleteProduct(int id) {
        for (int i = 0; i < this.productCodes.size(); i++) {
            if (this.productCodes.get(i).getProductCode() == id) {
                this.productCodes.remove(i);
                break;
            }
        }
    }

    public ArrayList<Product> getProducts() {
        return productCodes;
    }

    public Product getProductById(int id){
        for (int i = 0; i < this.productCodes.size(); i++) {
            if (this.productCodes.get(i).getProductCode() == id) {
                return this.productCodes.get(i);
            }
        }
        return null;
    }

    public void print() {
        for (Product p : this.productCodes) {
            System.out.println(p.toString());
        }
    }

    public ArrayList<String> getAllCategory(){
        ArrayList<String> productsCategory = new ArrayList<>();
        for(Product p: this.productCodes){
            for(String s: productsCategory){
                if(!s.equals(p.productName)){
                    productsCategory.add(p.getProductCategory());
                }
            }
        }
    return productsCategory;
    }

}