package com.o2pjualan.GUI;

import com.o2pjualan.Classes.Bills;
import com.o2pjualan.Classes.Product;
import com.o2pjualan.Classes.Products;
import com.o2pjualan.Classes.updatingData;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static com.o2pjualan.Main.controller;
import static com.o2pjualan.Main.mainTabPane;

public class editCatalogMenu extends Tab implements updatingData {
    private Button saveButton;
    private Button deleteButton;
    private Button cancelButton;

    private Image imageItem;
    private ImageView imageView;
    private TextField nameTextField;
    private TextField categoryTextField;
    private TextField sellPriceTextField;
    private TextField buyPriceTextField;
    private TextField stockTextField;
    private Button changeImage;
    private Products listProducts;
    private Product getProd;
    private String finalPath;
    private AlertGUI alertGUI;
    private Integer productCode;


    public editCatalogMenu(Integer productCode){
        this.productCode =productCode;

        Pane base = new Pane();
        VBox wrapper = new VBox();
        wrapper.prefHeightProperty().bind(base.heightProperty());
        wrapper.prefWidthProperty().bind(base.widthProperty());

        /*Whole Layout*/
        VBox wholeLayout = new VBox();
        wholeLayout.setId("editCatalog");
        wholeLayout.setMaxWidth(1000);
        wholeLayout.setMinHeight(500);
        wholeLayout.setAlignment(Pos.CENTER);
        listProducts = new Products();
        listProducts = controller.getProducts();
        alertGUI = new AlertGUI();

        getProd = new Product();
        getProd = listProducts.getProductById(productCode);
        this.setText("Edit " + getProd.getProductName());
        /*Edit Whole Item Layout*/
        HBox editLayout = new HBox();

        /*Edit Image Layout*/
        VBox editImageLayout = new VBox();
        this.imageItem = new Image(getProd.getImagePath());

        imageView = new ImageView();
        imageView.setImage(imageItem);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);

        this.changeImage = new Button("Change Image");
        this.changeImage.setId("buttonCatalog");
        finalPath = getProd.getImagePath();
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
                    this.imageItem = new Image(finalPath);
                    imageView.setImage(this.imageItem);
                }
            } else {
                finalPath = getProd.getImagePath();
            }
        });

        editImageLayout.getChildren().addAll(imageView, this.changeImage);
        editImageLayout.setAlignment(Pos.CENTER);
        editImageLayout.setSpacing(20);

        /*Edit Value Layout*/
        VBox editValueLayout = new VBox();
        editValueLayout.setStyle("-fx-padding: 0 0 0 90");
        VBox editField = new VBox();


        Label nameText = new Label("Name");
        nameText.setId("catalogLabel");
        this.nameTextField = new TextField();
        this.nameTextField.setId("textFieldCatalog");
        this.nameTextField.setText(getProd.getProductName());

        Label categoryText = new Label("Category");
        categoryText.setId("catalogLabel");
        this.categoryTextField = new TextField();
        this.categoryTextField.setText(getProd.getProductCategory());
        this.categoryTextField.setId("textFieldCatalog");

        Label buyPriceText = new Label("Buy Price");
        buyPriceText.setId("catalogLabel");
        this.buyPriceTextField = new TextField();
        this.buyPriceTextField.setId("textFieldCatalog");
        this.buyPriceTextField.setText(Double.toString(getProd.getBuyPrice()));
        buyPriceTextField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d+(\\.\\d+)?")) {
                return change;
            }
            return null;
        }));


        Label sellPriceText = new Label("Sell Price");
        sellPriceText.setId("catalogLabel");
        this.sellPriceTextField = new TextField();
        this.sellPriceTextField.setId("textFieldCatalog");
        this.sellPriceTextField.setText(Double.toString(getProd.getSellPrice()));
        sellPriceTextField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d+(\\.\\d+)?")) {
                return change;
            }
            return null;
        }));

        Label stockText = new Label("Stock");
        stockText.setId("catalogLabel");
        this.stockTextField = new TextField();
        this.stockTextField.setId("textFieldCatalog");
        this.stockTextField.setText(Integer.toString(getProd.getStock()));
        stockTextField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d+")) {
                return change;
            }
            return null;
        }));

        editValueLayout.getChildren().addAll(nameText, categoryText, buyPriceText, sellPriceText, stockText);
        editValueLayout.setSpacing(30);

        editField.getChildren().addAll(nameTextField, categoryTextField, buyPriceTextField, sellPriceTextField, stockTextField);
        editField.setSpacing(20);

        editLayout.getChildren().addAll(editImageLayout, editValueLayout, editField);
        editLayout.setSpacing(20);
        /*Bottom Button Layout*/
        VBox bottomButtonLayout = new VBox();

        this.deleteButton = new Button("Delete Product");
        this.deleteButton.setId("buttonCatalog");
        this.deleteButton.setStyle("-fx-background-color: #8f2929");
        this.saveButton = new Button("Save Product");
        this.saveButton.setId("buttonCatalog");


        this.saveButton.setOnAction(e -> {
            try{
                editItem();
            }catch (IOException | ParseException err){
                throw new RuntimeException(err);
            }
        });

        this.deleteButton.setOnAction(e -> {
            Thread thread = new Thread(() -> {
                deleteItem(productCode);
                Platform.runLater(() -> {
                    alertGUI.alertInformation("Product has been removed");
                    mainTabPane.getTabs().remove(this);
                });
            });
            thread.start();

        });

        bottomButtonLayout.getChildren().addAll(this.saveButton, this.deleteButton);
        bottomButtonLayout.setSpacing(20);
        wholeLayout.getChildren().addAll(editLayout, bottomButtonLayout);
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");



        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);

        wrapper.getChildren().add(wholeLayout);
        wrapper.setAlignment(Pos.CENTER);
        base.getChildren().add(wrapper);
        this.setContent(base);


    }
    public void editItem() throws IOException, ParseException {
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
            double buyPrice = Double.parseDouble(this.buyPriceTextField.getText());
            double sellPrice = Double.parseDouble(this.sellPriceTextField.getText());
            int stock = Integer.parseInt(this.stockTextField.getText());
            String categoryName = "";
            if(categoryTextField.equals("")){
                categoryName = "etc";
            } else {
                categoryName = this.categoryTextField.getText();
            }
            getProd.setProductName(productName);
            getProd.setProductCategory(categoryName);
            getProd.setBuyPrice(buyPrice);
            getProd.setSellPrice(sellPrice);
            getProd.setImagePath(finalPath);
            getProd.setStock(stock);
            controller.saveDataProduct(listProducts);
            alertGUI.alertInformation("Succesfully change " + productName);
        }
    }

    public void updateData() {
        getProd = controller.getProducts().getProductById(productCode);
        this.sellPriceTextField.setText(Double.toString(getProd.getSellPrice()));
        this.buyPriceTextField.setText(Double.toString(getProd.getBuyPrice()));
        this.nameTextField.setText(getProd.getProductName());
        this.categoryTextField.setText(getProd.getProductCategory());
        this.stockTextField.setText(Integer.toString(getProd.getStock()));
    }

    public void deleteItem(int productCode) {
        Products products = controller.getProducts();
        if (products.deleteProduct(productCode) == 0) {
            controller.saveDataProduct(products);
            Bills bills = controller.getBills();
            bills.removeProduct(productCode);
            controller.saveDataBill(bills);
        }
    }
}
