package view.communication;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import model.Resources;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import view.communication.protocol.GameSaveInfo;
import view.utils.HttpUtil;

public class ClientHandler {

	private final static Logger log = Logger.getLogger(ClientHandler.class);

	public static String[] listAllGameBases(String host, String path,
			Exception[] exception) {
		try {
			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path);
			URI uri = ub.build();

			String jsonGameList = HttpUtil.httpGet(uri);

			Gson gson = new Gson();

			String[] gameNames = gson.fromJson(jsonGameList, String[].class);

			return gameNames;
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return null;
		}

	}
	
	public static String[] listAllMultiPlayerGameBases(String host, String path,
			Exception[] exception) {
		try {
			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path);
			URI uri = ub.build();

			String jsonGameList = HttpUtil.httpGet(uri);

			Gson gson = new Gson();

			String[] gameNames = gson.fromJson(jsonGameList, String[].class);

			return gameNames;
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return null;
		}

	}

	public static String loadGameBase(String gameName, String host,
			String path, Exception[] exception) {
		try {
			if (gameName.isEmpty()) {
				exception[0] = new Exception("Required information is empty");
				return null;
			}

			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path)
					.setParameter("game_name", gameName);
			URI uri = ub.build();

			String gameDataXml = HttpUtil.httpGet(uri);

			return gameDataXml;

		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return null;
		}
	}

	public static boolean saveGameBase(String gameName, String gameAuthor,
			String gameData,Boolean isMultiPlayer, String host, String path, Exception[] exception) {
		try {
			if (gameName.isEmpty() || gameAuthor.isEmpty()
					|| gameData.isEmpty()) {
				exception[0] = new Exception("Required information is empty");
				return false;
			}
			String isMultiPlayerString = isMultiPlayer.toString();
			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path);
			URI uri = ub.build();

			List<NameValuePair> nvps = new ArrayList<NameValuePair>(3);
			nvps.add(new BasicNameValuePair("game_name", gameName));
			nvps.add(new BasicNameValuePair("game_author", gameAuthor));
			nvps.add(new BasicNameValuePair("game_data", gameData));
			nvps.add(new BasicNameValuePair("isMultiplayer", isMultiPlayerString));

			String json = HttpUtil.httpPost(uri, nvps);

			Gson gson = new Gson();
			Boolean saveOK = gson.fromJson(json, Boolean.class);
			return saveOK.booleanValue();

		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return false;
		}
	}

	public static GameSaveInfo[] listAllGamePlays(String playerName,
			String host, String path, Exception[] exception) {
		try {
			if (playerName.isEmpty()) {
				exception[0] = new Exception("Required information is empty");
				return null;
			}

			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path)
					.setParameter("player_name", playerName);
			URI uri = ub.build();

			String json = HttpUtil.httpGet(uri);

			Gson gson = new Gson();
			GameSaveInfo[] gameSaves = gson
					.fromJson(json, GameSaveInfo[].class);

			return gameSaves;
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return null;
		}
	}
	

	public static String loadGamePlay(int gameSaveId, String host, String path,
			Exception[] exception) {
		try {
			URIBuilder ub = new URIBuilder();
			ub.setScheme("http")
					.setHost(host)
					.setPath(path)
					.setParameter("game_save_id",
							new Integer(gameSaveId).toString());
			URI uri = ub.build();

			String gameDataXml = HttpUtil.httpGet(uri);

			return gameDataXml;

		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return null;
		}
	}

	public static boolean saveGamePlay(GameSaveInfo gameSave, String host,
			String path, Exception[] exception) {
		try {
			if (gameSave.getSaveName().isEmpty()
					|| gameSave.getGameName().isEmpty()
					|| gameSave.getGamePlayer().isEmpty()
					|| gameSave.getGameData().isEmpty()) {
				exception[0] = new Exception("Required information is empty");
				return false;
			}

			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path);
			URI uri = ub.build();

			List<NameValuePair> nvps = new ArrayList<NameValuePair>(4);
			nvps.add(new BasicNameValuePair("game_save_name", gameSave
					.getSaveName()));
			nvps.add(new BasicNameValuePair("game_name", gameSave.getGameName()));
			nvps.add(new BasicNameValuePair("game_player_name", gameSave
					.getGamePlayer()));
			nvps.add(new BasicNameValuePair("game_score", new Integer(gameSave
					.getGameScore()).toString()));
			nvps.add(new BasicNameValuePair("game_data", gameSave.getGameData()));

			String json = HttpUtil.httpPost(uri, nvps);

			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new StringReader(json));
			reader.setLenient(true);

			Boolean saveOK = gson.fromJson(json, Boolean.class);
			reader.close();
			return saveOK.booleanValue();

		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return false;
		}
	}

	public static GameSaveInfo[] listTopScores(String gameName, String host,
			String path, Exception[] exception) {
		try {
			if (gameName.isEmpty()) {
				exception[0] = new Exception("Required information is empty");
				return null;
			}

			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path)
					.setParameter("game_name", gameName);
			URI uri = ub.build();

			String json = HttpUtil.httpGet(uri);

			Gson gson = new Gson();
			GameSaveInfo[] gameSaves = gson
					.fromJson(json, GameSaveInfo[].class);

			return gameSaves;
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return null;
		}
	}

	public static boolean userLogin(String username, String password,
			String host, String path, Exception[] exception) {
		try {
			if (username.isEmpty()) {
				exception[0] = new Exception("Required information is empty");
				return false;
			}

			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path)
					.setParameter("username", username)
					.setParameter("password", password);
			URI uri = ub.build();

			String json = HttpUtil.httpGet(uri);

			Gson gson = new Gson();
			Boolean loginOK = gson.fromJson(json, Boolean.class);

			return loginOK.booleanValue();
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return false;
		}
	}

	public static boolean userRegister(String username, String password,
			String host, String path, Exception[] exception) {
		try {
			if (username.isEmpty()) {
				exception[0] = new Exception("Required information is empty");
				return false;
			}

			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path)
					.setParameter("username", username)
					.setParameter("password", password);
			URI uri = ub.build();

			String json = HttpUtil.httpGet(uri);

			Gson gson = new Gson();
			Boolean loginOK = gson.fromJson(json, Boolean.class);

			return loginOK.booleanValue();
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return false;
		}
	}

	public static Resources[] listPageResources(String pageNumber,
			String pageLength, String resourceName, String host, String path,
			Exception[] exception) {
		try {
			if (pageNumber.isEmpty() || pageLength.isEmpty()) {
				exception[0] = new Exception("Required information is empty");
				return null;
			}

			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path)
					.setParameter("page_number", pageNumber)
					.setParameter("page_length", pageLength);
			if (resourceName != null) {
				ub.setParameter("resource_name", resourceName);
			}
			URI uri = ub.build();

			String json = HttpUtil.httpGet(uri);

			Gson gson = new Gson();
			Resources[] resources = gson.fromJson(json, Resources[].class);

			return resources;
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return null;
		}
	}

	public static boolean saveResource(Resources resource, String host,
			String path, Exception[] exception) {
		try {
			if (resource == null) {
				exception[0] = new Exception("Required information is empty");
				return false;
			}

			Gson gson = new Gson();
			String r = gson.toJson(resource);

			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path);
			URI uri = ub.build();

			List<NameValuePair> nvps = new ArrayList<NameValuePair>(1);
			nvps.add(new BasicNameValuePair("resource", r));

			String json = HttpUtil.httpPost(uri, nvps);
			Boolean saveOK = gson.fromJson(json, Boolean.class);

			return saveOK.booleanValue();
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return false;
		}
	}

	public static Resources loadResource(String resourceId, String host,
			String path, Exception[] exception) {
		try {
			if (resourceId.isEmpty()) {
				exception[0] = new Exception("Required information is empty");
				return null;
			}

			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path)
					.setParameter("resource_id", resourceId);
			URI uri = ub.build();

			String json = HttpUtil.httpGet(uri);

			Gson gson = new Gson();
			Resources resources = gson.fromJson(json, Resources.class);

			return resources;
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return null;
		}
	}

	public static int countTag(String resourceName, String host, String path,
			Exception[] exception) {
		try {
			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path);
			if (resourceName != null) {
				ub.setParameter("tag", resourceName);
			}
			URI uri = ub.build();
			String data = HttpUtil.httpGet(uri);
			int count = Integer.parseInt(data);
			return count;
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return 0;
		}
	}

	public static String[] listTags(String host, String path,
			Exception[] exception) {
		try {
			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path);
			URI uri = ub.build();
			String json = HttpUtil.httpGet(uri);
			Gson gson = new Gson();
			String[] tags = gson.fromJson(json, String[].class);
			return tags;
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return null;
		}
	}
	

	private static boolean deleteHostedGameBase(int gameId, String host, String path, Exception[] exception) {
		try {
			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path).setParameter("id",new Integer(gameId).toString());
			URI uri = ub.build();

			//should be using deleteHostedGameBaseRecord
			String response = HttpUtil.httpGet(uri);

			Gson gson = new Gson();
			Boolean deleteOK = gson.fromJson(response, Boolean.class);

			return deleteOK.booleanValue();

		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return false;
		}
	}
	
	public static boolean deleteHostedGameBase(String hostName, String gameBaseName, 
			String saveGameBaseName, String host, String path, Exception[] exception) {
		//TODO: figure out an id based on hostname, gamebasename, savegamebasename
		int id = 2;
		return ClientHandler.deleteHostedGameBase(id,host,path,exception);
		
	}
	
	public static String[] loadHostGames(String host, String path,
			Exception[] exception) {
		try {
			URIBuilder ub = new URIBuilder();
			ub.setScheme("http").setHost(host).setPath(path);
			URI uri = ub.build();

			String jsonGameList = HttpUtil.httpGet(uri);

			Gson gson = new Gson();

			String[] gameNames = gson.fromJson(jsonGameList, String[].class);

			return gameNames;
		} catch (Exception ex) {
			log.error(ex);
			exception[0] = ex;
			return null;
		}

	}


	private ClientHandler() {
	}
}
