package com.o2pjualan.GUI;

import com.o2pjualan.Classes.Product;
import com.o2pjualan.Classes.Products;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static com.o2pjualan.Main.controller;

public class itemtoBill extends Tab {
    private Button saveButton;
    private Image imageItem;
    private ImageView imageView;
    private Label nameTextField;
    private Label categoryTextField;
    private Label sellPriceTextField;
    private Products listProducts;
    private Product getProd;
    private TextField itemTotal;
    public itemtoBill(Integer productCode){
        /*Whole Layout*/
        VBox wholeLayout = new VBox();
        wholeLayout.setId("layoutCatalog");
        listProducts = new Products();
        listProducts = controller.getProducts();

        getProd = new Product();
        getProd = listProducts.getProductById(productCode);
        this.setText("Add " + getProd.getProductName());
        /*Edit Whole Item Layout*/
        HBox editLayout = new HBox();

        /*Edit Image Layout*/
        VBox editImageLayout = new VBox();
        this.imageItem = new Image(getProd.getImagePath());

        imageView = new ImageView();
        imageView.setImage(imageItem);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);


        editImageLayout.getChildren().addAll(imageView);
        editImageLayout.setAlignment(Pos.CENTER);
        editImageLayout.setSpacing(20);

        /*Edit Value Layout*/
        VBox editValueLayout = new VBox();


        HBox nameLayout = new HBox();
        Label nameText = new Label("Name");
        nameText.setId("catalogLabel");
        this.nameTextField = new Label(getProd.getProductName());
        nameLayout.getChildren().addAll(nameText, this.nameTextField);
        nameLayout.setSpacing(15);

        HBox categoryLayout = new HBox();
        Label categoryText = new Label("Category");
        categoryText.setId("catalogLabel");
        this.categoryTextField = new Label(getProd.getProductCategory());
        categoryLayout.getChildren().addAll(categoryText, this.categoryTextField);
        categoryLayout.setSpacing(15);

        HBox sellPriceLayout = new HBox();
        Label sellPriceText = new Label("Sell Price");
        sellPriceText.setId("catalogLabel");
        this.sellPriceTextField = new Label(Double.toString(getProd.getSellPrice()));
        sellPriceLayout.getChildren().addAll(sellPriceText, this.sellPriceTextField);
        sellPriceLayout.setSpacing(15);


        editValueLayout.getChildren().addAll(nameLayout, categoryLayout, sellPriceLayout);
        editValueLayout.setSpacing(20);

        editLayout.getChildren().addAll(editImageLayout, editValueLayout);
        editLayout.setSpacing(20);
        /*Bottom Button Layout*/
        HBox bottomButtonLayout = new HBox();
        this.itemTotal = new TextField();
        itemTotal.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));

        this.saveButton = new Button("Save");
        this.saveButton.setId("buttonCatalog");


//        this.saveButton.setOnAction(e -> {
//            try{
//                editItem();
//            }catch (IOException | ParseException err){
//                throw new RuntimeException(err);
//            }
//        });
//

        bottomButtonLayout.getChildren().addAll(this.itemTotal, this.saveButton);
        bottomButtonLayout.setSpacing(450);
        wholeLayout.getChildren().addAll(editLayout, bottomButtonLayout);
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");


        Pane base = new Pane();
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);

        base.getChildren().add(wholeLayout);
        this.setContent(base);
    }

    public void addToBill(){

    }

}
