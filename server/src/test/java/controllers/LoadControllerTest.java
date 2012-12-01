package controllers;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.jboss.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;

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
		List<String> gameDataList = new ArrayList<String>();
		String dataItem1 = "assume that this is lots of data!!!";
		gameDataList.add(dataItem1);
		PowerMockito.mockStatic(DatabaseHandler.class);
		try {

			PowerMockito.when(DatabaseHandler.class, DatabaseHandler.listQuery((String) Matchers.any())).thenReturn(
					gameDataList);
			String result = loadController.listAllGameBases();
			String substring = result.substring(2, result.length() - 2);
			Assert.assertTrue(substring.equals(dataItem1));
		} catch (Exception e) {
			Logger.getLogger(LoadControllerTest.class).debug(e.getMessage());
		}
	}

	@Test
	public void testListAllGameBases() {
		final List<String> gameNames = new ArrayList<String>();
		gameNames.add("pacman");
		gameNames.add("asteroids");
		Gson gson = new Gson();
		String json = gson.toJson(gameNames);
		PowerMockito.mockStatic(DatabaseHandler.class);
		try {
			PowerMockito.when(DatabaseHandler.class, DatabaseHandler.listQuery((String) Matchers.any())).thenReturn(
					gameNames);
			String jsonResult = loadController.listAllGameBases();
			if (json.equals(jsonResult))
				Assert.assertTrue(true);
			else
				Assert.assertTrue(false);
		} catch (Exception e) {
			Logger.getLogger(LoadControllerTest.class).debug(e.getMessage());
		}
	}
}