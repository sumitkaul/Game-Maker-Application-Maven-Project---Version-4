package chat;

import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

public class ChatUsersMonitorTest {
	/**
	 * Run the List<String> getActiveUsers() method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetActiveUsers_1()
		throws Exception {
		ChatUsersMonitor fixture = ChatUsersMonitor.getInstance();
		fixture.setChatUsersReceiver(new ChatUsersReceiver());
		fixture.setChatUsersSender(new ChatUsersSender(new ChatUsersReceiver()));

		List<String> result = fixture.getActiveUsers();

		assertNotNull(result);
		assertEquals(0, result.size());
	}

	/**
	 * Run the ChatUsersMonitor getInstance() method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetInstance_1()
		throws Exception {

		ChatUsersMonitor result = ChatUsersMonitor.getInstance();

		assertNotNull(result);
	}

	/**
	 * Run the ChatUsersMonitor getInstance() method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetInstance_2()
		throws Exception {

		ChatUsersMonitor result = ChatUsersMonitor.getInstance();

		assertNotNull(result);
	}

	/**
	 * Run the void setChatUsersReceiver(ChatUsersReceiver) method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSetChatUsersReceiver_1()
		throws Exception {
		ChatUsersMonitor fixture = ChatUsersMonitor.getInstance();
		fixture.setChatUsersReceiver(new ChatUsersReceiver());
		fixture.setChatUsersSender(new ChatUsersSender(new ChatUsersReceiver()));
		ChatUsersReceiver chatUsersReceiver = new ChatUsersReceiver();

		fixture.setChatUsersReceiver(chatUsersReceiver);
	}

	/**
	 * Run the void setChatUsersSender(ChatUsersSender) method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testSetChatUsersSender_1()
		throws Exception {
		ChatUsersMonitor fixture = ChatUsersMonitor.getInstance();
		fixture.setChatUsersReceiver(new ChatUsersReceiver());
		fixture.setChatUsersSender(new ChatUsersSender(new ChatUsersReceiver()));
		ChatUsersSender chatUsersSender = new ChatUsersSender(new ChatUsersReceiver());

		fixture.setChatUsersSender(chatUsersSender);
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
		new org.junit.runner.JUnitCore().run(ChatUsersMonitorTest.class);
	}
}