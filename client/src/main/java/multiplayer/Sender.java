package multiplayer;

import java.awt.Toolkit;

import javax.jms.JMSException;

import model.SpriteModel;
import action.GameAction;

public class Sender {
	
	Toolkit toolkit;
	
	public void sendAsHost(String topic)
	{
		SessionFactory.getInstanceOf().createConnection();
		Publish.getInstanceOf().setTopic(topic);

		Publish.getInstanceOf().sendState();
		
	}
	
	public void sendAsClient(GameAction action, SpriteModel spriteModel)
	{
		SessionFactory.getInstanceOf().createConnection();
		Publish.getInstanceOf().sendGameAction(action, spriteModel);
	
	}
	public void sendAcknowledgement(String queue, String playerName) throws JMSException
	{
		SessionFactory.getInstanceOf().createConnection();
		Publish.getInstanceOf().setTopic(queue);
		Publish.getInstanceOf().sendAcknowledgement(playerName);
	}

	public void sendStartSignal() throws JMSException {
		SessionFactory.getInstanceOf().createConnection();
		Publish.getInstanceOf().sendStartSignal();
		
	}

}