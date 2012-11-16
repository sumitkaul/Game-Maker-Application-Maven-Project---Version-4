package multiplayer;

import java.awt.Toolkit;

import model.SpriteModel;
import action.GameAction;

public class Sender {
	
	Toolkit toolkit;
	
	public void sendAsHost(Test test,String topic)
	{
		
		
		SessionFactory.getInstanceOf().createConnection();
		Publish.getInstanceOf().setTopic(topic);
		Publish.getInstanceOf().setGameState(test);
		
//		Timer timer=new Timer();
//		timer.schedule(publishState,10);
//		   toolkit = Toolkit.getDefaultToolkit();
//		    timer = new Timer();
//		    timer.schedule(publishState, 10);


		Publish.getInstanceOf().sendState();
		
	}
	
	public void sendAsClient(GameAction action, SpriteModel spriteModel)
	{
		SessionFactory.getInstanceOf().createConnection();
		Publish.getInstanceOf().sendGameAction(action, spriteModel);
	
	}

}