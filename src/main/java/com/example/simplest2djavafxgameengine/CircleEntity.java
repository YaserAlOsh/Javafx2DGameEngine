package com.example.simplest2djavafxgameengine;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleEntity extends Entity{
    public Circle circleObj;
    double radius;
    CircleEntity(String name, double radius, Vector position){
        super(name, position);
        this.position = position;
        this.radius = radius;
        circleObj = new Circle(this.position.x,this.position.y,radius);
        UpdatePositionInCanvas();
    }
    public CircleEntity AddCollider(){
        components.add(new CircleCollider(this,position,radius));
        hasCollider = true;
        return this;
    }
    public CircleEntity AddRigidbody(){
        rigidbody = new Rigidbody(this,new CircleCollider(this,position,radius));
        this.position = rigidbody.position;
        components.add(rigidbody);
        components.add(rigidbody.collider);
        hasCollider = hasRigidbody = true;
        return this;
    }
    public boolean OutsideCanvas(Vector minCanvas, Vector maxCanvas){
        return position.x < minCanvas.x || position.y < minCanvas.y ||
                position.x > maxCanvas.x || position.y > maxCanvas.y;
    }
    /*public void SetPosition(Vector position){
        this.position = position;
        rigidbody.position = position;
        rigidbody.Reset();
    }*/
    public void UpdatePositionInCanvas(){
        circleObj.setCenterX(position.x);
        circleObj.setCenterY(position.y);
    }
    public void UpdatePositionInCanvas(double interpolationFactor){
        if(!hasRigidbody) {
            UpdatePositionInCanvas();
            return;
        }
        Vector pos = rigidbody.GetRenderPosition(interpolationFactor);
        circleObj.setCenterX(pos.x);
        circleObj.setCenterY(pos.y);
    }
    public void SetFill(Color color){
        circleObj.setFill(color);
    }
}
