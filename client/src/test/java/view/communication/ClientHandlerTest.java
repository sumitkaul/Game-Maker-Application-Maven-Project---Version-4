package view.communication;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import view.utils.HttpUtil;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

@RunWith( PowerMockRunner.class )
@PrepareForTest( HttpUtil.class )

public class ClientHandlerTest {
	private final static Logger log = Logger.getLogger(ClientHandlerTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListAllGameBases() {
		PowerMockito.mockStatic(HttpUtil.class);

		try {
			String[] values = {"Game1", "Game2","Game3", "Game4"};
			Gson g = new Gson();
			String gameListJson = g.toJson(values);				
			PowerMockito.when(HttpUtil.class, HttpUtil.httpGet((URI)(Matchers.any()))).thenReturn(gameListJson);

			String[] value = ClientHandler.listAllGameBases("testserver:1000", "/listGames", new Exception[1]);
			log.debug("first game name="+value[0]);
			assertEquals(value[0], "Game1");
			assertEquals(value[1], "Game2");
			assertEquals(value[2], "Game3");

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/* mocking the httpget method from the HttpUtil class */
	@Test
	public void testLoadGameBase() {

		PowerMockito.mockStatic(HttpUtil.class);

		try {

			String gameData = "This is the game data being mocked";
			XStream xStream = new XStream();
			String gameDataXML = xStream.toXML(gameData);
			PowerMockito.when(HttpUtil.class, HttpUtil.httpGet((URI) (Matchers.any()))).thenReturn(gameDataXML);

			String actualResult = ClientHandler.loadGameBase("testgame", "testhost:8080", "/loadGameBase",
					new Exception[1]);
			assertEquals(gameDataXML, actualResult);
		} catch (Exception e) {
			log.debug("Test failed");
		}
	}
}