package game.engine.slick2d.player;

import action.ActionCreateSpriteModel;
import action.ActionPlaySound;
import action.GameAction;
import eventlistener.EventListener;
import eventlistener.KeyPressedEventListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.swing.JFrame;
import loader.GameDataPackageIO;
import loader.GamePackage;
import model.Resources;
import model.SpriteModel;
import org.apache.log4j.Logger;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import utility.Constants;
import utility.SpriteList;
import view.communication.ClientHandler;
import view.companels.GameBaseLoadPanel;

public class GameEngineController extends BasicGame {

    private static final Logger LOG = Logger.getLogger(GameEngineController.class.getName());
    public final static int LOAD_MODE_LOCAL = 1;
    public final static int LOAD_MODE_REMOTE = 2;
    public final static int LOAD_MODE_REMOTE_NO_UI = 3;
    private GamePackage game;
    private Collection<SpriteModel> allSpriteModels;
    private Map<String, Image> imagesOfSprites;
    private List<EventListener> eventsForGameController;
    private List<EventListener> keyEvents;
    private PhysicsComponent physicsComponent;
    private HashMap<Integer, KeyPressedEventListener> keyReg;
    private HashMap<Integer, Integer> key2key;

    public GameEngineController(String title, int loadMode, String[] paras) {
        super(title);
        buildKeyModel();
        //buildPhysicsWorld();
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
        key2key.put(65, 30);
        key2key.put(66, 48);
        key2key.put(67, 46);
        key2key.put(68, 32);
        key2key.put(69, 18);
        key2key.put(70, 33);
        key2key.put(71, 34);
        key2key.put(72, 35);
        key2key.put(73, 23);
        key2key.put(74, 36);
        key2key.put(75, 37);
        key2key.put(76, 38);
        key2key.put(77, 50);
        key2key.put(78, 49);
        key2key.put(79, 24);
        key2key.put(80, 25);
        key2key.put(81, 16);
        key2key.put(82, 19);
        key2key.put(83, 31);
        key2key.put(84, 20);
        key2key.put(85, 22);
        key2key.put(86, 47);
        key2key.put(87, 17);
        key2key.put(88, 45);
        key2key.put(89, 21);
        key2key.put(90, 44);
        key2key.put(48, 11);
        key2key.put(49, 2);
        key2key.put(50, 3);
        key2key.put(51, 4);
        key2key.put(52, 5);
        key2key.put(53, 6);
        key2key.put(54, 7);
        key2key.put(55, 8);
        key2key.put(56, 9);
        key2key.put(57, 10);
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
        } else if (loadMode == LOAD_MODE_REMOTE_NO_UI) {
            String gameData;
            try {
                gameData = ClientHandler.loadGameBase(paras[0], paras[1], paras[2]);
            } catch (Exception ex) {
                LOG.error(ex);
                return null;
            }
            gamePackage = GameDataPackageIO.loadGamePackageFromFile(gameData);
        }

        return gamePackage;
    }

    public void initSpriteImageMapping() throws Exception {
        allSpriteModels = game.getSpriteList();
        imagesOfSprites = new HashMap<String, Image>();

        for (SpriteModel sprite : allSpriteModels) {

            SpriteList.getInstance().addSprite(sprite);

            String rid = sprite.getImageUrlString();
            Resources r = ClientHandler.loadResource(rid, Constants.HOST, Constants.PATH + "/loadResource");

            byte[] imageData = r.getResource();
            Image image = getImageFromBytes(imageData, r.getResourceName());

            if (image == null) {
                continue;
            }

            imagesOfSprites.put(sprite.getId(), image);
            //initSpriteBodyMapping(sprite);

        }

    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        try {
            initSpriteImageMapping();
        } catch (Exception ex) {
            LOG.error(ex);
        }
        initActionEvents();
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {

        checkEvents(gc);
        //physicsComponent.inputLogic();

    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        //physicsComponent.moveLogic();
        renderSpriteImageDraw();
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

    public void initActionEvents() {
        eventsForGameController = game.getEventsForGameController();
        keyEvents = game.getEventsForKeyController();
        for (EventListener keyevent : keyEvents) {
            KeyPressedEventListener key = (KeyPressedEventListener) keyevent;
            int i = key.getKeyRegistered();
            keyReg.put(i, key);
            LOG.debug("read one key: " + key + " " + i);

            //hard coding for ActionCreateSpriteModel
            if (!(key.getAction() instanceof ActionCreateSpriteModel)) {
                continue;
            }

            String gid = "Bomb";
            Image entityImage = imagesOfSprites.get(key.getRegisteredObjectId());
            imagesOfSprites.put(gid, entityImage);
        }

        for (EventListener event : eventsForGameController) {
            GameAction action = event.getGameAction();

            if (action != null) {
                if (action instanceof ActionPlaySound) {
                    ActionPlaySound playSound = (ActionPlaySound) action;
                    String soundFileName = playSound.getSoundFile();
                    if (SoundRepo.getSounds().containsKey(soundFileName)) {
                        continue;
                    }
                    try {
//                        Sound cache = new Sound("temp/resources/" + soundFileName);
//                        SoundRepo.getSounds().put(soundFileName, cache);
//                        
//                        LOG.info("======================== new sound: " +  soundFileName);
                        Sound cache = new Sound("data/" + soundFileName);
                        SoundRepo.getSounds().put(soundFileName, cache);

                        LOG.info("======================== new sound: " + soundFileName);
                    } catch (SlickException ex) {
                        LOG.warn(ex);
                    }
                }
            }
        }

    }

    public void renderSpriteImageDraw() {
        for (SpriteModel sprite : SpriteList.getInstance().getSpriteList()) {
            if (SpriteList.getInstance().getToBeRemovedSpriteModels().contains(sprite)) {
                SpriteList.getInstance().removeSprite(sprite);
                SpriteList.getInstance().getToBeRemovedSpriteModels().remove(sprite);
                continue;
            }

            if (!imagesOfSprites.containsKey(sprite.getId())) {
                if (imagesOfSprites.containsKey(sprite.getGroupId())) {
                    imageDraw(sprite, sprite.getGroupId());
                    continue;
                }
            }

            imageDraw(sprite, sprite.getId());

        }


    }

    public void imageDraw(SpriteModel sprite, String id) {

        // Vec2 bodyPostion = physicsComponent.bodies.get(sprite.getId()).getPosition();
        // sprite.setPosX((double) bodyPostion.x * 30);
        // sprite.setPosY((double) bodyPostion.y * 30);
        //imagesOfSprites.get(id).setRotation(physicsComponent.bodies.get(sprite.getId()).getAngle());
        imagesOfSprites.get(id).draw((float) sprite.getPosX(), (float) sprite.getPosY(), (float) sprite.getWidth(), (float) sprite.getHeight());//(float) bodyPostion.x * 30, (float) bodyPostion.y * 30, (float) sprite.getWidth(), (float) sprite.getHeight());
        //   Log.info("Sprite X : "+sprite.getPosX());
    }

    public void initSpriteBodyMapping(SpriteModel sprite) {
        try {
            physicsComponent.bodies.put(sprite.getId().toString(), physicsComponent.createBody(sprite.getId(), "polygon", "Dynamic", (float) sprite.getPosX(), (float) sprite.getPreviousY(), (float) sprite.getWidth(), (float) sprite.getHeight(), 0.0f));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GameEngineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void checkEvents(GameContainer gc) {
        for (EventListener event : eventsForGameController) {
            event.checkEvent(null);
        }

        for (Integer keycode : keyReg.keySet()) {
            try {
                if (gc.getInput().isKeyDown(key2key.get(keycode.intValue()))) {
                    LOG.debug(keycode + " is Pressed");
                    HashMap<String, Object> map = new HashMap<String, Object>(5);
                    map.put("keypressed", new Integer(keycode));
                    keyReg.get(keycode).checkEvent(map);
                }
            } catch (Exception e) {
                LOG.error("don't worry, it is only temp key-mapping error, we not done yet: " + e);
            }
        }

    }
}
