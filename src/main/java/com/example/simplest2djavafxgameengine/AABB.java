package com.example.simplest2djavafxgameengine;
// Axially aligned bounding box
public class AABB {
    public Vector minExtents;
    public Vector maxExtents;
    AABB(Vector min, Vector max){
        setMinExtents(min);
        setMaxExtents(max);
    }

    public Vector getMinExtents() {
        return minExtents;
    }

    public void setMinExtents(Vector minExtents) {
        this.minExtents = minExtents;
    }

    public Vector getMaxExtents() {
        return maxExtents;
    }

    public void setMaxExtents(Vector maxExtents) {
        this.maxExtents = maxExtents;
    }
    public Vector closestPoint(Vector point){
        Vector v = new Vector(point.x, point.y);
        //System.out.println(point+"__"+minExtents+"__"+maxExtents);
        if(point.x < minExtents.x)
            v.x = minExtents.x;
        else if(point.x > maxExtents.x)
            v.x = maxExtents.x;
        if(point.y < minExtents.y)
            v.y = minExtents.y;
        else if(point.y > maxExtents.y)
            v.y = maxExtents.y;
        return v;
    }
}
