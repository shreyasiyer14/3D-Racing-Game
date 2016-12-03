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
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Spatial;


public class Stage1 {
    
   private final AssetManager assetManager;
   private Spatial Scene;
   private final Vector3f LocalTranslation;
    private final float LocalScale;
   
   public Stage1(Vector3f LocalTranslation, float LocalScale, AssetManager assetManager){
       this.assetManager=  assetManager;
       this.LocalScale=LocalScale;
       this.LocalTranslation= LocalTranslation;
   }
   private void init_material() {
       
   }
   
   public void init_stage1(){
       Scene= assetManager.loadModel("Models/Tracks/Race Track/RaceTrack/FullTrack.j3o");
       Scene.scale(LocalScale);
       Scene.setShadowMode(ShadowMode.CastAndReceive);
       Scene.addControl(new RigidBodyControl(0));
       getPhysicsSpace().add(Scene);       
   }
   public Spatial get_Stage(){
        return Scene;
    }
    
}

