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
    
    
    private Ferrari Vehicle; 
    private float accelerationValue;
    private float steeringValue;
    int BreakPower;
    InputManager inputManager;
    public VehicleControls( Ferrari Vehicle, 
             InputManager inputManager ){
        this.Vehicle= Vehicle;
        this.inputManager= inputManager;      
    }
    
    
    public void setupKeys() {
        inputManager.addMapping("Lefts", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("Rights", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Ups", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("Downs", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Space", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Reset", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addListener(this, "Lefts");
        inputManager.addListener(this, "Rights");
        inputManager.addListener(this, "Ups");
        inputManager.addListener(this, "Downs");
        inputManager.addListener(this, "Space");
        inputManager.addListener(this, "Reset");
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
        } else if (binding.equals("Rights")) {
            if (value) {
                steeringValue += -.5f;
            } else {
                steeringValue += .5f;
            }
            Vehicle.getController().steer(steeringValue);
        } //note that our fancy car actually goes backwards..
        else if (binding.equals("Ups")) {
            if (value) {
                accelerationValue -= 800;
            } else {
                accelerationValue += 800;
            }
            Vehicle.getController().accelerate(accelerationValue);
            Vehicle.getController().setCollisionShape(CollisionShapeFactory.createDynamicMeshShape(Vehicle.getGeometryOfNode(Vehicle.getCarNode(), "Car")));
        } else if (binding.equals("Downs")) {
            if (value) {
                Vehicle.getController().brake(40f);
            } else {
                Vehicle.getController().brake(0f);
            }
        } else if (binding.equals("Reset")) {
            if (value) {
                System.out.println("Reset");
                Vehicle.getController().setPhysicsLocation(new Vector3f(240f, -50f, 15f));
                Vehicle.getController().setPhysicsRotation(new Matrix3f());
                Vehicle.getController().setLinearVelocity(Vector3f.ZERO);
                Vehicle.getController().setAngularVelocity(Vector3f.ZERO);
                Vehicle.getController().resetSuspension();
            } else {
            }
        }
    }
}
