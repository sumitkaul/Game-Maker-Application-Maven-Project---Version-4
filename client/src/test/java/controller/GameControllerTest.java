package controller;
// * 
 
//package controller;
//
import static org.junit.Assert.*;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.SpriteModel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uispec4j.UISpecTestCase;

import action.ActionBounce;
import action.ActionChangeSpeed;
import action.GameAction;

import eventlistener.CollisionEventListener;
import eventlistener.EventListener;
import gameMaker.gameMaker;

import utility.Constants;
import utility.SpriteList;
import view.GameMakerView;
import view.GamePanel;


public class GameControllerTest{

	/**
	 * @throws java.lang.Exception
	 */
	private GamePanel gamePanel;
    private ArrayList<EventListener> events;
    private static SpriteModel selectedSpriteModel;
    private static SpriteModel secondarySpriteModel;
    private Map<String, SpriteModel> gameObjectsWithId;
    private Map<String, List<SpriteModel>> gameObjectsWithGroupId;
	private GameController gameControllerTest;
	private  CollisionEventListener collisionListener;
	private  GameAction action;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//SpriteList spriteList= new SpriteList();
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	    GameMakerView.getInstance().reset();
//        setAdapter(new MainClassAdapter(gameMaker.class, new String[0]));        
        gamePanel=new GamePanel(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
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
		gameControllerTest = new GameController();
		gameControllerTest.registerListener(collisionListener);
		gameControllerTest.setGamePanel(gamePanel);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		GameMakerView.getInstance().reset();
	}


//	/**
//	 * Test method for {@link controller.GameController#registerListener(eventlistener.EventListener)}.
//	 */
	@Test
	public void testRegisterListener() {
		gameControllerTest.registerListener(collisionListener);
		List<EventListener> events =gameControllerTest.getEvents();
		if(events.get(1).equals(collisionListener))
			assertTrue(true);
		else
			assertTrue(false);
	}
//
//	/**
//	 * Test method for {@link controller.GameController#unregisterListener(eventlistener.EventListener)}.
//	 */
	@Test
	public void testUnregisterListener() {
		gameControllerTest.unregisterListener(collisionListener);
		List<EventListener> events =gameControllerTest.getEvents();
		if(events.isEmpty())
			assertTrue(true);
		else
			assertTrue(false);
			}

}
