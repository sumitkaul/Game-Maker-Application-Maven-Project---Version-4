package multiplayer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Topic;

public  final class Subscribe {

	private String topic;
	private MessageConsumer consumer;
	private static final Subscribe instance = new Subscribe();
	private Topic topicName;
	private Queue Name;
	
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
		this.topicName = (Topic) SessionFactory.getInstanceOf().getSession().createTopic(topic);
		
	}
	
	public Message receiveData()
	{
		Message message = null;
		try {
			consumer = SessionFactory.getInstanceOf().getSession().createConsumer(topicName);
			message =  consumer.receive(1000);
		} catch (JMSException e) {
			e.printStackTrace();
		}
       return message;
	}
	
	public Destination getTopicName() {
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
