package com.example.simplest2djavafxgameengine;

import java.util.ArrayList;
import java.util.List;

public class CircleCollider extends Collider{
    public double radius;
    CircleCollider(Entity entity, Vector position, double radius){
        super(entity, position,radius,radius);
        this.radius = radius;

    }
    /*CircleCollider(Vector position, double radius, Rigidbody rigidbody){
        super(position,radius,radius,rigidbody);
        this.radius = radius;
    }*/

    @Override
    public boolean isTouching(Collider collider, Hit colInfo) {
        if(collider instanceof  CircleCollider){
            double dist = collider.position.distance(position);
            double radSum = radius + ((CircleCollider)collider).radius;
            if(dist == radSum) {
                // posA+d = posB-d
                // x1 + dx = x2 - dx
                // x1 + 2dx = x2
                // 2dx = x2 - x1
                // dx = (x2-x1)/2

                colInfo.collisionPoint = new Vector(
                        (collider.position.x - position.x)/2,
                        (collider.position.y - position.y)/2);
                colInfo.collisionPoint.add(position);
                return true;
            }
        }else {
            Vector closestPoint = collider.box.closestPoint(position);

           if(position.distanceSquared(closestPoint) == radius * radius){
               colInfo.collisionPoint = closestPoint;
               return true;
           }
        }
        return false;
    }
    public boolean isIntersecting(Collider collider, Hit colInfo) {
        if(collider instanceof  CircleCollider){
            double dist = collider.position.distanceSquared(position);
            double radSum = radius + ((CircleCollider)collider).radius;
            if(dist <= radSum * radSum) {
                colInfo.collisionPoint = new Vector(
                        (collider.position.x - position.x)/2,
                        (collider.position.y - position.y)/2);
                colInfo.collisionPoint.add(position);
                colInfo.surfaceNormal = collider.position.sub(position).normalize();
                colInfo.penetrationDepth = Math.abs(((CircleCollider)collider).radius - (Math.sqrt(dist) - radius));

                colInfo.penetrationVector = colInfo.surfaceNormal;
                if(debug)
                    System.out.println(colInfo.penetrationDepth +" "+colInfo.penetrationVector);
                return true;
            }
        }else {
            Vector closestPoint = collider.box.closestPoint(position);
            //System.out.println(position +" Close to collider: "+ closestPoint);
            if(position.distanceSquared(closestPoint) <= radius * radius){

                colInfo.collisionPoint = Vector.ZERO();
                colInfo.surfaceNormal = Vector.ZERO();
                Vector mid = collider.box.minExtents.add(collider.box.maxExtents).scale(0.5);
                boolean grthMinY = position.y > collider.box.minExtents.y;
                boolean lethMaxY = position.y < collider.box.maxExtents.y;
                boolean grthMinX = position.x > collider.box.minExtents.x;
                boolean lethMaxX = position.x < collider.box.maxExtents.x;
                //Colliding on the left or right edges:
                if(grthMinY && lethMaxY){
                    if(position.x < mid.x){
                        colInfo.collisionPoint.x = collider.box.minExtents.x;
                        colInfo.surfaceNormal.x = -1;
                        colInfo.penetrationDepth = position.x + radius - collider.box.minExtents.x;
                    }else {
                        colInfo.collisionPoint.x = collider.box.maxExtents.x;
                        colInfo.surfaceNormal.x = 1;
                        colInfo.penetrationDepth = radius - (position.x - collider.box.maxExtents.x);
                    }
                    //colInfo.penetrationVector.Set(colInfo.surfaceNormal);
                }else {
                    //Colliding on the top or bottom edges:
                    if(grthMinX && lethMaxX){
                        if(position.y > mid.y){//bottom edge
                            colInfo.collisionPoint.y = collider.box.maxExtents.y;
                            colInfo.surfaceNormal.y = 1;
                            colInfo.penetrationDepth = radius  - (position.y - collider.box.maxExtents.y);
                            //System.out.println(collider.box.maxExtents+"__"+position+"__"+colInfo.penetrationDepth);
                        }else{
                            colInfo.collisionPoint.y = collider.box.minExtents.y;
                            colInfo.surfaceNormal.y = -1;
                            colInfo.penetrationDepth = radius - (collider.box.minExtents.y - position.y);
                        }
                        //colInfo.penetrationVector.Set(colInfo.surfaceNormal);
                    }
                    //Collision on one of the corners:
                    else if(!grthMinX){
                        if(lethMaxY){//bottom left corner
                            colInfo.collisionPoint.y = collider.box.minExtents.y;
                            colInfo.collisionPoint.x = collider.box.minExtents.x;
                            colInfo.surfaceNormal.Set(-1,-1).normalize(true);
                        }else {      //top left corner
                            colInfo.collisionPoint.y = collider.box.maxExtents.y;
                            colInfo.collisionPoint.x = collider.box.minExtents.x;
                            colInfo.surfaceNormal.Set(-1,1).normalize(true);
                        }
                    }else {
                        if (lethMaxY) {//bottom right corner
                            colInfo.collisionPoint.y = collider.box.minExtents.y;
                            colInfo.collisionPoint.x = collider.box.maxExtents.x;
                            colInfo.surfaceNormal.Set(1, -1).normalize(true);
                        } else {      //top right corner
                            colInfo.collisionPoint.y = collider.box.maxExtents.y;
                            colInfo.collisionPoint.x = collider.box.maxExtents.x;
                            colInfo.surfaceNormal.Set(1, 1).normalize(true);
                        }
                    }
                }
                colInfo.penetrationVector = new Vector(colInfo.surfaceNormal);
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean isTouching(List<Rigidbody> rigidbodyList,List<Rigidbody> touchingRigidbodies, Vector touchPoint) {
        touchingRigidbodies = new ArrayList<>();

        for( Rigidbody r : rigidbodyList){
            if(!r.hasCollider)
                continue;
            if(r.collider instanceof  CircleCollider){
                double dist = r.position.distance(position);
                double radSum = radius + ((CircleCollider)r.collider).radius;
                if(dist == radSum) {
                    touchingRigidbodies.add(r);
                }
            }else {
                double xMin = r.collider.minExtents.x, yMin = r.collider.minExtents.y;
                double xMax = r.collider.maxExtents.x, yMax = r.collider.maxExtents.y;

                if(position.x+radius == xMin || position.y+radius == yMin
                    || position.x+radius == xMax || position.y+radius == yMax){
                    touchingRigidbodies.add(r);
                }
            }
        }

        return touchingRigidbodies.size() > 0;
    }

    @Override
    public boolean isIntersecting(List<Rigidbody> rigidbodyList,List<Rigidbody> intersectingRigidbodies, Vector intersectPoint) {
        intersectingRigidbodies = new ArrayList<>();

        for( Rigidbody r : rigidbodyList){
            if(!r.hasCollider)
                continue;
            if(r.collider instanceof  CircleCollider){
                double dist = r.position.distance(position);
                double radSum = radius + ((CircleCollider)r.collider).radius;
                if(dist <= radSum) {
                    intersectingRigidbodies.add(r);
                }
            }else {
                double xMin = r.collider.minExtents.x, yMin = r.collider.minExtents.y;
                double xMax = r.collider.maxExtents.x, yMax = r.collider.maxExtents.y;

                if(position.x+radius >= xMin || position.y+radius >= yMin
                        || position.x+radius <= xMax || position.y+radius <= yMax){
                    intersectingRigidbodies.add(r);
                }
            }
        }

        return intersectingRigidbodies.size() > 0;
    }
}
