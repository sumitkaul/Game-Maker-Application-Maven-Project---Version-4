package view;

import interfaces.Drawable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JPanel;
import model.SpriteModel;
import utility.Constants;
import utility.SpriteList;

/**
 * Under development...
 */
public class GamePanelForPlaying extends JPanel implements KeyListener {

    private static final org.apache.log4j.Logger LOG =
            org.apache.log4j.Logger.getLogger(SpriteList.class);
    private static final long serialVersionUID = 1L;
    private Image image;
    private Graphics graphic;
    private List<Drawable> drawables;
    private String currentLayer;

    public GamePanelForPlaying(int width, int height) {
        this.setSize(width, height);
        this.drawables = new ArrayList<Drawable>(10);
        this.currentLayer = Constants.ALL_LAYERS;
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);



        if (currentLayer.equalsIgnoreCase(Constants.ALL_LAYERS)) {
            for (Drawable d : drawables) {
                if (d.isVisible()) {
                    d.draw(g);
                }
            }
        } else {
            for (Drawable d : drawables) {
                if (d.isVisible() && d.getLayer().equalsIgnoreCase(getCurrentLayer())) {
                    d.draw(g);
                }
            }
        }
    }

    @Override
    public void update(Graphics g) {
        if (image == null) {
            image = createImage(this.getSize().width, this.getSize().height);
            graphic = image.getGraphics();
        }

        graphic.setColor(this.getBackground());
        graphic.fillRect(0, 0, this.getSize().width, this.getSize().height);
        graphic.setColor(this.getForeground());

        paint(graphic);
        g.drawImage(image, 0, 0, this);
    }

    public void unregisterModel(SpriteModel model) {
        ArrayList<Drawable> toBeRemoved = new ArrayList<Drawable>();
        for (Drawable drawable : drawables) {
            if (drawable instanceof SpriteView) {
                SpriteView view = (SpriteView) drawable;
                if (view.getModel().equals(model)) {
                    toBeRemoved.add(drawable);
                }
            }
        }

        for (Drawable drawable : toBeRemoved) {
            unregisterDrawable(drawable);
        }
    }

    public void registerDrawable(Drawable d) {
        this.drawables.add(d);
    }

    public void unregisterDrawable(Drawable d) {
        this.drawables.remove(d);
    }

    public void removeAllDrawables() {
        this.drawables.clear();
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        if (arg0.getKeyChar() == KeyEvent.VK_DELETE) {
            GameMakerView.getInstance().getActionEventPanel().removeSpriteModelFromList(SpriteList.getInstance().getSelectedSpriteModel());
            SpriteList.getInstance().removeSprite(SpriteList.getInstance().getSelectedSpriteModel());
            GameMakerView.getInstance().getGamePanel().repaint();

        }

    }

    public List<Drawable> getDrawables() {
        return drawables;
    }

    public void setDrawables(List<Drawable> drawables) {
        this.drawables = drawables;
    }

    public String getCurrentLayer() {
        return this.currentLayer;
    }

    public void setCurrentLayer(String currentLayer) {
        this.currentLayer = currentLayer;
    }

    public void reset() {
        Collection<SpriteModel> allSpriteModels = SpriteList.getInstance().getSpriteList();
        for (SpriteModel model : allSpriteModels) {
            unregisterModel(model);
        }
        removeAllDrawables();
        repaint();
    }
}
