package com.o2pjualan.GUI;

import com.o2pjualan.Classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.scene.Node;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import static com.o2pjualan.Main.controller;

public class pluginSettings extends Tab {
    private Button loadPlugin;
    private VBox pluginLayout;
    private HBox wholeLayout;
    private AlertGUI alertGUI;
    private TabPane mainTabPane;
    private ComboBox<String> plugins;
    private ArrayList<String> listPlugin;
    private Plugins loadedPlugins;
    private Button runPlugin;
    private Button removePlugin;
    private HBox pluginExec;
    private FixedBills fixedBills;
    private HashMap<String, Integer> sales;
    private SalesReport salesData;


    public pluginSettings(TabPane mainTabPane){
        this.mainTabPane = mainTabPane;
        this.setText("Plugin");

        pluginLayout = new VBox();
        Label titlePlugin = new Label("Plugin");
        titlePlugin.setId("h1");
        this.loadPlugin = new Button("Upload Plugin");
        this.loadPlugin.setId("settingsButton");
        loadedPlugins = controller.getPlugins();
        fixedBills = controller.getFixedBills();

        alertGUI = new AlertGUI();
        plugins = new ComboBox<>();
        plugins.setId("idDropDown");
        updateData();

        loadPlugin.setOnAction(e-> {
            openFileDialog();
        });

        pluginExec = new HBox();
        runPlugin = new Button("Load Plugin");
        removePlugin = new Button("Remove Plugin");
        runPlugin.setId("settingsButton");
        removePlugin.setId("settingsButton");

        this.loadPlugin.setOnAction(e->{
            String selected = validatePlugin();
            if(!selected.equals("")){
                runPlugin(selected);
            }
        });

        this.removePlugin.setOnAction(e->{
            String selected = validatePlugin();
            if(!selected.equals("")){

            }
        });
        pluginExec.getChildren().addAll(runPlugin, removePlugin);
        pluginExec.setSpacing(50);


        pluginLayout.getChildren().addAll(titlePlugin, this.loadPlugin, plugins, pluginExec);
        pluginLayout.setAlignment(Pos.CENTER);
        pluginLayout.setSpacing(60);

        wholeLayout = new HBox();
        wholeLayout.setId("wholeLayout");
        wholeLayout.getChildren().addAll(pluginLayout);
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");
        wholeLayout.setAlignment(Pos.CENTER);
        Pane base = new Pane();
        Image illus = new Image("file:src/img/illustration1.png");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);
        base.setBackground(new Background(new BackgroundImage(illus, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));
        base.getChildren().add(wholeLayout);
        this.setContent(base);
        wholeLayout.prefWidthProperty().bind(base.widthProperty());
        wholeLayout.prefHeightProperty().bind(base.heightProperty());
        this.setOnSelectionChanged(event -> {
            if (this.isSelected()) {
                updateData();
            }
        });
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
                        Pair<String, Class<?>> classloaded = PluginManager.loadJarFile(pathFile);
                        Plugin newPlugin = new Plugin(classloaded.getKey(), pathFile, classloaded.getValue().getName());
                        loadedPlugins.addPlugin(newPlugin);
                        controller.saveDataPlugins(loadedPlugins);
                        updateData();
                    } catch (Exception err){
                        System.out.println(err);
                    }
            }
        }

    }

    public void updateData(){
        salesData = new SalesReport(fixedBills);
        sales = salesData.getListOfAllProductSales();
        ArrayList<Plugin> plugin = controller.getPlugins().getPlugins();
        if (plugin != null) {
            ArrayList<String> listPlugin = new ArrayList<>();
            for (Plugin p : plugin) {
                listPlugin.add(p.getPluginName());
            }
            ObservableList<String> options = FXCollections.observableArrayList(listPlugin);
            plugins.setItems(options);
        }
    }

    public void runPlugin(String className){
        for(Plugin p: loadedPlugins.getPlugins()){
            if(p.getPluginName().equals(className)){
                try{
                    if(p.getPluginName().contains("chart")){
                        Pair<String, Class<?>> classloaded = PluginManager.loadJarFile(p.getJarFilePath());
                        Class<?> classTes = classloaded.getValue();
                        pluginChartController pluginChartController = (pluginChartController) classTes.getDeclaredConstructor().newInstance();

//                        Method method = classTes.getDeclaredMethod("onLoadChart", HashMap.class);
//                        Object pluginObject = classTes.getDeclaredConstructor().newInstance();
//                        Object chart = method.invoke(pluginObject, sales);

                        alertGUI.alertInformation("Successfully loaded plugin");
                        Node chartNode = pluginChartController.onLoadChart(salesData.getListOfAllProductSales());
                        basePlugin pluginTab = new basePlugin(mainTabPane);
                        pluginTab.addChart(chartNode);
                    }
                } catch (Exception err){
                    System.out.println(err);
                }
            }
        }
    }

    public String validatePlugin(){
        String selectedPlugin = this.plugins.getValue();
        if(selectedPlugin == null){
            alertGUI.alertWarning("You have not selected the plugin");
        } else{
            return selectedPlugin;
        }
        return "";
    }
}
