package com.o2pjualan.GUI;

import com.o2pjualan.Classes.pluginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;

import java.util.HashMap;
import java.util.Map;

public class pluginPieChart implements pluginController {
    private PieChart chart;

    public pluginPieChart() {
        this.chart = new PieChart();
        this.chart.setTitle("Data Sales");
    }

    public Node onLoadChart(HashMap<String, Integer> dataSales) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Add the data to the chart
        for (Map.Entry<String, Integer> entry : dataSales.entrySet()) {
            String productName = entry.getKey();
            Integer productSold = entry.getValue();
            PieChart.Data dataPie = new PieChart.Data(productName, productSold);
            pieChartData.add(dataPie);
        }

        // Set the data for the chart
        chart.setData(pieChartData);
        return chart;
    }
}
