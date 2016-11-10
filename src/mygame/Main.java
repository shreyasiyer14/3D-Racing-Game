package mygame;
import vehicles.Ferrari;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.bullet.objects.VehicleWheel;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import terrain.Terrain1;
public class Main extends SimpleApplication {
    private BulletAppState physicsEngine;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        physicsEngine = new BulletAppState();
        stateManager.attach(physicsEngine);
        Ferrari ferrari = new Ferrari (5f, new Vector3f(240f, -50f, 15f), 20f, 40f);
        ferrari.setCarNode((Node)assetManager.loadModel("Models/Ferrari/Car.scene"));
        ferrari.initFerrari();
        ferrari.setChassis(getGeometryOfNode(ferrari.getCarNode(), "Car"));
        
        Terrain1 terrain =new Terrain1("1st", 65, new Vector3f(0, -100, 0), new Vector3f(2f,1f,2f) , assetManager);
        terrain.init_terrain();
        
        flyCam.setEnabled(false);
        CameraNode camNode = new CameraNode("Camera Node", cam);
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(new Vector3f(0, 4, 12));
        camNode.rotate(0, 22, 0);
        ferrari.getCarNode().attachChild(camNode);
        
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(0.1f, -0.7f, 0.0f));
        
        rootNode.attachChild(terrain.get_TerrainQuad());
        rootNode.attachChild(ferrari.getCarNode());
        rootNode.addLight(sun);
    } 
    private Geometry getGeometryOfNode(Spatial spatial, String name) {
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
