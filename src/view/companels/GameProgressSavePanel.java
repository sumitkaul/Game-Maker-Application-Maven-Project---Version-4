package view.companels;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.GameProgressSaveInfo;
import model.Player;
import utility.Score;
import view.communication.ClientHandler;
import view.communication.protocol.GameSaveInfo;

public class GameProgressSavePanel {

    private JComponent rootComp;
    private final String host = "tintin.cs.indiana.edu:8096";
    private final String path = "/GameMakerServer";
    private final String urlSaveGameProgress = "/saveGameProgress";

    public GameProgressSavePanel(JComponent rootComp) {
        this.rootComp = rootComp;
    }

    public void saveGameToRemoteServer(String gameData) {
        JTextField saveNameField = new JTextField();
        JTextField playerNamelField = new JTextField();

        if(Player.getInstance().getUsername()!=null)
        {
        Object[] message = new Object[]{"Save Name:", saveNameField};

        int r = JOptionPane.showConfirmDialog(rootComp, message, "Save Your Game Progress", JOptionPane.OK_CANCEL_OPTION);
        if (r != JOptionPane.OK_OPTION) {
            return;
        }

        String saveName = saveNameField.getText();
       // String playerName = playerNamelField.getText();
        String playerName=Player.getInstance().getUsername();
        
        //Saving the current score into the GameProgressSaveInfo instance.
        GameProgressSaveInfo.getInstance().setScore(Score.getInstance().getScore());
        GameSaveInfo gameSaveInfo = new GameSaveInfo(saveName, GameProgressSaveInfo.getInstance().getLoadedGameName(), playerName, GameProgressSaveInfo.getInstance().getScore(), 0, gameData);
        Exception[] exceptions = new Exception[1];
        boolean saveOK = ClientHandler.saveGamePlay(gameSaveInfo, host, path + urlSaveGameProgress, exceptions);

        if (exceptions[0] != null) {
            JOptionPane.showMessageDialog(rootComp, exceptions[0].toString());
        }

        if (saveOK) {
            JOptionPane.showMessageDialog(rootComp, "Game saved to " + host + " successfully");
        } else {
            JOptionPane.showMessageDialog(rootComp, "Error, Game is not saved please load game before saving");
        }
       }
        else
        {
        JFrame frame=new JFrame();
        JOptionPane.showMessageDialog(frame,"Please login");
        }
    }
}
