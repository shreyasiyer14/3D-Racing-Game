/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Ferrari car description
 * Created on: 8/11/2016
 */
package ai;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.bullet.objects.VehicleWheel;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.io.IOException;
import java.util.Vector;
import util.TextLoader;

/**
 *
 * @author EOF-1
 */
public class AICar {
    private VehicleWheel fr, fl, br, bl;
    private Node node_fr, node_fl, node_br, node_bl;
    private float wheelRadius;
    private float carScale;
    private float carSpeed;
    private float carMass;
    private float currentCoordX;
    private float currentCoordY;
    private float currentCoordZ;

    private Geometry ferrariChassis;
    private BoundingBox collider;
    private BulletAppState bulletAppState;
    private VehicleControl player;
    private AppStateManager stateManager;
    private AssetManager assetManager;
    private Node Car;
    private Vector pathTraceCoords;
    
    public AICar(float scale, float speed, float mass, AssetManager assetManager) {
        carScale = scale;
        carSpeed = speed;
        carMass = mass;
        this.assetManager= assetManager;
        Car = (Node)assetManager.loadModel("Models/Ferrari/Car.scene");
        pathTraceCoords = new Vector(1,1);
    }
    
    public void initAICar() throws IOException {
        initTracingCoordinates();
        currentCoordX = (Float)pathTraceCoords.get(0);
        currentCoordY = (Float)pathTraceCoords.get(1);
        currentCoordZ = (Float)pathTraceCoords.get(2);
        Car.setLocalTranslation((Float)pathTraceCoords.get(3), (Float)pathTraceCoords.get(4), (Float)pathTraceCoords.get(5));
        float stiffness = 120.0f;//200=f1 car
        float compValue = 0.2f; //(lower than damp!)
        float dampValue = 0.3f;
        Car.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        Geometry chasis = getGeometryOfNode(Car, "Car");
        BoundingBox box = (BoundingBox) chasis.getModelBound();
        CollisionShape carHull = CollisionShapeFactory.createDynamicMeshShape(chasis);

        //Create a vehicle control
        player = new VehicleControl(carHull, carMass/10f);

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
        
        AIMove();
        Car.addControl(player);
    }
    
    public void initTracingCoordinates() throws IOException {
        assetManager.registerLoader(TextLoader.class, "txt");
        String file = (String) assetManager.loadAsset("Models/AIPathCoordinates.txt");
        String[] currentLine = file.split(" ");
        for (String currentLine1 : currentLine) {
            pathTraceCoords.addElement(Float.parseFloat(currentLine1));
        }
    }
    
    public Geometry getGeometryOfNode(Spatial spatial, String name) {
        if (spatial instanceof Node) {
            Node node = (Node) spatial;
            for (int i = 0; i < node.getQuantity(); i++) {
                Spatial child = node.getChild(i);
                Geometry result = getGeometryOfNode(child, name);
                if (result != null) {
                    return result;
                }
            }
        } else if (spatial instanceof Geometry) {
            if (spatial.getName().startsWith(name)) {
                return (Geometry) spatial;
            }
        }
        return null;
    }
    public VehicleControl getController() {
        return player;
    }
    
    public Node getCarNode() {
        return Car;
    }
    public void AIMove() {
        Car.lookAt(new Vector3f(currentCoordX, currentCoordY, currentCoordZ), Vector3f.UNIT_X);
        player.setCollisionShape(CollisionShapeFactory.createDynamicMeshShape(getGeometryOfNode(Car, "Car")));
        player.applyWheelTransforms();
        
    }
}


