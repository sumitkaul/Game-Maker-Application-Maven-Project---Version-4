package multiplayer;

import javax.swing.Timer;

public class Sender {
	
	public void sendAsHost()
	{
//		Runnable runnable=(Runnable) new Publish();
//		Thread brokerThread=new Thread(runnable);
//		brokerThread.setDaemon(false);
//		brokerThread.start();
		
		
		SessionFactory.getInstanceOf().createConnection();
		Publish publishState=new Publish();
		publishState.setConnectionSettings();
		
		Timer timer=new Timer(10,publishState);
		timer.start();
		
		
	}

}