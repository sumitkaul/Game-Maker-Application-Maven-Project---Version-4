package view;


import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import loader.GameDataPackageIO;
import loader.GamePackage;
import model.GameBase;
import model.Player;
import model.Resource;
import model.SpriteModel;
import team3.a9.lookandfeel.AnimationHandler;
import utility.ClockDisplay;
import utility.Constants;
import utility.Layers;
import utility.Score;
import utility.SpriteList;
import view.companels.GameBaseLoadPanel;
import view.companels.GameBaseSavePanel;
import view.companels.GameProgressLoadPanel;
import view.companels.GameProgressSavePanel;

public class ButtonPanel {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ButtonPanel.class);
	private Design design;
	private ActionEventPanel actionEventPanel;
	private JPanel buttonPanel;
	private JLabel userName;
	
	public ButtonPanel(Design designArg) {
		this.design = designArg;
		actionEventPanel = design.getActionEventPanel();
        buttonPanel = new JPanel();

        //controlPanel.setSize(Constants.CONTROL_PANEL_WIDTH, Constants.CONTROL_PANEL_LENGTH);

        JButton newGame = new JButton("Clear");
        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	design.reset();
            }
        });

        JButton login= new JButton("Login");
        login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoginFrame f = new LoginFrame();
                                AnimationHandler.RotateIn(f.getLogin(), f.getLoginPanel(), 1000, 360, f.getLogin().getWidth()/2, f.getLogin().getHeight()/2);
				
			}
		});
        
        JButton register = new JButton("Register");
        register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				RegisterFrame f = new RegisterFrame();
                                AnimationHandler.RotateIn(f.getLogin(), f.getLoginPanel(), 1000, -360, f.getLogin().getWidth()/2, f.getLogin().getHeight()/2);
			}
		});
        
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	design.getFacade().startGame();
            	design.getGamePanel().requestFocusInWindow();
            }
        });

        JButton stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                design.getFacade().stopGame();
                // ScoreDialog.showScoreDialog();
                if (Design.getInstance().isShouldDisplayScore()) {
		    JOptionPane.showMessageDialog(design.getBaseFrame(), "Your Score is " + Score.getInstance().getScore());
		}
            }
        });



//        JButton save = new JButton("Save GameBase");
//        save.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), design.getFacade().getGameController().getEvents(), design.getFacade().getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
//                String gameData = GameDataPackageIO.convertGamePackageToString(game);
//                GameBaseSavePanel p = new GameBaseSavePanel(design.getControlPanel());
//                p.saveGameToRemoteServer(gameData);
//                
//                //Hibernate
////                LOG.debug("Maven + Hibernate + MySQL");
////                LOG.debug(gameData);
////		        Session session = HibernateUtil.getSessionFactory().openSession();
////		 
////		        session.beginTransaction();
////		        GameBase gameBase= new GameBase();
////		        gameBase.setAuthorName("pranab");
////		        gameBase.setGameName(" game ");
////		        gameBase.setGameData(gameData);
////		        session.save(gameBase);
////		        session.getTransaction().commit();
//            }
//        });
//
//        JButton load = new JButton("Load GameBase");
//        load.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                design.reset();
//                GameBaseLoadPanel p = new GameBaseLoadPanel(design.getControlPanel());
//
//                String gameData = p.readGameDataFromRemoteList();
//                if (gameData == null) {
//                    return;
//                }
//
//                GamePackage game = GameDataPackageIO.loadGamePackageFromFile(gameData);
//
//                LOG.debug("load done");
//
//                List<SpriteModel> allSpriteModels = game.getSpriteList();
//                List<String> layers = game.getLayers();
//                ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
//                // SpriteList.getInstance().setSpriteList(allSpriteModels);
//                SpriteList.getInstance().setSelectedSpriteModel(allSpriteModels.get(0));
//                design.getLayerBox().removeAllItems();
//                for (String layer : layers) {
//                	design.getLayerBox().addItem(layer);
//                }
//
//                design.getFacade().getGameController().setEvents(game.getEventsForGameController());
//                design.getFacade().getKeyListenerController().setKeyEvents(game.getEventsForKeyController());
//
//                design.getFacade().createViewsForModels(game.getSpriteList());
//                actionEventPanel = design.getActionEventPanel();
//                for (SpriteModel model : allSpriteModels) {
//                    SpriteList.getInstance().addSprite(model);
//                    SpriteList.getInstance().setSelectedSpriteModel(model);
//                             
//                    actionEventPanel.getSpriteListIndividualModel().addElement(model.getId());
//                    if (!actionEventPanel.getSpriteListGroupModel().contains(model.getGroupId())) {
//                    	actionEventPanel.getSpriteListGroupModel().addElement(model.getGroupId());
//                    }
//                    if (actionEventPanel.getSpriteListIndividualModel().size() > 0) {
//                    	actionEventPanel.getSpriteList().setModel(actionEventPanel.getSpriteListIndividualModel());
//                    }
//                    // if(spriteListGroupModel.size() >0 )
//                    // groupSpriteList.setModel(spriteListGroupModel);
//                }
//
//                design.updateProperties();
//            }
//        });
//
////        Button loadSavedGameButton = new Button("Load In-Progress Game");
////        loadSavedGameButton.addActionListener(new ActionListener() {
////
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                GameProgressLoadPanel p = new GameProgressLoadPanel(design.getControlPanel());
////
////                String gameData = p.readGameDataFromRemoteList();
////
////                if (gameData == null) {
////                    return;
////                }
////
////                GamePackage game = GameDataPackageIO.loadGamePackageFromFile(gameData);
////
////                LOG.debug("load done");
////
////                List<SpriteModel> allSpriteModels = game.getSpriteList();
////                List<String> layers = game.getLayers();
////                ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
////                // SpriteList.getInstance().setSpriteList(allSpriteModels);
////                SpriteList.getInstance().setSelectedSpriteModel(allSpriteModels.get(0));
////                design.getLayerBox().removeAllItems();
////                for (String layer : layers) {
////                	design.getLayerBox().addItem(layer);
////                }
////
////                design.getFacade().getGameController().setEvents(game.getEventsForGameController());
////                design.getFacade().getKeyListenerController().setKeyEvents(game.getEventsForKeyController());
////
////                design.getFacade().createViewsForModels(game.getSpriteList());
////
////                for (SpriteModel model : allSpriteModels) {
////                    SpriteList.getInstance().addSprite(model);
////                    SpriteList.getInstance().setSelectedSpriteModel(model);
////
////                    actionEventPanel.getSpriteListIndividualModel().addElement(model.getId());
////                    if (!actionEventPanel.getSpriteListGroupModel().contains(model.getGroupId())) {
////                    	actionEventPanel.getSpriteListGroupModel().addElement(model.getGroupId());
////                    }
////                    if (actionEventPanel.getSpriteListIndividualModel().size() > 0) {
////                    	actionEventPanel.getSpriteList().setModel(actionEventPanel.getSpriteListIndividualModel());
////                    }
////                    // if(spriteListGroupModel.size() >0 )
////                    // groupSpriteList.setModel(spriteListGroupModel);
////                }
////
////                design.updateProperties();
////            }
////        });
////
////        Button saveGameButton = new Button("Save Game Progress");
////        saveGameButton.addActionListener(new ActionListener() {
////
////            @Override
////            public void actionPerformed(ActionEvent e) {
////
////                GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), design.getFacade().getGameController().getEvents(), design.getFacade().getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
////                String gameData = GameDataPackageIO.convertGamePackageToString(game);
////                GameProgressSavePanel p = new GameProgressSavePanel(design.getControlPanel());
////
////                p.saveGameToRemoteServer(gameData);
////
////            }
////        });
       
        userName= new JLabel();
        
        buttonPanel.add(start);
        buttonPanel.add(stop);
        buttonPanel.add(login);
        buttonPanel.add(register);
        //buttonPanel.add(loadGameBaseButton);
        // buttonPanel.add(loadSavedGameButton);
//        buttonPanel.add(load);
//        buttonPanel.add(save);
//        
        // buttonPanel.add(saveGameButton);
        buttonPanel.add(newGame);
        buttonPanel.add(userName);
        

    }
	
	public JPanel getPanel()
	{
		return buttonPanel;
	}

	public JLabel getUserName() {
		return userName;
	}

	public void setUserName(JLabel userName) {
		this.userName = userName;
	}


}
