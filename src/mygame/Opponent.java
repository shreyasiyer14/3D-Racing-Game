/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Ferrari car description
 * Created on: 8/11/2016
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import vehicles.Vehicle;

/**
 *
 * @author EOF-1
 */
public class Opponent {
    private Node opponent;
    private Vector3f carTransform;
    private float carMass;
    private RigidBodyControl rbc;
    private AssetManager assetManager;
    public Opponent(Vector3f transform, float mass, AssetManager am) {
        carTransform = transform;
        carMass = mass;
        assetManager = am;
        opponent = (Node)assetManager.loadModel("Models/Ferrari/Car.scene");

    }
    //Initializes the dummy of the opponent client in the game world, and attach a rigidbodycontroller and a box collider to it.
    //Lastly adding it to the physics space.
    public void initOpponent() {
        opponent.setLocalTranslation(carTransform);
        Geometry chasis = Vehicle.getGeometryOfNode(opponent, "Car");
        BoundingBox box = (BoundingBox) chasis.getModelBound();
        CollisionShape carHull = CollisionShapeFactory.createDynamicMeshShape(chasis);
        rbc = new RigidBodyControl(carMass);
        opponent.addControl(rbc);
        rbc.setKinematic(true);
        rbc.setKinematicSpatial(true);
        getPhysicsSpace().add(opponent);
    }
    
    public RigidBodyControl getController() {
        return rbc;
    }
    
    public Node getCarNode() {
        return opponent;
    }
}
