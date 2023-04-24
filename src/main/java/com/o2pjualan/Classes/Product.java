package com.o2pjualan.Classes;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.Serializable;

import static com.o2pjualan.Main.controller;

public class Product implements Serializable {
    public static int numberOfProduct = 0;
    public int productCode;
    public String productName;
    public String productCategory;
    public double buyPrice;
    public double sellPrice;
    public int stock = 0;
    public String imagePath;

    public Product(String name, String category, double buyPrice, double sellPrice, int stock, String imagePath) throws IOException {
        numberOfProduct = controller.getTotalProducts() + 1;
        this.productCode = numberOfProduct;
        this.productName = name;
        this.productCategory = category;
        this.buyPrice = buyPrice;
        this.stock = stock;
        this.imagePath = imagePath;
        this.sellPrice = sellPrice;
    }

    public Product(@JsonProperty("productCode") int productCode, @JsonProperty("productName") String productName, @JsonProperty("productCategory")String productCategory, @JsonProperty("buyPrice")double buyPrice, @JsonProperty("sellPrice")double sellPrice, @JsonProperty("stock")int stock, @JsonProperty("imagePath")String imagePath) {
        this.productCode = productCode;
        this.productName = productName;
        this.productCategory = productCategory;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.stock = stock;
        this.imagePath = imagePath;
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

    @Override
    public String toString() {
        return "Product{" +
                "productCode=" + productCode +
                ", productName='" + productName + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                ", stock=" + stock +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}