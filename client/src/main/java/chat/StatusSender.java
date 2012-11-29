package chat;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import org.apache.log4j.Logger;


import model.Player;

import org.apache.activemq.ActiveMQConnectionFactory;

import utility.Constants;

public class StatusSender implements Runnable {
	private static final Logger LOG = Logger.getLogger(StatusSender.class);

	private MessageProducer producer;
	private Session session;
	public static boolean sendStatus = false;

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
			LOG.error(e);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				if(!(Player.getInstance().getUsername()==null)) {
					String message="Online:"+Player.getInstance().getUsername();
					//TextMessage textMessage = session.createTextMessage(message);
					//producer.send(textMessage);
					
				}
				Thread.sleep(1000);
			} catch (Exception e) {
				LOG.error(e);
			}
		}

	}
}
