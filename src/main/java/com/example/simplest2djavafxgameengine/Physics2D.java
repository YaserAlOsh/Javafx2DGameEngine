package com.example.simplest2djavafxgameengine;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;
import java.util.random.RandomGenerator;

public class Physics2D extends Application {
    public float width = 1440;
    public float height = 810;
    /* Physics and Time-step */
    public int framesPerSecond = 60;
    private long frameDelta = 1000/60;
    private double deltaTime = 30 / 1000.0;
    private double refreshRate = 60 / 1000.0;
    public double time = 0.0;
    List<Rigidbody> rigidBodies;
    List<Entity> entities;
    /* Canvas */
    public double paneMinX, paneMaxX, paneMinY,paneMaxY;
    public Vector canvasMin, canvasMax;
    /* Input Variables */
    Input inputManager;
    /* Player Variables */
    Entity player;
    Vector direction = Vector.ZERO();
    boolean jump = false, jumping=false;
    private double movingSpeed = 25;
    private double jumpForce = 250;
    private double lastJumpTime=0;
    private double jumpTime=0.5*1000;

    public boolean quit = false;
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(width,height);
        Pane root = new Pane();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREY);
        root.getChildren().add(canvas);
        frameDelta = 1000000000  / framesPerSecond;
        rigidBodies = new ArrayList<>();
        entities = new ArrayList<>();

        Scene scene = new Scene(root,width,height);
        paneMinX = paneMinY = 0;
        paneMaxX = root.getWidth();
        paneMaxY = root.getHeight();
        canvasMin = new Vector(paneMinX,paneMinY);
        canvasMax = new Vector(paneMaxX,paneMaxY);
        inputManager = new Input(new KeyCode[]{KeyCode.RIGHT,KeyCode.LEFT,KeyCode.UP,KeyCode.DOWN,KeyCode.SPACE});
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            inputManager.KeyPressed(key, this::HandlePressedInput);
        });
        scene.addEventFilter(KeyEvent.KEY_RELEASED, key -> {
            inputManager.KeyReleased(key, this::HandleReleasedInput);

        });

        double midWidth = root.getWidth()/2, midHeight = root.getHeight()/2;
        CircleEntity circleEntity = new CircleEntity("Player",10.0,new Vector(root.getWidth()/2,root.getHeight()/2));
        circleEntity.SetFill(Color.BLACK);
        circleEntity.AddRigidbody();
        root.getChildren().add(circleEntity.circleObj);
        circleEntity.SetPhysicsUpdate(new Entity.EntityPhysicsUpdate(){

            @Override
            public void PhysicsUpdate(double t,double dt) {
                Vector force = direction.scale(movingSpeed*2);
                if(force.dot(player.rigidbody.velocity) < 0)
                    force.scale(5,true);
                player.rigidbody.AddForce(force);
                if(jump) {
                    lastJumpTime = t;
                    jumping = !(jump = false);
                }
                if(jumping && t - lastJumpTime < jumpTime)
                    player.rigidbody.AddForce(direction.scale(jumpForce));
                else
                    jumping = false;
            }
        });
        rigidBodies.add(circleEntity.rigidbody);
        entities.add(circleEntity);
        player = circleEntity;


        /* Add Walls */
        double wallThickness = 10;
        BoxEntity wallLeft = new BoxEntity("Left Wall",new Vector(wallThickness/2,midHeight),wallThickness,root.getHeight()).AddCollider();
        BoxEntity wallRight = new BoxEntity("Right Wall",new Vector(root.getWidth()-wallThickness/2,midHeight), wallThickness,root.getHeight()).AddCollider();
        BoxEntity wallTop = new BoxEntity("Top Wall",new Vector(midWidth,wallThickness/2), root.getWidth(),wallThickness).AddCollider();
        BoxEntity wallBottom = new BoxEntity("Bottom Wall",new Vector(midWidth,root.getHeight() - wallThickness/2), root.getWidth(),wallThickness).AddCollider();
        root.getChildren().add(wallLeft.rectObj);
        root.getChildren().add(wallRight.rectObj);
        root.getChildren().add(wallTop.rectObj);
        root.getChildren().add(wallBottom.rectObj);

        entities.add(wallLeft);
        entities.add(wallTop);
        entities.add(wallRight);
        entities.add(wallBottom);
        /* Add Enemies */
        for(int i=0;i<5;i++){
            RandomGenerator gen = RandomGenerator.getDefault();

            double mass = gen.nextDouble(1.0,2.0);
            double r = mass * 10.0;
            double x = wallThickness + r + 5 * wallThickness *(i+1);
            double y = wallThickness + r + 5 * wallThickness *(i+1);
            CircleEntity circleEntity1 = new CircleEntity("circle"+i,r, new Vector(x,y));
            System.out.println(circleEntity1.position);
            circleEntity1.SetFill(Color.color(gen.nextDouble(),gen.nextDouble(),gen.nextDouble()));
            circleEntity1.AddRigidbody();
            circleEntity1.rigidbody.SetVelocity(new Vector(gen.nextDouble(1),gen.nextDouble(1)).normalize().scale(movingSpeed/mass));
            circleEntity1.rigidbody.SetElasticity(gen.nextDouble(0.1,1));
            root.getChildren().add(circleEntity1.circleObj);
            rigidBodies.add(circleEntity1.rigidbody);
            entities.add(circleEntity1);
        }


        primaryStage.setScene(scene);
        primaryStage.setTitle("The Simplest 2D Physics Simulation");
        primaryStage.show();
        double currTime = Calendar.getInstance().getTimeInMillis() / 1000.0;
        double accumulator = 0.0;

        new AnimationTimer(){
            long lastTime = 0;
            double currTime = Calendar.getInstance().getTimeInMillis() / 1000.0;
            double accumulator = 0.0;
            double updateAccumulator = 0.0;
            double avg;
            double frameTime;
            int i =0;
            @Override
            public void handle(long l) {
                if(lastTime==0) {
                    lastTime = l;
                    currTime = l / 1000000000.0;
                    //loop(gc,frameDelta);
                    Render(0);
                    return;
                }
                //long delta = l - lastTime;
                /*if(l - lastTime >= frameDelta){
                    loop(gc,l - lastTime);
                    lastTime = l;
                }*/

                double newTime= l / 1000000000.0;
                frameTime = newTime - currTime;
                if(i < 1000) {
                    avg += frameTime;
                    i++;
                }else if(i==1000){
                    //System.out.println("Avg fps: "+(avg / 1000));
                    i = 0;
                }
                //System.out.println("FPS: "+getFrameRateHertz());
                currTime = newTime;
                accumulator += frameTime;
                updateAccumulator += frameTime;
                while(accumulator >= deltaTime){
                    PhysicsUpdate(time,deltaTime);
                    accumulator -= deltaTime;
                    time += deltaTime;
                }
                //System.out.println(accumulator+" "+deltaTime);
                double alpha = accumulator / deltaTime;
                //if(updateAccumulator >= refreshRate) {
                    Render(alpha);
                  //  updateAccumulator = 0;
                //}
            }
            public double getFrameRateHertz() {

                return  1d / frameTime;
            }
        }.start();

    }

    private void PhysicsUpdate(double t, double dt){

        for(Entity entity : entities)
            entity.PhysicsUpdate(t,dt);
        for(Rigidbody rigidbody : rigidBodies){
            //if(player == entity)
            //  continue;
            //if(entity.rigidbody.position )
            rigidbody.Integrate(dt);
            rigidbody.ResetForces();
        }
        /*
         * Here we should process constraints, which are collision constraints and user constraints
         * */
        HashMap<Rigidbody, Rigidbody> solvedDynamicCollisions = new HashMap();
        for(Rigidbody rigidbody : rigidBodies){
            for(Entity entity : entities)
            {
                if(!entity.HasCollider())
                    continue;
                if(entity.HasRigidbody() && entity.rigidbody == rigidbody){
                    continue;
                }
                Collider c = entity.GetComponent(Collider.class);

                Hit hitInfo = new Hit();
                if(rigidbody.collider.isIntersecting(c,hitInfo)){

                    Vector v1New = Vector.ZERO(); Vector v2New = Vector.ZERO();
                    if(entity.HasRigidbody()){
                        if(solvedDynamicCollisions.containsKey(entity.rigidbody))
                            if(solvedDynamicCollisions.get(entity.rigidbody) == rigidbody)
                                continue;
                        System.out.println(rigidbody.entity.name+" Hit: "+entity.name);
                        solvedDynamicCollisions.put(rigidbody,entity.rigidbody);
                        PhysicsCollisionSolver.SolveCollision(rigidbody,entity.rigidbody,hitInfo,v1New,v2New);
                        entity.rigidbody.Integrate(dt);
                    }
                    else {
                        PhysicsCollisionSolver.SolveCollision(rigidbody, c,hitInfo, v1New);
                    }
                    rigidbody.Integrate(dt);
                }
            }
        }
    }

    private void Render(double interpolationFactor){
        /*
        Render the entities with their new positions.
         */
        for(Entity entity: entities){
            entity.RenderUpdate(time,interpolationFactor);
            if(entity.OutsideCanvas(canvasMin,canvasMax)){
                System.out.println(entity.position);
                entity.SetPosition(new Vector((paneMinX + paneMaxX)/2,(paneMinY+paneMaxY)/2));
            }else {
                entity.UpdatePositionInCanvas(interpolationFactor);
            }
            Rigidbody rig = entity.GetComponent(Rigidbody.class);
            if(rig != null)
                rig.ResetForces();

        }
    }


    private void HandlePressedInput(KeyEvent key){
        if (key.getCode() == KeyCode.RIGHT) {
            direction.add(Vector.RIGHT,true);
        }
        if (key.getCode() == KeyCode.LEFT) {
            direction.add(Vector.LEFT,true);
        }
        if (key.getCode() == KeyCode.DOWN) {
            direction.add(Vector.UP,true);
        }
        if (key.getCode() == KeyCode.UP) {
            direction.add(Vector.DOWN,true);
        }
        if (key.getCode() == KeyCode.SPACE) {
            jump = !jumping;
        }
    }
    private void HandleReleasedInput(KeyEvent key){
        if (key.getCode() == KeyCode.RIGHT) {
            direction.sub(Vector.RIGHT,true);
        }
        if (key.getCode() == KeyCode.LEFT) {
            direction.sub(Vector.LEFT,true);
        }
        if (key.getCode() == KeyCode.DOWN) {
            direction.sub(Vector.UP,true);
        }
        if (key.getCode() == KeyCode.UP) {
            direction.sub(Vector.DOWN, true);
        }
    }

}

