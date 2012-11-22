package action;

import static org.junit.Assert.*;

import java.awt.Rectangle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utility.ResizeHelper;
import utility.SpriteList;
import view.GameMakerView;

import eventlistener.CollisionEventListener;
import facade.Facade;

import model.SpriteModel;

public class ActionChangeGameStatusTest {
	
	   private  SpriteModel selectedSpriteModel;
	   private  SpriteModel secondarySpriteModel;
	   private  CollisionEventListener collisionListener;
	   private  ActionChangeGameStatus action;

	@Before
	public void setUp() throws Exception {
		
		GameMakerView.getInstance().reset();
		ResizeHelper.getInstance().reset();
      	selectedSpriteModel = new SpriteModel(190, 190, 10, 10, 100, 100, "","");
		secondarySpriteModel= new SpriteModel(100, 100, 10, 10, 100, 200, "","");
		secondarySpriteModel.setGroupId("Group1");
		SpriteList.getInstance().addSprite(selectedSpriteModel);
		SpriteList.getInstance().addSprite(secondarySpriteModel);
		collisionListener = new CollisionEventListener();
		collisionListener.setRegisteredGroupId1(selectedSpriteModel.getGroupId());
		collisionListener.setRegisteredGroupId2(secondarySpriteModel.getGroupId());
		action= new ActionChangeGameStatus(true);
		
	}
	
	@Test
	public void testDoAction() {
			Facade f = GameMakerView.getInstance().getFacade();
		f.startGame();
		action.setShowMessage(false);
		action.doAction(selectedSpriteModel);		
       	assertTrue(!f.getTimer().isRunning());	
       	
	}
	
	
	@After
	public void tearDown() throws Exception {
		GameMakerView.getInstance().getFacade().stopGame();
		GameMakerView.getInstance().reset();
		
	}

	
}
