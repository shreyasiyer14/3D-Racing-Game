/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Terrain description
 * Created on: 8/11/2016

package terrain;

import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.heightmap.HillHeightMap; // for exercise 2
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.ArrayList;
import java.util.List;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;

/**
 *
 * @author EOF-1
 */
/*
public class Terrain {
    private static TerrainQuad terrain;
    private static TerrainLodControl control;
    private static Texture heightMapImage;
    public Terrain (String name, int size, int num, Texture heightMap) {
        
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

}*/
