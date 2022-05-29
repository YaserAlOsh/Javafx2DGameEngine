package com.example.simplest2djavafxgameengine;

import java.util.List;

public abstract class Collider extends Component{
    public Vector minExtents;
    public Vector maxExtents;
    public AABB box;
    public Vector position;
    public Rigidbody rigidbody;
    public boolean hasRigidbody;
    Collider(Entity entity, Vector position, double xSize, double ySize){
        super(entity);
        minExtents = position.sub(new Vector(xSize/2,ySize/2));
        maxExtents = position.add(new Vector(xSize/2,ySize/2));
        box = new AABB(minExtents,maxExtents);
        this.position = position;
        hasRigidbody = false;
    }
    Collider(Entity entity, Vector position, double xSize, double ySize, Rigidbody rigidbody){
        super(entity);
        minExtents = position.addX(xSize);
        maxExtents = position.addY(ySize);
        box = new AABB(minExtents,maxExtents);
        this.position = position;
        this.rigidbody = rigidbody;
        hasRigidbody = true;
    }
    public void SetRigidbody(Rigidbody rigidbody){
        this.rigidbody = rigidbody;
        hasRigidbody = true;
        position = rigidbody.position;
    }
    public abstract boolean isTouching(Collider collider, Hit colInfo);
    public abstract boolean isIntersecting(Collider collider, Hit colInfo);
    public abstract boolean isTouching(List<Rigidbody> rigidbodyList,List<Rigidbody> touchingRigidbodies, Vector touchPoint);
    public abstract boolean isIntersecting(List<Rigidbody> rigidbodyList,List<Rigidbody> intersectingRigidbodies,  Vector intersectPoint);

}
