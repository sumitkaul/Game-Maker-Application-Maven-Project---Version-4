package team3.a9.imagewizard;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

class PreviewPanel extends JPanel {
	private Image image = null;
	private int width, heigth;

	public Dimension getMinimumSize()
	{ 
		return new Dimension((int)(this.width*0.7), (int)(this.heigth*0.7)); 
	}

	public Dimension getPreferredSize()
	{
		return getMinimumSize(); 
	}

	PreviewPanel(int width, int heigth) {
		this.width = width;
		this.heigth = heigth;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this.getWidth(),this.getHeight(), null);
		//g.drawImage(image,0,0,300,300,null);
		//g.drawString("BLAH", 20, 20);
		//g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-20);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}