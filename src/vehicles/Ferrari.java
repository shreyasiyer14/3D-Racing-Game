/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Ferrari car description
 * Created on: 8/11/2016
 */
package vehicles;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingBox;
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.bullet.objects.VehicleWheel;
import com.jme3.asset.*;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;

/**
 *
 * @author EOF-1
 */
public class Ferrari extends Vehicle{
    
    private VehicleWheel fr, fl, br, bl;
    private Node node_fr, node_fl, node_br, node_bl;
    private float wheelRadius;
    private final float carScale;
    private final Vector3f carTransform;
    private final float carSpeed;
    private final float carMass;
    private Geometry ferrariChassis;
    private BoundingBox collider;
    private BulletAppState bulletAppState;
    private VehicleControl player;
    private AppStateManager stateManager;
    private final AssetManager assetManager;
    private Node Car;

    public boolean rearLook = false;
    public Ferrari (float scale, Vector3f transform, float speed, float mass, AssetManager assetManager, ColorRGBA color) {
        carScale = scale;
        carTransform = transform;
        carSpeed = speed;
        carMass = mass;
        this.assetManager= assetManager;
        Car = (Node)assetManager.loadModel("Models/Ferrari/Car.scene");
    }
    
    //Initialize the various parameters of the car, and attach it to a VehicleController class, and a box collider as well.
    //We even separate out the wheel spatials of the car, and add controls to them.
    //So that the static spatial wheels can act as proper wheels, (rotating and turning)
    @Override
    public void initVehicle () {
        float stiffness = 120.0f;//200=f1 car
        float compValue = 0.2f; //(lower than damp!)
        float dampValue = 0.3f;

        Car.setLocalTranslation(carTransform);
        Car.setShadowMode(ShadowMode.CastAndReceive);
        Geometry chasis = getGeometryOfNode(Car, "Car");
        BoundingBox box = (BoundingBox) chasis.getModelBound();
        
        //Create a hull collision shape for the chassis
        CollisionShape carHull = CollisionShapeFactory.createDynamicMeshShape(chasis);
        //Create a vehicle control
        player = new VehicleControl(carHull, carMass);

        //Setting default values for wheels
        player.setSuspensionCompression(compValue * 2.0f * FastMath.sqrt(stiffness));
        player.setSuspensionDamping(dampValue * 2.0f * FastMath.sqrt(stiffness));
        player.setSuspensionStiffness(stiffness);
        player.setMaxSuspensionForce(10000);
      

        //Create four wheels and add them at their locations
        //note that our fancy car actually goes backwards..
        Vector3f wheelDirection = new Vector3f(0, -1, 0);
        Vector3f wheelAxle = new Vector3f(-1, 0, 0);

        Geometry wheel_fr = getGeometryOfNode(Car, "WheelFrontRight");
        wheel_fr.center();
        box = (BoundingBox) wheel_fr.getModelBound();
        wheelRadius = box.getYExtent();
        float back_wheel_h = (wheelRadius * 1.7f) - 1f;
        float front_wheel_h = (wheelRadius * 1.9f) - 1f;
        player.addWheel(wheel_fr.getParent(), box.getCenter().add(0, -front_wheel_h, 0),
                wheelDirection, wheelAxle, 0.2f, wheelRadius, true);

        Geometry wheel_fl = getGeometryOfNode(Car, "WheelFrontLeft");
        wheel_fl.center();
        box = (BoundingBox) wheel_fl.getModelBound();
        player.addWheel(wheel_fl.getParent(), box.getCenter().add(0, -front_wheel_h, 0),
                wheelDirection, wheelAxle, 0.2f, wheelRadius, true);

        Geometry wheel_br = getGeometryOfNode(Car, "WheelBackRight");
        wheel_br.center();
        box = (BoundingBox) wheel_br.getModelBound();
        player.addWheel(wheel_br.getParent(), box.getCenter().add(0, -back_wheel_h, 0),
                wheelDirection, wheelAxle, 0.2f, wheelRadius, false);

        Geometry wheel_bl = getGeometryOfNode(Car, "WheelBackLeft");
        wheel_bl.center();
        box = (BoundingBox) wheel_bl.getModelBound();
        player.addWheel(wheel_bl.getParent(), box.getCenter().add(0, -back_wheel_h, 0),
                wheelDirection, wheelAxle, 0.2f, wheelRadius, false);

        player.getWheel(1).setFrictionSlip(2);
        player.getWheel(3).setFrictionSlip(2);
        player.getWheel(0).setFrictionSlip(2);
        player.getWheel(2).setFrictionSlip(2);
        Car.setLocalRotation(new Quaternion(0f,-0.39f, 0f, 1f));

        Car.addControl(player);

        //ferrariCar.scale(carScale);
        collider = box;
        getPhysicsSpace().add(Car);

    }
    @Override
    public void setCarNode (Node carNode) {
        Car = carNode;
    }
    @Override
    public Node getCarNode () {
        return Car;
    }
   
    @Override
    public BoundingBox getBoxCollider() {
        return collider;
    }
    
    @Override
    public VehicleControl getController() {
        return player;
    }
    
}
