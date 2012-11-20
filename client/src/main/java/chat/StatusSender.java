package chat;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import model.Player;

import org.apache.activemq.ActiveMQConnectionFactory;

import utility.Constants;

public class StatusSender implements Runnable {

	private MessageProducer producer;
	private static String message;
	private Session session;

	public StatusSender() {
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.ActiveMQConnect);

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);


			// Create the destination (Topic or Queue)
			//Destination destination = session.createQueue("CHAT");
			Topic topic= session.createTopic("ISALIVE");

			// Create a MessageProducer from the Session to the Topic or Queue
			producer = session.createProducer(topic);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			Thread senderThread=new Thread(this);
			senderThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				if(!(Player.getInstance().getUsername()==null)) {
					message="Online:"+Player.getInstance().getUsername();
					TextMessage textMessage = session.createTextMessage(message);
					producer.send(textMessage);
					
				}
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
