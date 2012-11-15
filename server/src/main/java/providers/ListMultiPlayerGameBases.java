package providers;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import db.DatabaseHandler;

@Controller
public class ListMultiPlayerGameBases {
	
	
		@RequestMapping(value = "/listMultiPlayerGameBases", method = RequestMethod.GET)
		@ResponseBody
		public ResponseEntity<String> listMultiPlayerGameBases(HttpEntity<byte[]> requestEntity) {

			int isMultiplayer=1;

			String sql = "select game_name from GameBase where IsMultiplayer='+isMultiplayer+'";
			@SuppressWarnings("unchecked")
			List<String> names = DatabaseHandler.Query(sql);

			Gson gson = new Gson();
            String json = gson.toJson(names);
            
    		return new ResponseEntity<String>(json, null);

		
}
}
