package controllers;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;
import db.DatabaseHandler;
import db.Resources;



@Controller
public class ImageTagController {
	
	
	@RequestMapping(value = "/countTag", method = RequestMethod.GET)
	@ResponseBody	
	public String countTag(@RequestParam(value="tag", required=false) String tag){

		Gson gson = new Gson();
		Session session = DatabaseHandler.getDatabaseHandlerInstance()
				.getHibernateSession();
		Query query;
		
		System.out.println("***********************************servicing countTag request******************************************************");
		
		if (tag == null) {
			query = session
					.createSQLQuery("SELECT COUNT(*) FROM Resources");
		} else {
			query = session
					.createSQLQuery("SELECT COUNT(*) FROM Resources WHERE resource_name='"
							+ tag + "'");
		}
		String count=gson.toJson(query.list().get(0));
		session.close();
		
		
		
		return count;
	}
	
	@RequestMapping(value = "/listPageResources", method = RequestMethod.GET)
	@ResponseBody	
	public String fetchImagesForAPage(@RequestParam("page_number") String pageNumbers,@RequestParam("page_length") String pageLengths , @RequestParam(value="resource_name", required=false) String resourceName){
		
		  Gson gson = new Gson();
		  String json= null	;
		  List<Resources> r=null;
		  
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
            r=criteria.list();
            json=gson.toJson(resourcesList);
            session.close();
        }
            
        
		return gson.toJson(r);
	}
	
	@RequestMapping(value = "/getAllTags", method = RequestMethod.GET)
	@ResponseBody	
	public String getAllTagNames() {
		
		  Session session = DatabaseHandler.getDatabaseHandlerInstance().getHibernateSession();
	        Query query;

	        query = session.createSQLQuery("SELECT resource_name FROM Resources");
	        @SuppressWarnings("unchecked")
	        List<String> list = query.list();
	        Gson gson = new Gson();
	        session.close();
	        
		 return gson.toJson(list);
	}
	
	
}
