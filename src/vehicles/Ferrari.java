/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Ferrari car description
 * Created on: 8/11/2016
 */
package vehicles;
import com.jme3.bounding.BoundingBox;
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
/**
 *
 * @author EOF-1
 */
public class Ferrari {
    private static Node ferrariCar;
    private static float carScale;
    private static Vector3f carTransform;
    private static float carSpeed;
    private static float carMass;
    private static Geometry ferrariChassis;
    private static BoundingBox collider;
    private VehicleControl ferrariController;
    public Ferrari (float scale, Vector3f transform, float speed, float mass) {
        carScale = scale;
        carTransform = transform;
        carSpeed = speed;
        carMass = mass;
    }
    public void initFerrari () {
        ferrariCar.setLocalTranslation(carTransform);
        ferrariCar.scale(carScale);
        ferrariCar.setShadowMode(ShadowMode.CastAndReceive);
    }
    public void setCarNode (Node carNode) {
        ferrariCar = carNode;
    }
    public Node getCarNode () {
        return ferrariCar;
    }
    public void setChassis (Geometry chassis) {
        ferrariChassis = chassis;
        collider = (BoundingBox) ferrariChassis.getModelBound();
        CollisionShape carHull = CollisionShapeFactory.createDynamicMeshShape(ferrariChassis);
        ferrariController = new VehicleControl(carHull, carMass);
        ferrariCar.addControl(ferrariController);
        getPhysicsSpace().add(ferrariController);
    }
    public BoundingBox getBoxCollider() {
        return collider;
    }
    public VehicleControl getController() {
        return ferrariController;
    }
}
