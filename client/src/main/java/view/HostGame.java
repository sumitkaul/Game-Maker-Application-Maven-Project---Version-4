package view;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.GameProgressSaveInfo;

import utility.Constants;
import view.communication.ClientHandler;
import view.communication.protocol.GameHostInfo;

public class HostGame {
	
    private JComponent rootComp;
    private final String host = Constants.HOST;
    private final String path = Constants.PATH;
    private final String urlListMultiPlayerGameBases = "/listMultiPlayerGameBases";
    private final String urlLoadGameBase = "/loadGameBase";
	
	public HostGame (JComponent rootComp){
		
		this.rootComp= rootComp;

	}
	
	
	public String displayHostedGames(){
		   Exception[] exceptions = new Exception[1];
	        String[] gameNames = ClientHandler.listAllMultiPlayerGameBases(host, path + urlListMultiPlayerGameBases, exceptions);

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
