package com.example.simplest2djavafxgameengine;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
public class ex2 extends Application{
    public void start(Stage stage){
        Pane pane = new Pane();
        Scene scene = new Scene(pane,720,360);

        Rectangle red = new Rectangle(0,0,pane.getWidth()*0.25,pane.getHeight());
        red.setFill(Color.RED);

        double rectangleHeight = pane.getHeight()/3;
        double rectangleWidth = pane.getWidth() * 0.75;
        double x = pane.getWidth()*0.25;

        Rectangle green = new Rectangle(x,0, rectangleWidth, rectangleHeight);

        Rectangle white = new Rectangle(x,pane.getHeight()/3, rectangleWidth, rectangleHeight);

        Rectangle black = new Rectangle(x,pane.getHeight()*2/3, rectangleWidth, rectangleHeight);

        green.setFill(Color.GREEN);
        white.setFill(Color.WHITE);
        black.setFill(Color.BLACK);

        pane.getChildren().add(red);
        pane.getChildren().add(green);
        pane.getChildren().add(white);
        pane.getChildren().add(black);

        stage.setScene(scene);
        stage.setTitle("UAE Flag");
        stage.show();
    }
    public static void main(String[] args){
        Application.launch(args);
    }
}
