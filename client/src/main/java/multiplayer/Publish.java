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
	private final static Publish instance =new Publish();
	private Destination topic1;
	
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
			topic1 = (Destination) SessionFactory.getInstanceOf().getSession().createQueue(topic);
			producer = SessionFactory.getInstanceOf().getSession().createProducer(topic1);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		} catch (JMSException e1) {
		
		}
			
	}
	
	public void sendState() {
		String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
		try {
			Protocol protocol = new Protocol();
			objectMessage=protocol.createDataAsHost();
			  // Tell the producer to send the message
			LOG.debug("S is -------------------"+objectMessage.getObject());
	        producer.send(objectMessage);
	        SessionFactory.getInstanceOf().closeSession();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
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