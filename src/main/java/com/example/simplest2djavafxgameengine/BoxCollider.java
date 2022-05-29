package com.example.simplest2djavafxgameengine;

import java.util.List;

public class BoxCollider extends Collider {


    BoxCollider(Entity entity, Vector center, double xSize, double ySize) {
        super(entity, center, xSize, ySize);
    }

    @Override
    public boolean isTouching(Collider collider, Hit Collision) {
        return false;
    }

    @Override
    public boolean isIntersecting(Collider collider, Hit colInfo) {
        return false;
    }

    @Override
    public boolean isTouching(List<Rigidbody> rigidbodyList, List<Rigidbody> touchingRigidbodies, Vector touchPoint) {
        return false;
    }

    @Override
    public boolean isIntersecting(List<Rigidbody> rigidbodyList, List<Rigidbody> intersectingRigidbodies, Vector intersectPoint) {
        return false;
    }

}
