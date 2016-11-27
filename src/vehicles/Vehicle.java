/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.asset.*;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

/**
 *
 * @author udit
 */
abstract public class Vehicle {
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
    private Material mat;
    private ColorRGBA matColor;
    public boolean rearLook = false;
    /**
     *
     */
    abstract public void initVehicle();
    abstract public void setCarNode (Node carNode);
    abstract public Node getCarNode ();
    abstract public BoundingBox getBoxCollider();
    abstract public VehicleControl getController();
    public static Geometry getGeometryOfNode(Spatial spatial, String name) {
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
  
}
