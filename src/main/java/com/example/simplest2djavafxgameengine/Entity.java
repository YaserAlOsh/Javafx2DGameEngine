package com.example.simplest2djavafxgameengine;

import java.util.ArrayList;

public class Entity {
    public String name;
    public String tag;

    public Vector position;
    public Vector rotation; /* Euler angles. */
    public ArrayList<Component> components;
    protected boolean hasRigidbody, hasCollider;
    public boolean HasCollider() {return hasCollider;}
    public boolean HasRigidbody() {return hasRigidbody;}
    protected Rigidbody rigidbody;
    Entity(String name, Vector position){
        this.position = position;
        components = new ArrayList<>();
        this.name = name;
    }

    public void SetTag(String tag){
        this.tag = tag;
    }
    public boolean OutsideCanvas(Vector minCanvas, Vector maxCanvas){
        return position.x < minCanvas.x || position.y < minCanvas.y ||
                position.x > maxCanvas.x || position.y > maxCanvas.y;
    }
    public void SetPosition(Vector position){
        this.position.Set(position);
        if(hasRigidbody) {
            rigidbody.position.Set(this.position);
            rigidbody.ResetForces();
        }
        else if(hasCollider)
            GetComponent(Collider.class).position.Set(this.position);

    }
    public void UpdatePositionInCanvas(){

    }
    public void UpdatePositionInCanvas(double interpolationFactor){
        if(!hasRigidbody)
            UpdatePositionInCanvas();
    }

    public <T> T GetComponent(Class<T> clazz){
        for(Component c : components){
            if(clazz.isInstance(c))
                return (T)c;
        }
        return null;
    }



    /*public <T> T GetComponent(){
        for(Map.Entry<Type,Component> entry : components.entrySet()){
            if(entry.getKey() == type)
                return (T)entry.getValue();
        }
        for(Component c : components)
            if (clazz.isInstance(c))
                return (T) c;
        return null;
    }*/
    public static abstract class EntityPhysicsUpdate {
        public abstract void PhysicsUpdate(double t, double dt);
    }
    public static abstract class EntityRenderUpdate {
        public abstract void RenderUpdate(double t, double interplationFactor);
    }
    public EntityPhysicsUpdate physicsUpdate;
    private boolean hasPhysicsUpdate = false;
    public EntityRenderUpdate renderUpdate;
    private boolean hasRenderUpdate = false;

    public void SetPhysicsUpdate(EntityPhysicsUpdate physicsUpdate){
        this.physicsUpdate = physicsUpdate;
        hasPhysicsUpdate = true;
    }
    public void PhysicsUpdate(double t, double dt){
        if(!hasPhysicsUpdate)
            return;
        this.physicsUpdate.PhysicsUpdate(t,dt);
    }

    public void RenderUpdate(double t,double interpolationFactor) {
        if(!hasRenderUpdate)
            return;
        this.renderUpdate.RenderUpdate(t,interpolationFactor);
    }
    public void SetRenderUpdate(EntityRenderUpdate renderUpdate){
        this.renderUpdate = renderUpdate;
        hasRenderUpdate = true;
    }
}

