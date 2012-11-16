package view;

import game.engine.slick2d.player.GameEngineController;
import game.engine.slick2d.player.TestEngine;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import org.newdawn.slick.SlickException;
import utility.ClockDisplay;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import lookandfeel.AnimationHandler;

import org.newdawn.slick.AppGameContainer;

public class OptionsFrame implements ActionListener {

    private static final org.apache.log4j.Logger LOG =
            org.apache.log4j.Logger.getLogger(OptionsFrame.class);
    private JFrame optionFrame;
    private JButton makerButton, playerButton, gamestartButton, gamestopButton;
    private TestEngine te;

    public OptionsFrame() {
        optionFrame = new JFrame();
        makerButton = new JButton("Make Game");
        makerButton.addActionListener(this);
        playerButton = new JButton("Play Game");
        playerButton.addActionListener(this);
        gamestartButton = new JButton("Start GE Testing");
        gamestartButton.addActionListener(this);
        gamestopButton = new JButton("Stop GE Testing");
        gamestopButton.addActionListener(this);


        optionFrame.setLayout(new MigLayout("center,center"));
        optionFrame.add(makerButton, "wmin 100, hmin 150");
        optionFrame.add(playerButton, "wmin 100, hmin 150");
        optionFrame.add(gamestartButton, "wmin 100, hmin 150");
        optionFrame.add(gamestopButton, "wmin 100, hmin 150");

        optionFrame.setSize(500, 500);
        optionFrame.setFocusable(true);
        optionFrame.setLocationRelativeTo(null);
        optionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionFrame.setTitle("Option Page");
        optionFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == makerButton) {
            Design.getInstance().setOptionFrame(this);
            Design.getInstance().reset();
            Design.getInstance().clearAll();
            Design.getInstance().getGamePanel().registerDrawable(ClockDisplay.getInstance());
            Design.getInstance().getBaseFrame().setVisible(true);
            Design.getInstance().getBaseFrame().getContentPane().removeAll();
            Design.getInstance().getBaseFrame().getContentPane().add(Design.getInstance().getLeftImagePanel());
            Design.getInstance().getBaseFrame().getContentPane().add(Design.getInstance().getGamePanel());
            AnimationHandler.FadeOut(Design.getInstance().getPlayerButtonPanel(), (JComponent) Design.getInstance().getBaseFrame().getContentPane(), 500);
            Design.getInstance().getLeftImagePanel().setVisible(true);
            Design.getInstance().getBaseFrame().validate();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                LOG.debug(e1);
            }
            AnimationHandler.FadeIn(Design.getInstance().getGameMakerPanel(), (JComponent) Design.getInstance().getBaseFrame().getContentPane(), 500);
            optionFrame.setVisible(false);
        }

        if (e.getSource() == playerButton) {
            Design.getInstance();
            Design.getInstance().setOptionFrame(this);
            Design.getInstance().reset();
            Design.getInstance().clearAll();
            Design.getInstance().getBaseFrame().setVisible(true);
            AnimationHandler.FadeOut(Design.getInstance().getGameMakerPanel(), (JComponent) Design.getInstance().getBaseFrame().getContentPane(), 500);
            //Design.getInstance().getLeftImagePanel().setVisible(false);
            Design.getInstance().getBaseFrame().getContentPane().remove(Design.getInstance().getLeftImagePanel());
            Design.getInstance().getBaseFrame().validate();
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                LOG.debug(e1);
            }
            AnimationHandler.FadeIn(Design.getInstance().getPlayerButtonPanel(), (JComponent) Design.getInstance().getBaseFrame().getContentPane(), 500);

            /*
            GamePlayerWindow.getInstance();
            GamePlayerWindow.getInstance().setOptionFrame(this);
            GamePlayerWindow.getInstance().getBaseFrame().setVisible(true);
             */
            optionFrame.setVisible(false);
        }

        if (e.getSource() == gamestartButton) {
           new Thread(){public void run(){
             try {
                GameEngineController game = new GameEngineController("test", GameEngineController.LOAD_MODE_REMOTE, null);
                AppGameContainer app = new AppGameContainer(game);
                te = new TestEngine(app); 
                te.getApp().start();
            } catch (SlickException ex) {
                LOG.error(ex);
            }
           }}.start();
                         
        }
        
        if (e.getSource() == gamestopButton){
            te.getApp().exit();
        }

    }

    public JFrame getOptionFrame() {
        return optionFrame;
    }

    public void setOptionFrame(JFrame optionFrame) {
        this.optionFrame = optionFrame;
    }
}
