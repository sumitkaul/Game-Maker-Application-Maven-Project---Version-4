package controllers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ImageTagControllerTest {

	ImageTagController itc;
	
	@Before
	public void setUp() throws Exception {
		itc = new ImageTagController();
	}

	@Test
	public void testCountTag() {
		Integer count = Integer.parseInt(itc.countTag(null));
		// Not verifying if it is a specific values as the values change dynamically in the DB whenever a tag is added in the game.
		assert(count>0);
		Integer countBall = Integer.parseInt(itc.countTag("ball"));
		assert(countBall>0);
	}

	@Test
	public void testFetchImagesForAPage() {
		// Yet to be implemented
	}

	@Test
	public void testGetAllTagNames() {
		String tagNames = itc.getAllTagNames();
		assertNotNull(tagNames);
		assert(tagNames.contains("alien") && tagNames.contains("pacman"));
	}

}
