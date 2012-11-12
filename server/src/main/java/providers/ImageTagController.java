package providers;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
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
import db.Resources;


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
	
	@RequestMapping(value = "/listPageResources", method = RequestMethod.GET)
	@ResponseBody
	public String fetchImagesForAPage(HttpEntity<byte[]> requestEntity) {
		
		  Gson gson = new Gson();
		  String json= null	;
		  HttpHeaders requestHeaders= requestEntity.getHeaders();
		  
		  String pageNumbers = requestHeaders.get("page_number").get(0);				  
          String pageLengths = requestHeaders.get("page_length").get(0);
        		  
          String resourceName =requestHeaders.get("resource_name").get(0);  
        		  

          if (pageNumbers != null && pageLengths != null) {
              Session session = DatabaseHandler.getDatabaseHandlerInstance().getHibernateSession();
              int pageNumber = Integer.parseInt(pageNumbers);
              int pageLength = Integer.parseInt(pageLengths);

              Criteria criteria = session.createCriteria(Resources.class);

              if (resourceName != null) {
                  criteria.add(Restrictions.eq("resourceName", resourceName));
              }

              criteria.setFirstResult((pageNumber - 1) * pageLength);
              criteria.setMaxResults(pageLength);

              @SuppressWarnings("unchecked")
              List<Resources> resourcesList = criteria.list();
              json=gson.toJson(resourcesList);
              session.close();
          }
              
		return json;
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
