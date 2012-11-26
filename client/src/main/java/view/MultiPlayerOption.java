package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.jms.JMSException;

import javax.management.j2ee.statistics.SessionBeanStats;

import javax.jms.Session;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import utility.Constants;
import view.communication.ClientHandler;

import model.Player;
import multiplayer.Receiver;
import multiplayer.Sender;
import multiplayer.SessionFactory;
import net.miginfocom.swing.MigLayout;


public class MultiPlayerOption{

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MultiPlayerOption.class);
	private JComponent rootComp;
	private JFrame options;
	private JButton hostButton;
	private JButton joinButton;
	private JLabel optionLabel;
	private String sendingQueueName;
	private String receivingQueueName;
	
	
	public String getSendingQueueName() {
		return sendingQueueName;
	}

	public void setSendingQueueName(String sendingQueueName) {
		if (Constants.isHost)
		{
		this.sendingQueueName = sendingQueueName +"#sender";
		}
		else
		{
			this.sendingQueueName = sendingQueueName +"#receiver";
		}
		
	}

	public String getReceivingQueueName() {
		return receivingQueueName;
	}

	public void setReceivingQueueName(String receivingQueueName) {
		if (Constants.isHost)
		{
		this.receivingQueueName = receivingQueueName +"#receiver";
		}
		else
		{
			this.receivingQueueName = receivingQueueName +"#sender";
		}
	}

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
				if(Player.getInstance().getUsername()!=null)
		        {
					Constants.isHost = false;
					HostGame p = new HostGame(rootComp);
					String gameName = p.displayHostedGames();
					String queueName = JOptionPane.showInputDialog(new JFrame(), "Enter the name of the hosted game");

					String playerName = Player.getInstance().getUsername();
					setSendingQueueName(queueName);
					setReceivingQueueName(queueName);
					Exception[] exception=new Exception[1];
					ClientHandler.insertHostedGame(playerName, gameName, queueName, Constants.HOST, Constants.PATH+"/insertHostedGameBaseRecord", exception);
					Sender sender=new Sender();
					sender.sendAsHost(getSendingQueueName());
				try {
					SessionFactory.getInstanceOf().createConnection();
					Receiver.getInstanceOf().subscribe(getReceivingQueueName());
				} catch (JMSException e1) {
					LOG.info("Receiver failed");
				}
					Receiver.getInstanceOf().runGame();
				

			}
			else
			{
				JFrame frame=new JFrame();
		        JOptionPane.showMessageDialog(frame,"Please login");
			}
				
		}
		});
		
		joinButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Constants.isHost = false;
				
				JoinGame p = new JoinGame(GameMakerView.getInstance().getGamePanel());
				p.displayJoinGames();
				//Should be supported with a GUI displaying a list of games available to 
				String queueName = JOptionPane.showInputDialog(new JFrame(), "Enter the game you want to join");
				setSendingQueueName(queueName);
				setReceivingQueueName(queueName);
				Sender sender=new Sender();
				sender.sendAsHost(getSendingQueueName());
				try {
					SessionFactory.getInstanceOf().createConnection();
					Receiver.getInstanceOf().subscribe(getReceivingQueueName());
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Receiver.getInstanceOf().runGame();
			}
				
		});
		
		options.setSize(200, 200);
		options.setLocationRelativeTo(rootComp);
		options.setVisible(true);
		
		}

}
