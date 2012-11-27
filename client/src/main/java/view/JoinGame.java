package view;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import loader.GameDataPackageIO;
import model.GameProgressSaveInfo;
import multiplayer.Receiver;
import utility.Constants;
import view.communication.ClientHandler;

public class JoinGame {
	
    private JComponent rootComp;
    private final String host = Constants.HOST;
    private final String path = Constants.PATH;
    private final String urlListAllHostGames = "/listAllHostGames";
    private final String urlloadHostGames = "/loadHostGames";
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(JoinGame.class);

	public JoinGame (JComponent rootComp){
		
		this.rootComp= rootComp;

	}
	
	
	public String displayJoinGames(){
		
		Exception[] exceptions = new Exception[1];
		String[] gameNames = ClientHandler.loadHostGames(host, path + urlListAllHostGames, exceptions);
		LOG.info("The gamenames are "+gameNames);
		String chosen = (String) JOptionPane.showInputDialog(
                rootComp,
                "List of join games :",
                "Join games",
                JOptionPane.PLAIN_MESSAGE,
                null, gameNames,
                null);
		
		if (chosen == null) {
            return null;
        }

        String gameData = ClientHandler.loadGameBase(chosen, host, path + urlloadHostGames, exceptions);
        
        if (exceptions[0] != null) {
            JOptionPane.showMessageDialog(rootComp, exceptions[0].toString());
            return null;
        }

        GameProgressSaveInfo.getInstance().setLoadedGameName(chosen);
        GameDataPackageIO.loadGamePackageFromFile(gameData);
        return chosen;
		
	}
	
}
