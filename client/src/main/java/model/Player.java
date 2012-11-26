package model;

public class Player {

    private String username, password, usertype;
    private static Player player;

    public static Player getInstance() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public static Player setInstanceNull() {
        player = null;
        return player;
    }
}
