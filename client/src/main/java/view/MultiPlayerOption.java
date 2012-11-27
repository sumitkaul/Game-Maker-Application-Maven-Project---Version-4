package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.jms.JMSException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.Player;
import multiplayer.Receiver;
import multiplayer.Sender;
import multiplayer.SessionFactory;
import net.miginfocom.swing.MigLayout;
import utility.Constants;
import view.communication.ClientHandler;

public final class MultiPlayerOption {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(MultiPlayerOption.class);
    private JComponent rootComp;
    private JFrame options;
    private JButton hostButton;
    private JButton joinButton;
    private JLabel optionLabel;
    private String sendingQueueName;
    private String receivingQueueName;
    private JFrame joinWaitFrame;
    private final static MultiPlayerOption instance = new MultiPlayerOption();

    private MultiPlayerOption() {
    }

    public static MultiPlayerOption getInstanceOf() {
        return instance;
    }

    public JComponent getRootComp() {
        return rootComp;
    }

    public void setRootComp(JComponent rootComp) {
        this.rootComp = rootComp;
    }

    public String getSendingQueueName() {
        return sendingQueueName;
    }

    public void setSendingQueueName(String sendingQueueName) {
        if (Constants.isHost) {
            this.sendingQueueName = sendingQueueName + "#sender";
        } else {
            this.sendingQueueName = sendingQueueName + "#receiver";
        }

    }

    public String getReceivingQueueName() {
        return receivingQueueName;
    }

    public void setReceivingQueueName(String receivingQueueName) {
        if (Constants.isHost) {
            this.receivingQueueName = receivingQueueName + "#receiver";
        } else {
            this.receivingQueueName = receivingQueueName + "#sender";
        }
    }

    public void selectOption() {
        LOG.info("i m in multiplayer class");
        options = new JFrame();
        hostButton = new JButton("Host");
        joinButton = new JButton("Join");
        optionLabel = new JLabel("Would you like to:");
        options.setLayout(new MigLayout("center,center"));
        options.add(optionLabel, "wrap,wmin 100, hmin 50");
        options.add(hostButton, "wmin 50, hmin 50");
        options.add(joinButton, "wmin 50, hmin 50");

        hostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Player.getInstance().getUsername() != null) {
                    Constants.isHost = true;
                    HostGame p = new HostGame(rootComp);
                    String gameName = p.displayHostedGames();
                    String queueName = JOptionPane.showInputDialog(new JFrame(), "Enter the name of the hosted game");
                    String playerName = Player.getInstance().getUsername();
                    setSendingQueueName(queueName);
                    setReceivingQueueName(queueName);
                    try {
                        ClientHandler.insertHostedGame(playerName, gameName, queueName, Constants.HOST, Constants.PATH + "/insertHostedGameBaseRecord");
                    } catch (Exception ex) {
                        LOG.error(ex);
                        return;
                    }
                    Sender sender = new Sender();
                    sender.sendAsHost(getSendingQueueName());
                    joinWaitFrame();

                    try {
                        SessionFactory.getInstanceOf().createConnection();
                        Receiver.getInstanceOf().subscribe(getReceivingQueueName());
                    } catch (JMSException e1) {
                        LOG.info("Receiver failed");
                    }
                    Receiver.getInstanceOf().runGame();


                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Please login");
                }

            }
        });

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Player.getInstance().getUsername() != null) {
                    Constants.isHost = false;

                    JoinGame p = new JoinGame(GameMakerView.getInstance().getGamePanel());
                    String gameName = p.displayJoinGames();
                    //Should be supported with a GUI displaying a list of games available to 
                    //Below line gets replaced with the GUI as mentioned above
                    String queueName = JOptionPane.showInputDialog(new JFrame(), "Enter the game you want to join");
                    String playerName = Player.getInstance().getUsername();
                    setSendingQueueName(queueName);
                    setReceivingQueueName(queueName);
                    Sender sender = new Sender();
                    try {
                        sender.sendAcknowledgement(getSendingQueueName(), playerName);
                    } catch (JMSException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                    }
                    //sender.sendAsHost(getSendingQueueName());
                    try {
                        SessionFactory.getInstanceOf().createConnection();
                        Receiver.getInstanceOf().subscribe(getReceivingQueueName());
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                    Receiver.getInstanceOf().runGame();
                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Please login");
                }

            }
        });

        options.setSize(200, 200);
        options.setLocationRelativeTo(rootComp);
        options.setVisible(true);

    }

    public void joinWaitFrame() {
        joinWaitFrame = new JFrame();
        joinWaitFrame.setLayout(new MigLayout("center,center"));
        joinWaitFrame.setSize(200, 200);
        JLabel label = new JLabel("Waiting for joinee");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setSize(50, 20);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                joinWaitFrame.dispose();
                return;

            }
        });
        joinWaitFrame.add(label, "wrap,wmin 100, hmin 50");
        joinWaitFrame.add(cancelButton, "wmin 50, hmin 50");
        LOG.info("In Join Frame");
        joinWaitFrame.setLocationRelativeTo(rootComp);
        joinWaitFrame.setVisible(true);
        joinWaitFrame.setFocusable(true);
        joinWaitFrame.requestFocus();
    }

    public void acceptUserFrame(String user, String game) {
        LOG.info("In accept user Frame");
        joinWaitFrame.dispose();
        JFrame acceptUserFrame = new JFrame();
        acceptUserFrame.setLayout(new MigLayout("center,center"));
        acceptUserFrame.setSize(200, 200);
        JLabel label = new JLabel(user + " wants to join your game " + game);
        JButton allowButton = new JButton("Allow");
        JButton kickButton = new JButton("Kick");
        acceptUserFrame.add(label, "wrap,wmin 100, hmin 50");
        acceptUserFrame.add(allowButton, "wmin 50, hmin 50");
        acceptUserFrame.add(kickButton, "wmin 50, hmin 50");
    }
}
