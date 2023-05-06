package com.o2pjualan.GUI;

import com.o2pjualan.Classes.pluginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;

import java.util.HashMap;
import java.util.Map;

public class pluginLineBarChart implements pluginController {
    private LineChart<String, Number> lineChart;
    private BarChart barChart;

    public pluginLineBarChart() {
        CategoryAxis productName = new CategoryAxis();
        NumberAxis productSale = new NumberAxis();
        productName.setLabel("Product");
        productSale.setLabel("Sales");
        this.lineChart = new LineChart<>(productName, productSale);
        this.lineChart.setTitle("Data Sales");
        this.barChart = new BarChart<>(productName, productSale);
        this.barChart.setTitle("Data Sales");
    }

    public Node onLoadChart(HashMap<String, Integer> dataSales) {
        HBox layout = new HBox();
        layout.getChildren().addAll(lineChart, barChart);
        createChart(dataSales);
        return layout;
    }

    public void createChart(HashMap<String, Integer> dataSales) {
        XYChart.Series<String, Number> lineData = new XYChart.Series<>();
        XYChart.Series<String, Number> barData = new XYChart.Series<>();

        for (Map.Entry<String, Integer> entry : dataSales.entrySet()) {
            String productName = entry.getKey();
            Integer productSold = entry.getValue();
            lineData.getData().add(new XYChart.Data<>(productName, productSold));
            barData.getData().add(new XYChart.Data<>(productName, productSold));
        }

        lineChart.getData().add(lineData);
        barChart.getData().add(barData);
    }
}
