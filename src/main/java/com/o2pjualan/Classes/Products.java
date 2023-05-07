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
        this.productCodes = new ArrayList<>();
    }
    public void addProduct(Product product) {
        this.productCodes.add(product);
    }

    public boolean validateProduct(Product product) {
        for(Product prod : this.productCodes){
            if(prod.getProductName().contains(product.getProductName())){
                return false;
            }
        }
        return true;
    }

    public int deleteProduct(int id) {
        for (int i = 0; i < this.productCodes.size(); i++) {
            if (this.productCodes.get(i).getProductCode() == id) {
                this.productCodes.remove(i);
                return 0;
            }
        }
        return 1;
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


    public ArrayList<String> getAllCategory() {
        Set<String> categories = new HashSet<>();
        categories.add("All");
        for (Product p : this.productCodes) {
            categories.add(p.getProductCategory());
        }
        ArrayList<String> productsCategory = new ArrayList<>(categories);
        return productsCategory;
    }

}