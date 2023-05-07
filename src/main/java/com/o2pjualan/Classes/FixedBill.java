package com.o2pjualan.Classes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileOutputStream;
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
public class FixedBill implements Serializable {
    protected int idBill;
    protected int idCustomer;
    protected static int countBill;
    protected HashMap<Integer,Integer> ListOfProduct;
    protected HashMap<Integer,Double> ListPriceOfProduct;
    protected HashMap<Integer,String > ListNameOfProduct;
    protected double totalFixedBill;
    protected double paidPoint;
    protected double dicsount;

    public FixedBill(int idCustomer)  {
        this.idBill = controller.getTotalFixedBills() + 2001;
        this.idCustomer = idCustomer;
        this.ListOfProduct = new HashMap<>();
        this.ListPriceOfProduct = new HashMap<>();
        this.ListNameOfProduct = new HashMap<>();
        this.totalFixedBill = 0;
        this.paidPoint = 0;
    }

    public FixedBill(@JsonProperty("idBill") int idBill, @JsonProperty("idCustomer")int idCustomer,
                     @JsonProperty("ListOfProduct")HashMap<Integer, Integer> listOfProduct,
                     @JsonProperty("ListPriceOfProduct")HashMap<Integer, Double> listPriceOfProduct,
                     @JsonProperty("ListNameOfProduct")HashMap<Integer, String> listNameOfProduct,
                     @JsonProperty("TotalFixedBill")double totalFixedBill,
                     @JsonProperty("Point")double point,
                     @JsonProperty("Discount")double discount) {
        this.idBill = idBill;
        this.idCustomer = idCustomer;
        ListOfProduct = listOfProduct;
        ListPriceOfProduct = listPriceOfProduct;
        ListNameOfProduct = listNameOfProduct;
        this.totalFixedBill = totalFixedBill;
        this.paidPoint = point;
        this.dicsount = discount;
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

    public HashMap<Integer,Double> getListPriceOfProduct(){
        return this.ListPriceOfProduct;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public void setListOfProduct(HashMap<Integer, Integer> listOfProduct) {
        this.ListOfProduct = listOfProduct;
    }

    public void setListPriceOfProduct(HashMap<Integer, Double> listPriceOfProduct) {
        this.ListPriceOfProduct = listPriceOfProduct;
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

    public double getTotalFixedBill(){
        return this.totalFixedBill;
    }
    public double getPaidPoint(){
        return this.paidPoint;
    }

    public void setPaidPoint(double paidPoint){
        this.paidPoint = paidPoint;
    }
    public ArrayList<String> displayProductList() {
        HashMap<String, Double> products = new HashMap<>();
        for (int productCode : this.ListOfProduct.keySet()) {
            int quantity = this.ListOfProduct.get(productCode);
            double price = this.ListPriceOfProduct.get(productCode);
            String name = this.ListNameOfProduct.get(productCode);
            String key = name + "\t\t x" + quantity + " " + price;
            if (products.containsKey(key)) {
                double total = products.get(key) + (price * quantity);
                products.put(key, total);
            } else {
                products.put(key, price * quantity);
            }
        }

        ArrayList<String> productList = new ArrayList<>();
        for (String key : products.keySet()) {
            double total = products.get(key);
            productList.add(key + " : " + total);
        }
        return productList;
    }


    public void printPDF(String name)  {
        Document document = new Document();

        try{
            String directoryPath = "src/pdf/fixedBill/";
            String fileName = directoryPath + this.idBill + "_" + name + ".pdf";

            // Create a PdfWriter to write the PDF to the selected file
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);


            PdfPCell cellHeader = new PdfPCell();
            cellHeader.setBorder(PdfPCell.NO_BORDER);

            PdfPCell nameHeader = new PdfPCell();
            nameHeader.setBorder(PdfPCell.NO_BORDER);

            PdfPCell statusHeader = new PdfPCell();
            statusHeader.setBorder(PdfPCell.NO_BORDER);

            Font header = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
            Font text = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);

            String idBill = "#" + this.idBill;
            Paragraph p = new Paragraph(idBill, header);
            p.setAlignment(Element.ALIGN_LEFT);
            Paragraph nameCell = new Paragraph("Name: " + name, text);

            cellHeader.addElement(p);
            nameHeader.addElement(nameCell);
            table.addCell(cellHeader);
            table.addCell(nameHeader);

            ArrayList<String> productBill = displayProductList();
            for(String product: productBill){
                Paragraph line = new Paragraph(product, text);
                PdfPCell lineHeader = new PdfPCell();
                lineHeader.setBorder(PdfPCell.NO_BORDER);
                lineHeader.addElement(line);
                table.addCell(lineHeader);
            }

            Paragraph total = new Paragraph("Total\t\t : " + this.totalFixedBill, text);
            PdfPCell totalHeader = new PdfPCell();
            totalHeader.setBorder(PdfPCell.NO_BORDER);
            totalHeader.addElement(total);
            table.addCell(totalHeader);

            document.add(table);
            document.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}