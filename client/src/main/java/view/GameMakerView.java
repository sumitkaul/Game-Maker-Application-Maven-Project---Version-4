package view;


import facade.Facade;
import interfaces.Resizable;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.*;

import chat.ChatReceiver;
import chat.ChatSender;
import model.Resources;
import model.SpriteModel;
import utility.*;
import view.communication.ClientHandler;
import view.imagePanel.ImageActionListener;
import view.imagePanel.ImagePanel;
import view.imagePanel.ImageProperties;

import view.PropertyPanel;

public class GameMakerView implements Resizable, ActionListener {
	/********/	

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GameMakerView.class);
	private static GameMakerView sharedDesign = null;
	private GamePanel gamePanel; // Right view in game maker
	private JPanel rightPanel;//
	private JPanel switchPanel;//
	private String userName = "";
	private OptionsFrame optionFrame;
	private JPanel controlPanel; // Left view in game maker
	private ButtonPanel buttonPanel; // Top part of the left view
	private final JFrame baseFrame;
	private Facade facade;
	private JTextField velocityXTextField;
	private JTextField velocityYTextField;
	private JTextField widthTextField;
	private JPanel playerButtonPanel;
	private JTextField heightTextField;

	private JComboBox layerBox;
	private ActionEventPanel actionEventPanel;
	private ImagePanel extendedImagePanel;
	private boolean shouldDisplayScore = false;
	private PropertyPanel fieldPanel;
	private JPanel leftPanel;
	
	private JLayeredPane layeredPane;



	protected GameMakerView(int frameWidth, int frameHeight) {
		ChatReceiver chatReceiver=new ChatReceiver();
		ChatSender chatSender=new ChatSender();
		Thread chatSenderThread=new Thread(chatSender);
		Thread chatReceiverThread=new Thread(chatReceiver);
		chatReceiverThread.start();
		chatSenderThread.start();
		baseFrame = new JFrame();
		baseFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				GameMakerView.getInstance().getOptionFrame().getOptionFrame().setVisible(true);
				baseFrame.setVisible(false);
			}
		});
		// eventActionListModel = new DefaultListModel();
		baseFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		baseFrame.setTitle("Game Maker");
		baseFrame.setSize(frameWidth, frameHeight);
		baseFrame.setLayout(new GridLayout(1, 3));

		baseFrame.setMinimumSize(new Dimension(Constants.MINIMUM_FRAMEWIDTH, Constants.MINIMUM_FRAMEHEIGHT));

		baseFrame.getRootPane().addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				int originalFrameWidth = Constants.FRAME_WIDTH;
				int originalFrameHeight = Constants.FRAME_HEIGHT;

				Component rootPane = e.getComponent();
				Rectangle r = rootPane.getBounds();

				double xScale = (double) r.width / originalFrameWidth;
				double yScale = (double) r.height / originalFrameHeight;

				ResizeHelper.getInstance().setxFactor(xScale);
				ResizeHelper.getInstance().setyFactor(yScale);

				gamePanel.repaint();

			}
		});

		// This is the panel where the sprite will be inserted. The right side
		// of the game maker
		gamePanel = new GamePanel(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);

		MenuBarPanel menuBar = new MenuBarPanel();
		this.baseFrame.setJMenuBar(menuBar.getMenuBar());		

		playerButtonPanel = new PlayerButtonPanel(this).getPlayerButtonPanel();
		facade = new Facade(gamePanel);

		leftPanel = new JPanel(new FlowLayout());

		
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		switchPanel = new JPanel(new CardLayout());
		controlPanel = new JPanel();

		// Top part of the control panel, which contains buttons like
		// start/load/save.
		buttonPanel = new ButtonPanel(this);

		rightPanel.add(buttonPanel.getPanel());

		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

		fieldPanel = new PropertyPanel();
		rightPanel.add(fieldPanel);



		actionEventPanel = new ActionEventPanel(this);
		rightPanel.add(actionEventPanel.getPanel());
		switchPanel.add(controlPanel, "controlpanel");
		//?? amruta
		view.imagePanel.ImageActionListener imageActionListener = new view.imagePanel.ImageActionListener();
		extendedImagePanel = new ImagePanel(imageActionListener);
		JPanel[] extendedpanels = new JPanel[] { extendedImagePanel.getImagePanel() };
		leftPanel.add(extendedImagePanel.getImagePanel());
		
		layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.BLUE);
		baseFrame.getContentPane().add(layeredPane);
		
		
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new GridLayout(1, 3));
		basePanel.add(leftPanel, new Integer(0));
		basePanel.add(gamePanel, new Integer(0));
		basePanel.add(rightPanel, new Integer(0));
		
		basePanel.setBounds(0,0,Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT);
		layeredPane.add(basePanel);

		
//		InfoPanel infoPanel = new InfoPanel();
//		infoPanel.setBackground(Color.RED);
//		infoPanel.setBounds(150, 0, 200, 200);
//		layeredPane.add(infoPanel,new Integer(1));
		
		
		baseFrame.setVisible(true);
		baseFrame.setResizable(true);
	}

	public JPanel getLeftImagePanel() {
		return leftPanel;
	}

	public void setLeftImagePanel(JPanel leftImagePanel) {
		this.leftPanel = leftImagePanel;
	}

	@Override
	public void Resize(int framewidth, int frameheight) {
		int widthdiff = framewidth - Constants.FRAME_WIDTH;
		int heightdiff = frameheight - Constants.FRAME_HEIGHT;

		controlPanel.setSize(Constants.CONTROL_PANEL_WIDTH + (int) (widthdiff * 0.6), Constants.CONTROL_PANEL_LENGTH + (int) ((heightdiff * 2) / 7));
		}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

	public void createDuplicateSpriteModel(SpriteModel model) {

		SpriteModel spriteModel = new SpriteModel(model.getPosX() + model.getWidth() / 2, model.getPosY() + model.getHeight() / 2, model.getSpeedX(), model.getSpeedY(), model.getWidth(), model.getHeight(), 
				model.getImageUrlString(), model.getLayer(), model.getImageId());
		updateSpriteList(spriteModel);
		updateProperties();

		facade.addSpriteModelToView(spriteModel);
		gamePanel.repaint();
	}

	public void updateSpriteList(SpriteModel spriteModel) {
		SpriteList.getInstance().addSprite(spriteModel);
		SpriteList.getInstance().setSelectedSpriteModel(spriteModel);

		actionEventPanel.getSpriteListIndividualModel().addElement(spriteModel.getId());
		if (!actionEventPanel.getSpriteListGroupModel().contains(spriteModel.getGroupId())) {
			actionEventPanel.getSpriteListGroupModel().addElement(spriteModel.getGroupId());
		}
		if (actionEventPanel.getSpriteListIndividualModel().size() > 0) {
			actionEventPanel.getSpriteList().setModel(actionEventPanel.getSpriteListIndividualModel());
		}

	}

	public void updateProperties() {
		SpriteModel selectedSpriteModel = SpriteList.getInstance().getSelectedSpriteModel();
		if (selectedSpriteModel == null) {
			return;
		}
		fieldPanel.updateProperties(selectedSpriteModel);
		
		// layerBox.setSelectedItem(selectedSpriteModel.getLayer());
		int selectedItem = 0;
		DefaultListModel listModel = actionEventPanel.getSpriteListIndividualModel();
		for (int i = 0; i < listModel.size(); i++) {
			String element = (String) listModel.get(i);
			if (element.equalsIgnoreCase(selectedSpriteModel.getId())) {
				selectedItem = i;
			}
		}
		actionEventPanel.getSpriteList().setSelectedIndex(selectedItem);

	}

	public void clearAll() {
		fieldPanel.clearAll();

	}

	public Facade getFacade() {
		return facade;
	}

	public void removeSpriteModelFromList(SpriteModel selectedSpriteModel) {
		int selectedItem = 0;
		DefaultListModel listModel = actionEventPanel.getSpriteListIndividualModel();
		for (int i = 0; i < listModel.size(); i++) {
			String element = (String) listModel.get(i);
			if (element.equalsIgnoreCase(selectedSpriteModel.getId())) {
				selectedItem = i;
			}
		}
		listModel.remove(selectedItem);
		actionEventPanel.getSpriteList().setModel(listModel);

	}

	public void reset() {
		List<SpriteModel> allSpriteModels = SpriteList.getInstance().getSpriteList();
		for (SpriteModel model : allSpriteModels) {
			GameMakerView.getInstance().getGamePanel().unregisterModel(model);
		}
		ClockDisplay.getInstance().reset();
		SpriteList.getInstance().getSpriteList().clear();


		facade.getGameController().getEvents().clear();
		facade.getKeyListenerController().getKeyEvents().clear();

		actionEventPanel.getSpriteListIndividualModel().removeAllElements();
		actionEventPanel.getSpriteList().setModel(actionEventPanel.getSpriteListIndividualModel());

		updateProperties();

		gamePanel.removeAllDrawables();
		gamePanel.repaint();
	}

	/****************** GETTERS & SETTERS **********************************/
	public ImagePanel getExtendedImagePanel() {
		return extendedImagePanel;
	}

	public void setExtendedImagePanel(ImagePanel extendedImagePanel) {
		this.extendedImagePanel = extendedImagePanel;
	}

	public static GameMakerView getInstance() {
		if (sharedDesign == null) {
			sharedDesign = new GameMakerView(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		}
		return sharedDesign;
	}

	public JTextField getHeightTextField() {
		return heightTextField;
	}

	public void setHeightTextField(JTextField heightTextField) {
		this.heightTextField = heightTextField;
	}

	public JTextField getVelocityXTextField() {
		return velocityXTextField;
	}

	public void setVelocityXTextField(JTextField velocityXTextField) {
		this.velocityXTextField = velocityXTextField;
	}


	public JTextField getVelocityYTextField() {
		return velocityYTextField;
	}

	public void setVelocityYTextField(JTextField velocityYTextField) {
		this.velocityYTextField = velocityYTextField;
	}

	public JTextField getWidthTextField() {
		return widthTextField;
	}

	public void setWidthTextField(JTextField widthTextField) {
		this.widthTextField = widthTextField;
	}

	public JFrame getBaseFrame() {
		return this.baseFrame;
	}

	public OptionsFrame getOptionsFrame() {
		return this.optionFrame;
	}

	public void setOptionsFrame(OptionsFrame optionFrame) {
		this.optionFrame = optionFrame;
	}

	public void setControlPanel(JPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	public JPanel getSwitchPanel() {
		return switchPanel;
	}

	public void setSwitchPanel(JPanel switchPanel) {
		this.switchPanel = switchPanel;
	}

	public JPanel getGameMakerPanel() {
		return rightPanel;
	}

	public void setGameMakerPanel(JPanel gameMakerPanel) {
		this.rightPanel = gameMakerPanel;
	}

	public JComboBox getLayerBox() {
		return layerBox;
	}

	public JPanel getControlPanel() {
		return controlPanel;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public ActionEventPanel getActionEventPanel() {
		return actionEventPanel;
	}

	public ButtonPanel getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(ButtonPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public OptionsFrame getOptionFrame() {
		return optionFrame;
	}

	public void setOptionFrame(OptionsFrame optionFrame) {
		this.optionFrame = optionFrame;
	}

	public void showGameMakerWindow(boolean visibility) {
		this.baseFrame.setVisible(true);
	}

	public JPanel getPlayerButtonPanel() {
		return playerButtonPanel;
	}

	public void setPlayerButtonPanel(JPanel playerButtonPanel) {
		this.playerButtonPanel = playerButtonPanel;
	}

	public boolean isShouldDisplayScore() {
		return shouldDisplayScore;
	}

	public void setShouldDisplayScore(boolean shouldDisplayScore) {
		this.shouldDisplayScore = shouldDisplayScore;
	}
	
	public DefaultListModel getSpriteListIndividualModel() {
        return actionEventPanel.getSpriteListIndividualModel();
    } 
	
	public DefaultListModel getSpriteListGroupModel() {
        return actionEventPanel.getSpriteListGroupModel();
    } 
}

