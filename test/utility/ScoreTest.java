package utility;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ScoreTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIncreament() {
		Score.getInstance().increament();
		if(Score.getInstance().getScore()==1)
			assertTrue(true);
		else
			assertTrue(false);
	}

	@Test
	public void testDecrease() {
		Score.getInstance().decrease();
		if(Score.getInstance().getScore()==0)
			assertTrue(true);
		else
			assertTrue(false);
	}

	@Test
	public void testModifyBy() {
		Score.getInstance().modifyBy(1);
		if(Score.getInstance().getScore()==1)
			assertTrue(true);
		else
			assertTrue(false);
	}

	@Test
	public void testResetScore() {
		Score.getInstance().resetScore();
		if(Score.getInstance().getScore()==0)
			assertTrue(true);
		else
			assertTrue(false);
	}

}
