/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Ferrari car description
 * Created on: 8/11/2016
 */
package vehicles;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.ArrayList;
import java.util.List;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.asset.AssetManager;
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
    
    
    
}
