/*
 * Created by Shreyas Iyer, IMT2015018
 * Module: Ferrari car description
 * Created on: 8/11/2016
 */
package network;
import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;
import java.io.IOException;
import static java.lang.Math.random;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.UtNetworking.NetworkMessage;
import network.UtNetworking.PositionMessage;

/**
 *
 * @author EOF-1
 */
public class ServerMain extends SimpleApplication {
    Server myServer;
    float counter = 0;
    public static void main(String[] args) {
        UtNetworking.initialiseSerializables();
        ServerMain app = new ServerMain();
        app.start(JmeContext.Type.Headless);
    }

    @Override
    public void simpleInitApp() {

        try {
            myServer = Network.createServer(UtNetworking.PORT);
            myServer.start();
        } catch (IOException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        counter += tpf;
        if (counter > 0.3f) {
            float f = counter * 5;
            myServer.broadcast(new PositionMessage(new Vector3f(f,f,f)));
            count = 0;
        }
    }
    @Override
    public void destroy() {
        myServer.close();
        super.destroy();
    }
}
