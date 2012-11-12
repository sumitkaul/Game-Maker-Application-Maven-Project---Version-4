package view;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;


public class MultiPlayerOption {

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
		

		
		options.setSize(200, 200);
		options.setLocationRelativeTo(rootComp);
		options.setVisible(true);
		
		
	}
}
