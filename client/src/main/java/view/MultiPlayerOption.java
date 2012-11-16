package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import multiplayer.Sender;
import multiplayer.Test;
import net.miginfocom.swing.MigLayout;


public class MultiPlayerOption{

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MultiPlayerOption.class);
	private JComponent rootComp;
	private JFrame options;
	private JButton hostButton;
	private JButton joinButton;
	private JLabel optionLabel;
	
	
	public MultiPlayerOption(JComponent rootComp) {
        this.rootComp = rootComp;
    }
	
	public void selectOption(){
		LOG.info("i m in multiplayer class");
		options = new JFrame();
		hostButton = new JButton("Host");
		joinButton = new JButton("Join");
		optionLabel = new JLabel("Would you like to:");
		
		options.setLayout(new MigLayout("center,center"));
		options.add(optionLabel,"wrap,wmin 100, hmin 50");
		options.add(hostButton,"wmin 50, hmin 50");
		options.add(joinButton,"wmin 50, hmin 50");
		
		hostButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				

				String text="Hello world";
				String topic="TEST2";
				
				Test test=new Test();
				
				//   ByteArrayOutputStream b = new ByteArrayOutputStream();
			     //   ObjectOutputStream o;
				//	try {
				//		o = new ObjectOutputStream(b);
				//	    o.writeObject(test);
				//	} catch (IOException e1) {
				//		// TODO Auto-generated catch block
				//		e1.printStackTrace();
				//	}
			    
			        
				
				
				
				Sender sender=new Sender();
				sender.sendAsHost(test,topic);
				
				
				
				

				HostGame p = new HostGame(Design.getInstance().getGamePanel());
				p.displayHostedGames();

			}
				
		});
		
		joinButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JoinGame p = new JoinGame(Design.getInstance().getGamePanel());
				p.displayJoinGames();
			}
				
		});
		
		options.setSize(200, 200);
		options.setLocationRelativeTo(rootComp);
		options.setVisible(true);
		
		}

	
}
