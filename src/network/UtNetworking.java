/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Messages type storage class
 * Created on: 8/11/2016
 */
package network;

import com.jme3.math.Quaternion;
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
    
    //Registers the different types of messages that are to be transferred from the client to server, or to and fro.
    public static void initialiseSerializables() {
        Serializer.registerClass(NetworkMessage.class);
        Serializer.registerClass(PosAndRotMessage.class);
        Serializer.registerClass(PositionMessage.class);
    }
    
    //This network message sends the client's position and rotation in the game world,
    //so the other client can change the dummy model accordingly.
    @Serializable
    public static class PosAndRotMessage extends AbstractMessage {
        private Vector3f position;
        private Quaternion rotation;
        public PosAndRotMessage() {
            
        }
        public PosAndRotMessage(Vector3f pos, Quaternion rot) {
            position = pos;
            rotation = rot;
        }
        public Vector3f getPosition() {
            return position;
        }
        public Quaternion getRotation() {
            return rotation;
        }
    }
    
    //This only sends the position of the client.
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
    
    //This sends a string message to the server, which can be interpreted differently. 
    //Like "Connected", or "OpponentConnected" can tell the server to act accordingly to the message.
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
