package model;

public class GameBase {

	private String gameName;
	private String authorName;
	private String gameData;
	public String getGameName() {
		return gameName;
	}
	public String getAuthorName() {
		return authorName;
	}
	public String getGameData() {
		return gameData;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public void setGameData(String gameData) {
		this.gameData = gameData;
	}
}
