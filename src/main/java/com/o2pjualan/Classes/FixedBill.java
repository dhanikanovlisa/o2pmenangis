package com.o2pjualan.Classes;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.stage.FileChooser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import static com.o2pjualan.Main.controller;

@Data
@XmlRootElement(name = "fixedBill")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class FixedBill implements Serializable, printToPDF {
    protected int idBill;
    protected int idCustomer;
    protected static int countBill;
    protected HashMap<Integer,Integer> ListOfProduct;
    protected HashMap<Integer,Integer> ListPriceOfProduct;

    public FixedBill(int idCustomer)  {
        this.idBill = controller.getTotalFixedBills() + 2001;
        this.idCustomer = idCustomer;
        this.ListOfProduct = new HashMap<Integer,Integer>();
        this.ListPriceOfProduct = new HashMap<Integer,Integer>();
    }

    public FixedBill(@JsonProperty("idBill") int idBill, @JsonProperty("idCustomer")int idCustomer, @JsonProperty("ListOfProduct")HashMap<Integer, Integer> listOfProduct, @JsonProperty("ListPriceOfProduct")HashMap<Integer, Integer> listPriceOfProduct) {
        this.idBill = idBill;
        this.idCustomer = idCustomer;
        ListOfProduct = listOfProduct;
        ListPriceOfProduct = listPriceOfProduct;
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

    public HashMap<Integer,Integer> getListPriceOfProduct(){
        return this.ListPriceOfProduct;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public void setListOfProduct(HashMap<Integer, Integer> listOfProduct) {
        ListOfProduct = listOfProduct;
    }

    public void setListPriceOfProduct(HashMap<Integer, Integer> listPriceOfProduct) {
        ListPriceOfProduct = listPriceOfProduct;
    }

    public void AddProduct(int productCode, int quantity, int price){
        this.ListOfProduct.put(productCode,quantity);
        this.ListPriceOfProduct.put(productCode,quantity*price);
    }

    public void RemoveProduct(int productCode){
        this.ListOfProduct.remove(productCode);
    }

    public void print(){
        System.out.println(this.toString());
        int i=0;
        for (Map.Entry<Integer,Integer> product:this.ListOfProduct.entrySet()){
            i++;
            System.out.printf("%d. productCode: %d - quantity: %d - price: %d \n",i,product.getKey(),product.getValue(),ListPriceOfProduct.get(product.getKey()));
        }
    }

    @Override
    public String toString() {
        return "FixedBill{" +
                "idBill=" + idBill +
                ", idCustomer=" + idCustomer +
                ", ListOfProduct=" + ListOfProduct +
                ", ListPriceOfProduct=" + ListPriceOfProduct +
                '}';
    }

    public int getTotalFixedBill(){
        int total = 0;
        for (Map.Entry<Integer,Integer> product:this.ListPriceOfProduct.entrySet()){
            total += product.getValue();
        }
        return total;
    }

    public void printPDF() throws IOException {
        PDDocument document = new PDDocument();

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF Files", "*.pdf");
        fileChooser.getExtensionFilters().add(pdfFilter);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            document.save(file);
        }
        document.close();
    }
}