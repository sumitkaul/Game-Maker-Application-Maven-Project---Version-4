package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import protocol.GameSaveInfo;

import com.google.gson.Gson;

import db.DatabaseHandler;
import db.Resources;

@Controller
public class LoadController {
	@RequestMapping(value = "/loadGameBase", method = RequestMethod.GET)
	@ResponseBody
	public String loadGameBase(HttpEntity<byte[]> requestEntity) {

		HttpHeaders requestHeaders = requestEntity.getHeaders();
		String gameName = requestHeaders.get("game_name").get(0);

		String sql = "select game_data from GameBase where game_name='"
				+ gameName + "'";
		@SuppressWarnings("unchecked")
		List<String> game = DatabaseHandler.Query(sql);

		if (game.isEmpty()) {
			return "";
		}

		return game.get(0);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadGamePlay", method = RequestMethod.GET)
	@ResponseBody
	public String loadGamePlay(HttpEntity<byte[]> requestEntity) {
		HttpHeaders requestHeaders = requestEntity.getHeaders();

		String gameSaveId = requestHeaders.get("game_save_id").get(0);

		if (gameSaveId.isEmpty()) {
			return " ";
		}

		String sql = "select game_data from InProgressGame where save_id="
				+ gameSaveId;
		List<String> gameData = DatabaseHandler.Query(sql);

		if (gameData.isEmpty()) {
			return " ";
		}

		return gameData.get(0);
	}

	@RequestMapping(value = "/loadResource", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Gson> loadResource(HttpEntity<byte[]> requestEntity) {

		HttpHeaders requestHeaders = requestEntity.getHeaders();
		String id = requestHeaders.get("resource_id").get(0);

		Integer rid = Integer.parseInt(id);

		Session session = DatabaseHandler.getDatabaseHandlerInstance()
				.getHibernateSession();

		Resources r = (Resources) session.get(Resources.class, rid);

		session.close();

		Gson gson = new Gson();
		gson.toJson(r);

		return new ResponseEntity<Gson>(gson, null);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listAllGameBases", method = RequestMethod.GET)
	@ResponseBody	
	public String  listAllGameBases(HttpEntity<byte[]> requestEntity){
        
            String sql = "select game_name from GameBase";
            List<String> gameNames = DatabaseHandler.Query(sql);
            Gson gson = new Gson();
            String json = gson.toJson(gameNames);            
            return json;
        

    }

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listTopScores", method = RequestMethod.GET)
	@ResponseBody		
	 public String  listTopScores(HttpEntity<byte[]> requestEntity){
	       		
		 		HttpHeaders requestHeaders= requestEntity.getHeaders();
	            String gameName = requestHeaders.get("game_name").get(0);
	            		

	            if (gameName.isEmpty()) {
	                return " ";
	            }

	            String sql = "select game_name, player_name, score from InProgressGame where game_name='" + gameName + "' order by score desc";

	            List<GameSaveInfo> saves = new LinkedList<GameSaveInfo>();

	            List<Object[]> r = DatabaseHandler.Query(sql);

	            int rank = 1;
	            for (Object[] record : r) {
	                GameSaveInfo save = new GameSaveInfo(null, (String) record[0], (String) record[1], (Integer) record[2], rank);
	                saves.add(save);
	                rank++;
	            }

	            Gson gson = new Gson();
	            String json = gson.toJson(saves);

	            return json;

	        }
	
	@RequestMapping(value = "/listAllGamePlays", method = RequestMethod.GET)
	@ResponseBody	
	 public String listAllGamePlays(HttpEntity<byte[]> requestEntity)
	 {
	 		HttpHeaders requestHeaders= requestEntity.getHeaders();
	            String playerName = requestHeaders.get("player_name").get(0);
	            
	            		

	            if (playerName.isEmpty()) {
	                return " ";
	            }

	            String sql = "select save_name, game_name, player_name, save_id, score from InProgressGame where player_name='" + playerName + "'";

	            List<GameSaveInfo> saves = new LinkedList<GameSaveInfo>();

	            @SuppressWarnings("unchecked")
				List<Object[]> r = DatabaseHandler.Query(sql);

	            for (Object[] record : r) {
	                GameSaveInfo save = new GameSaveInfo((String) record[0], (String) record[1], (String) record[2], (Integer) record[3]);
	                save.setGameScore((Integer) record[4]);                
	                saves.add(save);
	            }

	            Gson gson = new Gson();
	            String json = gson.toJson(saves);

	 
	            return json;
	        }
	
	
		@RequestMapping(value = "/listMultiPlayerGameBases", method = RequestMethod.GET)
		@ResponseBody
		public String listMultiPlayerGameBases(HttpEntity<byte[]> requestEntity) {

				int isMultiplayer=1;

				//String sql = "select game_name from GameBase where IsMultiplayer="+isMultiplayer;
				String sql = "select game_name from GameBase";
				@SuppressWarnings("unchecked")
				List<String> names = DatabaseHandler.Query(sql);

				Gson gson = new Gson();
	            String json = gson.toJson(names);
	            
	    		return json;

			
	}


}
