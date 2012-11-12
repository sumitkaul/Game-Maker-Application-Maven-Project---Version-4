package multiplayer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;

public class Subscribe {

	private MessageConsumer consumer;
	public Message setTopicAndReceive() throws JMSException {
		// TODO Auto-generated method stub
		Destination destination = SessionFactory.getInstanceOf().getSession().createQueue("TEST.FOO");
		consumer = SessionFactory.getInstanceOf().getSession().createConsumer(destination);
	     // Wait for a message
        Message message = consumer.receive(1000);	
        return message;
	}

}
