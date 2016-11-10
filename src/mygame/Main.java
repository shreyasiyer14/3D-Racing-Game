package mygame;
import vehicles.Ferrari;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.terrain.*;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import terrain.Terrain1;
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        Ferrari ferrari = new Ferrari (5f, new Vector3f(240f, 22f, 20f), 20f, 40f);
        ferrari.setCarNode((Node)assetManager.loadModel("Models/Ferrari/Car.scene"));
        ferrari.initFerrari();
        Terrain1 terrain =new Terrain1("1st", 65, new Vector3f(0, -100, 0), 
                new Vector3f(2f,1f,2f) , assetManager);
        terrain.init_terrain();
        rootNode.attachChild(terrain.get_TerrainQuad());
        flyCam.setEnabled(false);
        CameraNode camNode = new CameraNode("Camera Node", cam);
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(new Vector3f(0, 4, 12));
        camNode.rotate(0, 22, 0);
        ferrari.getCarNode().attachChild(camNode);
        rootNode.attachChild(ferrari.getCarNode());
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(0.1f, -0.7f, 0.0f));
        rootNode.addLight(sun);
    } 
 
}
