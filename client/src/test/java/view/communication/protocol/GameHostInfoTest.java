package view.communication.protocol;

import static org.junit.Assert.*;

import org.junit.*;

public class GameHostInfoTest {
	/**
	 * Run the GameHostInfo(String,String,String,int) constructor test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGameHostInfo_1()
		throws Exception {
		String saveName = "";
		String gameName = "";
		String gamePlayer = "";
		int id = 1;

		GameHostInfo result = new GameHostInfo(saveName, gameName, gamePlayer, id);

		assertNotNull(result);
		assertEquals(1, result.getId());
		assertEquals(0, result.getRank());
		assertEquals("", result.getSaveName());
		assertEquals("", result.getGameName());
		assertEquals(0, result.getGameScore());
		assertEquals(null, result.getGameData());
		assertEquals("", result.getGamePlayer());
	}

	/**
	 * Run the GameHostInfo(String,String,String,String) constructor test.
	 *
	 * @throws Exception
	 *
	 *  
	 */
	@Test
	public void testGameHostInfo_2()
		throws Exception {
		String saveName = "";
		String gameName = "";
		String gamePlayer = "";
		String gameData = "";

		GameHostInfo result = new GameHostInfo(saveName, gameName, gamePlayer, gameData);

		assertNotNull(result);
		assertEquals(0, result.getId());
		assertEquals(0, result.getRank());
		assertEquals("", result.getSaveName());
		assertEquals("", result.getGameName());
		assertEquals(0, result.getGameScore());
		assertEquals("", result.getGameData());
		assertEquals("", result.getGamePlayer());
	}

	/**
	 * Run the GameHostInfo(String,String,String,int,int) constructor test.
	 *
	 * @throws Exception
	 *
	 *  
	 */
	@Test
	public void testGameHostInfo_3()
		throws Exception {
		String saveName = "";
		String gameName = "";
		String gamePlayer = "";
		int id = 1;
		int gameScore = 1;

		GameHostInfo result = new GameHostInfo(saveName, gameName, gamePlayer, id, gameScore);

		assertNotNull(result);
		assertEquals(1, result.getId());
		assertEquals(0, result.getRank());
		assertEquals("", result.getSaveName());
		assertEquals("", result.getGameName());
		assertEquals(1, result.getGameScore());
		assertEquals(null, result.getGameData());
		assertEquals("", result.getGamePlayer());
	}

	/**
	 * Run the GameHostInfo(String,String,String,int,int,String) constructor test.
	 *
	 * @throws Exception
	 *
	 *  
	 */
	@Test
	public void testGameHostInfo_4()
		throws Exception {
		String saveName = "";
		String gameName = "";
		String gamePlayer = "";
		int gameScore = 1;
		int rank = 1;
		String gameData = "";

		GameHostInfo result = new GameHostInfo(saveName, gameName, gamePlayer, gameScore, rank, gameData);

		assertNotNull(result);
		assertEquals(0, result.getId());
		assertEquals(1, result.getRank());
		assertEquals("", result.getSaveName());
		assertEquals("", result.getGameName());
		assertEquals(1, result.getGameScore());
		assertEquals("", result.getGameData());
		assertEquals("", result.getGamePlayer());
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 */
	@Before
	public void setUp()
		throws Exception {
		// Add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(GameHostInfoTest.class);
	}
}