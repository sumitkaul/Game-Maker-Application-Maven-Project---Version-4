package eventlistener;

import static org.junit.Assert.*;


import model.SpriteModel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utility.ResizeHelper;
import utility.SpriteList;
import view.GameMakerView;

import action.ActionChangeSpeed;
import action.GameAction;

public class CollisionEventListenerTest {
	private  SpriteModel selectedSpriteModel;
    private  SpriteModel secondarySpriteModel;
    private  CollisionEventListener collisionListener;
	private  GameAction action;

	@Before
	public void setUp() throws Exception {
		GameMakerView.getInstance().reset();
		ResizeHelper.getInstance().reset();
		selectedSpriteModel = new SpriteModel(100, 100, 10, 10, 20, 30, "","");
		secondarySpriteModel= new SpriteModel(100, 100, 10, 10, 100, 100, "","");
		
		selectedSpriteModel.setGroupId("Group1");
		secondarySpriteModel.setGroupId("Group2");		

		SpriteList.getInstance().addSprite(selectedSpriteModel);
		SpriteList.getInstance().addSprite(secondarySpriteModel);
		collisionListener = new CollisionEventListener();
		collisionListener.setRegisteredGroupId1(selectedSpriteModel.getGroupId());
		collisionListener.setRegisteredGroupId2(secondarySpriteModel.getGroupId());
		action= new ActionChangeSpeed(20,20);
		collisionListener.setAction(action);

	}

	@After
	public void tearDown() throws Exception {
		GameMakerView.getInstance().reset();		
	}

   @Test
	public void testCheckEvent() {
		collisionListener.checkEvent(null);
		if(selectedSpriteModel.getSpeedX()==20 &&selectedSpriteModel.getSpeedY()==20)
			 assertTrue(true);
		else
			 assertTrue(false);
	}

}
