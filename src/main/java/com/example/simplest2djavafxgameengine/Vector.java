package com.example.simplest2djavafxgameengine;

public class Vector {

    public double x;
    public double y;

    public final static Vector ZERO() {return new Vector();}
    public static final Vector RIGHT = new Vector(1,0);
    public static final Vector LEFT = new Vector(-1,0);
    public static final Vector UP = new Vector(0,1);
    public static final Vector DOWN = new Vector(0,-1);
    public void Zero(){
        x = y = 0;
    }
    Vector() {
        this(0, 0);
    }

    Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }
    public Vector Set(Vector position) {
        this.x = position.x;
        this.y = position.y;
        return this;
    }
    public Vector Set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }
    public Vector neg(){ return new Vector(-this.x, -this.y);}
    public Vector neg(boolean inPlace){ this.x = -this.x; this.y = -this.y; return this;}
    public Vector addX(double xSize) {
        return new Vector(this.x+xSize,this.y);
    }

    public Vector addY(double ySize) {
        return new Vector(this.x,this.y+ySize);
    }
    public Vector add(Vector v) {
        return new Vector(this.x + v.x,this.y + v.y);
    }
    public Vector add(Vector v, boolean inPlace) {
        x+=v.x;
        y+=v.y;
        return this;
    }
    public Vector sub(Vector v){
        return new Vector(this.x - v.x,this.y - v.y);
    }
    public Vector sub(Vector v,boolean inPlace){
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }
    public Vector scale(double k, boolean inPlace){
        x *= k;
        y *= k;
        return this;
    }
    public Vector scale(double k){
        return new Vector(this.x * k, this.y * k);
    }
    public Vector divide(double k){
        return new Vector(this.x / k, this.y / k);
    }
    public Vector divide(double k, boolean inPlace){
        x /= k;
        y /= k;
        return this;
    }
    public double magnitudeSquared(){
        return x*x + y*y;
    }
    public double magnitude(){
        return Math.sqrt(this.magnitudeSquared());
    }
    public double distance(Vector position) {
        return Math.sqrt(distanceSquared(position));
    }

    public double distanceSquared(Vector position) {
        return Math.pow(x - position.x, 2) + Math.pow(y - position.y, 2);
    }
    public String toString(){
        return "("+x+","+y+")";
    }

    public Vector normalize(){
        double mag = this.magnitude();
        return new Vector(x / mag,y/mag);
    }
    public void normalize(boolean inPlace){
        double mag = this.magnitude();
        x /= mag;
        y /= mag;
    }

    public double dot(Vector v){
        return x * v.x + y * v.y;
    }

}
