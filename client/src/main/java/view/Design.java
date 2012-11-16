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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import chat.ChatReceiver;
import chat.ChatSender;
import model.Resources;
import model.SpriteModel;
import utility.*;
import view.communication.ClientHandler;
import view.imagePanel.ImagePanel;
import view.imagePanel.ImageProperties;

import view.PropertyPanel;

public class Design implements Resizable, ActionListener {
	/********/	

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Design.class);
	private static Design sharedDesign = null;
	private GamePanel gamePanel; // Right view in game maker
	private JPanel gameMakerPanel;//
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

	// Score Modificaion:
	private JTextField scoreModificationField;
	private JButton addLayerBtn;
	private JTextField spriteNameTextField;
	private JTextField groupNameTextField;
	private JComboBox layerBox;
	// private DefaultListModel spriteListIndividualModel;
	private String layer;
	private ActionEventPanel actionEventPanel;
	private ImagePanel extendedImagePanel;
	private boolean shouldDisplayScore = false;
	private PropertyPanel fieldPanel;
	private JPanel leftImagePanel;



	protected Design(int frameWidth, int frameHeight) {
		// Create a baseframe for the game maker
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
				Design.getInstance().getOptionFrame().getOptionFrame().setVisible(true);
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
		this.baseFrame.setMenuBar(menuBar.getMenuBar());		

		playerButtonPanel = new PlayerButtonPanel(this).getPlayerButtonPanel();
		facade = new Facade(gamePanel);

		leftImagePanel = new JPanel(new FlowLayout());
	
		// This is the panel where all the controls are placed. The left side of
		// the game maker
		gameMakerPanel = new JPanel();
		gameMakerPanel.setLayout(new BoxLayout(gameMakerPanel, BoxLayout.Y_AXIS));
		switchPanel = new JPanel(new CardLayout());
		controlPanel = new JPanel();

		// Top part of the control panel, which contains buttons like
		// start/load/save.
		buttonPanel = new ButtonPanel(this);

		gameMakerPanel.add(buttonPanel.getPanel());

		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

		fieldPanel = new PropertyPanel();
		controlPanel.add(fieldPanel);



		actionEventPanel = new ActionEventPanel(this);
		controlPanel.add(actionEventPanel.getPanel());
		switchPanel.add(controlPanel, "controlpanel");
		//?? amruta
		view.imagePanel.ImageActionListener imageActionListener = new view.imagePanel.ImageActionListener();
		extendedImagePanel = new ImagePanel(imageActionListener);
		JPanel[] extendedpanels = new JPanel[] { extendedImagePanel.getImagePanel() };
		leftImagePanel.add(extendedImagePanel.getImagePanel());
		// extendedImagePanel.getImagePanel().setVisible(false);

		gameMakerPanel.add(switchPanel);
		// baseFrame.getContentPane().add(gameMakerPanel);
		// baseFrame.getContentPane().add(controlPanel);
		baseFrame.getContentPane().add(leftImagePanel);
		baseFrame.getContentPane().add(gamePanel);
		/*
		 * Moved the following code to showGameMakerWindow. Problem: Game Maker
		 * opens every time the Design class is initialized. JIRA: FATWVNINC-47
		 * baseFrame.setVisible(true);
		 */
		baseFrame.setResizable(true);
	}

	public JPanel getLeftImagePanel() {
		return leftImagePanel;
	}

	public void setLeftImagePanel(JPanel leftImagePanel) {
		this.leftImagePanel = leftImagePanel;
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
		spriteNameTextField.setText(selectedSpriteModel.getId());
		groupNameTextField.setText(selectedSpriteModel.getGroupId());
		widthTextField.setText(Double.toString(selectedSpriteModel.getWidth()));
		heightTextField.setText(Double.toString(selectedSpriteModel.getHeight()));
		velocityXTextField.setText(Double.toString(selectedSpriteModel.getSpeedX()));
		velocityYTextField.setText(Double.toString(selectedSpriteModel.getSpeedY()));
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
			Design.getInstance().getGamePanel().unregisterModel(model);
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

	public JScrollPane getImagePanel() {
		JPanel imagePanel = new JPanel();

		URL jar = this.getClass().getClassLoader().getResource("resource.jar");
		List<String> list = new ArrayList<String>();

		ZipInputStream zip;
		try {
			LOG.debug(jar);
			zip = new ZipInputStream(jar.openStream());
			ZipEntry ze = null;

			while ((ze = zip.getNextEntry()) != null) {
				String entryName = ze.getName();
				LOG.debug(entryName);
				if (entryName.endsWith(".png") || entryName.endsWith(".jpg")) {
					list.add(entryName);
				}
			}
		} catch (IOException e1) {
			LOG.error(e1);
		}


		ImageActionListener listener = this.new ImageActionListener();
		List<ImageProperties> allImages = new ArrayList<ImageProperties>();
		Exception[] exceptions = new Exception[1];
		Resources[] images = ClientHandler.listPageResources("1", "1000",
				null, "tintin.cs.indiana.edu:8096", "/GameMakerServer/listPageResources", exceptions);
		for(int i = 0; i < images.length; i++){
			Image image = Util.convertByteArraytoImage(images[i].getResource(), "jpg");
			ImageProperties im = new ImageProperties(String.valueOf(images[i].getReourceNumber()),
					images[i].getResourceName(), image);
			allImages.add(im);
		}


		for (int i = 0; i < allImages.size(); i++) {

			Image image = allImages.get(i).getImage();
			ImageIcon icon = null;
			try{
				icon = new ImageIcon(image.getScaledInstance(50, 50, 1));
			}catch(Exception ex){
				ex.printStackTrace();
			}
			JButton button = new JButton(icon);
			button.setName(allImages.get(i).getImageKey());
			button.addActionListener(listener);
			imagePanel.add(button);

		}
		JScrollPane scroller = new JScrollPane(imagePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		return scroller;
	}

	public BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
		Graphics2D g = scaledBI.createGraphics();
		if (preserveAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
		g.dispose();
		return scaledBI;
	}

	public class ImageActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			JButton btn = (JButton) arg0.getSource();

			double sizeX;
			double sizeY;
			double speedX;
			double speedY;
			String layer = null;
			try {
				sizeX = Double.valueOf(widthTextField.getText());
				sizeY = Double.valueOf(heightTextField.getText());
				LOG.debug("got size values for customObject x:" + sizeX + " y:" + sizeY);



			} catch (Exception exception) {
				LOG.error("did not read in size values ... using defaults");
				sizeX = 30;
				sizeY = 30;
			}
			try {
				speedX = Double.valueOf(velocityXTextField.getText());
				speedY = Double.valueOf(velocityYTextField.getText());
				LOG.debug("got speed values for customObject x:" + speedX + " y:" + speedY);
			} catch (Exception exception) {
				LOG.error("did not read in speed values ... using defaults");
				speedX = 1;
				speedY = 1;
			}
			try {
				layer = layerBox.getSelectedItem().toString();
				if (layer.equalsIgnoreCase(Constants.ALL_LAYERS)) {
					if (Layers.getInstance().getLayers().size() == 1) {
						Layers.getInstance().addNewLayer();
					}
					List<String> layers = new ArrayList<String>();
					layers = Layers.getInstance().getLayers();
					layer = layers.get(1);
				}
			} catch (Exception exception) {
				LOG.error("layer value not set", exception);
			}
			if (btn != null) {
				// TO-DO : To get two images while importing objects. So
				// that the second object can be used as a secondary image
				// based on requirements.
				Image image = null;
				/*try {
    				image = ImageIO.read(Design.class.getResource(btn.getName()));
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}*/
				int imageId = 0;
				try{
					imageId = Integer.parseInt(btn.getName());
				}catch (NumberFormatException ex){
					ex.printStackTrace();
				}
				SpriteModel spriteModel = new SpriteModel(100, 100, speedX, speedY, sizeX, sizeY, btn.getName(), layer, imageId);
				updateSpriteList(spriteModel);
				updateProperties();
				facade.addSpriteModelToView(spriteModel);
				gamePanel.repaint();
			}
		}
	}

	

	/****************** GETTERS & SETTERS **********************************/
	public ImagePanel getExtendedImagePanel() {
		return extendedImagePanel;
	}

	public void setExtendedImagePanel(ImagePanel extendedImagePanel) {
		this.extendedImagePanel = extendedImagePanel;
	}

	public static Design getInstance() {
		if (sharedDesign == null) {
			sharedDesign = new Design(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
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
		return gameMakerPanel;
	}

	public void setGameMakerPanel(JPanel gameMakerPanel) {
		this.gameMakerPanel = gameMakerPanel;
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

