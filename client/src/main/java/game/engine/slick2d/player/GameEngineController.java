package game.engine.slick2d.player;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import view.communication.ClientHandler;

import utility.SpriteList;

import eventlistener.EventListener;
import eventlistener.KeyPressedEventListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import view.companels.GameBaseLoadPanel;

public class GameEngineController extends BasicGame {

    private static final Logger LOG = Logger.getLogger(GameEngineController.class.getName());
    private List<SpriteModel> allSpriteModels;
    private Map<Integer, Image> tempImagesForTesting;
    private List<EventListener> eventsForGameController;
    private List<EventListener> keyEvents;
    private PhysicsComponent physicsComponent;
    private String test;
    private HashMap<Integer,KeyPressedEventListener> keyReg;
    private HashMap<Integer, Integer> key2key;
    
    
    public GameEngineController(String title) throws IOException {  		
        super(title);
        keyReg = new HashMap<Integer,KeyPressedEventListener>(10);
        key2key = new HashMap<Integer, Integer>(10);
        physicsComponent=new PhysicsComponent();
        JFrame jf = new JFrame();
        GameBaseLoadPanel gb = new GameBaseLoadPanel(jf.getRootPane());
        test = gb.readGameDataFromRemoteList();
        key2key.put(37, 203);
        key2key.put(39, 205);
        key2key.put(38, 200);
        key2key.put(40, 208);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        
        GamePackage game = GameDataPackageIO.loadGamePackageFromFile(test);//loadGamePackageFromLocalFile("/game/engine/slick2d/player/testing_game.xml");
        allSpriteModels = game.getSpriteList();
        eventsForGameController = game.getEventsForGameController();
        tempImagesForTesting = new HashMap<Integer, Image>(5);

        for (SpriteModel s : allSpriteModels) {

        	SpriteList.getInstance().addSprite(s);

            String rid = s.getImageUrlString();
            Resources r = ClientHandler.loadResource(rid, "tintin.cs.indiana.edu:8096", "/GameMakerServer/loadResource", new Exception[1]);
            byte[] imageData = r.getResource();
            Image image = null;
            try {
                image = new Image(new ByteArrayInputStream(imageData), r.getResourceName(), false);
            } catch (Exception ex) {
                LOG.error(ex);
                continue;
            }

            tempImagesForTesting.put(s.hashCode(), image);
        }
        
        
        keyEvents = game.getEventsForKeyController();
        for (EventListener keyevent: keyEvents){
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
        for (Integer keycode : keyReg.keySet()){
            if(gc.getInput().isKeyDown(key2key.get(keycode.intValue()))){
                LOG.debug(keycode + " is Pressed");
                HashMap<String,Object> map = new HashMap<String,Object>();
        	map.put("keypressed", new Integer(keycode));
        	keyReg.get(keycode).checkEvent(map);
            }
        }

    	
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
    	
        for (SpriteModel s : allSpriteModels) {
        	
        	physicsComponent.inputLogic();
            physicsComponent.moveLogic();
            
    		for(Body body: physicsComponent.bodies)
    		{
    			if(body.getType()==BodyType.DYNAMIC)
    			{
    				grphcs.pushTransform();
    				Vec2 bodyPosition=body.getPosition();
    				grphcs.translate(bodyPosition.x,bodyPosition.y);
    				grphcs.rotate(0f,0f,(float)Math.toDegrees(body.getAngle()));
    				grphcs.drawRect(-0.2f * 30,-0.2f * 30,0.2f * 30,0.2f * 30);
    				grphcs.popTransform();
    			}
    			}
    		
            tempImagesForTesting.get(s.hashCode()).draw((float) s.getPosX(), (float) s.getPosY(), (float) s.getWidth(), (float) s.getHeight());
            
            
    			}
    		}
    }

