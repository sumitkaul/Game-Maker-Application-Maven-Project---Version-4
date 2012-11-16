package multiplayer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Topic;

public  final class Subscribe {

	private String name;
	private MessageConsumer consumer;
	private static final Subscribe instance = new Subscribe();
	//private Destination topicName;
	private Destination queueName;
	
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
		this.queueName = (Destination) SessionFactory.getInstanceOf().getSession().createQueue(name);
		
	}
	
	public Message receiveData()
	{
		Message message = null;
		try {
			consumer = SessionFactory.getInstanceOf().getSession().createConsumer(queueName);
			message =  consumer.receive(1000);
		} catch (JMSException e) {
			e.printStackTrace();
		}
       return message;
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
	

}
