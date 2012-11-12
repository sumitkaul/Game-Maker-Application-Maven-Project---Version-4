package view;

import action.ActionStartOver;
import action.GameAction;
import controller.GameController;
import eventlistener.EventListener;
import eventlistener.KeyPressedEventListener;
import eventlistener.OutOfBoundaryEventListener;
import facade.Facade;
import gameMaker.gameMaker;
import interfaces.Resizable;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import loader.GameDataPackageIO;
import loader.GamePackage;
import model.Resources;
import model.SpriteModel;
import net.miginfocom.swing.MigLayout;
import newui.OptionsFrame;
import team3.a9.lookandfeel.AnimationHandler;
import utility.*;
import view.communication.ClientHandler;
import view.companels.GameBaseLoadPanel;
import view.companels.GameBaseSavePanel;
import view.companels.GameProgressLoadPanel;
import view.companels.GameProgressSavePanel;
import view.companels.ScoreDialog;
import view.companels.TopScoresPanel;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import view.companels.*;
import view.imagePanel.CollapsiblePanel;
import view.imagePanel.ImageActionListener;
import view.imagePanel.ImagePanel;
import view.imagePanel.ImageProperties;

public class Design implements Resizable, ActionListener {

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
	private CollapsiblePanel collapsiblePanel;
	private Facade facade;
	private JTextField velocityXTextField;
	private JTextField velocityYTextField;
	private JTextField widthTextField;
	private JPanel playerButtonPanel;
	private JButton startButton, pauseButton, saveButton, loadButton, newButton, topscoreButton;
	private JTextField heightTextField;
	private JButton send;
	private JTextField textSend;
	private JLabel textLabel;

	// Score Modificaion:
	private JTextField scoreModificationField;
	private JButton addLayerBtn;
	private JFileChooser fileChooser;
	private ThumbView thumbView = new ThumbView();
	private JTextField spriteNameTextField;
	private JTextField groupNameTextField;
	private JComboBox layerBox;
	// private DefaultListModel spriteListIndividualModel;
	private String layer;
	private ActionEventPanel actionEventPanel;
	private ImagePanel extendedImagePanel;
	private boolean shouldDisplayScore = false;
	private JTextArea textArea;
	private JScrollPane textScrollPane;

	protected Design(int frameWidth, int frameHeight) {
		// Create a baseframe for the game maker
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
		baseFrame.setLayout(new GridLayout(1, 2));

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

		MenuBar menuBar = new MenuBar();

		// Create a menu
		Menu menu = new Menu("Insert");
		menuBar.add(menu);

		// Create a menu item
		CheckboxMenuItem item = new CheckboxMenuItem("Clock");

		item.addItemListener(new ItemListener() {
			// .addActionListener(new ActionListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				CheckboxMenuItem cbi = (CheckboxMenuItem) e.getSource();
				ClockDisplay.getInstance().setVisible(cbi.getState());
				gamePanel.repaint();

			}
		});
		menu.add(item);

		this.baseFrame.setMenuBar(menuBar);

		Menu menuGame = new Menu("Game");
		menuBar.add(menuGame);

		// Create a menu item
		MenuItem itemGame1 = new MenuItem("Load");
		MenuItem itemGame2 = new MenuItem("Save");

		itemGame1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameBaseLoadPanel p = new GameBaseLoadPanel(controlPanel);

				String gameData = p.readGameDataFromRemoteList();
				if (gameData == null) {
					return;
				}

				GamePackage game = GameDataPackageIO.loadGamePackageFromFile(gameData);

				LOG.debug("load done");

				List<SpriteModel> allSpriteModels = game.getSpriteList();
				List<String> layers = game.getLayers();
				ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
				// SpriteList.getInstance().setSpriteList(allSpriteModels);
				SpriteList.getInstance().setSelectedSpriteModel(allSpriteModels.get(0));
				layerBox.removeAllItems();
				for (String layer : layers) {
					layerBox.addItem(layer);
				}

				facade.getGameController().setEvents(game.getEventsForGameController());
				facade.getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

				facade.createViewsForModels(game.getSpriteList());

				for (SpriteModel model : allSpriteModels) {
					SpriteList.getInstance().addSprite(model);
					SpriteList.getInstance().setSelectedSpriteModel(model);

					actionEventPanel.getSpriteListIndividualModel().addElement(model.getId());
					if (!actionEventPanel.getSpriteListGroupModel().contains(model.getGroupId())) {
						actionEventPanel.getSpriteListGroupModel().addElement(model.getGroupId());
					}
					if (actionEventPanel.getSpriteListIndividualModel().size() > 0) {
						actionEventPanel.getSpriteList().setModel(actionEventPanel.getSpriteListIndividualModel());
					}
					// if(spriteListGroupModel.size() >0 )
					// groupSpriteList.setModel(spriteListGroupModel);
				}

				updateProperties();
			}
		});

		itemGame2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), facade.getGameController().getEvents(), facade.getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
				String gameData = GameDataPackageIO.convertGamePackageToString(game);

				GameBaseSavePanel p = new GameBaseSavePanel(controlPanel);
				p.saveGameToRemoteServer(gameData);
			}
		});

		menuGame.add(itemGame1);
		menuGame.add(itemGame2);

		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				facade.startGame();
				gamePanel.requestFocusInWindow();
			}
		});
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				facade.stopGame();
				// ScoreDialog.showScoreDialog();
				JOptionPane.showMessageDialog(baseFrame, "Your Score is " + Score.getInstance().getScore());
			}
		});

		newButton = new JButton("New");
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Design.getInstance().reset();
				GameBaseLoadPanel p = new GameBaseLoadPanel(gamePanel);

				String gameData = p.readGameDataFromRemoteList();
				if (gameData == null) {
					return;
				}

				GamePackage game = GameDataPackageIO.loadGamePackageFromFile(gameData);

				LOG.debug("load done");

				List<SpriteModel> allSpriteModels = game.getSpriteList();
				List<String> layers = game.getLayers();
				ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
				// SpriteList.getInstance().setSpriteList(allSpriteModels);
				SpriteList.getInstance().setSelectedSpriteModel(allSpriteModels.get(0));

				facade.getGameController().setEvents(game.getEventsForGameController());
				facade.getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

				facade.createViewsForModels(game.getSpriteList());

				for (SpriteModel model : allSpriteModels) {
					SpriteList.getInstance().addSprite(model);
					SpriteList.getInstance().setSelectedSpriteModel(model);
				}
			}
		});

		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GameProgressLoadPanel p = new GameProgressLoadPanel(gamePanel);

				String gameData = p.readGameDataFromRemoteList();

				if (gameData == null) {
					return;
				}

				GamePackage game = GameDataPackageIO.loadGamePackageFromFile(gameData);

				LOG.debug("load done");

				List<SpriteModel> allSpriteModels = game.getSpriteList();
				List<String> layers = game.getLayers();
				ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
				// SpriteList.getInstance().setSpriteList(allSpriteModels);
				SpriteList.getInstance().setSelectedSpriteModel(allSpriteModels.get(0));

				facade.getGameController().setEvents(game.getEventsForGameController());
				facade.getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

				facade.createViewsForModels(game.getSpriteList());

				for (SpriteModel model : allSpriteModels) {
					SpriteList.getInstance().addSprite(model);
					SpriteList.getInstance().setSelectedSpriteModel(model);
				}
			}
		});
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), facade.getGameController().getEvents(), facade.getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
				String gameData = GameDataPackageIO.convertGamePackageToString(game);
				GameProgressSavePanel p = new GameProgressSavePanel(gamePanel);

				p.saveGameToRemoteServer(gameData);

			}
		});

		topscoreButton = new JButton("Top Scores");
		topscoreButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				TopScoresPanel p = new TopScoresPanel(gamePanel);
				p.readGameScoresFromRemoteList();
			}
		});
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new LoginFrame();

			}
		});

		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				new RegisterFrame();
			}
		});

		textArea = new JTextArea();
		textArea.setEditable(false);
		textScrollPane = new JScrollPane(textArea);

		textLabel = new JLabel("Enter Your Text Here:");

		textSend = new JTextField();
		send = new JButton("Send");
		send.addActionListener(this);

		playerButtonPanel = new JPanel(new MigLayout("center,center"));
		playerButtonPanel.add(loginButton, "wrap, wmin 200, hmin 30");
		playerButtonPanel.add(registerButton, "wrap, wmin 200, hmin 30");
		playerButtonPanel.add(newButton, "wrap, wmin 200, hmin 30");
		playerButtonPanel.add(loadButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(saveButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(startButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(pauseButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(topscoreButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(textScrollPane, "wrap,wmin 500, hmin 150");
		playerButtonPanel.add(textLabel,"wrap,wmin 100, hmin 10");
		playerButtonPanel.add(textSend,"wrap,wmin 500, hmin 50");
		playerButtonPanel.add(send,"wrap,wmin 200, hmin 30");
		facade = new Facade(gamePanel);

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

		// Bottom part of the control panel
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new GridBagLayout());

		JLabel layerLabel = new JLabel("Select the Layer");
		addLayerBtn = new JButton("Add New Layer");
		addLayerBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Layers.getInstance().addNewLayer();
			}
		});

		layerBox = new JComboBox(Layers.getInstance().getLayers().toArray());
		layerBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				layer = ((String) ((JComboBox) e.getSource()).getSelectedItem());
				gamePanel.setCurrentLayer(layer);
				gamePanel.repaint();
			}
		});
		JLabel spriteNameLabel = new JLabel("Sprite Name");
		spriteNameTextField = new JTextField(10);
		spriteNameTextField.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				// warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				LOG.debug(spriteNameTextField.getText());
				String spriteNameTextFieldString = spriteNameTextField.getText();
				if (SpriteList.getInstance().getSelectedSpriteModel() != null) {

					String previousName = SpriteList.getInstance().getSelectedSpriteModel().getId();
					if (spriteNameTextFieldString != null && !spriteNameTextFieldString.equalsIgnoreCase("")) {
						SpriteList.getInstance().getSelectedSpriteModel().setId(spriteNameTextFieldString);

						for (int i = 0; i < actionEventPanel.getSpriteListIndividualModel().size(); i++) {
							String textString = (String) actionEventPanel.getSpriteListIndividualModel().get(i);
							if (textString.equalsIgnoreCase(previousName)) {
								actionEventPanel.getSpriteListIndividualModel().set(i, spriteNameTextFieldString);
							}
						}

					}
				}

			}
		});

		JLabel spriteGroupLabel = new JLabel("Group Name");

		groupNameTextField = new JTextField(10);
		groupNameTextField.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				// warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				LOG.debug(groupNameTextField.getText());
				String groupNameTextFieldString = groupNameTextField.getText();
				if (SpriteList.getInstance().getSelectedSpriteModel() != null) {
					String previousGroupId = SpriteList.getInstance().getSelectedSpriteModel().getGroupId();
					if (groupNameTextFieldString != null && !groupNameTextFieldString.equalsIgnoreCase("")) {
						SpriteModel spriteModel = SpriteList.getInstance().getSelectedSpriteModel();
						spriteModel.setGroupId(groupNameTextFieldString);

						for (int i = 0; i < actionEventPanel.getSpriteListGroupModel().size(); i++) {
							String textString = (String) actionEventPanel.getSpriteListGroupModel().get(i);
							if (textString.equalsIgnoreCase(previousGroupId)) {

								actionEventPanel.getSpriteListGroupModel().set(i, groupNameTextFieldString);
								// if(groupNameTextFieldString.equals(textString)){
								// spriteListGroupModel.remove(i);
								// }

							}

						}

						for (int i = 0; i < actionEventPanel.getSpriteListGroupModel().size(); i++) {
							String textString = (String) actionEventPanel.getSpriteListGroupModel().get(i);
							if (textString.equalsIgnoreCase(groupNameTextFieldString)) {
								actionEventPanel.getSpriteListGroupModel().remove(i);
							}

						}

						if (!actionEventPanel.getSpriteListGroupModel().contains(spriteModel.getGroupId())) {
							actionEventPanel.getSpriteListGroupModel().addElement(spriteModel.getGroupId());
						}

					}
				}

				if (groupNameTextFieldString != null && !groupNameTextFieldString.equalsIgnoreCase("")) {
					SpriteList.getInstance().getSelectedSpriteModel().setGroupId(groupNameTextFieldString);
				}

			}

		});

		JLabel velocityXLabel = new JLabel("Velocity X");

		velocityXTextField = new JTextField(10);
		velocityXTextField.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				LOG.debug(velocityXTextField.getText());
				String velocityTextString = velocityXTextField.getText();
				if (velocityTextString != null && !velocityTextString.equalsIgnoreCase("")) {
					SpriteList.getInstance().getSelectedSpriteModel().setSpeedX(Double.parseDouble(velocityTextString));
				}
			}
		});

		JLabel velocityYLabel = new JLabel("Velocity Y");

		velocityYTextField = new JTextField(10);
		velocityYTextField.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				LOG.debug(velocityYTextField.getText());
				String velocityTextString = velocityYTextField.getText();
				if (velocityTextString != null && !velocityTextString.equalsIgnoreCase("")) {
					SpriteList.getInstance().getSelectedSpriteModel().setSpeedY(Double.parseDouble(velocityTextString));
				}
			}
		});

		JLabel heightLabel = new JLabel("Height");

		heightTextField = new JTextField(10);
		heightTextField.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				LOG.debug(velocityXTextField.getText());
				String heightTextString = heightTextField.getText();
				if (heightTextString != null && !heightTextString.equalsIgnoreCase("")) {
					SpriteList.getInstance().getSelectedSpriteModel().setHeight(Double.valueOf(heightTextString));
					gamePanel.repaint();
				}

			}
		});

		JLabel widthLabel = new JLabel("Width");

		widthTextField = new JTextField(10);
		widthTextField.getDocument().addDocumentListener(new DocumentListener() {

			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				LOG.debug(velocityXTextField.getText());
				String widthTextString = widthTextField.getText();
				if (widthTextString != null && !widthTextString.equalsIgnoreCase("")) {
					SpriteList.getInstance().getSelectedSpriteModel().setWidth(Double.valueOf(widthTextField.getText()));
					gamePanel.repaint();
				}

			}
		});

		// Score Modificaion:
		scoreModificationField = new JTextField(10);
		scoreModificationField.setVisible(false);

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10, 5, 0, 0);
		c.weightx = 0.25;

		// Row 0
		c.gridy = 0;
		c.gridx = 0;
		fieldPanel.add(layerLabel);
		c.gridx = 1;
		fieldPanel.add(layerBox);
		c.gridx = 2;
		fieldPanel.add(addLayerBtn);
		// Row 1
		c.gridy = 1;

		c.gridx = 0;
		fieldPanel.add(spriteNameLabel, c);
		c.gridx = 1;
		fieldPanel.add(spriteNameTextField, c);

		// Row 2
		c.gridy = 2;
		c.gridx = 0;
		fieldPanel.add(spriteGroupLabel, c);

		c.gridx = 1;
		fieldPanel.add(groupNameTextField, c);

		// Row 3
		c.gridy = 3;
		c.gridx = 0;
		fieldPanel.add(widthLabel, c);

		c.gridx = 1;
		fieldPanel.add(widthTextField, c);

		// Row 4
		c.gridy = 4;
		c.gridx = 0;
		fieldPanel.add(heightLabel, c);

		c.gridx = 1;
		fieldPanel.add(heightTextField, c);

		// Row 5
		c.gridy = 5;
		c.gridx = 0;
		fieldPanel.add(velocityXLabel, c);

		c.gridx = 1;
		fieldPanel.add(velocityXTextField, c);

		// Row 6
		c.gridy = 6;
		c.gridx = 0;
		fieldPanel.add(velocityYLabel, c);

		c.gridx = 1;
		fieldPanel.add(velocityYTextField, c);

		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

		collapsiblePanel = new CollapsiblePanel(baseFrame);
		// collapsiblePanel.setPanels(extendedpanels);
		gameMakerPanel.add(collapsiblePanel.getComponent());

		JScrollPane imagePanel = getImagePanel();

		controlPanel.add(imagePanel);

		controlPanel.add(fieldPanel);

		// Row 7
		c.gridy = 7;
		c.gridx = 0;

		actionEventPanel = new ActionEventPanel(this);
		controlPanel.add(actionEventPanel.getPanel());
		switchPanel.add(controlPanel, "controlpanel");
		view.imagePanel.ImageActionListener imageActionListener = new view.imagePanel.ImageActionListener();
		extendedImagePanel = new ImagePanel(imageActionListener);
		JPanel[] extendedpanels = new JPanel[] { extendedImagePanel.getImagePanel() };
		switchPanel.add(extendedImagePanel.getImagePanel(), "imagepanel");
		// extendedImagePanel.getImagePanel().setVisible(false);

		gameMakerPanel.add(switchPanel);
		// baseFrame.getContentPane().add(gameMakerPanel);
		// baseFrame.getContentPane().add(controlPanel);
		baseFrame.getContentPane().add(gamePanel);
		/*
		 * Moved the following code to showGameMakerWindow. Problem: Game Maker
		 * opens every time the Design class is initialized. JIRA: FATWVNINC-47
		 * baseFrame.setVisible(true);
		 */
		baseFrame.setResizable(true);
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
		// if(spriteListGroupModel.size() >0 )
		// groupSpriteList.setModel(spriteListGroupModel);

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
		spriteNameTextField.setText("");
		groupNameTextField.setText("");
		widthTextField.setText("");
		heightTextField.setText("");
		velocityXTextField.setText("");
		velocityYTextField.setText("");
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

	public CollapsiblePanel getCollapsiblePanel() {
		return this.collapsiblePanel;
	}

	public void setCollapsiblePanel(CollapsiblePanel collapsiblePanel) {
		this.collapsiblePanel = collapsiblePanel;
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



}
