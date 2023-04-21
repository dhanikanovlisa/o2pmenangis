package com.o2pjualan.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.skin.TextInputControlSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class editCatalogMenu extends Tab {
    private Button backButton;
    private Button saveButton;
    private Button cancelButton;

    private Image imageItem;
    private TextField nameTextField;
    private TextField priceTextField;
    private TextField stockTextField;
    private Button changeImage;

    public editCatalogMenu(){
        this.setText("Edit Catalog Item");

        this.backButton = new Button("Back");
        this.backButton.setId("buttonCatalog");

        /*Whole Layout*/
        VBox wholeLayout = new VBox();
        wholeLayout.setId("layoutCatalog");

        /*Edit Whole Item Layout*/
        HBox editLayout = new HBox();

        /*Edit Image Layout*/
        VBox editImageLayout = new VBox();
        Label imageHolder = new Label();
        this.imageItem = new Image("file:src/img/placeholderimg.png");
        imageHolder.setGraphic(new ImageView(this.imageItem));

        this.changeImage = new Button("Change Image");
        this.changeImage.setId("buttonCatalog");

        editImageLayout.getChildren().addAll(imageHolder, this.changeImage);
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

        HBox priceLayout = new HBox();
        Label priceText = new Label("Price");
        priceText.setId("catalogLabel");
        this.priceTextField = new TextField();
        this.priceTextField.setId("textFieldCatalog");
        priceLayout.getChildren().addAll(priceText, this.priceTextField);
        priceLayout.setSpacing(15);

        HBox stockLayout = new HBox();
        Label stockText = new Label("Stock");
        stockText.setId("catalogLabel");
        this.stockTextField = new TextField();
        this.stockTextField.setId("textFieldCatalog");
        stockLayout.getChildren().addAll(stockText, this.stockTextField);
        stockLayout.setSpacing(15);

        editValueLayout.getChildren().addAll(nameLayout, priceLayout, stockLayout);
        editValueLayout.setSpacing(20);

        editLayout.getChildren().addAll(editImageLayout, editValueLayout);
        editLayout.setSpacing(20);
        /*Bottom Button Layout*/
        HBox bottomButtonLayout = new HBox();

        this.saveButton = new Button("Save Button");
        this.saveButton.setId("buttonCatalog");
        this.cancelButton = new Button("Cancel Button");
        this.cancelButton.setId("buttonCatalog");

        bottomButtonLayout.getChildren().addAll(this.saveButton, this.cancelButton);
        bottomButtonLayout.setSpacing(450);
        wholeLayout.getChildren().addAll(this.backButton, editLayout, bottomButtonLayout);
        wholeLayout.getStylesheets().add("file:src/main/java/com/o2pjualan/style/style.css");


        Pane base = new Pane();
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, true, true);

        base.getChildren().add(wholeLayout);
        this.setContent(base);


    }
}
