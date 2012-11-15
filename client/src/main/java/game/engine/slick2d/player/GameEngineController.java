package game.engine.slick2d.player;

import eventlistener.EventListener;
import eventlistener.KeyPressedEventListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import loader.GameDataPackageIO;
import loader.GamePackage;
import model.Resources;
import model.SpriteModel;
import org.apache.log4j.Logger;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utility.SpriteList;
import view.communication.ClientHandler;
import view.companels.GameBaseLoadPanel;

public class GameEngineController extends BasicGame {

    private static final Logger LOG = Logger.getLogger(GameEngineController.class.getName());
    public final static int LOAD_MODE_LOCAL = 1;
    public final static int LOAD_MODE_REMOTE = 2;
    private GamePackage game;
    private List<SpriteModel> allSpriteModels;
    private Map<String, Image> imagesOfSprites;
    private List<EventListener> eventsForGameController;
    private List<EventListener> keyEvents;
    private PhysicsComponent physicsComponent;
    private HashMap<Integer, KeyPressedEventListener> keyReg;
    private HashMap<Integer, Integer> key2key;

    public GameEngineController(String title, int loadMode, String[] paras) {
        super(title);
        buildKeyModel();
        buildPhysicsWorld();
        game = loadGameData(loadMode, paras);
    }

    private void buildPhysicsWorld() {
        try {
            physicsComponent = new PhysicsComponent();
        } catch (IOException ex) {
            LOG.error(ex);
        }
    }

    private void buildKeyModel() {
        keyReg = new HashMap<Integer, KeyPressedEventListener>(10);
        key2key = new HashMap<Integer, Integer>(10);
        key2key.put(37, 203);
        key2key.put(39, 205);
        key2key.put(38, 200);
        key2key.put(40, 208);
        key2key.put(32, 57);
    }

    private GamePackage loadGameData(int loadMode, String[] paras) {
        GamePackage gamePackage = null;

        if (loadMode == LOAD_MODE_LOCAL) {
            gamePackage = GameDataPackageIO.loadGamePackageFromLocalFile(paras[0]);
        } else if (loadMode == LOAD_MODE_REMOTE) {
            JFrame jf = new JFrame();
            GameBaseLoadPanel gb = new GameBaseLoadPanel(jf.getRootPane());
            String gameData = gb.readGameDataFromRemoteList();
            gamePackage = GameDataPackageIO.loadGamePackageFromFile(gameData);
        }

        return gamePackage;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        allSpriteModels = game.getSpriteList();
        eventsForGameController = game.getEventsForGameController();
        imagesOfSprites = new HashMap<String, Image>(5);

        for (SpriteModel s : allSpriteModels) {

            SpriteList.getInstance().addSprite(s);

            String rid = s.getImageUrlString();
            Resources r = ClientHandler.loadResource(rid, "tintin.cs.indiana.edu:8096", "/GameMakerServer/loadResource", new Exception[1]);

            byte[] imageData = r.getResource();
            Image image = getImageFromBytes(imageData, r.getResourceName());

            if (image == null) {
                continue;
            }

            imagesOfSprites.put(s.getId(), image);
        }


        keyEvents = game.getEventsForKeyController();
        for (EventListener keyevent : keyEvents) {
            KeyPressedEventListener key = (KeyPressedEventListener) keyevent;
            int i = key.getKeyRegistered();
            keyReg.put(i, key);
            LOG.debug("read one key: " + key + " " + i);
        }

    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        for (EventListener event : eventsForGameController) {
            event.checkEvent(null);
        }
        for (Integer keycode : keyReg.keySet()) {
            try {
                if (gc.getInput().isKeyDown(key2key.get(keycode.intValue()))) {
                    LOG.debug(keycode + " is Pressed");
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("keypressed", new Integer(keycode));
                    keyReg.get(keycode).checkEvent(map);
                }
            } catch (Exception e) {
                LOG.error("don't worry, it is only temp key-mapping error, we not done yet: " + e);
            }
        }


    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {

        
        
        

        for (Body body : physicsComponent.bodies) {
            if (body.getType() == BodyType.DYNAMIC) {
                grphcs.pushTransform();
                Vec2 bodyPosition = body.getPosition();
                grphcs.translate(bodyPosition.x, bodyPosition.y);
                grphcs.rotate(0f, 0f, (float) Math.toDegrees(body.getAngle()));
                grphcs.drawRect(-0.2f * 30, -0.2f * 30, 0.2f * 30, 0.2f * 30);
                grphcs.popTransform();
            }
        }
        //

        for (SpriteModel s : SpriteList.getInstance().getSpriteList()) {
            if (!s.isVisible()) {
                continue;
            }

            if (!imagesOfSprites.containsKey(s.getId())) {
                String rid = s.getImageUrlString();
                Resources r = ClientHandler.loadResource(rid, "tintin.cs.indiana.edu:8096", "/GameMakerServer/loadResource", new Exception[1]);

                byte[] imageData = r.getResource();
                Image image = getImageFromBytes(imageData, r.getResourceName());

                if (image == null) {
                    continue;
                }

                imagesOfSprites.put(s.getId(), image);
            }

            imagesOfSprites.get(s.getId()).draw((float) s.getPosX(), (float) s.getPosY(), (float) s.getWidth(), (float) s.getHeight());
        }
    }

    public Image getImageFromBytes(byte[] imageData, String imageName) {
        Image image = null;
        try {
            image = new Image(new ByteArrayInputStream(imageData), imageName, false);
        } catch (Exception ex) {
            LOG.error(ex);
            return null;
        }
        return image;
    }
}
