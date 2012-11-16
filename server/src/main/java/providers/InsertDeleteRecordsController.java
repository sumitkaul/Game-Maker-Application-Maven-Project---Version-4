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
public class InsertDeleteRecordsController {

	private static Logger log = Logger.getLogger(InsertDeleteRecordsController.class);
	
	@RequestMapping(value = "/deleteHostedGameBaseRecord", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Gson> saveGame(HttpEntity<byte[]> requestEntity) {
		
		HttpHeaders requestHeader = requestEntity.getHeaders();
		
		String gameId = requestHeader.get("id").get(0);

        Gson gson = new Gson();

        if (gameId != null) {

            String sql = "select count(*) FROM HostedGameBases where id=" + gameId;
            String sql1 = "delete from HostedGameBases where id="+gameId;

            List<BigInteger> count = DatabaseHandler.Query(sql);
            log.info(count);

            if (count.get(0).intValue() < 1) {
            	//let the caller know that there was no record with a requested id
            } else {
                DatabaseHandler.ExecuteQuery(sql1);
            }

            log.info("deleted entry id: " + gameId);

            gson.toJson(true);
        } else {
            gson.toJson(false);
        }
        
        return new ResponseEntity<Gson>(gson, null);
	}
	
}
