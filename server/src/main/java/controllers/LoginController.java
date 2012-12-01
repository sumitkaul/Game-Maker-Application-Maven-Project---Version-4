package controllers;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import db.DatabaseHandler;
import db.User;

@Controller
public class LoginController {

	@RequestMapping(value = "/loginUser", method = RequestMethod.GET)
	@ResponseBody
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password) {

		Gson gson = new Gson();
		List<User> loginQueryList = DatabaseHandler.loginQuery(username, password);

		if (loginQueryList.isEmpty()) {
			return gson.toJson(false);

		} else {
			return gson.toJson(true);
		}
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
	@ResponseBody
	public String register(@RequestParam("username") String userName,
			@RequestParam("password") String passWord) {
		Gson gson = new Gson();
		Session session = DatabaseHandler.getDatabaseHandlerInstance()
				.getHibernateSession();
		Transaction t = session.beginTransaction();
		User u = new User(userName, passWord, "");
		session.save(u);
		t.commit();

		session.close();
		gson.toJson(true);
		return gson.toJson(true);

	}
}
