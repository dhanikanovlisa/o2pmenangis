package com.o2pjualan.Classes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
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
public class FixedBill implements Serializable, printToPDF {
    protected int idBill;
    protected int idCustomer;
    protected static int countBill;
    protected HashMap<Integer,Integer> ListOfProduct;
    protected HashMap<Integer,Double> ListPriceOfProduct;
    protected double totalFixedBill;
    protected double paidPoint;

    public FixedBill(int idCustomer)  {
        this.idBill = controller.getTotalFixedBills() + 2001;
        this.idCustomer = idCustomer;
        this.ListOfProduct = new HashMap<>();
        this.ListPriceOfProduct = new HashMap<>();
        this.totalFixedBill = 0;
        this.paidPoint = 0;
    }

    public FixedBill(@JsonProperty("idBill") int idBill, @JsonProperty("idCustomer")int idCustomer,
                     @JsonProperty("ListOfProduct")HashMap<Integer, Integer> listOfProduct,
                     @JsonProperty("ListPriceOfProduct")HashMap<Integer, Double> listPriceOfProduct,
                     @JsonProperty("TotalFixedBill")double totalFixedBill,
                     @JsonProperty("Point")double point) {
        this.idBill = idBill;
        this.idCustomer = idCustomer;
        ListOfProduct = listOfProduct;
        ListPriceOfProduct = listPriceOfProduct;
        this.totalFixedBill = totalFixedBill;
        this.paidPoint = point;
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

    public void printPDF(Products products)  {
        Document document = new Document();

        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File selectedFile = fileChooser.showSaveDialog(null);
            if (selectedFile == null) {
                return; // The user cancelled the file chooser
            }

            // Create a PdfWriter to write the PDF to the selected file
            PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
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
            Paragraph nameCell = new Paragraph("Name: ", text);
            Paragraph statusCell = new Paragraph("Status: ", text);

            cellHeader.addElement(p);
            nameHeader.addElement(nameCell);
            statusHeader.addElement(statusCell);

            table.addCell(cellHeader);
            table.addCell(nameHeader);
            table.addCell(statusHeader);
            document.add(table);

            // Creating table for items
            PdfPTable tableItem = new PdfPTable(3);
            tableItem.setTotalWidth(new float[]{2f, 0.5f, 1f});
            tableItem.setLockedWidth(true);

            PdfPCell nameColumnHeader = new PdfPCell(new Paragraph("Product Name", new Font(Font.FontFamily.COURIER, 12, Font.BOLD)));
            PdfPCell qtyColumnHeader = new PdfPCell(new Paragraph("Qty", new Font(Font.FontFamily.COURIER, 12, Font.BOLD)));
            PdfPCell priceColumnHeader = new PdfPCell(new Paragraph("Price", new Font(Font.FontFamily.COURIER, 12, Font.BOLD)));

            tableItem.addCell(nameColumnHeader);
            tableItem.addCell(qtyColumnHeader);
            tableItem.addCell(priceColumnHeader);

            for (Map.Entry<Integer, Double> product : this.ListPriceOfProduct.entrySet()) {
                for (Product a : products.getProducts()) {
                    if (a.getProductCode() == product.getKey()) {
                        String name = "Product " + product.getKey();
                        String qty = Double.toString(product.getValue());
                        String price = Double.toString(a.getSellPrice() * product.getValue());

                        tableItem.addCell(new PdfPCell(new Paragraph(name, new Font(Font.FontFamily.COURIER, 12, Font.NORMAL))));
                        tableItem.addCell(new PdfPCell(new Paragraph(qty, new Font(Font.FontFamily.COURIER, 12, Font.NORMAL))));
                        tableItem.addCell(new PdfPCell(new Paragraph(price, new Font(Font.FontFamily.COURIER, 12, Font.NORMAL))));
                    }
                }
            }
            document.add(tableItem);

            PdfPTable tableTotal = new PdfPTable(2);
            tableTotal.setTotalWidth(new float[]{2.5f, 1f});
            tableTotal.setLockedWidth(true);


            tableTotal.addCell(new PdfPCell(new Paragraph("Total", new Font(Font.FontFamily.COURIER, 12, Font.NORMAL))));
            tableTotal.addCell(new PdfPCell(new Paragraph(Double.toString(getTotalFixedBill()),
                    new Font(Font.FontFamily.COURIER, 12, Font.NORMAL))));

            document.add(tableTotal);
            document.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}