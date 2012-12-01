package loader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import eventlistener.EventListener;
import game.engine.slick2d.player.GameEnginePanel;

import model.SpriteModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class GamePackageTest {

	
	private    GamePackage mock;
	@Before
	public void setUp() throws Exception {
		mock= Mockito.mock(GamePackage.class); 	
	}

	@After
	public void tearDown() throws Exception {
		mock=null;
	}

	
	@Test
	public void testsetEventsForGameController() {
		   List<EventListener> mockpara= Mockito.mock(List.class); 
	       doThrow(new RuntimeException()).when(mock).setEventsForGameController(mockpara);
	       doCallRealMethod().when(mock).setEventsForGameController(mockpara);

	      	}
	
	@Test
	public void testGetEventsForKeyController() {
		   List<EventListener> mockpara= Mockito.mock(List.class); 
	       doThrow(new RuntimeException()).when(mock).setEventsForGameController(mockpara);
	       doCallRealMethod().when(mock).setEventsForGameController(mockpara);
	}

	@Test
	public void testSetEventsForKeyController() {
		   List<EventListener> mockpara= Mockito.mock(List.class); 
	       doThrow(new RuntimeException()).when(mock).setEventsForGameController(mockpara);
	       doCallRealMethod().when(mock).setEventsForGameController(mockpara);
	}

	@Test
	public void testGetSpriteList() {
		   List<EventListener> mockpara= Mockito.mock(List.class); 
	       doThrow(new RuntimeException()).when(mock).setEventsForGameController(mockpara);
	       doCallRealMethod().when(mock).setEventsForGameController(mockpara);
	}

}
