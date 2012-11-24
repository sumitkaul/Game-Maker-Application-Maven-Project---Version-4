package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameBaseTest {
	private String testName = "tester";
	private String testAuthor = "P532-A10";
	private String testGameData = "xml-data";
	private GameBase testerGame;
	
	@Before
	public void setUp() throws Exception {
		testerGame = new GameBase();
		testerGame.setAuthorName(testAuthor);
		testerGame.setGameData(testGameData);
		testerGame.setGameName(testName);
	}

	@After
	public void tearDown() throws Exception {
		testerGame = new GameBase();
	}

	@Test
	public void testGetGameName() {
		testerGame.setGameName("Game 1");
		assertEquals("Get Game Name works", "Game 1", testerGame.getGameName());
	}

	@Test
	public void testGetAuthorName() {
		testerGame.setAuthorName("Author 1");
		assertEquals("Get Author Name works", "Author 1", testerGame.getAuthorName());
	}

	@Test
	public void testGetGameData() {
		testerGame.setGameData("XML Data 1");
		assertEquals("Get Game Data works", "XML Data 1", testerGame.getGameData());
	}

	@Test
	public void testSetGameName() {
		assertEquals("Set Game Name works", testName, testerGame.getGameName());
	}

	@Test
	public void testSetAuthorName() {
		assertEquals("Set Author Name works", testAuthor, testerGame.getAuthorName());
	}

	@Test
	public void testSetGameData() {
		assertEquals("Set Game Data works", testGameData, testerGame.getGameData());
	}

}
