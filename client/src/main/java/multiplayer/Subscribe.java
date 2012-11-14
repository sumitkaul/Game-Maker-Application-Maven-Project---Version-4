package multiplayer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

public  final class Subscribe {

	private String topic;
	private MessageConsumer consumer;
	private static final Subscribe instance = new Subscribe();
	private Topic topicName;
	
	public static Subscribe getInstanceOf()
	{
		return instance;
	}
	
	
	private Subscribe()
	{
		
	}
	public void setTopic(String topic) throws JMSException {
		// TODO Auto-generated method stub
		this.topic=topic;
		topicName = SessionFactory.getInstanceOf().getSession().createTopic(topic);
		
	}
	
	public Message receiveData()
	{
		Message message = null;
		try {
			consumer = SessionFactory.getInstanceOf().getSession().createConsumer(topicName);
		
	     // Wait for a message
       message = consumer.receive(1000);
		} catch (JMSException e) {
			e.printStackTrace();
		}
       return message;
	}
	
	public Topic getTopicName() {
		return topicName;
	}


	public void setTopicName(Topic topicName) {
		this.topicName = topicName;
	}


	public String getTopic() {
		return topic;
	}


	private void receiveState() throws JMSException {
		
		Receiver.getInstanceOf().receiveFromHost(topic);
	}
	

}
