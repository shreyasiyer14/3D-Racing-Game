package mygame;
import vehicles.Ferrari;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.bullet.PhysicsSpace;
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
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
         if (settings.getRenderer().startsWith("LWJGL")) {
            BasicShadowRenderer bsr = new BasicShadowRenderer(assetManager, 512);
            bsr.setDirection(new Vector3f(-0.5f, -0.3f, -0.3f).normalizeLocal());
         //   viewPort.addProcessor(bsr);
        }
         
        cam.setFrustumFar(150f);
        flyCam.setMoveSpeed(100);
        
        Ferrari ferrari = new Ferrari (5f, new Vector3f(240f, -50f, 15f), 20f, 400f,assetManager);
        ferrari.initFerrari();
        VehicleControls Control= new VehicleControls( ferrari , inputManager);
        Control.setupKeys();
        Terrain1 terrain =new Terrain1("1st", 65, new Vector3f(0, -100, 0), new Vector3f(2f,1f,2f) , assetManager);
        terrain.init_terrain();
        
        //flyCam.setEnabled(false);
        /*CameraNode camNode = new CameraNode("Camera Node", cam);
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(new Vector3f(0, 4, 12));
        camNode.rotate(0, 22, 0);
        ferrari.getCarNode().attachChild(camNode);*/
        getPhysicsSpace().add(ferrari.getController());
        
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.5f, -1f, -0.3f).normalizeLocal());
        rootNode.addLight(dl);

        dl = new DirectionalLight();
        dl.setDirection(new Vector3f(0.5f, -0.1f, 0.3f).normalizeLocal());
        
        rootNode.attachChild(terrain.get_TerrainQuad());
        rootNode.attachChild(ferrari.getCarNode());
        //rootNode.addLight(dl);
    } 
    

}
