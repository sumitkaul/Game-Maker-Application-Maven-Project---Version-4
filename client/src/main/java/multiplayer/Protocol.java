package multiplayer;

import action.GameAction;
import java.util.Collection;
import java.util.HashMap;
import java.util.Queue;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.newdawn.slick.util.Log;

import loader.GamePackage;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Layers;
import utility.SpriteList;
import view.ButtonPanel;
import view.GameMakerView;

public class Protocol {

    private ObjectMessage msg;
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ButtonPanel.class);

    public ObjectMessage createDataAsHost() {
        GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), GameMakerView.getInstance().getFacade().getGameController().getEvents(), GameMakerView.getInstance().getFacade().getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
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

        	Log.info("In create Data");
            msg = SessionFactory.getInstanceOf().getSession().createObjectMessage();
            msg.setObject(map);
            msg.setJMSType("Sending Actions");
        } catch (JMSException e) {
            LOG.info("sending Actions");
        }
        return msg;
    }

    public void setGameState(GamePackage game) {
        LOG.debug("load done");
        Collection<SpriteModel> allSpriteModels = game.getSpriteList();
        game.getLayers();
        ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
        // SpriteList.getInstance().setSpriteList(allSpriteModels);
        SpriteModel m = (SpriteModel) ((Queue) allSpriteModels).peek();
        SpriteList.getInstance().setSelectedSpriteModel(m);
        GameMakerView.getInstance().getFacade().getGameController().setEvents(game.getEventsForGameController());
        GameMakerView.getInstance().getFacade().getKeyListenerController().setKeyEvents(game.getEventsForKeyController());
        GameMakerView.getInstance().getFacade().createViewsForModels(game.getSpriteList());
        for (SpriteModel model : allSpriteModels) {
            SpriteList.getInstance().addSprite(model);
            SpriteList.getInstance().setSelectedSpriteModel(model);
            LOG.info("The id of the object is " + model.getId());
        }
        GameMakerView.getInstance().updateProperties();

    }

    public void setMultiplayerAction(HashMap<GameAction, SpriteModel> map) {
        SpriteModel model = null;
        LOG.info("Setting multiplayer action");
        for (GameAction action : map.keySet()) {
            model = map.get(action);
            action.doAction(model);

        }

    }
}
