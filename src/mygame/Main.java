package mygame;
import ai.AICar;
import vehicles.*;
import util.TextLoader;
import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.bullet.PhysicsSpace;
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import terrain.*;
public class Main extends SimpleApplication {
    private BulletAppState physicsEngine;
    private AICar bot;
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
            viewPort.addProcessor(bsr);
        }
        cam.setFrustumFar(1000f);
      
        viewPort.setBackgroundColor(ColorRGBA.White);
        
        Vehicle ferrari = new Ferrari (0.3f, new Vector3f(-19f, 18,-2f), 20f, 1000f,assetManager, ColorRGBA.Red);
        ferrari.initVehicle();
        VehicleControls Control= new VehicleControls("Car", ferrari ,1725f, inputManager);
        Control.setupKeys();
        
        
        //Vehicle ferrari2 = new Ferrari (0.5f, new Vector3f(-19f, 18,-6f), 20f, 1000f,assetManager, ColorRGBA.Yellow);
        //ferrari2.initVehicle();
        
        bot = new AICar(0.5f, 20f, 1000f, assetManager);
        try {
            bot.initAICar();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        bot.AIAccelerate();
        //VehicleControls Control1= new VehicleControls( "Car", ferrari2 ,1725f, inputManager);
        //Control1.setupKeys();
        //Terrain1 terrain =new Terrain1("1st", 5, new Vector3f(0, -100, 0), new Vector3f(2f,1f,2f) , assetManager);
        //terrain.init_terrain();
        Stage1 stage= new Stage1(new Vector3f(270f, -20f, 15f), 75f,assetManager);
        
        stage.init_stage1();
        
        //flyCam.setEnabled(false);
        CameraNode camNode = new CameraNode("Camera Node", cam);
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(new Vector3f(0, 4, 12));
        camNode.rotate(0, 22f, 0);
        ferrari.getCarNode().attachChild(camNode);
        getPhysicsSpace().setGravity(new Vector3f(0, -20f, 0));

        //getPhysicsSpace().add(ferrari2.getController());
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.5f, -1f, -0.3f).normalizeLocal());
        rootNode.addLight(dl);

        dl = new DirectionalLight();
        dl.setDirection(new Vector3f(0.5f, 0.0f, 0.3f).normalizeLocal());
        rootNode.attachChild(ferrari.getCarNode());
        //rootNode.attachChild(ferrari2.getCarNode());
        rootNode.attachChild(stage.get_Stage());
        rootNode.attachChild(bot.getCarNode());
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        bot.AIUpdate();
        bot.AIMove();
    }
}
