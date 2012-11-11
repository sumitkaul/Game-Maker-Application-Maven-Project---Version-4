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

public class GameEngineController extends BasicGame {

    private static final Logger LOG = Logger.getLogger(GameEngineController.class.getName());
    private List<SpriteModel> allSpriteModels;
    private Map<Integer, Image> tempImagesForTesting;
    private List<EventListener> eventsForGameController;
    private PhysicsComponent physicsComponent;
    
    public GameEngineController(String title) throws IOException {
    	
    		
        super(title);
        physicsComponent=new PhysicsComponent();
        
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        GamePackage game = GameDataPackageIO.loadGamePackageFromLocalFile("/game/engine/slick2d/player/testing_game.xml");
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
        
     
        
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
    	for (EventListener event : eventsForGameController) {
            
    		event.checkEvent(null);
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

