package mygame;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.JmeContext;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import network.UtNetworking;
public class Main extends SimpleApplication implements ScreenController {
    private boolean singlePlayer = false;
    private boolean multiPlayer = false;
    
    private Nifty nifty;
    private static Main app;
    public static void main(String[] args) {
        app = new Main();
        app.start();
    }

    public Main() {
        super(new StatsAppState());
    }
    @Override
    public void simpleInitApp() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/showcase.xml", "GScreen0", this);
        guiViewPort.addProcessor(niftyDisplay);
       // flyCam.setEnabled(false);
       // flyCam.setDragToRotate(false);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        System.out.println();
    }

    @Override
    public void onStartScreen() {
        System.out.println();
    }

    @Override
    public void onEndScreen() {
        System.out.println();
    }
    
    public void startSinglePlayer() {
        UtNetworking.initialiseSerializables();
        //***** NOT WORKING *****
        ClientMain mainapp = new ClientMain();
        mainapp.start();
    }
    
    public void startMultiplayer() {
        multiPlayer = true;
    }
}
