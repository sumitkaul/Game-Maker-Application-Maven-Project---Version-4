//package utility;
//
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//public class ScoreTest {
//	
//	Score score = Score.getInstance();
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@Test
//	public void testIncreament() {
//		score.increament();
//		if(score.getScore()==1)
//			assertTrue(true);
//		else
//			assertTrue(false);
//	}
//
//	@Test
//	public void testDecrease() {
//		score.decrease();
//		if(score.getScore()==0)
//			assertTrue(true);
//		else
//			assertTrue(false);
//	}
//
//	@Test
//	public void testModifyBy() {
//		score.modifyBy(1);
//		if(score.getScore()==1)
//			assertTrue(true);
//		else
//			assertTrue(false);
//	}
//
//	@Test
//	public void testResetScore() {
//		score.resetScore();
//		if(score.getScore()==0)
//			assertTrue(true);
//		else
//			assertTrue(false);
//	}
//
//}
