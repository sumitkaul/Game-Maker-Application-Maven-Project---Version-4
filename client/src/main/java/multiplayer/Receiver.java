package multiplayer;

import java.util.HashMap;
import java.util.Set;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import model.SpriteModel;

import org.newdawn.slick.tests.xml.GameData;

import action.GameAction;

public class Receiver implements Runnable{

	
	private MessageConsumer consumer;
	private static Receiver receiver= new Receiver();
	private boolean receiveStatus = true;
	private Thread thread;
	

	public static Receiver getInstanceOf()
	{
		return receiver;
	}
	private Receiver()
	{
		thread = new Thread();
	}
	
	public void startListening()
	{
		thread.start();
	}
	
	public void stopListening()
	{
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveFromHost(String topic) throws JMSException{
		
		SessionFactory.getInstanceOf().createConnection();
		Subscribe.getInstanceOf().setTopic(topic);
		Message message= Subscribe.getInstanceOf().receiveData();
		
		if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
           
        } else 
        {
           
        }
   
	}
	
	public void receiveData() throws JMSException
	{
		SessionFactory.getInstanceOf().getConnection();
		Message message= Subscribe.getInstanceOf().receiveData();
		
		if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Object data = objectMessage.getObject();
            if (data instanceof HashMap)
            {
            	HashMap<GameAction, SpriteModel> map = (HashMap<GameAction, SpriteModel>) data;
            	Set <GameAction> actionSet = map.keySet();
            	for (GameAction action: actionSet)
            	{
            		SpriteModel model = map.get(action);
            		action.doAction(model);
            	}
            }
           
        } 
	}

	@Override
	public void run() 
	{
		while (receiveStatus)
		{
			try {
				receiveData();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
}
