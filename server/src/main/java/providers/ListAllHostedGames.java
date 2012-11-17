package providers;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import db.DatabaseHandler;

@Controller
public class ListAllHostedGames {
	
	@RequestMapping(value = "/loadHostGames", method = RequestMethod.GET)
	@ResponseBody
	public List<String> loadHostGames(HttpEntity<byte[]> requestEntity) {

		//HttpHeaders requestHeaders = requestEntity.getHeaders();
		//String gameName = requestHeaders.get("game_name").get(0);

		String sql = "select gamebasename from HostedGameBases";
		@SuppressWarnings("unchecked")
		List<String> game = DatabaseHandler.Query(sql);

		if (game.isEmpty()) {
			return null;
		}

		return game;
	}

}