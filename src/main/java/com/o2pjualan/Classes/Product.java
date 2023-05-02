package com.o2pjualan.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

import static com.o2pjualan.Main.controller;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Data

public class Product implements Serializable {
    public static int numberOfProduct = 0;
    public int productCode;
    public String productName;
    public String productCategory;
    public double buyPrice;
    public double sellPrice;
    public int stock = 0;
    public String imagePath;

    public Product(String name, String category, double buyPrice, double sellPrice, int stock, String imagePath) {
        ArrayList<Product> prodList= controller.getProducts().getProducts();
        int maxID = prodList.stream().mapToInt(Product::getProductCode).max().orElse(0);
        numberOfProduct = maxID + 1;

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

    public boolean validateStock(int quantity){
        if(quantity > stock){
            return false;
        } else {
            return true;
        }
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

    public boolean reduceStock(int stock){
        if(this.stock >= stock){
            this.stock -= stock;
            return true;
        }
        return false;

    }

}