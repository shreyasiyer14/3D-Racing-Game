/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Ferrari car description
 * Created on: 8/11/2016
 */
package network;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;

/**
 *
 * @author EOF-1
 */
public class UtNetworking {
    public static final int PORT = 6143;
    
    public static void initialiseSerializables() {
        Serializer.registerClass(NetworkMessage.class);
        Serializer.registerClass(PosAndRotMessage.class);
        Serializer.registerClass(PositionMessage.class);
    }
    
    @Serializable
    public static class PosAndRotMessage extends AbstractMessage {
        private Vector3f position;
        private Vector3f rotation;
        public PosAndRotMessage() {
            
        }
        public PosAndRotMessage(Vector3f pos, Vector3f rot) {
            position = pos;
            rotation = rot;
        }
        public Vector3f getPosition() {
            return position;
        }
        public Vector3f getRotation() {
            return rotation;
        }
    }
    
    @Serializable
    public static class PositionMessage extends AbstractMessage {
        private Vector3f position;
        public PositionMessage() {
            
        }
        public PositionMessage(Vector3f pos) {
            position = pos;
        }
        public Vector3f getPosition() {
            return position;
        }
    }
    
    @Serializable
    public static class NetworkMessage extends AbstractMessage {
        private String message;
        
        public NetworkMessage() {
            
        }
        
        public NetworkMessage(String message) {
            this.message = message;
        }
        
        public String getMessage() {
            return message;
        }
    }
}
