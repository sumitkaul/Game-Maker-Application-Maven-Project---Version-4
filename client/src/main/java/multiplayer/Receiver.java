package multiplayer;

import java.util.HashMap;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.newdawn.slick.tests.xml.GameData;

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
		Subscribe.getInstanceOf().setTopic(topic);
		Message message= Subscribe.getInstanceOf().receiveData();
		
		if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
           
        } else 
        {
           
        }
   
	}
	
	public void receiveData() throws JMSException
	{
		SessionFactory.getInstanceOf().getConnection();
		Message message= Subscribe.getInstanceOf().receiveData();
		
		if (message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            Object data = objectMessage.getObject();
            if (data instanceof HashMap)
            {
            	
            }
           
        } 
	}

	@Override
	public void run() 
	{
		try {
			receiveData();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
