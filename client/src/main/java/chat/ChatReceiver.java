package chat;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import view.ChatPanel;
import utility.Constants;
public class ChatReceiver implements Runnable {

	private MessageConsumer consumer;

	public ChatReceiver() {
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
			Topic topic= session.createTopic("CHAT");
			// Create a MessageConsumer from the Session to the Topic or Queue
			consumer = session.createConsumer(topic);
			Thread chatReceiverThread=new Thread(this);
			chatReceiverThread.start();
		} catch (Exception ex) {

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
					if(text.charAt(0)==':') {
						
					}
					else
						ChatPanel.updateChatWindow(text);
					//Design.getInstance().updateChatWindow(text);

				} else {
					ChatPanel.updateChatWindow(message.toString());
				}
			} catch (Exception e) {
			}
		}
	}
}
