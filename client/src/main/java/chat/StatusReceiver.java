package chat;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import utility.Constants;


public class StatusReceiver implements Runnable {

	private MessageConsumer consumer;

	public StatusReceiver() {
		try {
			// Create a ConnectionFactory
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Constants.ActiveMQConnect);

			// Create a Connection
			Connection connection = connectionFactory.createConnection();
			connection.start();


			// connection.setExceptionListener((ExceptionListener) this);

			// Create a Session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// Create the destination (Topic or Queue)
			//Destination destination = session.createQueue("CHAT");
			Topic topic= session.createTopic("STATUS");
			// Create a MessageConsumer from the Session to the Topic or Queue
			consumer = session.createConsumer(topic);
			Thread ststusReceiverThread=new Thread(this);
			ststusReceiverThread.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (true) {
			try {
				// Wait for a message
				Message message = consumer.receive();
				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;

					String text = textMessage.getText();
					String[] onlineUsers=text.split(",");
					for(String i:onlineUsers) {
						//Users online
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
