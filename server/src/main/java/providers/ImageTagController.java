package providers;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import db.DatabaseHandler;
import db.User;

@Controller
public class ImageTagController {
	@RequestMapping(value = "/countTag", method = RequestMethod.GET)
	@ResponseBody
	public String countTag(HttpEntity<byte[]> requestEntity) {
		Session session = DatabaseHandler.getDatabaseHandlerInstance()
				.getHibernateSession();
		Query query;
		HttpHeaders requestHeader = requestEntity.getHeaders();
		String tag = requestHeader.get("tag").get(0);

		if (tag == null) {
			query = session
					.createSQLQuery("SELECT COUNT(*) FROM a9team3.Resources");
		} else {
			query = session
					.createSQLQuery("SELECT COUNT(*) FROM a9team3.Resources WHERE resource_name='"
							+ tag + "'");
		}
		session.close();

		return query.list().get(0).toString();
	}

}
