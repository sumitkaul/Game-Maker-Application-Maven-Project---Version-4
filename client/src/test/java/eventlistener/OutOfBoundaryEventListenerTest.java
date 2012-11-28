package eventlistener;

import static org.junit.Assert.*;

import model.SpriteModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import action.ActionChangeSpeed;
import action.ActionMove;
import action.GameAction;

import utility.SpriteList;
import view.GameMakerView;
import java.lang.Math;

public class OutOfBoundaryEventListenerTest {
    
	@Before
	public void setUp() throws Exception {
		SpriteList.getInstance().getSpriteList().clear();    		
	}

	@After
	public void tearDown() throws Exception {
	
	}

	@Test
	public void testRightVerticleWall() {
		double width = 20;
		double height = 100;
		double xspeed = 20;
		double yspeed = 20;
		// to remove the dependancy on the GameMakerView - removing it
		//double posX = GameMakerView.getInstance().getGamePanel().getWidth() + width +10;
		double posX = 800;
		double posY = 100;
		SpriteModel selectedSpriteModel = new SpriteModel( posX, posY, xspeed, yspeed, width, height, "","");
		SpriteList.getInstance().addSprite(selectedSpriteModel);
		OutOfBoundaryEventListener oOBEventListner = new OutOfBoundaryEventListener();
		oOBEventListner.setRegisteredGroupId(selectedSpriteModel.getGroupId());
		oOBEventListner.setRegisteredObjectId(selectedSpriteModel.getId()); 
		GameAction action = new ActionChangeSpeed(-xspeed, yspeed);
		oOBEventListner.setAction(action);
		oOBEventListner.checkEvent(null);
		//accounting for floating point errors comparion errors
		assertTrue(Math.abs(selectedSpriteModel.getSpeedX()+xspeed) < 10e-12);	
		
	}
	
	@Test
	public void testLeftVerticleWall() {
		double width = 20;
		double height = 100;
		double xspeed = -20;
		double yspeed = 20;
		double posX = -width-10;
		double posY = 100;
		SpriteModel selectedSpriteModel = new SpriteModel( posX, posY, xspeed, yspeed, width, height, "","");
		SpriteList.getInstance().addSprite(selectedSpriteModel);
		OutOfBoundaryEventListener oOBEventListner = new OutOfBoundaryEventListener();
		oOBEventListner.setRegisteredGroupId(selectedSpriteModel.getGroupId());
		oOBEventListner.setRegisteredObjectId(selectedSpriteModel.getId()); 
		GameAction action = new ActionChangeSpeed(-xspeed, yspeed);
		oOBEventListner.setAction(action);
		oOBEventListner.checkEvent(null);
		//accounting for floating point errors comparion errors
		assertTrue(Math.abs(selectedSpriteModel.getSpeedX()+xspeed) < 10e-12);		
	}
	
	@Test
	public void testBottomHorizontalFloor() {
		double width = 20;
		double height = 100;
		double xspeed = 20;
		double yspeed = 20;
		double posX = 100;
		//to remove the dependancy on GameMakerView
		//double posY = GameMakerView.getInstance().getGamePanel().getHeight() + height + 10;
		double posY = 800;
		SpriteModel selectedSpriteModel = new SpriteModel( posX, posY, xspeed, yspeed, width, height, "","");
		SpriteList.getInstance().addSprite(selectedSpriteModel);
		OutOfBoundaryEventListener oOBEventListner = new OutOfBoundaryEventListener();
		oOBEventListner.setRegisteredGroupId(selectedSpriteModel.getGroupId());
		oOBEventListner.setRegisteredObjectId(selectedSpriteModel.getId()); 
		GameAction action = new ActionChangeSpeed(xspeed, -yspeed);
		oOBEventListner.setAction(action);
		oOBEventListner.checkEvent(null);
		//accounting for floating point errors comparsion errors
		assertTrue(Math.abs(selectedSpriteModel.getSpeedY()+yspeed) < 10e-12);		
	}
	
	@Test
	public void testTopHorizontalRoof() {
		double width = 20;
		double height = 100;
		double xspeed = 20;
		double yspeed = -20;
		double posX = 100;
		double posY = -height -10;
		SpriteModel selectedSpriteModel = new SpriteModel( posX, posY, xspeed, yspeed, width, height, "","");
		SpriteList.getInstance().addSprite(selectedSpriteModel);
		OutOfBoundaryEventListener oOBEventListner = new OutOfBoundaryEventListener();
		oOBEventListner.setRegisteredGroupId(selectedSpriteModel.getGroupId());
		oOBEventListner.setRegisteredObjectId(selectedSpriteModel.getId()); 
		GameAction action = new ActionChangeSpeed(xspeed, -yspeed);
		oOBEventListner.setAction(action);
		oOBEventListner.checkEvent(null);
		//accounting for floating point errors comparsion errors
		assertTrue(Math.abs(selectedSpriteModel.getSpeedY()+yspeed) < 10e-12);		
	}



}
