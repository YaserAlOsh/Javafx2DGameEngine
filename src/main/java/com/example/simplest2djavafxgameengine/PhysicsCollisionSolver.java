package com.example.simplest2djavafxgameengine;

public class PhysicsCollisionSolver {

    public static void SolveCollision(Rigidbody rig1, Rigidbody rig2, Hit hitInfo, Vector v1New, Vector v2New) {
        if(hitInfo.penetrationDepth > .01){
            rig1.position.add(hitInfo.penetrationVector.scale(hitInfo.penetrationDepth));
            rig2.position.sub(hitInfo.penetrationVector.scale(hitInfo.penetrationDepth));
            //System.out.println("Penetration: "+hitInfo.penetrationVector.scale(hitInfo.penetrationDepth));
        }
        //v1 = rig1.velocity; v2 = rig.velocity;
        //relVel = v1-v2
        Vector relVelocity = rig1.velocity.sub(rig2.velocity);

        //v1' = V1 - k*<n>/m1
        //v2' = v2 + k*<n>/m2
        //Assume surfaceNormal is a unit-vector
        // Solve for K.
        double k =  (1 + Math.min(rig1.GetElasticity(),rig2.GetElasticity())) * relVelocity.dot(hitInfo.surfaceNormal) / ((1 / rig1.mass) + (1 / rig2.mass));
        rig1.velocity.sub(hitInfo.surfaceNormal.scale(k/rig1.mass),true);
        rig2.velocity.add(hitInfo.surfaceNormal.scale(k/rig2.mass),true);
    }

    public static void SolveCollision(Rigidbody rig, Collider c, Hit hitInfo, Vector v1New) {

        if(hitInfo.penetrationDepth > .01){
            rig.position.add(hitInfo.penetrationVector.scale(hitInfo.penetrationDepth));
            //System.out.println("Penetration: "+hitInfo.penetrationVector.scale(hitInfo.penetrationDepth));
        }
        //v1 = 0. v2 = rig.velocity
        //relVel = v1-v2
        Vector relVelocity = new Vector(rig.velocity.neg());

        //v1' = V1 - k*<n>/m1
        //v2' = v2 + k*<n>/m2
        //Assume mass m1 of collider is infinity
        //Assume surfaceNormal is a unit-vector
        // Solve for K.
        double k = (1 + rig.GetElasticity()) * relVelocity.dot(hitInfo.surfaceNormal) / (1 / rig.mass);
        rig.velocity.add(hitInfo.surfaceNormal.scale(k/rig.mass),true);
        //System.out.println("Vel: "+hitInfo.surfaceNormal);
    }
}
