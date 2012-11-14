package multiplayer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Topic;

public  final class Subscribe {

	private String topic;
	private MessageConsumer consumer;
	private static final Subscribe instance = new Subscribe();
	
	public static Subscribe getInstanceOf()
	{
		return instance;
	}
	
	
	private Subscribe()
	{
		
	}
	public Message setTopicAndReceive(String topic) throws JMSException {
		// TODO Auto-generated method stub
		this.topic=topic;
		Topic topic1 = SessionFactory.getInstanceOf().getSession().createTopic(topic);
		consumer = SessionFactory.getInstanceOf().getSession().createConsumer(topic1);
	     // Wait for a message
        Message message = consumer.receive(1000);	
        return message;
	}
	
	private void receiveState() throws JMSException {
		
		Receiver.getInstanceOf().receiveAsHost(topic);
	}

}
