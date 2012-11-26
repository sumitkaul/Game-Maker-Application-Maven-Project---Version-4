package multiplayer;

import java.util.HashMap;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import loader.GamePackage;
import model.SpriteModel;
import action.GameAction;

public class Receiver implements MessageListener{


	private MessageConsumer consumer;
	private static Receiver receiver= new Receiver();
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Receiver.class);

	private boolean receiveStatus = true;
	private Message message;
	private Destination queueName;


	public static Receiver getInstanceOf()
	{
		return receiver;
	}
	


	public  void receiveFromHost(String topic) throws JMSException{

		SessionFactory.getInstanceOf().createConnection();
		Subscribe.getInstanceOf().setQueue(topic);
		consumer= Subscribe.getInstanceOf().receiveData();

		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			LOG.info("The text is " +text);

		} else 
		{

		}
	}
	

	private void receiveData() throws JMSException
	{
		SessionFactory.getInstanceOf().createConnection();
		consumer=  Subscribe.getInstanceOf().receiveData();

	}

	public void runGame() 
	{
		//		while (receiveStatus)
		//		{
		try {
			LOG.debug("in run ???????????????????????");
			receiveData();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		//}

	}
	public boolean isReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(boolean receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		
		LOG.info("-------------------------------------------------------------------------------------in on message");
	
		ObjectMessage objectMessage = (ObjectMessage) message;
		LOG.info("object message is" +message);
		
		try{
			Object data = objectMessage.getObject();
			if ( data instanceof GamePackage)
			{
				GamePackage gameData = (GamePackage) data;
				Protocol protocol = new Protocol();
				protocol.setGameState(gameData);
				//LOG.info("-----------------"+data.getS());
				LOG.info("The data is GamePackage type " + objectMessage.getObject());
			}
			else if(data instanceof HashMap)
			{
				HashMap<GameAction, SpriteModel> actionData = (HashMap<GameAction, SpriteModel>) data;
				Protocol protocol = new Protocol();
				protocol.setMultiplayerAction(actionData);
				//LOG.info("-----------------"+data.getS());
				LOG.info("The data is HashMap type" + objectMessage.getObject());
			}
		
		//		SessionFactory.getInstanceOf().closeSession();
			
		// Come back to this.This is very important in multiplayer!!!!!	
			
			
		}
		catch ( NullPointerException ne)
		{
			LOG.info("No message received");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	
	}
	
	
	public void setQueueName(Destination queueName){
		
		this.queueName=queueName;
	}
	



	public void subscribe(String receivingQueueName) throws JMSException {
		Subscribe.getInstanceOf().setQueue(receivingQueueName);
		
	}


}
