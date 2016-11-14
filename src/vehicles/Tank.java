/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import com.jme3.scene.Spatial;
import mygame.VehicleControls;



/**
 *
 * @author udit
 */
public class Tank extends Vehicle {
    private Node Car;
    private float carScale;
    private Vector3f carTransform;
    private float carSpeed;
    private float carMass;
    private Geometry ferrariChassis;
    private BoundingBox collider;
    private BulletAppState bulletAppState;
    private VehicleControl player;
    private AppStateManager stateManager;
    private AssetManager assetManager;
    public Tank (float scale, Vector3f transform, float speed, float mass, AssetManager assetManager) {
        this.carScale = scale;
        carTransform = transform;
        carSpeed = speed;
        carMass = mass;
        this.assetManager= assetManager;
        Car = (Node)assetManager.loadModel("Models/Ferrari/Car.scene");

    }
    @Override
    public void initVehicle () {
        float stiffness = 120.0f;//200=f1 car
        float compValue = 0.2f; //(lower than damp!)
        float dampValue = 0.3f;
        //Load model and get chassis Geometry
        Car.scale(carScale);
        Car.setLocalTranslation(carTransform);
        Car.setShadowMode(ShadowMode.CastAndReceive);
        
        Geometry chasis = getGeometryOfNode(Car, "Car");
        BoundingBox box = (BoundingBox) chasis.getModelBound();
        

        //Create a hull collision shape for the chassis
        CollisionShape tankHull = CollisionShapeFactory.createDynamicMeshShape(chasis);

        //Create a vehicle control
        player = new VehicleControl(tankHull, carMass);
        player.setSuspensionCompression(compValue * 2.0f * FastMath.sqrt(stiffness));
        player.setSuspensionDamping(dampValue * 2.0f * FastMath.sqrt(stiffness));
        player.setSuspensionStiffness(stiffness);
        player.setMaxSuspensionForce(10000);
        Car.addControl(player);
        player.setFriction(carScale);
    }
    @Override
    public void setCarNode (Node tankNode) {
        Car = tankNode;
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
