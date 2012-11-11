package view.imagePanel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import model.SpriteModel;
import utility.Constants;
import utility.Layers;
import view.Design;

public class ImageActionListener implements ActionListener {
	
	 private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ImageActionListener.class);

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton btn = (JButton) arg0.getSource();
		
		Design design = Design.getInstance();

        double sizeX;
        double sizeY;
        double speedX;
        double speedY;
        String layer = null;
        try {
            sizeX = Double.valueOf(design.getWidthTextField().getText());
            sizeY = Double.valueOf(design.getHeightTextField().getText());
            LOG.debug("got size values for customObject x:" + sizeX + " y:" + sizeY);

        } catch (Exception exception) {
            LOG.error("did not read in size values ... using defaults");
            sizeX = 30;
            sizeY = 30;
        }
        try {
            speedX = Double.valueOf(design.getVelocityXTextField().getText());
            speedY = Double.valueOf(design.getVelocityYTextField().getText());
            LOG.debug("got speed values for customObject x:" + speedX + " y:" + speedY);
        } catch (Exception exception) {
            LOG.error("did not read in speed values ... using defaults");
            speedX = 1;
            speedY = 1;
        }
        try {
            layer = design.getLayerBox().getSelectedItem().toString();
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
				image = ImageIO.read(ImageActionListener.class.getResource(btn.getName()));
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
            design.updateSpriteList(spriteModel);
            design.updateProperties();
            design.getFacade().addSpriteModelToView(spriteModel);
            design.getGamePanel().repaint();
        }

	}

	

}
