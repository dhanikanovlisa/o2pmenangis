package com.o2pjualan.Classes;
import java.util.*;

public class Products {
    private ArrayList<Product> productCodes;

    public Products() {
        this.productCodes = new ArrayList<Product>();
    }

    public void addProduct(Product product) {
        this.productCodes.add(product);
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

    public void print() {
        for (Product p : this.productCodes) {
            System.out.println(p.toString());
        }
    }


}