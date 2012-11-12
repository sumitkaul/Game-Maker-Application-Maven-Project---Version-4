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
}
