package multiplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

public class Publish implements ActionListener {

	private MessageProducer producer;
	private TextMessage message;
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		  try {  
			sendState(); 
			receiveState();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void setConnectionSettings() {
		// TODO Auto-generated method stub
		
		Destination destination=(Destination) SessionFactory.getInstanceOf().getSession();
		
		try {
			producer = SessionFactory.getInstanceOf().getSession().createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        	
	}
	
	private void sendState() {
		// TODO Auto-generated method stub
		String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
		try {
			message = SessionFactory.getInstanceOf().getSession().createTextMessage(text);
			  // Tell the producer to send the message
	          producer.send(message);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	

	

	private void receiveState() throws JMSException {
		
		Receiver.getInstanceOf().receiveAsHost();
	}

	
	
}
