package multiplayer;

import action.GameAction;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import game.engine.slick2d.player.GameEngineController;
import loader.GamePackage;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Helper;
import utility.SpriteList;
import view.GameMakerView;
import view.GamePlayerView;
import view.PlayerButtonPanel;

public class Protocol {

    private ObjectMessage msg;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Protocol.class);

    public ObjectMessage createDataAsHost() {
    	GamePlayerView gamePlayerView = (GamePlayerView) Helper.getsharedHelper().getGamePlayerView();
    	GameEngineController gameEngine = gamePlayerView.getGameEnginePanel().getGame();
    	if (gameEngine != null)
    	{
    		LOG.info("Game engine is not nul =================================");
    	}
    	if(SpriteList.getInstance().getSpriteList().isEmpty()){
    		LOG.info("Sprite list is empty here -----------------");
        }
        GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), gameEngine.getEventsForGameController(), gameEngine.getKeyEvents(), null, false);
        if (game != null)
    	{
    		LOG.info("Game  is not nul =================================");
    	}
        try {
            msg = SessionFactory.getInstanceOf().getSession().createObjectMessage();
            msg.setObject(game);
            msg.setJMSType("Sending as Host");
        } catch (JMSException e) {
            LOG.info("sending falied as host");
        }
    	//}
        return msg;
    	
    }

    public ObjectMessage createData(GameAction action, String spriteID) {
        HashMap<GameAction, String> map = new HashMap<GameAction, String>();
        map.put(action, spriteID);

        try {


            msg = SessionFactory.getInstanceOf().getSession().createObjectMessage();
            msg.setObject(map);
            msg.setJMSType("Sending Actions");
        } catch (JMSException e) {
            LOG.info("sending Actions");
        }
        return msg;
    }
    public ObjectMessage createAcknowledgement(String data) {
        try {
            msg = SessionFactory.getInstanceOf().getSession().createObjectMessage(data);
        } catch (JMSException e) {
            LOG.info("creating Acknowledgement failed");
        }
        return msg;
    }

    public void setGameState(GamePackage game) {
        GamePlayerView gamePlayerView = (GamePlayerView) Helper.getsharedHelper().getGamePlayerView();

        LOG.debug("load done");

        Collection<SpriteModel> allSpriteModels = game.getSpriteList();
        game.getLayers();
        ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
        
        SpriteModel m = (SpriteModel) ((Queue) allSpriteModels).peek();
        SpriteList.getInstance().setSelectedSpriteModel(m);
        gamePlayerView.getGameEnginePanel().removeGame();
        
        GameEngineController gameEngine = new GameEngineController(Subscribe.getInstanceOf().getName(), game);
        //gameEngine.setEventsForGameController(game.getEventsForGameController());
        //gameEngine.setKeyEvents(game.getEventsForKeyController());
        try {
            CanvasGameContainer app = new CanvasGameContainer(gameEngine);
            gamePlayerView.getGameEnginePanel().addGame(app);
            gamePlayerView.getBaseFrame().pack();
            app.start();
            app.requestFocusInWindow();
            //gamePlayerView.getGameEnginePanel().startGame();
        } catch (SlickException ex) {
            Logger.getLogger(PlayerButtonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (SpriteModel model : allSpriteModels) {
            SpriteList.getInstance().addSprite(model);
            SpriteList.getInstance().setSelectedSpriteModel(model);
        }
    }
    

    public void setMultiplayerAction(HashMap<GameAction, String> map) {
    	String id = null;
    	for (GameAction action : map.keySet()) {	
            id = map.get(action);
           
    	}
           // Helper.getsharedHelper().getGamePlayerView().getGameEnginePanel().repaint();

    	Collection<SpriteModel> collection = SpriteList.getInstance().getSpriteList();
    	SpriteModel actionModel = null;
    	for (SpriteModel sprite: collection)
    	{
    		if (sprite.getId().equals(id)){
    			actionModel = sprite;
    			break;
    		}
    	}
    	for (GameAction action : map.keySet()) {	
            action.doAction(actionModel);
            LOG.info("Action is ============"+ action.toString());
            LOG.info("The model id is =============="+  actionModel.getId()); 
           
    	}
    	
        LOG.info("Setting multiplayer action");
        
         
        
    }

	public ObjectMessage createStartSignal(String data) {
		try {
            msg = SessionFactory.getInstanceOf().getSession().createObjectMessage(data);
        } catch (JMSException e) {
            LOG.info("creating start signal failed");
        }
        return msg;
	}
}
