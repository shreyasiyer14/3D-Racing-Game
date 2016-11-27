/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Audio node and it's description
 * Created on: 8/11/2016
 */
package audio;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;

/**
 *
 * @author EOF-1
 */
public class AudioManager {
    private AudioNode backGroundTrack;
    private AssetManager assetManager;
    private String audioFile;
    public AudioManager(AssetManager assetManager, String audioFile) {
        this.assetManager =  assetManager;
        this.audioFile = audioFile;
    }
    
    public void initAudio() {
        backGroundTrack = new AudioNode(assetManager, audioFile, true);
        backGroundTrack.setLooping(true);  // activate continuous playing
        backGroundTrack.setPositional(false);   
        backGroundTrack.setVolume(3);
    }
    
    public AudioNode getAudioNode() {
        return backGroundTrack;
    }
    
}
