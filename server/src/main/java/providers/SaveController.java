package providers;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;
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

	private static Logger log = Logger.getLogger(SaveController.class);
	
	@RequestMapping(value = "/saveResource", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Gson> saveResourc(HttpEntity<byte[]> requestEntity) {
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
	
	@RequestMapping(value = "/saveGameBase", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Gson> saveGame(HttpEntity<byte[]> requestEntity) {
		
		HttpHeaders requestHeader = requestEntity.getHeaders();
		
		String game_name = requestHeader.get("game_name").get(0);
        String game_data = requestHeader.get("game_data").get(0);
        String game_author = requestHeader.get("game_author").get(0);

        Gson gson = new Gson();

        if (game_name != null && game_data != null && game_author != null) {

            String sql = "select count(*) FROM GameBase where game_name='" + game_name + "'";
            String sql2 = "insert into GameBase (game_name, game_author, game_data) VALUES ('" + game_name + "', '" + game_author + "', '" + game_data + "')";
            String sql3 = "update GameBase set game_author = '" + game_author + "', game_data = '" + game_data + "' where game_name='" + game_name + "'";

            List<BigInteger> count = DatabaseHandler.Query(sql);
            log.info(count);

            if (count.get(0).intValue() < 1) {
                DatabaseHandler.ExecuteQuery(sql2);
            } else {
                DatabaseHandler.ExecuteQuery(sql3);
            }

            log.info("added game: " + game_name);

            gson.toJson(true);
        } else {
            gson.toJson(false);
        }
        
        return new ResponseEntity<Gson>(gson, null);
	}
	
	@RequestMapping(value = "/saveGameProgress", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Gson> saveProgressGame(HttpEntity<byte[]> requestEntity) {
		
		HttpHeaders requestHeader = requestEntity.getHeaders();
		
		String game_name = requestHeader.get("game_name").get(0);
        String save_name = requestHeader.get("game_save_name").get(0);
        String game_data = requestHeader.get("game_data").get(0);
        String player_name = requestHeader.get("game_player_name").get(0);
        int game_score = Integer.parseInt(requestHeader.get("game_score").get(0));

        Gson gson = new Gson();

        if (save_name != null && game_data != null && player_name != null && game_name != null) {
            String sql1 = "select count(*) FROM InProgressGame where game_name='" + game_name + "'and save_name='" + save_name + "' and player_name='" + player_name + "'";
            String sql2 = "insert into InProgressGame (game_name, player_name, score, game_data, save_name) VALUES ('" + game_name + "', '" + player_name + "'," + game_score + ",'" + game_data + "','" + save_name + "')";
            String sql3 = "update InProgressGame set game_data = '" + game_data + "',score = " + game_score + " where game_name='" + game_name + "' and save_name='" + save_name + "' and player_name='" + player_name + "'";

            List<BigInteger> count = DatabaseHandler.Query(sql1);


            if (count.get(0).intValue() < 1) {
                DatabaseHandler.ExecuteQuery(sql2);
            } else {
                DatabaseHandler.ExecuteQuery(sql3);
            }


           gson.toJson(true);
        } else {
            gson.toJson(false);
        }
        return new ResponseEntity<Gson>(gson, null);
	}
}
