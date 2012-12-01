package controllers;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import db.DatabaseHandler;
import db.User;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DatabaseHandler.class)
public class LoginControllerTest {
	private static final Logger LOG = Logger.getLogger(LoginController.class);
	private LoginController loginController;

	@Before
	public void setUp() {
		loginController = new LoginController();
	}

	@Test
	public void testLogin() {
		PowerMockito.mockStatic(DatabaseHandler.class);

		try {
			ArrayList retValue = new ArrayList();
			retValue.add(new User("asdf", "asdf", "1"));
			PowerMockito.when(DatabaseHandler.class,
					DatabaseHandler.loginQuery("asdf", "asdf")).thenReturn(
					retValue);
			String ret = loginController.login("asdf", "asdf");
			assert (ret == "true");

			ArrayList secondRetValue = new ArrayList();
			PowerMockito.when(DatabaseHandler.class,
					DatabaseHandler.loginQuery("salkdf", "sdfsdfsdf"))
					.thenReturn(secondRetValue);
			ret = loginController.login("salkdf", "sdfsdfsdf");
			assert (ret == "false");
			
		} catch (Exception e) {
			LOG.error(e);
		}

	}

}