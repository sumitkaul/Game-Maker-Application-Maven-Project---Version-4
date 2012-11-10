package game.engine.slick2d.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import loader.GameDataPackageIO;
import loader.GamePackage;
import model.SpriteModel;
import org.apache.log4j.Logger;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utility.SpriteList;

import eventlistener.EventListener;

public class GameEngineController extends BasicGame {

    private static final Logger LOG = Logger.getLogger(GameEngineController.class.getName());
    private List<SpriteModel> allSpriteModels;
    private Map<Integer, Image> tempImagesForTesting;
    private List<EventListener> eventsForGameController;

    public GameEngineController(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        GamePackage game = GameDataPackageIO.loadGamePackageFromLocalFile("/game/engine/slick2d/player/testing_game.xml");
        allSpriteModels = game.getSpriteList();
        eventsForGameController = game.getEventsForGameController();
        tempImagesForTesting = new HashMap<Integer, Image>(5);

        for (SpriteModel s : allSpriteModels) {
        	SpriteList.getInstance().addSprite(s);
            Image image = new Image(s.getImageUrlString());
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
            tempImagesForTesting.get(s.hashCode()).draw((float) s.getPosX(), (float) s.getPosY(), (float) s.getWidth(), (float) s.getHeight());
        }
    }
}
