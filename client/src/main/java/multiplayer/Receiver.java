package multiplayer;

import java.util.HashMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import action.GameAction;

import loader.GamePackage;
import model.SpriteModel;

public class Receiver implements MessageListener{


	private MessageConsumer consumer;
	private static Receiver receiver= new Receiver();
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Receiver.class);

	private boolean receiveStatus = true;
	private Message message;


	public static Receiver getInstanceOf()
	{
		return receiver;
	}
	


	public  void receiveFromHost(String topic) throws JMSException{

		SessionFactory.getInstanceOf().createConnection();
		Subscribe.getInstanceOf().setQueue(topic);
		Message message= Subscribe.getInstanceOf().receiveData();

		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			LOG.info("The text is " +text);

		} else 
		{

		}
	}
	//	public static void main(String[] args0)
	//	{
	//		while(true)
	//		{
	//			try {
	//				receiveData();
	//			} catch (JMSException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//		}
	//	}

	private void receiveData() throws JMSException
	{
		SessionFactory.getInstanceOf().createConnection();
		Subscribe.getInstanceOf().setQueue("TEST2");
		message=  Subscribe.getInstanceOf().receiveData();

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
	@Override
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
		
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
		
				SessionFactory.getInstanceOf().closeSession();
		}
		catch ( NullPointerException ne)
		{
			LOG.info("No message received");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	
	}


}
