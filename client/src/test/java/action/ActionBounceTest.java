package action;

import static org.junit.Assert.*;

import model.SpriteModel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utility.SpriteList;

import eventlistener.CollisionEventListener;

public class ActionBounceTest {

	private static SpriteModel selectedSpriteModel;
    private static SpriteModel secondarySpriteModel;
    private static CollisionEventListener collisionListener;
	private static GameAction action;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		selectedSpriteModel = new SpriteModel(190, 190, 10, 10, 100, 100, "","");
		secondarySpriteModel= new SpriteModel(100, 100, 10, 10, 100, 200, "","");
		secondarySpriteModel.setGroupId("Group1");
		SpriteList.getInstance().addSprite(selectedSpriteModel);
		SpriteList.getInstance().addSprite(secondarySpriteModel);
		collisionListener = new CollisionEventListener();
		collisionListener.setRegisteredGroupId1(selectedSpriteModel.getGroupId());
		collisionListener.setRegisteredGroupId2(secondarySpriteModel.getGroupId());
		action= new ActionBounce(true);
		
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDoAction() {
		double previousSpeedX=selectedSpriteModel.getSpeedX();
		double previousSpeedY=selectedSpriteModel.getSpeedY();
		action.doAction(selectedSpriteModel);
		if(selectedSpriteModel.getSpeedX()== -previousSpeedX||selectedSpriteModel.getSpeedY()== -previousSpeedY)
			 assertTrue(true);
		else
			assertTrue(false);
		
	}

}
