package chat;

import static org.junit.Assert.*;

import javax.jms.MessageProducer;
import javax.jms.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChatUsersSenderTest {

	private MessageProducer producer,producer1,expectedProducer,actualProducer;
	private Session session,session1,expectedSession,actualSession;
	private ChatUsersReceiver initialChatUsersReceiver,receiver1,finalChatUsersReceiver;
	private ChatUsersReceiver receiver;
	
	private ChatUsersSender sender;
	@Before
	public void setUp() throws Exception {
		sender = new ChatUsersSender(receiver);
	}	

	@After
	public void tearDown() throws Exception {
		sender = null;
	}

//	@Test
//	public void testChatUsersSender() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRun() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testGetProducer() {
		sender.setProducer(producer1);
		actualProducer = sender.getProducer();
		expectedProducer = producer1;
		assertEquals(expectedProducer,actualProducer);
	}

	@Test
	public void testGetSession() {
		sender.setSession(session1);
		actualSession = sender.getSession();
		expectedProducer = producer1;
		assertEquals(expectedProducer,actualProducer);
	}

	@Test
	public void testGetChatUsersReceiver() {
		initialChatUsersReceiver = sender.getChatUsersReceiver();
		sender.setChatUsersReceiver(receiver1);
		finalChatUsersReceiver = sender.getChatUsersReceiver();
		assertTrue(finalChatUsersReceiver == initialChatUsersReceiver);
			
		
	}

}
