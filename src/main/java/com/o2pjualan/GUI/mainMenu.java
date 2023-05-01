package com.o2pjualan.GUI;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class mainMenu extends Tab{
    private Label time;
    private volatile boolean run = true;
    public  mainMenu() {
        // set Tab name
        this.setText("Main Menu");

        Label date = new Label();
        date.setId("dateLabel");
        date.setText(getCurrentDateStr());

        this.time = new Label();
        time.setId("timeLabel");
        clock();

        Image backgroundImage = new Image("file:src/img/home.png");

        // Setup base pane
        Pane base = new Pane();
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
        base.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize)));

        // Vertical layout (layer 1)
        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.prefWidthProperty().bind(base.widthProperty());
        vbox.prefHeightProperty().bind(base.heightProperty());

        // Horizontal layout (layer 2, consist name and nim)
        HBox hbox = new HBox();
        hbox.setFillHeight(true);

        Label nama = new Label();
        nama.setId("namaLabel");
        nama.setText("Kevin John Wesley\nFebryan Arota Hia\nDhanika Novlisariyanti\nCetta Reswara Parahita\nRyan Samuel Chandra");
        nama.setWrapText(true);

        Label nim = new Label();
        nim.setId("nimLabel");
        nim.setText("13521042\n13521120\n13521132\n13521133\n13521140");
        nim.setWrapText(true);

        // Logo (layer 2)
        ImageView logo = new ImageView(new Image("file:src/img/logo.png"));
        logo.setFitWidth(250);
        logo.setPreserveRatio(true);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.millis(1500));
        transition.setNode(logo);
        transition.setToY(-20);
        transition.setAutoReverse(true);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.play();




        // Add components
        hbox.getChildren().addAll(nama, nim);
        hbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(logo, date, time);
        vbox.getChildren().add(hbox);
        vbox.setAlignment(Pos.CENTER);

        base.getChildren().add(vbox);

        // Set the content of the tab to the base pane
        this.setContent(base);

        // stop clock while closed
        this.setOnClosed(event -> {
            stopClock();
            System.out.println("clock stopped");
        });

    }
    public String getCurrentDateStr() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
        return currentDate.format(formatter);
    }
    private void clock(){
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while(run){
                try{
                    Thread.sleep(1000);
                }catch(Exception e){
                    System.out.println(e);
                }
                final String timeNow = sdf.format(new Date());
                Platform.runLater(() -> {
                    time.setText(timeNow); // This is the label
                });
            }
        });
        thread.start();
    }

    public void stopClock() {
        run = false;
    }
}