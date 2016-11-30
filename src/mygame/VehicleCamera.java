/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Camera attached to car's description
 * Created on: 8/11/2016
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.control.CameraControl;

/**
 *
 * @author EOF-1
 */
public class VehicleCamera {
    private CameraNode camNode;
    private Vector3f transform;
    private Vector3f rotation;
    private String name;
    public VehicleCamera (String name, Vector3f transform, Vector3f rotation, Camera cam) {
        this.name = name;
        this.transform = transform;
        this.rotation = rotation;
        camNode = new CameraNode(name, cam);
    }
    //Initialize the parameter of the camera node, and set it's direction and position to the rear of the car.
    public void initCamera() {
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(transform);
        camNode.rotate(rotation.x, rotation.y, rotation.z);
    }
    public CameraNode getCamera() {
        return camNode;
    }
}
