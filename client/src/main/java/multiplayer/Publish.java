package multiplayer;

import java.util.HashMap;
import java.util.TimerTask;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;

import model.SpriteModel;
import action.GameAction;

public final class Publish{

	private MessageProducer producer;
	private TextMessage message;
	private boolean done=false;
	private ObjectMessage objectMessage;
	private String topic;
	private String gameState;
	private final static Publish instance =new Publish();
	
	
	public static Publish getInstanceOf(){
		return instance;
	}
	
	private Publish()
	{
		
	}
	public String getTopic() {
		return topic;
	}


	
	
	public void setTopic(String topic) {
		// TODO Auto-generated method stub
		this.topic=topic;
		Destination destination;
		Topic topic1;
		try {
			topic1 = (Topic) SessionFactory.getInstanceOf().getSession().createTopic(topic);
			producer = SessionFactory.getInstanceOf().getSession().createProducer(topic1);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		} catch (JMSException e1) {
		
		}
			
	}
	
	public void sendState() {
		String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
		try {
			message = SessionFactory.getInstanceOf().getSession().createTextMessage(gameState);
			  // Tell the producer to send the message
	          producer.send(message);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	

	public void setGameState(String text) {
		this.gameState=text;
	}
	
	public void sendGameAction(GameAction action, SpriteModel spriteModel)
	{
		try {
			Protocol protocol = new Protocol();
			
			objectMessage = protocol.createData(action, spriteModel);
	          producer.send(objectMessage);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
