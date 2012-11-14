package multiplayer;

import java.awt.Toolkit;
import java.util.Timer;

public class Sender {
	
	Toolkit toolkit;
	
	public void sendAsHost(String text,String topic)
	{
		
		
		SessionFactory.getInstanceOf().createConnection();
		Publish publishState=new Publish();
		publishState.setTopic(topic);
		publishState.setGameState(text);
		
//		Timer timer=new Timer();
//		timer.schedule(publishState,10);
//		   toolkit = Toolkit.getDefaultToolkit();
//		    timer = new Timer();
//		    timer.schedule(publishState, 10);


		publishState.sendState();
		
	}

}