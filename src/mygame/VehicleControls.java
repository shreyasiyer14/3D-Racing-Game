/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;
import vehicles.Ferrari;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.asset.AssetManager;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.InputManager;


/**
 *
 * @author udit
 */
public class VehicleControls implements ActionListener {
    
    
    private final Ferrari Vehicle; 
    private float accelerationValue;
    private float steeringValue;
    private final float accelerationPower;
    private float jumpPower;
    int BreakPower;
    InputManager inputManager;
    public VehicleControls( Ferrari Vehicle, float accelerationPower, 
             InputManager inputManager ){
        this.Vehicle= Vehicle;
        this.accelerationPower= accelerationPower;
        this.inputManager= inputManager;      
    }
    
    
    public void setupKeys() {
        inputManager.addMapping("Lefts", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Rights", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Ups", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Downs", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Reset", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("Reverse", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(this, "Lefts");
        inputManager.addListener(this, "Rights");
        inputManager.addListener(this, "Ups");
        inputManager.addListener(this, "Downs");
        inputManager.addListener(this, "Space");
        inputManager.addListener(this, "Reset");
        inputManager.addListener(this, "Reverse");
    }
    @Override
    public void onAction(String binding, boolean value, float tpf) {
        if (binding.equals("Lefts")) {
            if (value) {
                steeringValue += 0.5f;
            } else {
                steeringValue += -.5f;
            }
            Vehicle.getController().steer(steeringValue);
        } if (binding.equals("Rights")) {
            if (value) {
                steeringValue += -.5f;
            } else {
                steeringValue += .5f;
            }
            Vehicle.getController().steer(steeringValue);
        } //note that our fancy car actually goes backwards..
        if (binding.equals("Ups")) {
            if (value) {
                accelerationValue -= accelerationPower;
            } else {
                accelerationValue += accelerationPower;
            }
            Vehicle.getController().accelerate(accelerationValue);
            Vehicle.getController().setCollisionShape(CollisionShapeFactory.createDynamicMeshShape(Vehicle.getGeometryOfNode(Vehicle.getCarNode(), "Car")));
        }
         if (binding.equals("Reverse")) {
            if (value) {
                accelerationValue += accelerationPower;
            } else {
                accelerationValue -= accelerationPower;
            }
            Vehicle.getController().accelerate(accelerationValue);
            Vehicle.getController().setCollisionShape(CollisionShapeFactory.createDynamicMeshShape(Vehicle.getGeometryOfNode(Vehicle.getCarNode(), "Car")));
        }
         if (binding.equals("Downs")) {
            if (value) {
                Vehicle.getController().brake(310f);
            } else {
                Vehicle.getController().brake(0f);
            }
        } if (binding.equals("Reset")) {
            if (value) {
                System.out.println("Reset");
                Vehicle.getController().setPhysicsLocation(new Vector3f(-19f, 18,-2f));
                Vehicle.getController().setPhysicsRotation(new Matrix3f());
                Vehicle.getController().setLinearVelocity(Vector3f.ZERO);
                Vehicle.getController().setAngularVelocity(Vector3f.ZERO);
                Vehicle.getController().resetSuspension();
            } else {
            }
        }
    }
}
