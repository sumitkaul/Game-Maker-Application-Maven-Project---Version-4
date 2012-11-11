package eventlistener;

import static org.junit.Assert.*;


import model.SpriteModel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utility.SpriteList;

import action.ActionChangeSpeed;
import action.GameAction;

public class CollisionEventListenerTest {
	private static SpriteModel selectedSpriteModel;
    private static SpriteModel secondarySpriteModel;
    private static CollisionEventListener collisionListener;
	private static GameAction action;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		selectedSpriteModel = new SpriteModel(100, 100, 10, 10, 100, 100, "","");
		secondarySpriteModel= new SpriteModel(190, 190, 10, 10, 100, 100, "","");
		secondarySpriteModel.setGroupId("Group1");
		SpriteList.getInstance().addSprite(selectedSpriteModel);
		SpriteList.getInstance().addSprite(secondarySpriteModel);
		collisionListener = new CollisionEventListener();
		collisionListener.setRegisteredGroupId1(selectedSpriteModel.getGroupId());
		collisionListener.setRegisteredGroupId2(secondarySpriteModel.getGroupId());
		action= new ActionChangeSpeed(20,20);
		collisionListener.setAction(action);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheckEvent() {
		collisionListener.checkEvent(null);
		if(selectedSpriteModel.getSpeedX()==20&&selectedSpriteModel.getSpeedY()==20)
			 assertTrue(true);
		else
			 assertTrue(false);
	}

}
