package multiplayer;

import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.Connection;

public final class SessionFactory {
	
	private static SessionFactory instance= new SessionFactory();
	private javax.jms.Connection connection;
	private Session session; 
	
	public javax.jms.Connection getConnection() {
		return connection;
	}

	public void setConnection(javax.jms.Connection connection) {
		this.connection = connection;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public static SessionFactory getInstanceOf()
	{
		return instance;
	}
	

	public void createConnection(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://129.29.247.5:61616");

        // Create a Connection
        connection=null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		
	}
	
	
}
