/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Server side application
 * Created on: 8/11/2016
 */
package network;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.UtNetworking.PosAndRotMessage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import network.UtNetworking.NetworkMessage;
/**
 *
 * @author EOF-1
 */
public class ServerMain extends SimpleApplication {
    Server myServer;
    
    public static String serverIP;
    private boolean matchReady = false;
    public static int numOfClients;
    public static void main(String[] args) {
        UtNetworking.initialiseSerializables();
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless);
        try {
            InetAddress ipAddr = InetAddress.getLocalHost();
            serverIP = ipAddr.getHostAddress();
            System.out.println("Server running on port: " + UtNetworking.PORT + " IP: " + serverIP);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void simpleInitApp() {
        try {
            myServer = Network.createServer(UtNetworking.PORT);
            myServer.start();
            myServer.addMessageListener(new MessageHandler());
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        numOfClients = myServer.getConnections().size();
        if (myServer.getConnections().size() >= 2 && !matchReady) {
            myServer.broadcast(new NetworkMessage("StartMatch"));
            //matchReady = true;
        }
    }
    
    private class MessageHandler implements MessageListener<HostedConnection> {
       public void messageReceived(HostedConnection source, Message m) {
           if (myServer.getConnections().size() >= 2) {
                int otherClientIndex = 0;
                Collection<HostedConnection> col = myServer.getConnections();
                for (int i = 0; i < col.size(); i++) {
                    if (col.toArray()[i] != source) {
                        otherClientIndex = i;
                        break;
                    }
                }
                if (m instanceof NetworkMessage) {
                    NetworkMessage msg = (NetworkMessage) m;
                    if ("Completed".equals(msg.getMessage())) {
                        myServer.broadcast(Filters.equalTo(source), new NetworkMessage("Won"));
                        myServer.broadcast(Filters.notEqualTo(source), new NetworkMessage("Lost"));
                    }
                }
                if (m instanceof PosAndRotMessage) {
                    PosAndRotMessage msg = (PosAndRotMessage) m;
                    Vector3f pos = msg.getPosition();
                    Quaternion rot = msg.getRotation();
                             
                    myServer.broadcast(Filters.in(col.toArray()[otherClientIndex]), new PosAndRotMessage(pos,rot));
                }
           }
       } 
    }
    @Override
    public void destroy() {
        myServer.close();
        super.destroy();
    }
}
