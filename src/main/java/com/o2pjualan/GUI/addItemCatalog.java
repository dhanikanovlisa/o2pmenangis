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
    private AlertGUI alertGUI;

    public addItemCatalog(){
        this.setText("Add Catalog Item");
        Pane base = new Pane();

        VBox wrapper = new VBox();
        wrapper.prefWidthProperty().bind(base.widthProperty());
        wrapper.prefHeightProperty().bind(base.heightProperty());


        /*Whole Layout*/
        VBox wholeLayout = new VBox();
        wholeLayout.setId("addCatalogLayout");
        wholeLayout.setMaxWidth(1000);
        wholeLayout.setAlignment(Pos.CENTER);
        alertGUI = new AlertGUI();

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
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(changeImage.getScene().getWindow());
            if (selectedFile != null) {
                String path = selectedFile.getAbsolutePath();
                int crop = path.indexOf("src/");
                if(crop == -1){
                    alertGUI.alertWarning("Cannot find image in src file");
                } else {
                    String image = path.substring(crop);
                    this.finalPath = "file:" + image;
                    this.image = new Image(finalPath);
                    imageView.setImage(this.image);
                }
            } else {
                this.finalPath = "file:src/img/placeholderimg.png";
            }
        });

        editImageLayout.getChildren().addAll(this.imageView, this.changeImage);
        editImageLayout.setAlignment(Pos.CENTER);
        editImageLayout.setSpacing(20);

        /*Edit Value Layout*/
        VBox editValueLayout = new VBox();
        VBox editTextField = new VBox();

        Label nameText = new Label("Name");
        nameText.setId("catalogLabel");
        this.nameTextField = new TextField();
        this.nameTextField.setId("textFieldCatalog");

        Label categoryText = new Label("Category");
        categoryText.setId("catalogLabel");
        this.categoryTextField = new TextField();
        this.categoryTextField.setId("textFieldCatalog");

        Label buyPriceText = new Label("Buy Price");
        buyPriceText.setId("catalogLabel");
        this.buyPriceTextField = new TextField();
        this.buyPriceTextField.setId("textFieldCatalog");


        Label sellPriceText = new Label("Sell Price");
        sellPriceText.setId("catalogLabel");
        this.sellPriceTextField = new TextField();
        this.sellPriceTextField.setId("textFieldCatalog");


        Label stockText = new Label("Stock");
        stockText.setId("catalogLabel");
        this.stockTextField = new TextField();
        this.stockTextField.setId("textFieldCatalog");


        editValueLayout.getChildren().addAll(nameText, categoryText, buyPriceText, sellPriceText, stockText);
        editTextField.getChildren().addAll(nameTextField, categoryTextField, buyPriceTextField, sellPriceTextField, stockTextField);
        editValueLayout.setSpacing(30);
        editValueLayout.setStyle("-fx-padding: 0 0 0 50;");
        editTextField.setSpacing(20);

        editLayout.getChildren().addAll(editImageLayout, editValueLayout, editTextField);
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


        wrapper.getChildren().add(wholeLayout);
        wrapper.setAlignment(Pos.CENTER);
        base.getChildren().add(wrapper);
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
            String categoryName = "";
            if(categoryTextField.equals("") || categoryTextField.equals(null) || categoryTextField == null){
                categoryName = "etc";
            } else {
                categoryName = this.categoryTextField.getText();
            }

            if(!inputValidater(this.buyPriceTextField.getText()) ||
                    !inputValidater(this.sellPriceTextField.getText()) ||
                    !inputValidater(this.stockTextField.getText()) ){
                alertGUI.alertWarning("Please input number");
            } else {
                int buyPrice = Integer.parseInt(this.buyPriceTextField.getText());
                int sellPrice = Integer.parseInt(this.sellPriceTextField.getText());
                int stock = Integer.parseInt(this.stockTextField.getText());
                Product newProduct = new Product(productName, categoryName, buyPrice, sellPrice, stock, finalPath);
                Products products = controller.getProducts();
                boolean success = products.validateProduct(newProduct);
                if(success){
                    products.addProduct(newProduct);
                    controller.saveDataProduct(products);
                    alertGUI.alertInformation("Successfully add item to product database");
                    this.nameTextField.setText("");
                    this.nameTextField.setPromptText("");
                    this.categoryTextField.setText("");
                    this.categoryTextField.setPromptText("");
                    this.buyPriceTextField.setText("");
                    this.buyPriceTextField.setPromptText("");
                    this.sellPriceTextField.setText("");
                    this.sellPriceTextField.setPromptText("");
                    this.stockTextField.setText("");
                    this.stockTextField.setPromptText("");
                    this.finalPath = "file:src/img/placeholderimg.png";
                    this.imageView.setImage(new Image(finalPath));
                } else {
                    alertGUI.alertWarning("Product name already exist");
                }
            }
        }
    }

    public boolean inputValidater(String input){
        boolean isDigit = true;
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                isDigit = false;
                break;
            }
        }
        return isDigit;
    }
}
