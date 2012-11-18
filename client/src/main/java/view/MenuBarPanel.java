package view;

import java.awt.CheckboxMenuItem;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

import loader.GameDataPackageIO;
import loader.GamePackage;
import lookandfeel.AnimationHandler;
import lookandfeel.ThemeHandler;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Constants;
import utility.Layers;
import utility.SpriteList;
import view.companels.GameBaseLoadPanel;
import view.companels.GameBaseSavePanel;

public class MenuBarPanel implements ActionListener, ItemListener {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GameMakerView.class);
	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem[] modes = new JMenuItem[2];

	public MenuBarPanel() {

		JMenu menuGame = new JMenu("Game");
		menuBar.add(menuGame);

		// Create a menu item
		JMenuItem loadItem = new JMenuItem("Load");
		JMenuItem saveItem = new JMenuItem("Save");
		loadItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadGame();
			}

		});

		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveGame();		    	
			}
		});
		
		JMenuItem changeTheme= new JMenuItem("Change Theme");
		changeTheme.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
            	ThemeHandler.showThemePanel();
            	JFrame frame= GameMakerView.getInstance().getBaseFrame();            	
                //frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
            	SwingUtilities.updateComponentTreeUI(frame.getRootPane());
            	//SwingUtilities.updateComponentTreeUI(menuBar); 
            	frame.getRootPane().updateUI();
            	//Update UI
            	GameMakerView.getInstance().getGameMakerPanel().updateUI();
            	//GameMakerView.getInstance().getControlPanel().updateUI();
            	GameMakerView.getInstance().getGamePanel().updateUI();
            	GameMakerView.getInstance().getActionEventPanel().getPanel().updateUI();
            	//pack the frame
            	frame.pack();
		    	
		    }
		});
		
		JMenuItem exitItem = new JMenuItem("Exit");

		exitItem.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	System.exit(0);		    	
		    }
		});

		menuGame.add(loadItem);
		menuGame.add(saveItem);
		menuGame.add(new JSeparator());
		menuGame.add(changeTheme);
		menuGame.add(new JSeparator());		
		menuGame.add(exitItem);


		// Create a menu
		JMenu menu = new JMenu("Insert");
		menuBar.add(menu);

		// Create a menu item
		JCheckBoxMenuItem item = new JCheckBoxMenuItem("Clock");
		item.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				CheckboxMenuItem cbi = (CheckboxMenuItem) e.getSource();
				ClockDisplay.getInstance().setVisible(cbi.getState());
				GameMakerView.getInstance().getGamePanel().repaint();
			}
		});
		menu.add(item);

		JMenu user = new JMenu("User");
		menuBar.add(user);

		JMenuItem login = new JMenuItem("Login");
		JMenuItem register = new JMenuItem("Register");

		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LoginFrame f = new LoginFrame();
				AnimationHandler.RotateIn(f.getLogin(), f.getLoginPanel(), 1000, 360, f.getLogin().getWidth()/2, f.getLogin().getHeight()/2);

			}
		});

		register.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterFrame f = new RegisterFrame();
				AnimationHandler.RotateIn(f.getLogin(), f.getLoginPanel(), 1000, -360, f.getLogin().getWidth()/2, f.getLogin().getHeight()/2);

			}
		});

		user.add(login);
		user.add(register);

				JMenu menuMultiPlayer = new JMenu("MultiPlayer");
		JMenuItem startMultiPlayer = new JMenuItem("Start"); 
		menuMultiPlayer.add(startMultiPlayer);
		menuBar.add(menuMultiPlayer);

		startMultiPlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Constants.isMultiplayer = true;
				MultiPlayerOption p = new MultiPlayerOption(GameMakerView.getInstance().getGamePanel());
				LOG.info("in start action listener");
				p.selectOption();
			}
		});

		JMenu menuMode = new JMenu("Mode");
		ButtonGroup modeGroup = new ButtonGroup();
		
		modes[0] = new JRadioButtonMenuItem("Single Player");
		modes[1] = new JRadioButtonMenuItem("Multi Player");
		modes[0].setSelected(true);
		modes[0].addItemListener(this);
		modes[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Constants.isMultiplayer = true;
				MultiPlayerOption p = new MultiPlayerOption(Design.getInstance().getGamePanel());
				LOG.info("in start action listener");
				p.selectOption();
			}
		});
		modeGroup.add(modes[0]);
		modeGroup.add(modes[1]);
		menuMode.add(modes[0]);
		menuMode.add(modes[1]);
		menuBar.add(menuMode);
				
	}
	/********* Getters and Setters *************/

	public void setMenuBar(JMenuBar menuBar){
		this.menuBar = menuBar;
	}

	public JMenuBar getMenuBar(){
		return this.menuBar;
	}

	public static void saveGame()
	{
		GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), GameMakerView.getInstance().getFacade().getGameController().getEvents(), GameMakerView.getInstance().getFacade().getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
		String gameData = GameDataPackageIO.convertGamePackageToString(game);

		GameBaseSavePanel p = new GameBaseSavePanel(GameMakerView.getInstance().getBaseFrame().getRootPane());
		p.saveGameToRemoteServer(gameData);

	}

	public static void loadGame()
	{
		GameBaseLoadPanel p = new GameBaseLoadPanel(GameMakerView.getInstance().getBaseFrame().getRootPane());

		String gameData = p.readGameDataFromRemoteList();
		if (gameData == null) {
			return;
		}

		postProcessingAfterLoad(gameData);
	}

	public static void postProcessingAfterLoad(String gameData)
	{
		GamePackage game = GameDataPackageIO.loadGamePackageFromFile(gameData);

		LOG.debug("load done");

		List<SpriteModel> allSpriteModels = game.getSpriteList();
		List<String> layers = game.getLayers();
		ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
		// SpriteList.getInstance().setSpriteList(allSpriteModels);
		SpriteList.getInstance().setSelectedSpriteModel(allSpriteModels.get(0));
		GameMakerView.getInstance().getLayerBox().removeAllItems();
		for (String layer : layers) {
			GameMakerView.getInstance().getLayerBox().addItem(layer);
		}

		GameMakerView.getInstance().getFacade().getGameController().setEvents(game.getEventsForGameController());
		GameMakerView.getInstance().getFacade().getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

		GameMakerView.getInstance().getFacade().createViewsForModels(game.getSpriteList());

		for (SpriteModel model : allSpriteModels) {
			SpriteList.getInstance().addSprite(model);
			SpriteList.getInstance().setSelectedSpriteModel(model);

			GameMakerView.getInstance().getActionEventPanel().getSpriteListIndividualModel().addElement(model.getId());
			if (!GameMakerView.getInstance().getActionEventPanel().getSpriteListGroupModel().contains(model.getGroupId())) {
				GameMakerView.getInstance().getActionEventPanel().getSpriteListGroupModel().addElement(model.getGroupId());
			}
			if (GameMakerView.getInstance().getActionEventPanel().getSpriteListIndividualModel().size() > 0) {
				GameMakerView.getInstance().getActionEventPanel().getSpriteList().setModel(GameMakerView.getInstance().getActionEventPanel().getSpriteListIndividualModel());
			}
			// if(spriteListGroupModel.size() >0 )
				// groupSpriteList.setModel(spriteListGroupModel);
		}
		GameMakerView.getInstance().updateProperties();
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		


	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getItem().equals(modes[0]))
				{
			LOG.info("In single player mode");
			Constants.isMultiplayer = false;
			GameMakerView.getInstance().getBaseFrame().validate();
			//GameMakerView.getInstance().getControlPanel().validate();
			GameMakerView.getInstance().getActionEventPanel().getInputKeyPanel().getComboBox().setVisible(false);
			GameMakerView.getInstance().getActionEventPanel().getInputKeyPanel().getInputPanel().validate();

				}
		if (e.getItem().equals(modes[1]))
		{
			LOG.info("In Multiplayer mode");
	Constants.isMultiplayer = true;
	GameMakerView.getInstance().getBaseFrame().validate();
	//GameMakerView.getInstance().getControlPanel().validate();
	GameMakerView.getInstance().getActionEventPanel().getInputKeyPanel().getComboBox().setVisible(true);
	GameMakerView.getInstance().getActionEventPanel().getInputKeyPanel().getInputPanel().validate();

		}
		
	}
}



