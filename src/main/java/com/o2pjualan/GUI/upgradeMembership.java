package com.o2pjualan.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class upgradeMembership extends Tab {
    private ComboBox<String> name;
    private Button upgrade;

    public upgradeMembership(){

        this.setText("Upgrade Membership");

        this.name = new ComboBox<>();
        this.name.setId("nameDropDown");
        this.name.setPromptText("Pick Customer Name...");

        this.upgrade = new Button("Upgrade");

        Label vipMembership = new Label("VIP Membership");
        vipMembership.setId("h1");

        Label info = new Label("Enjoy countless discount by upgrading your account to VIP.");
        info.setId("paragraph");

        VBox labelLayout = new VBox();
        labelLayout.getChildren().addAll(vipMembership, info);
        labelLayout.setSpacing(20);
        labelLayout.setPadding(new Insets(20));
        labelLayout.setAlignment(Pos.CENTER);

        VBox upgradeLayout = new VBox();
        upgradeLayout.getChildren().addAll(this.name, this.upgrade);
        upgradeLayout.setSpacing(50);
        upgradeLayout.setPadding(new Insets(20));
        upgradeLayout.setAlignment(Pos.CENTER);

        VBox wholeLayout = new VBox();
        wholeLayout.getChildren().addAll(labelLayout, upgradeLayout);
        wholeLayout.setAlignment(Pos.CENTER);
        wholeLayout.setId("wholeLayout");
        wholeLayout.setSpacing(100);
        Pane base = new Pane();
        base.getChildren().add(wholeLayout);
        this.setContent(base);
        wholeLayout.setFillWidth(true);
        wholeLayout.prefWidthProperty().bind(base.widthProperty());
        wholeLayout.prefHeightProperty().bind(base.heightProperty());
        Image backgroundImage = new Image("file:src/img/upgradeToVIP.png");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
        base.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));

    }
}
