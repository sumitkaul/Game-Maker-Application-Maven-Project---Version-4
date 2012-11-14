package providers;

import java.util.List;

import org.hibernate.Session;
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
}
