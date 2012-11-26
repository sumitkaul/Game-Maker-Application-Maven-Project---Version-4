package view.communication;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
import org.mockito.Matchers;
import com.google.gson.Gson;
 


import view.utils.HttpUtil;

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
		
		   //URIBuilder ub = PowerMockito.mock(URIBuilder.class);  -- For Ref 
		  
		  try {
			//PowerMockito.whenNew(URIBuilder.class).withNoArguments().thenReturn(ub);			
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  
		 // PowerMockito.  expect(HttpUtil.httpGet(ub.build())
				
	}

	

}
