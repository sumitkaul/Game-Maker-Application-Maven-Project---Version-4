package controllers;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import db.DatabaseHandler;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DatabaseHandler.class)
public class ImageTagControllerTest {
	private static final Logger LOG = Logger.getLogger(ImageTagControllerTest.class);

	ImageTagController itc;

	@Before
	public void setUp() throws Exception {
		itc = new ImageTagController();
	}

	@Test
	public void testCountTag() {
		PowerMockito.mockStatic(DatabaseHandler.class);
		
		try {
			List returnedValue = new ArrayList();
			returnedValue.add(5);
			PowerMockito.when(DatabaseHandler.class, DatabaseHandler.listQuery(
					(String) Matchers.any())).thenReturn(returnedValue);
			Integer count = Integer.parseInt(itc.countTag(null));
			
			assert(count==5);
			Integer countBall = Integer.parseInt(itc.countTag("ball"));
			assert(countBall==5);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

	@Test
	public void testFetchImagesForAPage() {
		// Yet to be implemented
	}

	@Test
	public void testGetAllTagNames() {
		String tagNames = itc.getAllTagNames();
		assertNotNull(tagNames);
		assert (tagNames.contains("alien") && tagNames.contains("pacman"));
	}

}
