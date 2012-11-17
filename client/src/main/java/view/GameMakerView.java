package view;


import facade.Facade;
import interfaces.Resizable;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import model.SpriteModel;
import utility.*;
import view.imagePanel.ImagePanel;
import view.PropertyPanel;

public class GameMakerView {

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GameMakerView.class);
	private static GameMakerView sharedDesign = null;
	
	private JFrame baseFrame;
	private JLayeredPane layeredPane;
	private JPanel leftPanel;
	private GamePanel gamePanel; 
	private JPanel rightPanel;
	private ButtonPanel buttonPanel;
	private PropertyPanel propertyPanel;
	private ActionEventPanel actionEventPanel;
	private ImagePanel extendedImagePanel;
	
	private String userName = "";
	private OptionsFrame optionFrame;
	private Facade facade;
	private JComboBox layerBox;
	private boolean shouldDisplayScore = false;
	
	
	protected GameMakerView(int frameWidth, int frameHeight) {
		
		baseFrame = Helper.getsharedHelper().createBaseFrame(frameWidth,frameHeight);
		baseFrame.setJMenuBar(new MenuBarPanel().getMenuBar());
		
		leftPanel = new JPanel(new FlowLayout());
		view.imagePanel.ImageActionListener imageActionListener = new view.imagePanel.ImageActionListener();
		extendedImagePanel = new ImagePanel(imageActionListener);
		leftPanel.add(extendedImagePanel.getImagePanel());
		
		
		gamePanel = new GamePanel(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
		facade = new Facade(gamePanel);

		buttonPanel = new ButtonPanel(this);
		propertyPanel = new PropertyPanel();
		actionEventPanel = new ActionEventPanel(this);
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(buttonPanel.getPanel());
		rightPanel.add(propertyPanel);
		rightPanel.add(actionEventPanel.getPanel());
		
		layeredPane = new JLayeredPane();
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
		propertyPanel.updateProperties(selectedSpriteModel);
	
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
		propertyPanel.clearAll();

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

	public JFrame getBaseFrame() {
		return this.baseFrame;
	}

	public OptionsFrame getOptionsFrame() {
		return this.optionFrame;
	}

	public void setOptionsFrame(OptionsFrame optionFrame) {
		this.optionFrame = optionFrame;
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
	public PropertyPanel getPropertyPanel() {
		return propertyPanel;
	}

	public void setPropertyPanel(PropertyPanel propertyPanel) {
		this.propertyPanel = propertyPanel;
	}
	public JPanel getLeftImagePanel() {
		return leftPanel;
	}

	public void setLeftImagePanel(JPanel leftImagePanel) {
		this.leftPanel = leftImagePanel;
	}
}
