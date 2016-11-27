/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Information about the Laps is stored here.
 * Created on: 8/11/2016
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
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
    public boolean matchCompleted = false;
    public LapManager(Vector3f transform, int laps) {
        startPoint = transform;
        lapCount = laps;
    }
    
    public void checkCompletion(Vector3f vehicleTransform, Node guiNode, BitmapFont guiFont, AssetManager assetManager, float tpf) {
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText helloText = new BitmapText(guiFont, false);
        BitmapText helloText2 = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize()*2);
        helloText.setText("Lap: " + (3 - lapCount) + "/" + "3");
        helloText.setColor(ColorRGBA.Yellow);
        helloText.setLocalTranslation(100, ClientMain.settings.getHeight() - 100, 0);
        helloText2.setSize(guiFont.getCharSet().getRenderedSize()*2);
        helloText2.setText("Split Time: " + tpf);
        helloText2.setColor(ColorRGBA.Yellow);
        helloText2.setLocalTranslation(ClientMain.settings.getWidth() - 300, ClientMain.settings.getHeight() - 100, 0);
        guiNode.attachChild(helloText);
        guiNode.attachChild(helloText2);

        
        if (vehicleTransform.distance(startPoint) <= 7.0f) {
            if (lapCount == 0)
                matchCompleted = true;
            else {
                if (!justCompleted) {
                    lapCount -= 1;
                    justCompleted = true;
                }
            }
        }
        if (vehicleTransform.distance(startPoint) > 7.0f) {
            justCompleted = false;
        }
    }
    public int getLapCount() {
        return lapCount;
    }

    public boolean matchCompleted() {
        return matchCompleted;
    
    }
    
    public void resetLaps() {
        lapCount = 3;
    }
}
