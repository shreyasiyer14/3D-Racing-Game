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
        initTerrain();
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
    public void initTerrain () {
        Material mat_terrain = new Material(assetManager, 
            "Common/MatDefs/Terrain/Terrain.j3md");
        mat_terrain.setTexture("Alpha", assetManager.loadTexture(
            "Textures/Terrain/splat/alphamap.png"));
        Texture grass = assetManager.loadTexture(
            "Textures/Terrain/splat/grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex1", grass);
        mat_terrain.setFloat("Tex1Scale", 64f);
        Texture dirt = assetManager.loadTexture(
            "Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex2", dirt);
        mat_terrain.setFloat("Tex2Scale", 32f);
        Texture rock = assetManager.loadTexture(
            "Textures/Terrain/splat/road.jpg");
        rock.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex3", rock);
        mat_terrain.setFloat("Tex3Scale", 128f);
        AbstractHeightMap heightmap = null;
        Texture heightMapImage = assetManager.loadTexture(
            "Textures/Terrain/splat/mountains512.png");
        heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightmap.load();
        int patchSize = 65;
        TerrainQuad terrain = new TerrainQuad("my terrain", patchSize, 513, heightmap.getHeightMap());
        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(-100, 0, -100);
        terrain.setLocalScale(2f, 1f, 2f);
        rootNode.attachChild(terrain);
        TerrainLodControl control = new TerrainLodControl(terrain, getCamera());
        terrain.addControl(control);
    }
}
