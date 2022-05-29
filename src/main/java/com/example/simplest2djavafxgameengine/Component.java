package com.example.simplest2djavafxgameengine;

public class Component {
    public boolean enabled;
    protected boolean debug;
    public Entity entity;
    Component(Entity e){
        this.entity = e;
    }
    public void EnableDebug(){
        debug = true;
    }
    public void DisableDebug(){
        debug = false;
    }
}
