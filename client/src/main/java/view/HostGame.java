package view;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.GameProgressSaveInfo;

import view.communication.ClientHandler;
import view.communication.protocol.GameHostInfo;

public class HostGame {
	
    private JComponent rootComp;
    private final String host = "tintin.cs.indiana.edu:8096";
    private final String path = "/GameMakerServer";
    private final String urlListAllGameBases = "/listAllGameBases";
    private final String urlLoadGameBase = "/loadGameBase";
	
	public HostGame (JComponent rootComp){
		
		this.rootComp= rootComp;

	}
	
	
	public void displayHostedGames(){
		
		Exception[] exceptions = new Exception[1];
		String[] gameNames={"aaaa","bbbbb"};
		
		String chosen = (String) JOptionPane.showInputDialog(
                rootComp,
                "List of hosted games :",
                "Hosted games",
                JOptionPane.PLAIN_MESSAGE,
                null, gameNames,
                null);
		
		

	}
	
}
