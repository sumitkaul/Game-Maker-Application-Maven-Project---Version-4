package utility;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClockDisplayTest {

	ClockDisplay clockDisplay;

	@Before
	public void setUp() {
		clockDisplay = new ClockDisplay();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testUpdateClock() {

	}

	@Test
	public void testReset() {
		clockDisplay.setHour(4);
		clockDisplay.setMinute(24);
		clockDisplay.setSecond(56);
		Assert.assertEquals(clockDisplay.getHour(), 4);
		Assert.assertEquals(clockDisplay.getMinute(), 24);
		Assert.assertEquals(clockDisplay.getSecond(), 56);
		clockDisplay.reset();
		Assert.assertEquals(0, clockDisplay.getHour());
		Assert.assertEquals(0, clockDisplay.getMinute());
		Assert.assertEquals(0, clockDisplay.getSecond());
	}
}