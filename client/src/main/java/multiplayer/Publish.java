package multiplayer;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
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
	private Test gameState;
	private final static Publish instance =new Publish();
	private Topic topic1;
	
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Publish.class);
	
	
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
		
		
		try {
			//System.out.println(topic);
			topic1 = (Topic) SessionFactory.getInstanceOf().getSession().createTopic(topic);
			producer = SessionFactory.getInstanceOf().getSession().createProducer(topic1);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		} catch (JMSException e1) {
		
		}
			
	}
	
	public void sendState() {
		String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
		try {
			objectMessage = SessionFactory.getInstanceOf().getSession().createObjectMessage();
			objectMessage.setObject(gameState);
			objectMessage.setJMSType("Some string");
			  // Tell the producer to send the message
			LOG.debug(gameState.getS());
			LOG.debug("S is -------------------"+objectMessage.getObject());
	        producer.send(objectMessage);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}


	public void setGameState(Test test) {
		this.gameState=test;
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
