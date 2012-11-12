package providers;

import org.hibernate.Session;
import org.hibernate.Transaction;
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
public class SaveController {

	@RequestMapping(value = "/saveResource", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Gson> saveGame(HttpEntity<byte[]> requestEntity) {
		Gson gson = new Gson();
        try {
            //log.info("request /saveResource from: " + request.getRemoteHost() + " " + request.getRemoteAddr());
        	
        	HttpHeaders requestHeader = requestEntity.getHeaders();
            String json =requestHeader.get("resource").get(0);

            //json = "{'name':'test2','type':'test','data':[1,1,2,2],'username':'han'}";

            Resources resource = gson.fromJson(json, Resources.class);

            Session session = DatabaseHandler.getDatabaseHandlerInstance().getHibernateSession();
            Transaction t = session.beginTransaction();
            session.save(resource);
            t.commit();

            session.close();

            gson.toJson(true);

        } catch (Exception e) {
            gson.toJson(false);
        } 
		
		return new ResponseEntity<Gson>(gson, null);
	}
}
