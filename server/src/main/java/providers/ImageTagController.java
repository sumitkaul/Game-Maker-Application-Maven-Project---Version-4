package providers;

import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import db.DatabaseHandler;


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
					.createSQLQuery("SELECT COUNT(*) FROM Resources");
		} else {
			query = session
					.createSQLQuery("SELECT COUNT(*) FROM Resources WHERE resource_name='"
							+ tag + "'");
		}
		session.close();

		return query.list().get(0).toString();
	}
	
	@RequestMapping(value = "/getAllTags", method = RequestMethod.GET)
	@ResponseBody
	public String getAllTagNames(HttpEntity<byte[]> requestEntity) {
		
		  Session session = DatabaseHandler.getDatabaseHandlerInstance().getHibernateSession();
	        Query query;

	        query = session.createSQLQuery("SELECT resource_name FROM Resources");
	        @SuppressWarnings("unchecked")
	        List<String> list = query.list();
	        Gson gson = new Gson();
	        String json = gson.toJson(list);
	        session.close();
		 return json;
	}

}
