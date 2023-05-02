package com.o2pjualan.GUI;

import com.o2pjualan.Classes.Product;
import com.o2pjualan.Classes.Products;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class addItemCatalog extends Tab {
    private Button saveButton;
    private Button cancelButton;
    private Label imagePath;
    private Image imageItem;
    private TextField nameTextField;
    private TextField categoryTextField;
    private TextField buyPriceTextField;
    private TextField sellPriceTextField;
    private TextField stockTextField;
    private Button changeImage;
    private ImageView imageView;
    private Image image;
    private String finalPath;
    private TextField itemTotal;
    private Products products;

    public addItemCatalog(){
        this.setText("Add Catalog Item");

        /*Whole Layout*/
        VBox wholeLayout = new VBox();
        wholeLayout.setId("layoutCatalog");

        /*Edit Whole Item Layout*/
        HBox editLayout = new HBox();

        /*Edit Image Layout*/
        VBox editImageLayout = new VBox();
        finalPath = "file:src/img/placeholderimg.png";
        image = new Image(finalPath);
        imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);

        this.changeImage = new Button("Upload Image");
        this.changeImage.setId("buttonCatalog");

        this.changeImage.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(changeImage.getScene().getWindow());
            if (selectedFile != null) {
                String path = selectedFile.getAbsolutePath();
                int crop = path.indexOf("src/");
                String image = path.substring(crop);
                this.finalPath = "file:" + image;
//                System.out.println(finalPath);
                this.image = new Image(finalPath);
                imageView.setImage(this.image);
            } else {
                this.finalPath = "file:src/img/placeholderimg.png";
            }
        });

        editImageLayout.getChildren().addAll(this.imageView, this.changeImage);
        editImageLayout.setAlignment(Pos.CENTER);
        editImageLayout.setSpacing(20);

        /*Edit Value Layout*/
        VBox editValueLayout = new VBox();


        HBox nameLayout = new HBox();
        Label nameText = new Label("Name");
        nameText.setId("catalogLabel");
        this.nameTextField = new TextField();
        this.nameTextField.setId("textFieldCatalog");
        nameLayout.getChildren().addAll(nameText, this.nameTextField);
        nameLayout.setSpacing(15);

        HBox categoryLayout = new HBox();
        Label categoryText = new Label("Category");
        categoryText.setId("catalogLabel");
        this.categoryTextField = new TextField();
        this.categoryTextField.setId("textFieldCatalog");
        categoryLayout.getChildren().addAll(categoryText, this.categoryTextField);
        categoryLayout.setSpacing(15);

        HBox buyPriceLayout = new HBox();
        Label buyPriceText = new Label("Buy Price");
        buyPriceText.setId("catalogLabel");
        this.buyPriceTextField = new TextField();
        this.buyPriceTextField.setId("textFieldCatalog");
        buyPriceLayout.getChildren().addAll(buyPriceText, this.buyPriceTextField);
        buyPriceLayout.setSpacing(15);
        buyPriceTextField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));

        HBox sellPriceLayout = new HBox();
        Label sellPriceText = new Label("Sell Price");
        sellPriceText.setId("catalogLabel");
        this.sellPriceTextField = new TextField();
        this.sellPriceTextField.setId("textFieldCatalog");
        sellPriceLayout.getChildren().addAll(sellPriceText, this.sellPriceTextField);
        sellPriceLayout.setSpacing(15);
        sellPriceTextField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));

        HBox stockLayout = new HBox();
        Label stockText = new Label("Stock");
        stockText.setId("catalogLabel");
        this.stockTextField = new TextField();
        this.stockTextField.setId("textFieldCatalog");
        stockLayout.getChildren().addAll(stockText, this.stockTextField);
        stockLayout.setSpacing(15);
        stockTextField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));

        editValueLayout.getChildren().addAll(nameLayout, categoryLayout, buyPriceLayout, sellPriceLayout, stockLayout);
        editValueLayout.setSpacing(20);

        editLayout.getChildren().addAll(editImageLayout, editValueLayout);
        editLayout.setSpacing(20);
        /*Bottom Button Layout*/
        HBox bottomButtonLayout = new HBox();



        this.saveButton = new Button("Add Item");
        this.saveButton.setId("buttonCatalog");

        this.saveButton.setOnAction(e -> {
            try{
                addNewItem();
            }catch (IOException | ParseException err){
                throw new RuntimeException(err);
            }
        });

        bottomButtonLayout.getChildren().addAll(this.saveButton);
        bottomButtonLayout.setSpacing(450);
        wholeLayout.getChildren().addAll(editLayout, bottomButtonLayout);
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");


        Pane base = new Pane();
        base.getChildren().add(wholeLayout);
        this.setContent(base);
    }
    public void addNewItem() throws IOException, ParseException {
        if(this.nameTextField.getText().equals("")){
            nameTextField.setPromptText("Please enter product's name");
            nameTextField.setStyle("-fx-prompt-text-fill: red;");
        }  else if(this.buyPriceTextField.getText().equals("")){
            buyPriceTextField.setPromptText("Please enter product's buy price");
            buyPriceTextField.setStyle("-fx-prompt-text-fill: red;");
        } else if(this.sellPriceTextField.getText().equals("")){
            sellPriceTextField.setPromptText("Please enter product's sell price");
            sellPriceTextField.setStyle("-fx-prompt-text-fill: red;");
        } else if(this.stockTextField.getText().equals("")){
            stockTextField.setPromptText("Please enter product's stock");
            stockTextField.setStyle("-fx-prompt-text-fill: red;");
        } else {
            String productName = this.nameTextField.getText();
            int buyPrice = Integer.parseInt(this.buyPriceTextField.getText());
            int sellPrice = Integer.parseInt(this.buyPriceTextField.getText());
            int stock = Integer.parseInt(this.buyPriceTextField.getText());
            String categoryName = "";
            if(categoryTextField.equals("")){
                categoryName = "etc";
            } else {
                categoryName = this.categoryTextField.getText();
            }

            Product newProduct = new Product(productName, categoryName, buyPrice, sellPrice, stock, finalPath);
            Products products = controller.getProducts();
            boolean success = products.addProduct(newProduct);
            if(success){
                controller.saveDataProduct(products);
                alert("Succesfully add item to product database");
            } else {
                alert("Product name already exist");
            }
        }
    }

    public void alert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.show();
    }
}
