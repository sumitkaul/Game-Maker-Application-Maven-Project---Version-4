package view;

import interfaces.Drawable;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;

import model.Resources;
import model.SpriteModel;
import utility.Constants;
import utility.ResizeHelper;
import utility.Util;
import view.communication.ClientHandler;

public class SpriteView implements Drawable {

	private BufferedImage image;
	private SpriteModel model;

	public SpriteView(SpriteModel model) {
	        try {
	        	URL url = SpriteView.class.getResource(model.getImageUrlString());
	            //image = ImageIO.read(url);
	        	image = (BufferedImage) getImageFromDb(model.getImageId());
	            
	            this.model = model;
	        } catch (Exception e) {
	            e.printStackTrace();
	            //log exception
	        }
	    }
    
	 @Override
	    public void draw(Graphics g) {
		 
		 Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			int x = (int) (model.getPosX() * ResizeHelper.getInstance().getxFactor());
			int y = (int) (model.getPosY() * ResizeHelper.getInstance().getyFactor());
			int width = (int) (model.getWidth() * ResizeHelper.getInstance().getxFactor());
			int height = (int) (model.getHeight() * ResizeHelper.getInstance().getyFactor());
			
			g2.rotate(Math.toRadians(model.getHeading()), x+width/ 2, y+height/ 2);
	        g2.drawImage(image,x, y,width,height,null);
	        g2.rotate(Math.toRadians(-model.getHeading()), x+width/ 2, y+height/ 2);
	    }

    @Override
    public boolean isVisible() {
        return model.isVisible();
    }
    
    public SpriteModel getModel() {
		return model;
	}

	public void setModel(SpriteModel model) {
		this.model = model;
	}

	@Override
	public String getLayer() {
		// TODO Auto-generated method stub
		return this.model.getLayer();
	}
	
	public Image getImageFromDb(int id){
		Resources resource = ClientHandler.loadResource(String.valueOf(id), Constants.HOST, 
				Constants.PATH+"/loadResource", new Exception[1]);
		Image image = Util.convertByteArraytoImage(resource.getResource(), "jpg");
		return image;
	}
	

}
