package chat;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import db.DatabaseHandler;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ChatUsersSender.class)

public class ChatUsersSenderTest {

	private MessageProducer producer1,expectedProducer,actualProducer;
	private Session session1,expectedSession,actualSession;
	private ChatUsersReceiver initialChatUsersReceiver,receiver1,finalChatUsersReceiver;
	private ChatUsersReceiver receiver;
	private ChatUsersSender sender,senderMock;
	@Before
	public void setUp() throws Exception {
		sender = new ChatUsersSender(receiver);
		senderMock = mock(ChatUsersSender.class);
	}	

	@After
	public void tearDown() throws Exception {
		sender = null;
	}

	@Test
	public void testChatUsersSender() {
		//PowerMockito.when(sender,sender.run()).thenReturn();
		
		doAnswer(new Answer<String>() {
		      public String answer(InvocationOnMock invocation) {
		          String[] args = (String[]) invocation.getArguments();
		          //Mock mock = invocation.getMock();
		          return null;
		      }}).when(senderMock).run();
	}

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
		expectedSession = session1;
		assertEquals(expectedSession,actualSession);
	}

	@Test
	public void testGetChatUsersReceiver() {
		initialChatUsersReceiver = sender.getChatUsersReceiver();
		sender.setChatUsersReceiver(receiver1);
		finalChatUsersReceiver = sender.getChatUsersReceiver();
		assertTrue(finalChatUsersReceiver == initialChatUsersReceiver);
			
		
	}

}
