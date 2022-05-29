package com.example.simplest2djavafxgameengine;

public class Hit {
    Collider[] colliders;
    Vector collisionPoint;
    Vector surfaceNormal;
    double penetrationDepth = 0.0;
    Vector penetrationVector;
    Hit(){

    }
    Hit(Vector point, Vector normal, Collider c1, Collider c2){
        this.collisionPoint = point;
        this.surfaceNormal = normal;
        colliders = new Collider[2];
        colliders[0] = c1;
        colliders[1] = c2;
    }
    Hit(Vector point, Vector normal, Collider ... _colliders){
        this.collisionPoint = point;
        this.surfaceNormal = normal;
        colliders = _colliders;

    }
}
