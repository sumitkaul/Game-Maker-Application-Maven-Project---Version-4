package multiplayer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import loader.GamePackage;

public class Receiver implements Runnable{


	private MessageConsumer consumer;
	private static Receiver receiver= new Receiver();
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Receiver.class);

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
		LOG.debug("in start listening ------------------------------------------");
	}

	public void stopListening()
	{
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		Message message=  Subscribe.getInstanceOf().receiveData();

		LOG.info("received message");
		//	if (message instanceof ObjectMessage) {
		LOG.info("In the if loop");
		ObjectMessage objectMessage = (ObjectMessage) message;
		//LOG.info("the kms type is "+message.getJMSType());
		LOG.info("object message is" +message);
		GamePackage data =(GamePackage) objectMessage.getObject();
		if (data != null)
		{
			LOG.info("The data is not null");
		Protocol protocol = new Protocol();
		protocol.setGameState(data);
		//LOG.info("-----------------"+data.getS());
		LOG.info("The data is " + objectMessage.getObject());
		SessionFactory.getInstanceOf().closeSession();
		//            if (data instanceof HashMap)
		//            {
		//				HashMap<GameAction, SpriteModel> map = (HashMap<GameAction, SpriteModel>) data;
		//            	Set <GameAction> actionSet = map.keySet();
		//            	for (GameAction action: actionSet)
		//            	{
		//            		SpriteModel model = map.get(action);
		//            		action.doAction(model);
		//            	}
		//            }
		//            else if (data instanceof MultiPlayerOption)
		//            {
		//            	LOG.info("The data is " + data);
		//            }
		//           
		// } 
		}
	}

	@Override
	public void run() 
	{
		while (receiveStatus)
		{
			try {
				LOG.debug("in run ???????????????????????");
				receiveData();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

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


}
