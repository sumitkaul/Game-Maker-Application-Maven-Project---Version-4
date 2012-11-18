package facade;

import static org.junit.Assert.*;

import interfaces.Drawable;

import java.util.List;

import gameMaker.gameMaker;

import model.SpriteModel;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.interception.MainClassAdapter;

import utility.Constants;
import utility.SpriteList;
import view.GameMakerView;
import view.GamePanel;

public class FacadeTest   {
	private  Facade facade;
	private GamePanel gamePanel;
	
	@Before
	public void setUp() throws Exception {
		//setAdapter(new MainClassAdapter(gameMaker.class, new String[0]));        
        gamePanel=new GamePanel(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
		facade=new Facade(gamePanel);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}

@Test
	public void testStartGame() {
		facade.startGame();
		if(facade.getTimer().isRunning())
			assertTrue(true);
		else
			assertTrue(false);
		facade.stopGame();
		
	}

	@Test
	public void testStopGame() {
	facade.stopGame();
		if(!facade.getTimer().isRunning())
			assertTrue(true);
		else
			assertTrue(false);
	}

	@Test
	public void testCreateViewsForModels() {
		//removing all the drawables and spritemodels.
		GameMakerView.getInstance().reset();

		SpriteModel selectedSpriteModel = new SpriteModel(190, 190, 10, 10, 100, 100, "","");
		selectedSpriteModel.setImageId(12);
		SpriteModel secondarySpriteModel= new SpriteModel(100, 100, 10, 10, 100, 200, "","");
		secondarySpriteModel.setImageId(10);
		secondarySpriteModel.setGroupId("Group1");
		SpriteList.getInstance().addSprite(selectedSpriteModel);
		SpriteList.getInstance().addSprite(secondarySpriteModel);
		facade.createViewsForModels(SpriteList.getInstance().getSpriteList());
		
		if(facade.getGamePanel().getDrawables().size()==2)
			assertTrue(true);
		else
			assertTrue(false);
		//post processing needed only for this test
		GameMakerView.getInstance().reset();
				
	}	

}
