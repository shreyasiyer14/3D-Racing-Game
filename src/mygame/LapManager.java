/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Ferrari car description
 * Created on: 8/11/2016
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author EOF-1
 */
public class LapManager {
    private final Vector3f startPoint;
    private int lapCount;
    private boolean justCompleted = false;
    public LapManager(Vector3f transform, int laps) {
        startPoint = transform;
        lapCount = laps;
    }
    
    public void checkCompletion(Vector3f vehicleTransform, Node guiNode, BitmapFont guiFont, AssetManager assetManager) {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setText("Lap: " + (3 - lapCount) + "/" + "3");
        helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
        guiNode.attachChild(helloText);

        if (vehicleTransform.distance(startPoint) <= 5.0f) {
            if (lapCount == 0)
                System.out.println("DONE");
            else {
                if (!justCompleted) {
                    lapCount -= 1;
                    justCompleted = true;
                }
            }
        }
        if (vehicleTransform.distance(startPoint) > 5.0f) {
            justCompleted = false;
        }
    }
    
}
