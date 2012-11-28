package controllers;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.junit.Assert;
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
public class LoadControllerTest {

	private LoadController loadController;

	@Before
	public void setUp() {
		loadController = new LoadController();
	}

	@Test
	public void testLoadGameBase() {

		PowerMockito.mockStatic(DatabaseHandler.class);
		List<String> gameDataList = new ArrayList<String>();

		String dataItem1 = "assume that this is lots of data!!!";
		String dataItem2 = "again assume that this is lots of data!!!";

		gameDataList.add(dataItem1);
		gameDataList.add(dataItem2);

		try {

			PowerMockito.when(DatabaseHandler.class, "Query", Matchers.any()).thenReturn(gameDataList);
			String actualResult = loadController.loadGameBase("some random game name which does not matter");
			if (actualResult.equals(dataItem1)) {
				Assert.assertTrue(true);
			} else
				Assert.assertTrue(false);
		} catch (Exception e) {
			Logger.getLogger(LoadControllerTest.class).debug(e.getMessage());
		}
	}
}