/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Ferrari car description
 * Created on: 8/11/2016
 */
package network;

import com.jme3.math.Vector3f;

/**
 *
 * @author EOF-1
 */
public class RoundRobinSpawn {
    public Vector3f[] spawnPoints;
    
    public RoundRobinSpawn() {
        spawnPoints = new Vector3f[2];
        spawnPoints[0] =  new Vector3f(-20f, 18f,-2f);
        spawnPoints[1] =  new Vector3f(-28f, 18f,-2f);
    }
}
