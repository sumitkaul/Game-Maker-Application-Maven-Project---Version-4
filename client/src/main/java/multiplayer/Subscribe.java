package multiplayer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Topic;

import org.newdawn.slick.util.Log;

public  final class Subscribe {

	private String name;
	private MessageConsumer consumer;
	private static final Subscribe instance = new Subscribe();
	//private Destination topicName;
	private Destination queueName;
	private boolean isMessageListenerSet=false;
	
	public static Subscribe getInstanceOf()
	{
		return instance;
	}
	
	
	private Subscribe()
	{
		
	}
	public void setQueue(String name) throws JMSException {
		// TODO Auto-generated method stub
		this.name=name;
		this.queueName = (Destination) SessionFactory.getInstanceOf().getSession().createQueue(this.name);
		Log.debug("Queue name is --------------------"+name);
		Receiver.getInstanceOf().setQueueName(queueName);
		
		
	}
	
	public MessageConsumer receiveData()
	{
		Message message = null;
		
		try {
			consumer = SessionFactory.getInstanceOf().getSession().createConsumer(queueName);
			
			
			//Receiver.getInstanceOf().onMessage(message);
			Log.info("6666666666666666666666666666666666666666666666666666666666666 in receive data");
			setMessageListener();
			
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
       return consumer;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public MessageConsumer getConsumer() {
		return consumer;
	}


	public void setConsumer(MessageConsumer consumer) {
		this.consumer = consumer;
	}


	public Destination getQueueName() {
		return queueName;
	}


	public void setQueueName(Destination queueName) {
		this.queueName = queueName;
	}
	
	public void setMessageListener() throws JMSException{
		
		if(!this.isMessageListenerSet){
		consumer.setMessageListener(Receiver.getInstanceOf());
		this.isMessageListenerSet=true;
		}
		
	}
	
	

}
