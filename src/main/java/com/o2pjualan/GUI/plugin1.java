package com.o2pjualan.GUI;

import com.o2pjualan.Classes.FixedBills;
import com.o2pjualan.Classes.Product;
import com.o2pjualan.Classes.Products;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import javafx.embed.swing.*;

import java.util.HashMap;
import java.util.Map;

import static com.o2pjualan.Main.controller;

public class plugin1 extends Tab {
    private Products listProducts;
    private FixedBills fixedBills;
    private HashMap<Integer, Integer> sales;
    public plugin1(){

        this.setText("Ceritanya plugin1");
        fixedBills = controller.getFixedBills();
        listProducts = controller.getProducts();

        DefaultPieDataset dataset = new DefaultPieDataset();
        sales = fixedBills.salesReport();
        for(Map.Entry<Integer, Integer> product : sales.entrySet()){
            for(Product p: listProducts.getProducts()){
                if(p.getProductCode() == product.getKey()){
                    dataset.setValue(p.getProductName(), product.getValue());
                }
            }
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Data Sales Report",  // chart title
                dataset,           // data
                true,              // include legend
                true,              // tooltips
                false              // urls
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        // create SwingNode to hold chart panel
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(chartPanel);

        // create VBox to hold SwingNode
        VBox vbox = new VBox(swingNode);



        VBox wholeLayout = new VBox();
        wholeLayout.getChildren().addAll(vbox);
        wholeLayout.setPadding(new Insets(10));
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
        Pane base = new Pane();
        Image illus = new Image("file:src/img/illustration1.png");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);

        base.getChildren().add(wholeLayout);
    }
}
