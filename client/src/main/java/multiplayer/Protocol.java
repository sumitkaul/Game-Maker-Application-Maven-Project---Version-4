package multiplayer;

import java.util.HashMap;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import loader.GamePackage;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Layers;
import utility.SpriteList;
import view.ButtonPanel;
import view.Design;
import action.GameAction;

public class Protocol {
	private ObjectMessage msg;
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ButtonPanel.class);


	public ObjectMessage createDataAsHost()
	{
		GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), Design.getInstance().getFacade().getGameController().getEvents(), Design.getInstance().getFacade().getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
		try{
			msg = SessionFactory.getInstanceOf().getSession().createObjectMessage();
			msg.setObject(game);
			LOG.debug(game.getEventsForGameController().get(0));
			msg.setJMSType("Sending as Host");
		} catch (JMSException e) {
			LOG.info("sending falied as host");
		}
		return msg;

	}
	public ObjectMessage createData(GameAction action, SpriteModel model)
	{
		HashMap<GameAction,SpriteModel> map = new HashMap<GameAction,SpriteModel>();
		map.put(action, model);

		try{


			msg = SessionFactory.getInstanceOf().getSession().createObjectMessage();
			msg.setObject(map );
			msg.setJMSType("Sending Actions");
		} catch (JMSException e) {
			LOG.info("sending Actions");
		}
		return msg;
	}

	public void setGameState(GamePackage game)
	{
		LOG.debug("load done");
		List<SpriteModel> allSpriteModels = game.getSpriteList();
		List<String> layers = game.getLayers();
		ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
		// SpriteList.getInstance().setSpriteList(allSpriteModels);
		SpriteList.getInstance().setSelectedSpriteModel(allSpriteModels.get(0));
		Design.getInstance().getFacade().getGameController().setEvents(game.getEventsForGameController());
		Design.getInstance().getFacade().getKeyListenerController().setKeyEvents(game.getEventsForKeyController());
		Design.getInstance().getFacade().createViewsForModels(game.getSpriteList());
		for (SpriteModel model : allSpriteModels) {
			SpriteList.getInstance().addSprite(model);
			SpriteList.getInstance().setSelectedSpriteModel(model);
			LOG.info("The id of the object is " + model.getId());
		}
		Design.getInstance().updateProperties();

	}
	public void setMultiplayerAction(HashMap<GameAction, SpriteModel> map)
	{
		SpriteModel model = null;
		LOG.info("Setting multiplayer action");
		for (GameAction action : map.keySet())
		{
			model = map.get(action);
			action.doAction(model);

		}

	}
}


