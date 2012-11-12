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

import loader.GameDataPackageIO;
import loader.GamePackage;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Layers;
import utility.SpriteList;
import view.companels.GameBaseLoadPanel;
import view.companels.GameBaseSavePanel;

public class MenuBarPanel {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(Design.class);
	private MenuBar menuBar = new MenuBar();

	public MenuBarPanel(){

		// Create a menu
		Menu menu = new Menu("Insert");
		menuBar.add(menu);

		// Create a menu item
		CheckboxMenuItem item = new CheckboxMenuItem("Clock");
		item.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				CheckboxMenuItem cbi = (CheckboxMenuItem) e.getSource();
				ClockDisplay.getInstance().setVisible(cbi.getState());
				Design.getInstance().getGamePanel().repaint();
			}
		});
		menu.add(item);

		Menu menuGame = new Menu("Game");
		menuBar.add(menuGame);

		// Create a menu item
		MenuItem itemGame1 = new MenuItem("Load");
		MenuItem itemGame2 = new MenuItem("Save");
		itemGame1.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			GameBaseLoadPanel p = new GameBaseLoadPanel(Design.getInstance().getControlPanel());

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
			Design.getInstance().getLayerBox().removeAllItems();
			for (String layer : layers) {
				Design.getInstance().getLayerBox().addItem(layer);
			}

			Design.getInstance().getFacade().getGameController().setEvents(game.getEventsForGameController());
			Design.getInstance().getFacade().getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

			Design.getInstance().getFacade().createViewsForModels(game.getSpriteList());

			for (SpriteModel model : allSpriteModels) {
			    SpriteList.getInstance().addSprite(model);
			    SpriteList.getInstance().setSelectedSpriteModel(model);

			    Design.getInstance().getActionEventPanel().getSpriteListIndividualModel().addElement(model.getId());
			    if (!Design.getInstance().getActionEventPanel().getSpriteListGroupModel().contains(model.getGroupId())) {
			    	Design.getInstance().getActionEventPanel().getSpriteListGroupModel().addElement(model.getGroupId());
			    }
			    if (Design.getInstance().getActionEventPanel().getSpriteListIndividualModel().size() > 0) {
			    	Design.getInstance().getActionEventPanel().getSpriteList().setModel(Design.getInstance().getActionEventPanel().getSpriteListIndividualModel());
			    }
			    // if(spriteListGroupModel.size() >0 )
			    // groupSpriteList.setModel(spriteListGroupModel);
			}

			Design.getInstance().updateProperties();
		    }
		});

		itemGame2.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), Design.getInstance().getFacade().getGameController().getEvents(), Design.getInstance().getFacade().getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
			String gameData = GameDataPackageIO.convertGamePackageToString(game);

			GameBaseSavePanel p = new GameBaseSavePanel(Design.getInstance().getControlPanel());
			p.saveGameToRemoteServer(gameData);
		    }
		});
		menuGame.add(itemGame1);
		menuGame.add(itemGame2);
	}
		/********* Getters and Setters *************/

		public void setMenuBar(MenuBar menuBar){
			this.menuBar = menuBar;
		}

		public MenuBar getMenuBar(){
			return this.menuBar;
		}
}
