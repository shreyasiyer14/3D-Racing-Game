/*
 * Created by Shreyas Iyer & Udit Jindal
 * Module: Client side application
 * Created on: 8/11/2016
 */
package mygame;
import gui.LapManager;
import ai.AICar;
import audio.AudioManager;
import vehicles.*;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.light.DirectionalLight;
import com.jme3.bullet.BulletAppState;
import com.jme3.shadow.BasicShadowRenderer;
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.jme3.util.SkyFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.RoundRobinSpawn;
import network.ServerMain;
import network.UtNetworking;
import network.UtNetworking.NetworkMessage;
import network.UtNetworking.PosAndRotMessage;
import terrain.*;
public class ClientMain extends SimpleApplication {
    private BulletAppState physicsEngine;
    private AICar bot;
    private Vehicle ferrari;
    private VehicleControls Control;
    private VehicleCamera vcam;
    private Vector3f userTransform;
    private String serverIP;
    
    private Opponent opponent;
    RigidBodyControl rbc;
    
    LapManager lapManager;
    
    public Client myClient;
    private static ClientMain app;
    public static AppSettings settings;
    private boolean hasWon = false;
    private boolean isConnected = false;
    private boolean startMatch = false;
    private boolean hasCompleted = true;
    private BitmapText helloText;
    private float timer = 0f;
    ConcurrentLinkedQueue<String> messageQueue;
    private Vector3f originalCam;
    public static void main(String[] args) {
        UtNetworking.initialiseSerializables();
        settings = new AppSettings(true);
        settings.setFrameRate(300);
        settings.setResolution(1024, 768);
        app = new ClientMain();
        app.start(JmeContext.Type.Display);

        app.setDisplayFps(false);
        app.setDisplayStatView(false);
    }

    public ClientMain() {
        super(new StatsAppState());
        super.setSettings(settings);
    }
    @Override
    public void simpleInitApp() {

        try {
            serverIP = "172.16.81.21";
            myClient = Network.connectToServer(serverIP, UtNetworking.PORT);
            myClient.start();
        } catch (IOException ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        messageQueue = new ConcurrentLinkedQueue<String>();
        myClient.addMessageListener(new NetworkMessageListener());
        
        RoundRobinSpawn rrs = new RoundRobinSpawn();
        InetAddress ipAddr;
        try {
            ipAddr = InetAddress.getLocalHost();
            if (ipAddr.getHostAddress().equals(serverIP)) {
                userTransform = rrs.spawnPoints[0];
            }
            else {
                userTransform = rrs.spawnPoints[1];
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        physicsEngine = new BulletAppState();
        stateManager.attach(physicsEngine);
         if (settings.getRenderer().startsWith("LWJGL")) {
            BasicShadowRenderer bsr = new BasicShadowRenderer(assetManager, 2048);
            bsr.setDirection(new Vector3f(-0.5f, -0.3f, -0.3f).normalizeLocal());
            viewPort.addProcessor(bsr);
        }
        cam.setFrustumFar(1000f);
        viewPort.setBackgroundColor(ColorRGBA.White);
        inputManager.setCursorVisible(true);
        rootNode.attachChild(SkyFactory.createSky(
            assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
        getPhysicsSpace().setGravity(new Vector3f(0, -20f, 0));

        lapManager = new LapManager(new Vector3f(0.38055262f, 14.283572f, -25.188498f), 3);
        
        ferrari = new Ferrari (0.3f, userTransform , 20f, 1000f,assetManager, ColorRGBA.Red);
        ferrari.initVehicle();      
        Control= new VehicleControls("Car", ferrari ,2000f, inputManager);
        Control.setupKeys(); 
        
        opponent = new Opponent(new Vector3f(0f,-100f,0f), 1000f, assetManager);
        opponent.initOpponent();
        
        bot = new AICar(0.5f, 3f, 1000f, assetManager);
        try {
            bot.initAICar();
        } catch (IOException ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Stage1 stage= new Stage1(new Vector3f(270f, -20f, 15f), 75f,assetManager);
        stage.init_stage1();
        
        vcam = new VehicleCamera("Camera Node", new Vector3f(0f,4f,12f), new Vector3f(0f,22f,0f), cam);
        vcam.initCamera();
        ferrari.getCarNode().attachChild(vcam.getCamera());
        
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.5f, -1f, -0.3f).normalizeLocal());
        rootNode.addLight(dl);
        
        AudioManager am = new AudioManager(assetManager, "Sounds/Bullet For My Valentine - Waking The Demon.ogg");
        am.initAudio();
        rootNode.attachChild(am.getAudioNode());
        am.getAudioNode().play();
        /*
        
        guiNode.detachAllChildren();
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setColor(ColorRGBA.Red);
        helloText.setLocalTranslation(320, helloText.getLineHeight() + 300, 0);
        
        */
        rootNode.attachChild(ferrari.getCarNode());
        rootNode.attachChild(stage.get_Stage());
        rootNode.attachChild(bot.getCarNode());
        rootNode.attachChild(opponent.getCarNode());
        
    }  
 
    @Override
    public void simpleUpdate(float tpf) {
        lapManager.checkCompletion(ferrari.getCarNode().getLocalTranslation(), guiNode, guiFont, assetManager, timer);
        if (lapManager.matchCompleted()) {
            myClient.send(new NetworkMessage("Completed"));
            hasCompleted = true;
            lapManager.matchCompleted = false;
        }
        myClient.send(new UtNetworking.PosAndRotMessage(ferrari.getCarNode().getLocalTranslation(), ferrari.getCarNode().getLocalRotation()));
        if (myClient.isConnected()) {
            timer += tpf;
            myClient.send(new NetworkMessage("Connected"));
        }
        if (startMatch) {
            if (rootNode.hasChild(bot.getCarNode())) {
                bot.getCarNode().removeControl(bot.getController());
                getPhysicsSpace().remove(bot.getCarNode());
                rootNode.detachChild(bot.getCarNode());
                try {
                    startMatch();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else
            bot.AIUpdate();
        if (ferrari.rearLook) {
            vcam.getCamera().rotate(0, 3.14f, 0);
            ferrari.rearLook = false;
        }
        else {
            vcam.getCamera().setLocalTranslation(new Vector3f(0f, 4f, 12f));
        }

    }
    private class NetworkMessageListener implements MessageListener<Client> {
        @Override
        public void messageReceived(Client source, Message m) {
            if (m instanceof NetworkMessage) {
                NetworkMessage message = (NetworkMessage) m;
                if ("Won".equals(message.getMessage())) {
                    hasWon = true;
                    System.out.println("Congratulations! You have won!");

                    //helloText.setText("Congratulations! You have Won!");
                    //guiNode.attachChild(helloText);

                }
                else if ("Lost".equals(message.getMessage())) {
                    hasWon = false;
                    System.out.println("Damn! So close!");
                    //helloText.setText("Damn! Better luck next time...");
                    //guiNode.attachChild(helloText);
                }   
                if ("OpponentConnected".equals(message.getMessage()) && !isConnected) {
                    isConnected = true;
                    startMatch = true;
                }
            } 
            if (m instanceof PosAndRotMessage && startMatch) {
                final PosAndRotMessage posMsg = (PosAndRotMessage) m;
                ClientMain.this.enqueue(new Callable() {
                    @Override
                    public Object call() throws Exception {
                        opponent.getCarNode().setLocalTranslation(posMsg.getPosition());                 
                        opponent.getCarNode().setLocalRotation(posMsg.getRotation());
                        opponent.getController().setPhysicsLocation(opponent.getCarNode().getLocalTranslation());
                        return null;
                    }
                });            
            }
        }
    }
    
    @Override
    public void destroy() {
        myClient.close();
        super.destroy();
    }
    
    public void startMatch() throws InterruptedException {
        ferrari.getCarNode().setLocalTranslation(userTransform);
        ferrari.getController().setPhysicsLocation(userTransform);
        ferrari.getCarNode().setLocalRotation(new Quaternion(0f, -0.4f, 0f, 1.0f));
        ferrari.getController().setLinearVelocity(Vector3f.ZERO);
        ferrari.getController().setAngularVelocity(Vector3f.ZERO);
        ferrari.getController().resetSuspension();
        lapManager.resetLaps();
        Thread.sleep(5000);
    }
}