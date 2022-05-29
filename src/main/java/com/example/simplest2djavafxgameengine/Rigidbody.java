package com.example.simplest2djavafxgameengine;

public class Rigidbody extends Component {
    public Vector position;
    public Vector velocity;
    public Vector acceleration;
    public Collider collider;
    public boolean hasCollider;

    public double mass;
    Vector force;
    Vector linImpulse;
    protected double elasticity = 1.0;

    private Vector prevPosition = new Vector(0,0);
    public Vector renderPosition = new Vector(0,0);

    public boolean interpolate = true;


    Rigidbody(Entity entity){
        this(entity,0,0);
    }
    Rigidbody(Entity entity,double x, double y){
        super(entity);
        position = new Vector(x,y);
        velocity = new Vector();
        acceleration = new Vector();
        force = new Vector();
        linImpulse = new Vector();
        mass = 1.0;
        renderPosition = position;
    }
    Rigidbody(Entity entity, double x, double y,Collider coll){
        super(entity);
        position = new Vector(x,y);
        velocity = new Vector();
        acceleration = new Vector();
        collider = coll;
        hasCollider = true;
        mass = 1.0;
    }
    Rigidbody(Entity entity, Collider coll){
        super(entity);
        position = coll.position;
        velocity = new Vector();
        acceleration = new Vector();
        collider = coll;
        hasCollider = true;
        force = new Vector();
        linImpulse = new Vector();
        mass = 1.0;
    }
    public Vector getPosition(){
        return position;
    }
    public Vector getVelocity(){
        return velocity;
    }
    public Vector getAcceleration(){
        return acceleration;
    }
    public void SetElasticity(double e){
        this.elasticity = e;
    }
    public double GetElasticity(){
        return this.elasticity;
    }

    public void SetVelocity(Vector velocity) {
        this.velocity = velocity;
    }
    public void SetVelocity(double x, double y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }
    public void SetForce(Vector force){
        this.force = force;
    }
    public void AddForce(Vector force){
        this.force.add(force,true);
    }
    public void SetAcceleration(Vector acceleration){
        this.acceleration = acceleration;
    }

    public void Integrate(double dt){
        acceleration = force.divide(mass);
        velocity.add(acceleration.scale(dt),true);
        prevPosition.Set(position);
        position.add(velocity.scale(dt),true);
    }
    public void Integrate(double dt, double interpolationFactor){
        acceleration = force.divide(mass);
        velocity.add(acceleration.scale(dt),true);
        position.add(velocity.scale(dt));
    }
    public void ResetForces() {
        force.Zero();
        linImpulse.Zero();
    }

    public void Reset() {
        velocity.Zero();
        acceleration.Zero();
        ResetForces();
    }

    public Vector GetRenderPosition(double interpolationFactor){
        if(!interpolate)
            return position;
        renderPosition.Set(position.scale(interpolationFactor).add(prevPosition.scale(1-interpolationFactor)));
        return renderPosition;
    }
}

