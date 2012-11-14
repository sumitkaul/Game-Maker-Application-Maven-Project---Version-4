package multiplayer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

public class Receiver implements Runnable{

	
	private MessageConsumer consumer;
	private static Receiver receiver= new Receiver();
	private boolean receiveStatus = true;
	private Thread thread;
	

	public static Receiver getInstanceOf()
	{
		return receiver;
	}
	private Receiver()
	{
		thread = new Thread();
	}
	
	public void startListening()
	{
		thread.start();
	}
	
	public void stopListening()
	{
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveAsHost(String topic) throws JMSException{
		
		SessionFactory.getInstanceOf().createConnection();
		Message message=Subscribe.getInstanceOf().setTopicAndReceive(topic);
		
		if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
           
        } else {
           
        }
   
	}
	
	public void receiveData()
	{
		
	}

	@Override
	public void run() 
	{
		receiveData();
		
	}
	
	
}
