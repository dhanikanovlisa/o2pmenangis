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