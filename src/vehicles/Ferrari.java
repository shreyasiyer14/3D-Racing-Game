/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Ferrari car description
 * Created on: 8/11/2016
 */
package vehicles;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
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
