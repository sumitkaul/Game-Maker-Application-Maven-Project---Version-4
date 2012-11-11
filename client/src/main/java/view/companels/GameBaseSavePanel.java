package view.companels;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Player;
import view.communication.ClientHandler;

public class GameBaseSavePanel {

    private JComponent rootComp;
    private final String host = "tintin.cs.indiana.edu:8096";
    private final String path = "/GameMakerServer";
    private final String urlSaveGameBase = "/saveGameBase";

    public GameBaseSavePanel(JComponent rootComp) {
        this.rootComp = rootComp;
    }

    public void saveGameToRemoteServer(String gameData) {
        JTextField gameNameField = new JTextField();
        JTextField authorNamelField = new JTextField();
        if(Player.getInstance().getUsername()!=null)
        {
        Object[] message = new Object[]{"Game Base Name:", gameNameField};

        int r = JOptionPane.showConfirmDialog(rootComp, message, "Save Your Game Base", JOptionPane.OK_CANCEL_OPTION);
        if (r != JOptionPane.OK_OPTION) {
            return;
        }

        String gameName = gameNameField.getText();	
        String authorName = Player.getInstance().getUsername();

        Exception[] exceptions = new Exception[1];
        boolean saveOK = ClientHandler.saveGameBase(gameName, authorName, gameData, host, path + urlSaveGameBase, exceptions);

        if (exceptions[0] != null) {
            JOptionPane.showMessageDialog(rootComp, exceptions[0].toString());
        }

        if (saveOK) {
            JOptionPane.showMessageDialog(rootComp, "Game saved to " + host + " successfully");
        } else {
            JOptionPane.showMessageDialog(rootComp, "Error, Game is not saved");
        }
        }
        else
        {
        JFrame frame=new JFrame();
        JOptionPane.showMessageDialog(frame,"Please login");
        }
    }
}