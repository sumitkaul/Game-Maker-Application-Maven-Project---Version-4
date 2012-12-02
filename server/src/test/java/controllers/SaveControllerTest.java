package controllers;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import db.DatabaseHandler;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Session.class, DatabaseHandler.class })
public class SaveControllerTest {

	SaveController controller;

	@Before
	public void setUp() {
//		controller = new SaveController();
	}

	@After
	public void tearDown() {
//		controller = null;
	}

	@Test
	public void testSaveResource() {

//		Session session = PowerMockito.mock(Session.class);
//		Resources resources = new Resources();
//		resources.setResource((new String("<data> Data lots of it </data>").getBytes()));
//		resources.setResourceName("test image");
//		resources.setResourceNumber(1);
//		resources.setUsername("test user");
//		resources.setResourceType("image");
//
//		Gson gson = new Gson();
//		String jsonContent = gson.toJson(resources);
//		try {
//			PowerMockito.when(session, "save", (Resources) Matchers.any()).thenReturn(resources);
//			PowerMockito.stub(session.getClass().getMethod("save", Object.class));
//			String retResult = "false";
//			Assert.assertEquals(controller.saveResource(jsonContent), retResult);
//		} catch (Exception e) {
//			Logger.getLogger(SaveControllerTest.class).debug(e.getMessage());
//		}
	}
}
