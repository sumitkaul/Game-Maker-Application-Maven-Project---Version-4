package multiplayer;

import static org.junit.Assert.*;
import javax.jms.JMSException;
import org.junit.*;

public class SubscribeTest {

	@Test
	public void testQueueName(){
		Subscribe subs = Subscribe.getInstanceOf();
		String nameValue="data";
		try {
			subs.setQueue(nameValue);
			String name = subs.getName();
			assertEquals(nameValue, name);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}
}
