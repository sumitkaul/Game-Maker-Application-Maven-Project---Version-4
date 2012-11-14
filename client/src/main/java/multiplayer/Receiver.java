package multiplayer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

public class Receiver {

	
	private MessageConsumer consumer;
	private static Receiver receiver= new Receiver();
	

	public static Receiver getInstanceOf()
	{
		return receiver;
	}
	
	public void receiveAsHost(String topic) throws JMSException{
		
		SessionFactory.getInstanceOf().createConnection();
		
		Subscribe subscribe=new Subscribe();
		Message message=subscribe.setTopicAndReceive(topic);
		
		if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
           
        } else {
           
        }
   
	}
	
}
