package com.example.simplest2djavafxgameengine;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class ex1 extends Application{
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane,320,160);
        int i =0;
        while (i<5){
            double x =  pane.getWidth() * 0.1 + (i) * pane.getWidth() * 0.15;
            double y = pane.getHeight()/2;

            Text newText = new Text(x,y,"JavaFx");
            double opacity = Math.random();
            opacity = 0.1*(1-opacity) + opacity; //make opacity random between 0.1 and 1
            Color newColor = new Color(Math.random(),Math.random(),Math.random(),opacity);
            newText.setRotate(90);
            Font newFont = Font.font("Times New Roman",FontWeight.BOLD,FontPosture.ITALIC,27);
            newText.setFont(newFont);
            newText.setFill(newColor);

            pane.getChildren().add(newText);
            i++;
        }

        primaryStage.setTitle("Displaying 5 Texts");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args){
        launch();
    }
}