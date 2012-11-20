package model;

public class SaveGame {
	
	private String username;
	private String gamename;
	private byte[] game;
	public String getUsername() {
		return username;
	}
	public String getGamename() {
		return gamename;
	}
	public byte[] getGame() {
		return game;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	public void setGame(byte[] game) {
		this.game = game;
	}
	
 
}
