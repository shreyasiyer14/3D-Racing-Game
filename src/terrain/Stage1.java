/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package terrain;

/**
 *
 * @author udit
 */

import com.jme3.asset.AssetManager;
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
import com.jme3.app.Application;
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.control.VehicleControl;
import com.jme3.font.BitmapText;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;


public class Stage1 {
    
   private AssetManager assetManager;
   private Spatial Scene;
   private Vector3f LocalTrasnlation;
    private float LocalScale;
   
   public Stage1(Vector3f LocalTrasnlation, float LocalScale, AssetManager assetManager){
       this.assetManager=  assetManager;
       this.LocalScale=LocalScale;
       this.LocalTrasnlation= LocalTrasnlation;
   }
   private void init_material() {
       
   }
   public void init_stage1(){
       Scene= assetManager.loadModel("Models/Tracks/Race Track/RaceTrack/FullTrack.j3o");
       Scene.scale(LocalScale);
       Scene.setShadowMode(ShadowMode.Receive);
       Scene.addControl(new RigidBodyControl(0));
       getPhysicsSpace().add(Scene);
      // Scene.setLocalTranslation(LocalTrasnlation);
       
   }
   public Spatial get_Stage(){
        return Scene;
    }
    
}

