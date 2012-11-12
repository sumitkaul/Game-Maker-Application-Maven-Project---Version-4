package multiplayer;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import loader.GameDataPackageIO;
import loader.GamePackage;
import utility.ClockDisplay;
import utility.Layers;
import utility.SpriteList;
import view.ButtonPanel;
import view.Design;
import view.companels.GameProgressSavePanel;

public class Protocol {
	private ObjectMessage msg;
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ButtonPanel.class);
	
	
	public ObjectMessage createDataAsHost()
	{
		GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), Design.getInstance().getFacade().getGameController().getEvents(), Design.getInstance().getFacade().getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
		try{
			
		
		msg = SessionFactory.getInstanceOf().getSession().createObjectMessage();
		msg.setObject(game );
			msg.setJMSType("Sending as Host");
		} catch (JMSException e) {
			LOG.info("sending falied as host");
		}
		return msg;
		
	}
	public void createData()
	{
		
	}

}
