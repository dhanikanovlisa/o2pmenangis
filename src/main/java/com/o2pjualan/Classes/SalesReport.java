package com.o2pjualan.Classes;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SalesReport {
    private FixedBills fixedBills;
    protected HashMap<String, Integer> listOfAllProductSales;

    public SalesReport(FixedBills fixedBills) {
        this.fixedBills = fixedBills;
        this.listOfAllProductSales = new HashMap<>();
        countSalesReport(); // Call the method to count the sales report immediately upon instantiation
    }

    public HashMap<String, Integer> getListOfAllProductSales() {
        return this.listOfAllProductSales;
    }

    private void countSalesReport() {
        for (FixedBill fixedBill : fixedBills.getFixedBills()) {
            for (Map.Entry<Integer, Integer> product : fixedBill.getListOfProduct().entrySet()) {
                int getProductQuantity = product.getValue();
                String getProductName = fixedBill.getListNameOfProduct().get(product.getKey());

                boolean productFound = false;
                for (Map.Entry<String, Integer> products : this.listOfAllProductSales.entrySet()) {
                    if (products.getKey().equals(getProductName)) {
                        int updatedQuantity = getProductQuantity + products.getValue();
                        this.listOfAllProductSales.put(getProductName, updatedQuantity);
                        productFound = true;
                        break;
                    }
                }

                if (!productFound) {
                    this.listOfAllProductSales.put(getProductName, getProductQuantity);
                }
            }
        }
    }

    public void printPDF() {
        Document document = new Document();

        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            String directoryPath = "src/pdf/report/";
            String fileName = directoryPath + currentDateTime + "_salesReport" + ".pdf";

            // Create a PdfWriter to write the PDF to the selected file
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);

            PdfPCell cellHeader = new PdfPCell();
            cellHeader.setBorder(PdfPCell.NO_BORDER);

            PdfPCell nameHeader = new PdfPCell();
            nameHeader.setBorder(PdfPCell.NO_BORDER);

            Font header = new Font(Font.FontFamily.COURIER, 20, Font.BOLD);
            Font text = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);

            String idBill = "Sales Report ";
            Paragraph p = new Paragraph(idBill, header);
            p.setAlignment(Element.ALIGN_LEFT);
            Paragraph nameCell = new Paragraph(currentDateTime.toString(), text);

            cellHeader.addElement(p);
            nameHeader.addElement(nameCell);
            table.addCell(cellHeader);
            table.addCell(nameHeader);

            for (Map.Entry<String, Integer> products : this.listOfAllProductSales.entrySet()) {
                Paragraph line = new Paragraph(products.getKey() + "\t\t\t x" + products.getValue(), text);
                PdfPCell lineHeader = new PdfPCell();
                lineHeader.setBorder(PdfPCell.NO_BORDER);
                lineHeader.addElement(line);
                table.addCell(lineHeader);
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}