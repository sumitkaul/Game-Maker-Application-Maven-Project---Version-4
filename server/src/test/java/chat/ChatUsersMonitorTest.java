package chat;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ChatUsersMonitorTest {

	ChatUsersMonitor monitor;
	@Before
	public void setUp() throws Exception {
		 monitor = ChatUsersMonitor.getInstance();
	}

	@Test
	public void testGetInstance() {
		assertNotNull(monitor);
	}

	@Test
	public void testGetActiveUsers() {
		assertEquals(0, monitor.getActiveUsers().size());
	}

	@Test
	public void testGetChatUsersSender() {
		assertNotNull(monitor.getChatUsersSender());
	}

	@Test
	public void testGetChatUsersReceiver() {
		assertNotNull(monitor.getChatUsersReceiver());
	}

}
