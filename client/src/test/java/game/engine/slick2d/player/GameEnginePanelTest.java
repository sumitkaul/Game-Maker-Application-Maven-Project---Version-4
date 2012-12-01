package game.engine.slick2d.player;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.newdawn.slick.CanvasGameContainer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameEnginePanelTest {

	GameEnginePanel gameEnginePanel,gameEnginePanelMock;
	CanvasGameContainer canvasGameContainer,expectedApp,actualApp;
	GameEngineController gameEngineController,expectedGameEngineController,actualGameEngineController;
	
	
	@Before
	public void setUp() throws Exception {
		gameEnginePanel= new GameEnginePanel();
		gameEnginePanelMock= mock(GameEnginePanel.class);
		
	}
   
	@After
	public void tearDown() throws Exception {
		gameEnginePanel=null;
	}

	@Test
	public void testGameEnginePanel() {
		
	}

	@Test
	public void testSetApp() {
		gameEnginePanel.setApp(canvasGameContainer);
		actualApp= gameEnginePanel.getApp();
		expectedApp= canvasGameContainer;
		assertEquals(expectedApp,actualApp);
	}

	@Test
	public void testGetGame() {
		gameEnginePanel.setGame(gameEngineController);
		actualGameEngineController= gameEnginePanel.getGame();
		expectedGameEngineController= gameEngineController;
		assertEquals(expectedGameEngineController,actualGameEngineController);
		
	}
	
	@Test
	public void testGetApp() {
		when(gameEnginePanelMock.getApp()).thenReturn(canvasGameContainer);
		assertEquals(canvasGameContainer,gameEnginePanelMock.getApp());
	}
	
}
