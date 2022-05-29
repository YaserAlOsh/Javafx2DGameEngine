package com.example.simplest2djavafxgameengine;

import javafx.scene.shape.Rectangle;

public class BoxEntity extends Entity{
    public Rectangle rectObj;
    double width, height;
    BoxEntity(String name, Vector center, double width, double height) {
        super(name, center);
        this.position = center;
        rectObj = new Rectangle(position.x - width/2,position.y - height/2,width,height);
        this.width = width;
        this.height = height;


        UpdatePositionInCanvas();
    }
    public BoxEntity AddCollider(){
        components.add(new BoxCollider(this,position,width,height));
        hasCollider = true;
        return this;
    }
    public BoxEntity AddRigidbody(){
        rigidbody = new Rigidbody(this,new BoxCollider(this,position,width,height));
        this.position = rigidbody.position;
        components.add(rigidbody);
        components.add(rigidbody.collider);
        hasCollider = hasRigidbody = true;
        return this;
    }
    public void UpdatePositionInCanvas(){
        rectObj.setX(position.x - width/2);
        rectObj.setY(position.y - height/2);
    }
    public void UpdatePositionInCanvas(double interpolationFactor){
        if(!hasRigidbody) {
            UpdatePositionInCanvas();
            return;
        }
        Vector pos = rigidbody.GetRenderPosition(interpolationFactor);
        rectObj.setX(pos.x - width/2);
        rectObj.setY(pos.y - height/2);
    }
}