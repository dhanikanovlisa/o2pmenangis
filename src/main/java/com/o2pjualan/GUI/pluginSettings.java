package com.o2pjualan.GUI;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import com.o2pjualan.Classes.pluginManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class pluginSettings extends Tab {
    private Button loadPlugin;
    private VBox pluginLayout;
    private HBox wholeLayout;
    private pluginManager pluginController;
    private AlertGUI alertGUI;
    private TabPane mainTabPane;
    private pluginManager pluginManager;
    private ArrayList<Class<?>> classes;

    public pluginSettings(TabPane mainTabPane){
        this.mainTabPane = mainTabPane;
        this.setText("Plugin");

        pluginLayout = new VBox();
        Label titlePlugin = new Label("Plugin");
        titlePlugin.setId("h1");
        this.loadPlugin = new Button("Upload Plugin");
        this.loadPlugin.setId("settingsButton");
        pluginController = new pluginManager();

        alertGUI = new AlertGUI();
        pluginManager = new pluginManager();

        loadPlugin.setOnAction(e-> {
            openFileDialog();

        });

        Button pieChart = new Button("Pie Chart");
        Button lineChart = new Button("Line Chart");
        HBox tes = new HBox();
        tes.getChildren().addAll(pieChart, lineChart);

        pieChart.setOnAction(e -> {
//            plugin1 pieplug = new plugin1();
//            mainTabPane.getTabs().add(pieplug);
            HashMap<String, Integer> dataSales = new HashMap<>();
            dataSales.put("Barang 1", 100);
            dataSales.put("Barang 2", 100);
            dataSales.put("Barang 3", 100);
            dataSales.put("Barang 4", 100);
        });

        lineChart.setOnAction(e -> {
//            plugin1 pieplug = new plugin1();
//            mainTabPane.getTabs().add(pieplug);
            HashMap<String, Integer> dataSales = new HashMap<>();
            dataSales.put("Barang 1", 100);
            dataSales.put("Barang 2", 100);
            dataSales.put("Barang 3", 100);
            dataSales.put("Barang 4", 100);
        });

        pluginLayout.getChildren().addAll(titlePlugin, this.loadPlugin);
        pluginLayout.setSpacing(20);

        wholeLayout = new HBox();
        wholeLayout.setId("wholeLayout");
        wholeLayout.getChildren().addAll(pluginLayout, tes);
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");

        Pane base = new Pane();
        Image illus = new Image("file:src/img/illustration1.png");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);
        base.setBackground(new Background(new BackgroundImage(illus, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));
        base.getChildren().add(wholeLayout);
        this.setContent(base);
    }

    public void openFileDialog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open JAR File");

        FileChooser.ExtensionFilter jarFilter = new FileChooser.ExtensionFilter("JAR Files", "*.jar");
        fileChooser.getExtensionFilters().add(jarFilter);

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();
            int crop = path.indexOf("src/");
            if(crop == -1){
                alertGUI.alertWarning("Cannot find in src file");
            } else {
                String pathFile = path.substring(crop);

                    try{
                        HashMap<String, Integer> dataSales = new HashMap<>();
                        dataSales.put("Barang 1", 100);
                        dataSales.put("Barang 2", 100);
                        dataSales.put("Barang 3", 100);
                        dataSales.put("Barang 4", 100);


                        Class<?> classTes = pluginController.loadJarFile(pathFile);
                        Method method = classTes.getDeclaredMethod("onLoadChart", HashMap.class);
                        Object pluginObject = classTes.getDeclaredConstructor().newInstance(); // Instantiate an object of classTes
                        Object chart = method.invoke(pluginObject, dataSales); // Invoke the method on the instantiated object

                        alertGUI.alertInformation("Successfully loaded plugin");
                        Node chartNode = (Node) chart;
                        basePlugin pluginTab = new basePlugin(mainTabPane);
                        pluginTab.addChart(chartNode);

                    } catch (Exception err){
                        System.out.println(err);
                    }
            }
        }

    }
}
