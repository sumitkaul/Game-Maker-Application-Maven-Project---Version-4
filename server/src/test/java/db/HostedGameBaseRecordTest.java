package db;

import static org.junit.Assert.*;

import org.junit.*;

public class HostedGameBaseRecordTest {
	/**
	 * Run the HostedGameBaseRecord(String,String,String) constructor test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testHostedGameBaseRecord_1()
		throws Exception {
		String hostName = "";
		String gameBaseName = "";
		String saveGameBaseName = "";

		HostedGameBaseRecord result = new HostedGameBaseRecord(hostName, gameBaseName, saveGameBaseName);

		assertNotNull(result);
		assertEquals(0, result.getId());
		assertEquals("", result.getHostName());
		assertEquals("", result.getSaveGameBaseName());
		assertEquals("", result.getGameBaseName());
	}

	/**
	 * Run the String getGameBaseName() method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetGameBaseName_1()
		throws Exception {
		HostedGameBaseRecord fixture = new HostedGameBaseRecord("", "", "");
		fixture.setId(1);

		String result = fixture.getGameBaseName();

		assertEquals("", result);
	}

	/**
	 * Run the String getHostName() method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetHostName_1()
		throws Exception {
		HostedGameBaseRecord fixture = new HostedGameBaseRecord("", "", "");
		fixture.setId(1);

		String result = fixture.getHostName();

		assertEquals("", result);
	}

	/**
	 * Run the int getId() method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetId_1()
		throws Exception {
		HostedGameBaseRecord fixture = new HostedGameBaseRecord("", "", "");
		fixture.setId(1);

		int result = fixture.getId();

		assertEquals(1, result);
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
		// add additional set up code here
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
		new org.junit.runner.JUnitCore().run(HostedGameBaseRecordTest.class);
	}
}