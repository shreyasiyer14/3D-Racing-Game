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
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Quaternion;
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
    Geometry wheel_fl, wheel_fr, wheel_br, wheel_bl;
    private final float carScale;
    private final float carSpeed;
    private final float carMass;
    public float currentCoordX;
    public float currentCoordY;
    public float currentCoordZ;

    private Geometry ferrariChassis;
    private BoundingBox collider;
    private BulletAppState bulletAppState;
    private VehicleControl player;
    private AppStateManager stateManager;
    private final AssetManager assetManager;
    private final Node Car;
    private final Vector pathTraceCoords;
    private int i = 0;
    private RigidBodyControl rbc;
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
        Car.setLocalTranslation((Float)pathTraceCoords.get(0), (Float)pathTraceCoords.get(1), (Float)pathTraceCoords.get(2));

        currentCoordX = (Float)pathTraceCoords.get(3);
        currentCoordY = (Float)pathTraceCoords.get(4);
        currentCoordZ = (Float)pathTraceCoords.get(5);
        float stiffness = 120.0f;
        float compValue = 0.2f; 
        float dampValue = 0.3f;
        Car.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
                
        Geometry chasis = getGeometryOfNode(Car, "Car");
        BoundingBox box = (BoundingBox) chasis.getModelBound();
        CollisionShape carHull = CollisionShapeFactory.createDynamicMeshShape(chasis);
        rbc = new RigidBodyControl(carMass);
        Car.addControl(rbc);
        rbc.setKinematic(true);
        rbc.setKinematicSpatial(true);

        wheel_fr = getGeometryOfNode(Car, "WheelFrontRight");

        wheel_fl = getGeometryOfNode(Car, "WheelFrontLeft");

        wheel_br = getGeometryOfNode(Car, "WheelBackRight");
        
        wheel_bl = getGeometryOfNode(Car, "WheelBackLeft");
        getPhysicsSpace().add(Car);

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
        Car.lookAt(new Vector3f(currentCoordX, currentCoordY, currentCoordZ), Vector3f.UNIT_Y);
        Car.setLocalRotation(Car.getLocalRotation().opposite());
        Car.move(new Vector3f((currentCoordX - Car.getLocalTranslation().x), (currentCoordY - Car.getLocalTranslation().y), (currentCoordZ - Car.getLocalTranslation().z)).normalize().divide(carSpeed));
        rbc.setCollisionShape(CollisionShapeFactory.createDynamicMeshShape(getGeometryOfNode(Car, "Car")));        
    }
    public void AIAccelerate() { 
        //Car.move(0.2f, 0.0f, 0.0f );
        //player.accelerate(-700f);
    }
    public void AIUpdate() {
        rbc.setPhysicsLocation(Car.getLocalTranslation());
        if (Car.getLocalTranslation().distance(new Vector3f(currentCoordX, currentCoordY, currentCoordZ)) < 3.0f) {
            i += 3;
        }
        if (i < pathTraceCoords.size() - 5) {
            currentCoordX = (Float)pathTraceCoords.get(3 + i);
            currentCoordY = (Float)pathTraceCoords.get(4 + i);
            currentCoordZ = (Float)pathTraceCoords.get(5 + i);
        }
        else if (i > pathTraceCoords.size() - 5) {
            i = 0;
            currentCoordX = (Float)pathTraceCoords.get(3 + i);
            currentCoordY = (Float)pathTraceCoords.get(4 + i);
            currentCoordZ = (Float)pathTraceCoords.get(5 + i);
        }
        AIMove();
        AIAccelerate();

        //UpdateWheels();
    }
    
    public void UpdateWheels() {
        wheel_fl.rotate(new Quaternion(0.0f, 0.0f, 2.0f, 1.0f));
        wheel_fr.rotate(new Quaternion(0.0f, 0.0f, 2.0f, 1.0f));
        wheel_bl.rotate(new Quaternion(0.0f, 0.0f, 2.0f, 1.0f));
        wheel_br.rotate(new Quaternion(0.0f, 0.0f, 2.0f, 1.0f));
    }
}


