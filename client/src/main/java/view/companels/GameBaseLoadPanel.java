package view.companels;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import model.GameProgressSaveInfo;
import view.communication.ClientHandler;

public class GameBaseLoadPanel {

    private JComponent rootComp;
    private final String host = "tintin.cs.indiana.edu:8096";
    private final String path = "/GameMakerServer";
    private final String urlListAllGameBases = "/listAllGameBases";
    private final String urlLoadGameBase = "/loadGameBase";

    public GameBaseLoadPanel(JComponent rootComp) {
        this.rootComp = rootComp;
    }

    public String readGameDataFromRemoteList() {
        Exception[] exceptions = new Exception[1];
        String[] gameNames = ClientHandler.listAllGameBases(host, path + urlListAllGameBases, exceptions);

        if (exceptions[0] != null) {
            JOptionPane.showMessageDialog(rootComp, exceptions[0].toString());
            return null;
        }

        String chosen = (String) JOptionPane.showInputDialog(
                rootComp,
                "Select Game Base from " + host,
                "Load Game Base",
                JOptionPane.PLAIN_MESSAGE,
                null, gameNames,
                null);

        if (chosen == null) {
            return null;
        }

        String gameData = ClientHandler.loadGameBase(chosen, host, path + urlLoadGameBase, exceptions);

        if (exceptions[0] != null) {
            JOptionPane.showMessageDialog(rootComp, exceptions[0].toString());
            return null;
        }

        GameProgressSaveInfo.getInstance().setLoadedGameName(chosen);

        return gameData;
    }
}
