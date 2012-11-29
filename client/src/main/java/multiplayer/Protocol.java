package multiplayer;

import action.GameAction;
import java.util.Collection;
import java.util.HashMap;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;

import game.engine.slick2d.player.GameEngineController;
import loader.GamePackage;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Helper;
import utility.SpriteList;
import view.ButtonPanel;
import view.GamePlayerView;
import view.PlayerButtonPanel;

public class Protocol {

    private ObjectMessage msg;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Protocol.class);

    public ObjectMessage createDataAsHost() {
    	GamePlayerView gamePlayerView = Helper.getsharedHelper().getGamePlayerView();
    	GameEngineController gameEngine = gamePlayerView.getGameEnginePanel().getGame();
        GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), gameEngine.getEventsForGameController(), gameEngine.getKeyEvents(), null, false);
        try {
            msg = SessionFactory.getInstanceOf().getSession().createObjectMessage();
            msg.setObject(game);
            msg.setJMSType("Sending as Host");
        } catch (JMSException e) {
            LOG.info("sending falied as host");
        }
        return msg;

    }

    public ObjectMessage createData(GameAction action, SpriteModel model) {
        HashMap<GameAction, SpriteModel> map = new HashMap<GameAction, SpriteModel>();
        map.put(action, model);

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
//        LOG.debug("load done");
//        GameProgressLoadPanel p = new GameProgressLoadPanel(GameMakerView.getInstance().getGamePanel());
        GamePlayerView gamePlayerView = (GamePlayerView) Helper.getsharedHelper().getGamePlayerView();
//        String gamename[] = new String[1];
//        String gameData = p.readGameDataFromRemoteList(gamename);
//
//        if (gameData == null) {
//            return;
//        }

       // GamePackage game = GameDataPackageIO.loadGamePackageFromFile(gameData);

        LOG.debug("load done");

        Collection<SpriteModel> allSpriteModels = game.getSpriteList();
        game.getLayers();
        ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
        // SpriteList.getInstance().setSpriteList(allSpriteModels);
        SpriteModel m = (SpriteModel) ((Queue) allSpriteModels).peek();
        SpriteList.getInstance().setSelectedSpriteModel(m);
        gamePlayerView.getGameEnginePanel().removeGame();
        
        GameEngineController gameEngine = new GameEngineController(Subscribe.getInstanceOf().getName(), game);
        gameEngine.setEventsForGameController(game.getEventsForGameController());
        gameEngine.setKeyEvents(game.getEventsForKeyController());
        try {
            CanvasGameContainer app = new CanvasGameContainer(gameEngine);
            gamePlayerView.getGameEnginePanel().addGame(app);
            gamePlayerView.getGameEnginePanel().startGame();
        } catch (SlickException ex) {
            Logger.getLogger(PlayerButtonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (SpriteModel model : allSpriteModels) {
            SpriteList.getInstance().addSprite(model);
            SpriteList.getInstance().setSelectedSpriteModel(model);
        }
    }
    

    public void setMultiplayerAction(HashMap<GameAction, SpriteModel> map) {
        SpriteModel model = null;
        LOG.info("Setting multiplayer action");
        for (GameAction action : map.keySet()) {
            model = map.get(action);
            action.doAction(model);
        }
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
