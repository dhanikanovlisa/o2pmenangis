package com.o2pjualan.Classes;
import java.io.Serializable;

public class Product implements Serializable {
    public static int numberOfProduct = 0;
    public int productCode;
    public String productName;
    public String productCategory;
    public double buyPrice;
    public double sellPrice;
    public int stock = 0;
    public String imagePath;

    public Product(String name, String category, double buyPrice, double sellPrice) {
        numberOfProduct = numberOfProduct + 1;
        this.productCode = numberOfProduct;

        this.productName = name;
        this.productCategory = category;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public int getProductCode() {
        return (this.productCode);
    }
    
    public void changeStock(int val) {
        this.stock = val;
    }

    public void setImage(String path) {
        this.imagePath = path;
    }

    public void setSellPrice(double price) {
        this.sellPrice = price;
    }
}