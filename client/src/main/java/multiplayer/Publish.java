package multiplayer;

import java.util.TimerTask;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class Publish{

	private MessageProducer producer;
	private TextMessage message;
	private boolean done=false;
	private String topic;
	private String gameState;
	
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
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
			
	}
	
	public void sendState() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		this.gameState=text;
	}
	
	
}
